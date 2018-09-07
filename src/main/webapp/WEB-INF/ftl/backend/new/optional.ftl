<@page isBackend=true >
<div class="col-sm-9 col-md-10" style="width: 95%; margin-left: 10px;"  >
    <ol class="breadcrumb header">
        <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
        <li>系统设置</li>
    </ol>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="icon glyphicon glyphicon-briefcase"></span>常规</div>
        <div class="panel-body">
            <#if success?? && success?string("true", "false") = 'true'>
                <div class="alert alert-success" style="padding: 10px 15px;">修改成功</div>
            </#if>
            <form action="" method="post" class="form-horizontal text-left" role="form">
                <div class="form-group <#if form.title?? && form.title != "">'has-error' <#else> </#if>">
                    <label class="col-sm-3" for="title">*站点标题</label>
                    <div class="col-sm-6">
                        <input placeholder="站点标题" name="title" class="form-control" type="text" value="${form.title}" />
                        <p class="help-block"><#if form1??>${form1.title!}</#if></p>
                    </div>
                </div>
                <div class="form-group <#if form.subtitle?? && form.subtitle!= "">'has-error'<#else></#if>">
                    <label class="col-sm-3" for="subtitle">*副标题</label>
                    <div class="col-sm-6">
                        <input placeholder="副标题" name="subtitle" class="form-control" type="text" value="${form.subtitle}" />
                    </div>
                </div>
                <div class="form-group <#if description?? && description!= "">'has-error'<#else></#if>">
                    <label class="col-sm-3" for="description">站点描述</label>
                    <div class="col-sm-6">
                        <input placeholder="站点描述" name="description" class="form-control" type="text" value="${form.description}" />
                        <p class="help-block"> <#if form1?? && form1.description?? && form1.description!= "">${form1.description!}<#else>用简洁的文字描述本站点。</#if></p>
                    </div>
                </div>
                <div class="form-group <#if form.keywords?? && form.keywords!= "">'has-error'<#else></#if>">
                    <label class="col-sm-3" for="keywords">站点keywords</label>
                    <div class="col-sm-6">
                        <input placeholder="keywords" name="keywords" class="form-control" type="text" value="${form.keywords}" />
                        <p class="help-block"><#if form1?? && form1.keywords?? && form1.keywords!= "">${form1.keywords!}<#else>填写本站的关键字。</#if></p>
                    </div>
                </div>
                <div class="form-group <#if form.weburl?? && form.weburl!= "">'has-error'<#else ></#if>">
                    <label class="col-sm-3" for="weburl">*站点地址（URL）</label>
                    <div class="col-sm-6">
                        <input placeholder="http://" name="weburl" class="form-control" type="text" value="${form.weburl!}">
                        <p class="help-block"><#if form1??>${form1.weburl!}</#if></p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3" for="email">电子邮件地址 </label>
                    <div class="col-sm-6">
                        <input class="form-control" type="text" name="email" />
                        <p class="help-block">这个电子邮件地址仅为了管理方便而索要，例如新注册用户通知。</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3">成员资格</label>
                    <div class="col-sm-6">
                        <div class="checkbox">
                            <label style="padding-left: 20px;" >
                                <input type="checkbox" name="enableReg" readonly="readonly">任何人都可以注册</label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3">新用户默认角色</label>
                    <div class="col-sm-6">
                        <select class="form-control" name="defaultUserRole">
                            <option>订阅者</option><option>编辑</option>
                            <option>作者</option><option>投稿者</option>
                            <option>管理员</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="padding-top: 20px;">
                    <div class="col-sm-offset-3 col-sm-2">
                        <button type="submit" class="btn btn-primary btn-block">保存更改</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</@page>