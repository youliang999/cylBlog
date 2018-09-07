<@page>
<div id="content" class="clearfix">
    <div id="left_col" class="clearfix">
        <#include "content.ftl"/>
        <div id="comments_wrapper">
            <div id="comment_header" class="clearfix">
                <span class="comments_right">评论 (${blog.ccount})</span>
            </div>
            <div id="comments">
                <div id="comment_area">
                        <#if commentVos?? && commentVos?size gt 0>
                            <#include "comments_list.ftl"/>
                        <#else >
                            <ol class="commentlist"><li id="nocomment" class="comment"><div class="comment-content"><p>暂无评论</p></div></li></ol>
                        </#if>
                </div>
                <#include "comments_form.ftl"/>
            </div>
        </div>
        <div id="previous_next_post">
            <div class="clearfix">
                <#if prev?? && prev.id??><p id="prev_post"><a href="${prev.id}">${prev.title}</a></p></#if>
                <#if next?? && next.id??><p id="next_post"><a href="${next.id}">${next.title}</a></p></#if>
            </div>
        </div>
    </div>
    <div id="post_mask"></div>
    <div id="right_col">
        <div id="introduction_widget" class="side_widget clearfix">
            <h3 class="headline">About</h3>
            <ul id="social_link">
                <li class="rss_button"><a target="_blank" href="${g.domain}/feed"></a></li>
                <li class="github_button"><a target="_blank" href="https://github.com/youliang999"></a></li>
            </ul>
            <#--<p>You can show your site introduction by using Site Introduction Widget.-->
                <#--You also can show Social Icon on upper part, and search form at bottom. </p>-->
            <div id="search_area" class="clearfix">
                <form method="get" action="${g.domain}">
                    <div class="search_input">
                        <input type="text" autocomplete="off" name="word" placeholder="搜索一下" />
                    </div>
                    <div class="search_button">
                        <input type="submit" value="Search" />
                    </div>
                </form>
            </div>
        </div>
        <#include "index/common/recent.ftl"/>
        <#include "index/common/tagcloud.ftl"/>
        <#include "index/common/archive.ftl"/>
        <#include "index/common/link.ftl"/>
    </div>
    <#include "index/common/footer.ftl"/>
</div>
</@page>
