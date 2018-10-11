<@page isBackend=true >
    <div class="col-sm-9 col-md-10 bdiv" >
    <h3 class="page-header header">${currentUser.nickName!}<small>Welcome to Cblog</small></h3>
    <div class="row">
        <div class="col-sm-3 col-md-3">
            <div class="databox">
                <div class="sybomol sybomol_terques"><i class="glyphicon glyphicon-user"></i></div>
                <div class="value">${userCount!}<p>用户</p></div>
            </div>
        </div>
        <div class="col-sm-3 col-md-3">
            <div class="databox">
                <div class="sybomol sybomol_red"><i class="glyphicon glyphicon-pencil"></i></div>
                <div class="value">${postCount!}<p>文章</p></div>
            </div>
        </div>
        <div class="col-sm-3 col-md-3">
            <div class="databox">
                <div class="sybomol sybomol_yellow"><i class="glyphicon glyphicon-comment"></i></div>
                <div class="value">${commentCount!}<p>评论</p></div>
            </div>
        </div>
        <div class="col-sm-3 col-md-3">
            <div class="databox">
                <div class="sybomol sybomol_blue"><i class="glyphicon glyphicon-download-alt"></i></div>
                <div class="value">${uploadCount!}<p>附件</p></div>
            </div>
        </div>
    </div>
    <div class="row" style="padding-top: 15px; ">
        <div class="col-sm-6 col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading"><span class="icon glyphicon glyphicon-certificate"></span>系统信息</div>
                <div class="panel-body">
                    <ul class="list-unstyled ul-group">
                        <#if osInfo??>
                            <li>操作系统: ${osInfo.osName!}&nbsp;${osInfo.osVersion!}</li>
                            <li>服务器: ${osInfo.serverInfo!}</li>
                            <li>Java环境: Java ${osInfo.javaVersion!}</li>
                            <li>系统内存: ${osInfo.totalMemory!}M</li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-6" style="float: right;">
            <div class="panel panel-default">
                <div class="panel-heading"><span class="icon glyphicon glyphicon-filter"></span>最近发表</div>
                <div class="list-group">
                    <#if posts??>
                        <#list posts as post>
                            <a class="list-group-item" href="${g.domain}/blog/${post.id!}" target="_blank">
                                <span class="badge">${post.rcount!}</span>
                                <h4 class="list-group-item-heading">${post.title!}</h4>
                                <p>${post.createDate!}</p>
                            </a>
                        </#list>
                    </#if>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading"><span class="icon glyphicon glyphicon-comment"></span>近期留言</div>
                <ul class="list-group">
                    <#if comments??>
                        <#list comments as comment>
                            <li class="list-group-item">
                                <span class="badge">${comment.createDate!}</span>
                                <a href="${g.domain}/blog/${comment.postid!}/#comment-${comment.id!}" target="_blank">${comment.content!}</a>
                            </li>
                        </#list>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
</div>
</@page>
