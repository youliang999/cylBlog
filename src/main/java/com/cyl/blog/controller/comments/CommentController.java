package com.cyl.blog.controller.comments;


import com.cyl.blog.constants.OptionConstants;
import com.cyl.blog.constants.PostConstants;
import com.cyl.blog.controller.comments.validataor.CommentValidator;
import com.cyl.blog.entity.Blog;
import com.cyl.blog.entity.Comment;
import com.cyl.blog.service.BlogService;
import com.cyl.blog.service.CommentService;
import com.cyl.blog.service.OptionsService;
import com.cyl.blog.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/comments")
public class CommentController {
  private static final Logger log = LoggerFactory.getLogger(CommentController.class);
  @Autowired
  private CommentService commentService;
  @Autowired
  private BlogService blogService;
  @Autowired
  private OptionsService optionsService;

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object post(Comment comment,
                     HttpServletRequest request, HttpServletResponse response) {
    CookieUtil cookieUtil = new CookieUtil(request, response);
    log.info("===>>> {} creator:{}", comment, comment.getCreator());
    if (StringUtils.isBlank(comment.getCreator())) {
      comment.setCreator(cookieUtil.getCookie("comment_author"));
      comment.setUrl(cookieUtil.getCookie("comment_author_url", false));
      comment.setEmail(cookieUtil.getCookie("comment_author_email", false));
    }
    log.info("===>>> {}", comment );
    Map<String, Object> form = CommentValidator.validate(comment);
    if (!form.isEmpty()) {
      form.put("success", false);
      return form;
    }

    if (!"true".equals(optionsService.getOptionValue(OptionConstants.ALLOW_COMMENT))){
      form.put("success", false);
      form.put("msg", "当前禁止评论");
      return form;
    }
    Blog blog = blogService.loadById(comment.getPostid());
    if(blog == null || PostConstants.COMMENT_CLOSE.equals(blog.getCstatus())){
      form.put("success", false);
      form.put("msg", "当前禁止评论");
      return form;
    }

    if(StringUtils.isBlank(comment.getParent())){
      comment.setParent(null);
    }

    /* 根据RFC-2109中的规定，在Cookie中只能包含ASCII的编码 */
    cookieUtil.setCookie("comment_author", comment.getCreator(), "/", false, 365 * 24 * 3600, true);
    cookieUtil.setCookie("comment_author_email", comment.getEmail(), "/", false, 365 * 24 * 3600, false);
    cookieUtil.setCookie("comment_author_url", comment.getUrl(), "/", false, 365 * 24 * 3600, false);

    comment.setId(IdGenerator.uuid19());
    comment.setIp(ServletUtils.getIp(request));
    comment.setAgent(request.getHeader("User-Agent"));
    comment.setCreateTime(new Date());
    comment.setLastUpdate(comment.getCreateTime());
    /* 使用jsoup来对帖子内容进行过滤 */
    String content = HtmlUtils.htmlUnescape(comment.getContent());
    comment.setContent(JsoupUtils.simpleText(content));
    commentService.insert(comment);
    form.put("success", true);
    return form;
  }

}
