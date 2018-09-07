
<#assign depth = 0>
<ol class="commentlist">
    <#list commentVos as commentVo>
        <#if depth?? || depth == 0 >
            <li class="comment <#if commentVo_index%2==0>even_comment<#else>odd_comment</#if>}" id="comment-${commentVo.id}">
            </>
        <#else>
            <li class="comment <#if commentVo_index%2 == depth%2>even_comment<#else>odd_comment</#if>" id="comment-${commentVo.id}">
        </#if>

        <div class="comment-meta">
            <img class="avatar" width="35" height="35" src="${g.domain}/resource/img/avatar.png" />
            <ul class="comment-name-date">
                <li class="comment-name"><a class="url" target="_blank" href="${commentVo.url}">${commentVo.creator}</a></li>
                <li class="comment-date">${commentVo.createTime?string("yyyy/MM/dd hh:mma")}</li>
            </ul>
            <#if commentVo.status=='approve'><div class="comment-reply"><a href='#respond'>回复</a></div></#if>
        </div>
        <div class="comment-content">
            <#if commentVo.status=='wait'>
                <span class="comment-note">你的评论正在等待审核。。。</span>
            </#if>
            <p>${commentVo.content}</p>
        </div>
        <#if commentVo.children?? && commentVo.children?size gt 0>
            <#assign commentVos = commentVo.children />
            <#include "comments_list.ftl" />
        </#if>
    </#list>
</ol>



<#--<#assign depth=0/>-->
<#--<ol class="commentlist">-->
    <#--<#list commentVos as comment>-->
            <#--<#if depth?? || depth == 0>-->
                <#--<li class="comment <#if comment_index%2==0>even_comment<#else>odd_comment</#if>}" id="comment-${comment.id}">-->
            <#--<#else>-->
                 <#--<#if parent??><#assign parentV =0><#else><#assign parentV =1></#if>/>-->
                <#--<li class="comment <#if parentV == depth%2>even_comment<#else>odd_comment</#if>" id="comment-${comment.id}">-->
            <#--</#if>-->
        <#--<div class="comment-meta">-->
            <#--<img class="avatar" width="35" height="35" src="${g.domain}/resource/img/avatar.png" />-->
            <#--<ul class="comment-name-date">-->
                <#--<li class="comment-name"><a class="url" target="_blank" href="${comment.url}">${comment.creator}</a></li>-->
                <#--<li class="comment-date">"${comment.createTime?string("yyyy-MM-dd HH:mm:ss")}" </li>-->
            <#--</ul>-->
            <#--<#if comment.status=='approve'><div class="comment-reply"><a href='#respond'>回复</a></div></#if>-->
        <#--</div>-->
        <#--<div class="comment-content">-->
            <#--<#if comment.status=='wait'>-->
                <#--<span class="comment-note">你的评论正在等待审核。。。</span>-->
            <#--</#if>-->
            <#--<p>${comment.content}</p>-->
        <#--</div>-->
        <#--<#if comment.children?? && comment.children?size gt 0>-->
            <#--<#assign depth=depth+1/>-->
            <#--<#assign parent = comment_index%2==0 />-->
            <#--<#assign comments=comment.children/>-->
            <#---->
            <#--<#include "comments_list.ftl" />-->
            <#--<#assign depth=depth-1/>-->
        <#--</#if>-->
        <#--</li>-->
    <#--</#list>-->
<#--</ol>-->
