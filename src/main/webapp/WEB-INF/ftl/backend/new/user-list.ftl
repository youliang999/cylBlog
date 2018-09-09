<#assign assets=[
"/resource/js/backend/admin.user.js"
]/>
<@page isBackend=true assets=assets>
    <div class="col-sm-9 col-md-10">
    <ol class="breadcrumb header">
        <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
        <li class="active">用户</li>
    </ol>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="icon glyphicon glyphicon-list"></span>用户列表</div>
        <div class="panel-body">
            <table id="post-table" class="table table-striped list-table">
                <thead><tr>
                    <th>用户名</th>
                    <th>姓名</th>
                    <th>电子邮件</th>
                    <th>角色</th>
                    <th>文章</th>
                    <th>创建日期</th>
                    <th class="center">操作</th>
                </tr></thead>
                <tbody>
                <#list users as user>
                    <tr><td>${user.nickName!}</td><td>${user.realName!}</td>
                        <td><a href="mailto:${user.email}">${user.email!}</a></td><td>${user.role!}</td><td>...</td>
                        <td>${user.createTime?string("yyyy-MM-dd")!}</td>
                        <td class="center"><span class="icon glyphicon glyphicon-pencil pointer" onclick="zblog.user.edit('${user.id!}')"></span>
                            <#if currentuser?? && currentuser != user.nickName>
                                <span class="glyphicon glyphicon-trash pointer" onclick="zblog.user.remove('${user.id!}')"></span>
                            </#if>
                        </td></tr>
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