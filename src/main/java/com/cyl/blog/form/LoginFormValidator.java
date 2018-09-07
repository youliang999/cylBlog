package com.cyl.blog.form;

import com.cyl.blog.util.CommRegular;
import com.google.gson.Gson;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class LoginFormValidator {
  private static String guard;
  private static final Logger LOG = LoggerFactory.getLogger(LoginFormValidator.class);

  /**
   * 设置防止恶意登录token,由spring注入
   *
   * @param guard
   */
  public static void setLoginGuard(String guard){
    LoginFormValidator.guard = guard;
  }

  public static Map<String, String> validateLogin(LoginForm form){
    guard = "guard";
    form.setGuard("guard");
    LOG.info("===>>>guard:{}   input form:{}", guard, new Gson().toJson(form));

	  Map<String, String> result = new HashMap<>();
    /* 防止用户恶意登录 */
    if(!guard.equals(form.getGuard())){
      result.put("msg", "请不要尝试登录了!");
    }else if(StringUtil.isBlank(form.getUsername()) || !form.getUsername().matches(CommRegular.USERNAME)){
      result.put("msg", "请输入正确的用户名");
    }else if(StringUtil.isBlank(form.getPassword()) || !form.getPassword().matches(CommRegular.PASSWD)){
      result.put("msg", "密码输入有误");
    }

    return result;
  }

}
