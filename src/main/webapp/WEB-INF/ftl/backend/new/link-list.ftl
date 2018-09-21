<#assign assets=[
"/resource/js/backend/admin.link.js"
]/>
<@page isBackend=true assets=assets>
    <div class="col-sm-9 col-md-10 bdiv">
    <ol class="breadcrumb header">
        <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
        <li class="active">链接</li>
    </ol>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="icon glyphicon glyphicon-list"></span>链接列表</div>
        <div class="panel-body">
            <table id="post-table" class="table table-striped list-table">
                <thead><tr>
                    <th>名称</th>
                    <th>站点</th>
                    <th>可见性</th>
                    <th>创建时间</th>
                    <th class="center">操作</th>
                </tr></thead>
                <tbody>
                <#list links as link>
                    <tr><td>${link.name!}</td><td><a target="_blank" href='${link.url}'>${link.url!}</a></td>
                        <td><#if link.visible?string("1", "0") == "1">是<#else>否</#if></td><td>${link.createTime?string("yyyy-MM-dd")!}</td>
                        <td class="center"><span class="icon glyphicon glyphicon-pencil pointer" onclick="zblog.link.editCate('${link.id!}')"></span>
                            <span class="glyphicon glyphicon-trash pointer" onclick="zblog.link.remove('${link.id!}')"></span></td></tr>
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
                            title: "全部链接",
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