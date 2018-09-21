<#assign assets=[
"/resource/js/backend/admin.comment.js"
]/>
<@page isBackend=true assets=assets>
    <div class="col-sm-9 col-md-10 bdiv">
    <ol class="breadcrumb header">
        <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
        <li class="active">留言</li>
    </ol>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="icon glyphicon glyphicon-list"></span>留言列表</div>
        <div class="panel-body">
            <ul class="table-nav">
                <li><a class="<#if type=='all'>active</#if>" href="?type=all">全部</a> | </li>
                <li><a class="<#if type=='wait'>active</#if>" href="?type=wait">待审
                    <#if stat.wait??><span>(${stat.wait!})</span></#if> | </a>
                </li>
                <li><a class="<#if type=='approve'>active</#if>" href="?type=approve">获准
                    <#if stat.approve??><span>(${stat.approve!})</span></#if> | </a>
                </li>
                <li><a class="<#if type=='trash'>active</#if>" href="?type=trash">垃圾评论
                    <#if stat.trash??>span>(${stat.trash!})</span></#if></a>
                </li>
            </ul>
            <table id="post-table" class="table table-striped list-table">
                <thead><tr>
                    <th style="width: 20%;">作者</th>
                    <th>评论</th>
                    <th style="width: 15%;">回应给</th>
                    <th class="center">操作</th>
                </tr></thead>
                <tbody>
                <#list comments as comment>
                    <tr><td><strong><img class="avatar" src='${g.domain}/resource/img/avatar.png' width="32" height="32" />
                    ${comment.creator!}</strong><br/>
                        <a href="${comment.url!}">(${comment.url!})</a><br/>
                        <a href="mailto:${comment.email}">${comment.email!}</a></br/>
                        <a href="#">${comment.ip!}</a></td>
                        <td><div>提交于<a href="${g.domain}/blog/${comment.postid!}/#comment-${comment.id!}" target="_blank">
                            ${comment.createTime?string("yyyy-MM-dd ahh:mm")!}</a>
                            <#if comment.pid??>| 回复给
                                <a href="${g.domain}/blog/${comment.postid!}/#comment-${comment.pid!}" target="_blank">${comment.pcreator!}</a>
                            </#if>
                        </div>
                            <p style="margin: 7px 0;">${comment.content!}</p>
                            <div class="row-action">
                                <#if type=='all'>
                                    <span><a href="#" onclick="zblog.comment.approve('${comment.id!}','<#if comment.status=='wait'>approve<#else>wait</#if>');"><#if comment.status=='wait'>批准<#else >驳回</#if></a>&nbsp;|&nbsp;</span>
                                    <span><a href="#" onclick="zblog.comment.approve('${comment.id!}','reject');">垃圾评论</a>&nbsp;|&nbsp;</span>
                                    <span><a href="#" onclick="zblog.comment.approve('${comment.id!}','trash');">移动到回收站</a></span>
                                </#if>
                                <#if type=='wait'>
                                    <span><a href="#" onclick="zblog.comment.approve('${comment.id!}','approve');">批准</a>&nbsp;|&nbsp;</span>
                                    <span><a href="#" onclick="zblog.comment.approve('${comment.id!}','wait');">驳回</a>&nbsp;|&nbsp;</span>
                                    <span><a href="#" onclick="zblog.comment.approve('${comment.id!}','reject');">垃圾评论</a>&nbsp;|&nbsp;</span>
                                    <span><a href="#" onclick="zblog.comment.approve('${comment.id!}','trash');">移动到回收站</a></span>
                                </#if>
                                <#if type=='approve'>
                                    <span><a href="#" onclick="zblog.comment.approve('${comment.id!}','wait');">驳回</a>&nbsp;|&nbsp;</span>
                                    <span><a href="#" onclick="zblog.comment.approve('${comment.id!}','reject');">垃圾评论</a>&nbsp;|&nbsp;</span>
                                    <span><a href="#" onclick="zblog.comment.approve('${comment.id!}','trash');">移动到回收站</a></span>
                                </#if>
                                <#if type=='reject'>
                                    <span><a href="#" onclick="zblog.comment.approve('${comment.id!}','approve');">批准</a>&nbsp;|&nbsp;</span>
                                    <span><a href="#" onclick="zblog.comment.approve('${comment.id!}','wait');">还原</a>&nbsp;|&nbsp;</span>
                                </#if>
                            </div>
                        </td>
                        <td><a href="${g.domain}/blog/${comment.postid!}" target="_blank">${comment.title!}</a><br /></td>
                        <td class="center"><span class="glyphicon glyphicon-trash pointer" onclick="zblog.comment.remove('${comment.id!}')"></span></td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <div class="row-fulid">
                <div class="col-sm-6 col-md-6">
                    <div class="page-info">第 ${currentPage!}页, 共 ${totalPages!}页, ${totalCount!} 项</div>
                </div>
                <div id="page_nav">
                <#--<div class="clearfix page">-->
                    <div class="zxf_pagediv"></div>
                    <script type="text/javascript">
                        $(".zxf_pagediv").createPage({
                            pageNum: ${totalPages!0},
                            current: ${currentPage!0},
                            urlPrefix: "${domain}",
                            isBackend: true,
                            title: "评论",
                            backfun: function(e) {
                                //console.log(e);//回调
                            }
                        });
                    </script>
                <#--</div>-->
                </div>
                <div class="col-sm-6 col-md-6">
                </div>
            </div>
        </div>
    </div>

</div>
</@page>