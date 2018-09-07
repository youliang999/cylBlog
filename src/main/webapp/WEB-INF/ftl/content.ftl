
<#if blog.type=='post'>
    <div id="bread_crumb">
        <ul class="clearfix">
            <li><a title="首页" href="${g.domain}">首页</a></li>
            <li><a href="${g.domain}/categorys/${blog.category.name}">${blog.category.name}</a></li>
            <li class="last">${blog.title}</li>
        </ul>
    </div>
</#if>
<div class="post_wrap clearfix">
    <div class="post">
        <h3 class="title"><span>${blog.title}</span></h3>
        <div class="post_content">
            <#if !sf.isUser && blog.pstatus== 0>
                由于作者设置了权限，你没法阅读此文，请与作者联系
            <#else >
                ${blog.content}
                <div style="margin-top: 15px; font-style: italic;">
                    <p style="margin:0;"><strong>原创文章，转载请注明：</strong>转载自<a href="${g.domain}">${g.title} – youliang.cheng</a></p>
                </div>
            </#if>
        </div>
    </div>
    <div class="meta">
        <ul>
            <li class="post_date clearfix">
                <span class="date">${blog.createTime?string("dd")}</span>
                <span class="month">${blog.createTime?string("MMM")}</span>
                <span class="year">${blog.createTime?string("yyyy")}</span>
            </li>
            <li class="post_date">
                <span class="year">${blog.createTime?string("HH:mm:ss")}</span>
            </li>
            <li class="post_read">${blog.rcount}人阅读</li>
            <#if blog.type=='post'>
                <#--<li class="post_category"><a href="${g.domain}/categorys/${blog.category.name}">${blog.category.name}</a></li>-->
            </#if>
            <li class="post_author">
                <a title="由${blog.user.nickName}发布" href="#">${blog.user.nickName}</a>
            </li>
            <li class="post_comment">
                <a title="${blog.title}" href="#respond">发表评论</a>
            </li>
        </ul>
    </div>
</div>
