<#assign assets=[
"/resource/js/backend/admin.upload.js"
]/>
<@page isBackend=true assets=assets>
    <div class="col-sm-9 col-md-10 bdiv">
    <ol class="breadcrumb header">
        <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
        <li class="active">多媒体</li>
    </ol>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="icon glyphicon glyphicon-list"></span>媒体库</div>
        <div class="panel-body">
            <table id="upload-table" class="table table-striped list-table">
                <thead><tr>
                    <th style="width: 80px;">文件</th>
                    <th style="width: 30%"></th>
                    <th>作者</th>
                    <th>上传至</th>
                    <th>日期</th>
                    <th class="center">操作</th>
                </tr></thead>
                <tbody>
                <#if uploads??>
                    <#list uploads as upload>
                    <tr>
                        <td>
                            <img src="${g.domain}/resource/img/fileIcon/${upload.suffix!"image"}.jpg" width="80" height="50" />
                        </td>
                        <td class="filename"><strong>${upload.name!}</strong>
                            <#--<p class="fileformat">${upload.fileExt!}</p>-->
                            <div class="row-action">
                                <span><a href="javascript:zblog.upload.remove('${upload.id}');">永久删除</a>&nbsp;|&nbsp;</span>
                                <span><a href="${g.domain}/${upload.path!}">查看</a></span>
                            </div></td>
                        <td>${upload.user.nickName!}</td><td><a href='<#if upload.post?? >${g.domain}/posts/${upload.post.id!}</#if>' target="_blank"><#if upload.post?? >${upload.post.title!}</#if></a></td>
                        <td>${upload.createTime?string("yyyy-MM-dd")!}</td>
                        <td class="center">
                            <span class="glyphicon glyphicon-trash pointer" onclick="zblog.upload.remove('${upload.id!}');"></span></td></tr>
                    </#list>
                </#if>
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
                            title: "媒体库",
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