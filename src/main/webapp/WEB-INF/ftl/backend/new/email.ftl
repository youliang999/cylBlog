<@page isBackend=true >
<div class="col-sm-9 col-md-10 bdiv" >
    <ol class="breadcrumb header">
        <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
        <li>系统设置</li>
    </ol>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="icon glyphicon glyphicon-briefcase"></span>邮件服务器</div>
        <div class="panel-body">
            <#if success?? && success?string("true", "false") = 'true'>
                <div class="alert alert-success" style="padding: 10px 15px;">修改成功</div>
            </#if>
            <form action="" method="post" class="form-horizontal text-left" role="form">
                <div class="form-group <#if form?? && form.host?? && form.host!="">'has-error'</#if>">
                    <label class="col-sm-3" for="host">*主机Host</label>
                    <div class="col-sm-6">
                        <input placeholder="host" name="host" class="form-control" type="text" value="<#if form??>${form.host!}</#if>" />
                        <p class="help-block"><#if form1??>${form1.host!}</#if></p>
                    </div>
                </div>
                <div class="form-group <#if form?? && form.port?? && form.port gt 0>'has-error'</#if>">
                    <label class="col-sm-3" for="port">*端口</label>
                    <div class="col-sm-6">
                        <input name="port" class="form-control" type="text" value="<#if form??>${form.port!}</#if>" />
                    </div>
                </div>
                <div class="form-group <#if form?? && form.username?? && form.username!="">'has-error'</#if>">
                    <label class="col-sm-3" for="username">*用户名</label>
                    <div class="col-sm-6">
                        <input name="username" class="form-control" type="text" value="<#if form??>${form.username!}</#if>" />
                        <p class="help-block"><#if form1??>${form1.username!}</#if></p>
                    </div>
                </div>
                <div class="form-group <#if form?? && form.password?? && form.password!="">'has-error'</#if>">
                    <label class="col-sm-3" for="password">*密码</label>
                    <div class="col-sm-6">
                        <input name="password" class="form-control" type="text" value="<#if form??>${form.password!}</#if>" />
                        <p class="help-block"><#if form1??>${form1.password!}</#if></p>
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