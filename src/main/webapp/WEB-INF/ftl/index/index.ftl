<@page >
</div>
    <div id="content" class="clearfix">
    <div id="left_col" class="clearfix">
            <#if (category?? && category?length gt 0) ||
                 (tag?? && tag?length gt 0) ||
                 (archive?? && archive?length gt 0) ||
                 (search?? && search?length gt 0)>
                <div id="archive_headline">
                    <h2>
                        <#if category?? && category?length gt 0 >'<span>${category}</span>' 目录归档</#if>
                        <#if tag?? && tag?length gt 0> 标签 '<span>${tag}</span>'</#if>
                        <#if search?? && search?length gt 0>
                            搜索结果 [ '<span>${search}</span>' ] - ${totalCount!}条
                        <#else >
                            <#if search?? && search?length gt 0>
                                归档 '<span>${archive}</span>'
                            </#if>
                        </#if>
                    </h2>
                </div>
            </#if>
        <#list pages as post>
            <div class="post_wrap clearfix">
                <div class="post">
                    <h3 class="title"><a href="${g.domain}/blog/${post.id}">

                        <#if post.pstatus== 0>私密:</#if>
                    ${post.title}</a></h3>
                    <div class="post_content">
                        <p class="excerpt">${post.excerpt}...</p>
                        <p><a class="more_link" href="${g.domain}/blog/${post.id}">阅读全文</a></p>
                    </div>
                    <#if post.tags?? && post.tags?size gt 0>
                        <div class="post_tags clearfix">
                            <ul class="clearfix">
                                <li></li>
                                <#list post.tags as tag>
                                    <li><a href="${g.domain}/tags/${tag}">${tag}</a></li>
                                </#list>
                            </ul>
                        </div>
                    </#if>
                </div>
                <div class="meta">
                    <ul>
                        <li class="post_date clearfix">
                            <span class="date">${post.createTime?string("dd")}</span>
                            <span class="month">${post.createTime?string("MMM")}</span>
                            <span class="year">${post.createTime?string("yyyy")}</span>
                        </li>
                        <li class="post_date">
                            <span class="year">${post.createTime?string("HH:mm:ss")}</span>
                        </li>
                        <li class="post_read">${post.rcount}人阅读</li>
                        <li class="post_author">
                            <a rel="author" title="由${post.user.nickName!"用户"}发布" href="${g.domain}/authors/${post.user.nickName}">${post.user.nickName}</a>
                        </li>
                        <li class="post_comment">
                            <a title="${post.title}的评论" href="${g.domain}/posts/${post.id}/#respond">
                                <#if post.ccount gt 0>
                                    ${post.ccount}条评论
                                <#else >
                                    发表评论
                                </#if>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </#list>
        <#--分页-->
        <div id="page_nav">
            <#--<div class="clearfix page">-->
                <div class="zxf_pagediv"></div>
                <script type="text/javascript">
                    $(".zxf_pagediv").createPage({
                        pageNum: ${totalPages!0},
                        current: ${currentPage!0},
                        urlPrefix: "${domain}",
                        backfun: function(e) {
                            //console.log(e);//回调
                        }
                    });
                </script>
            <#--</div>-->
        </div>
    </div>
    <div id="post_mask"></div>
    <div id="right_col">
        <div id="introduction_widget" class="side_widget clearfix">
            <h3 class="headline">About</h3>
            <ul id="social_link">
                <li class="rss_button"><a target="_blank" href="${g.domain}/feed"></a></li>
                <li class="github_button"><a target="_blank" href=""></a></li>
            </ul>
            <#--<p>You can show your site introduction by using Site Introduction Widget.-->
                <#--You also can show Social Icon on upper part, and search form at bottom. </p>-->
            <#include "../common/search.ftl"/>
        </div>
        <#include "common/recent.ftl"/>
        <#include "common/tagcloud.ftl"/>
        <#include "common/archive.ftl"/>
        <#include "common/link.ftl"/>
    </div>
    <#include "common/footer.ftl"/>
</div>
</@page>

