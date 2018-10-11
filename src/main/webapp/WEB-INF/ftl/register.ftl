<@page isRegister=true>
<div class="col-sm-9 col-md-10">
    <ol class="breadcrumb header">
        <#--<li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>-->
        <#--<li>用户</li>-->
        <li class="active">注册</li>
    </ol>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="icon glyphicon glyphicon-briefcase"></span>注册</div>
        <div class="panel-body">
            <form id="reg-form" action="${g.domain}/register/submit" method="post" class="form-horizontal text-left" role="form">
                <#if user?? >
                    <#--<input type="hidden" name="_method" value="PUT" />-->
                    <input type="hidden" name="id" value="${user.id!}" />
                </#if>
                <div class="form-group <#if form?? && form.nickName != "">has-error</#if>">
                    <label class="col-sm-3" for="title">*用户名</label>
                    <div class="col-sm-6">
                        <input class="form-control" type="text" id="nickName" name="nickName" onblur="hideNickError();" value="<#if user?? >${user.nickName!}</#if>" <#if user??>readonly</#if>/>
                        <p id="nickerr" class="help-block"><#if form?? >${form.nickName!}</#if></p>
                    </div>
                </div>
                <div class="form-group <#if form?? && form.email!="">has-error</#if>">
                    <label class="col-sm-3" for="email">*电子邮件</label>
                    <div class="col-sm-6">
                        <input class="form-control" type="text" name="email" onblur="hideEmailError()" value="<#if user?? >${user.email!}</#if>" />
                        <p id="emailerr" class="help-block"><#if form??>${form.email!}</#if></p>
                    </div>
                </div>
                <div class="form-group <#if form?? && form.realName!="">has-error</#if>">
                    <label class="col-sm-3" for="realName">真实名称</label>
                    <div class="col-sm-6">
                        <input class="form-control" autocomplete="off" type="text" onblur="hideRealErr()" name="realName" value="<#if user?? >${user.realName!}</#if>" />
                        <p id="realerr" class="help-block"><#if form??>${form.realName!}</#if></p>
                    </div>
                </div>
                <div class="form-group <#if form?? && form.password!="">has-error</#if>">
                    <label class="col-sm-3" for="password">*密码 </label>
                    <div class="col-sm-6">
                        <input class="form-control input-sm" type="password" name="password" onblur="hidePwErr()"/>
                        <#if user??><p class="help-block">如果您想修改您的密码，请在此输入新密码。不然请留空</p></#if>
                        <input style="margin-top: 5px;" class="form-control input-sm" type="password" name="repass"  onblur="hidePwErr()" />
                        <p id="passwderr" class="help-block"><#if form?? && form.password !="">${form.password!} <#else >密码长度6-16 </#if></p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3">角色</label>
                    <div class="col-sm-6">
                        <select class="form-control" name="role">
                            <option value="contributor" <#if user?? && user.role =='contributor'>selected = 'selected'</#if>>投稿者</option>
                            <#--<option value="editor" <#if user?? && user.role=='editor'>selected = 'selected'</#if>>编辑</option>-->
                            <#--<option value="admin" <#if user?? && user.role=='admin'>selected = 'selected'</#if>>管理员</option>-->
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3" for="description">备注</label>
                    <div class="col-sm-6">
                        <textarea rows="3" class="form-control" name="description"><#if user?? >${user.description!}</#if></textarea>
                    </div>
                </div>
                <div class="form-group" style="padding-top: 20px;">
                    <div class="col-sm-offset-3 col-sm-2">
                        <button id="reg-btn" type="button" class="btn btn-primary btn-block" ">注册</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script  type="text/javascript">
    $("#reg-btn").click(function() {
        $("#reg-form").ajaxSubmit(
                {
                    dataType: 'json',
                    success: function (data) {
                        if(data.success == 0) {
                            alert(data.msg);
                            window.location.href="/backend/login";
                        } else {
                            if(data.form != null) {
                                $("#nickerr").text(data.form.nickName);
                                $("#emailerr").text(data.form.email);
                                $("#realerr").text(data.form.realName);
                                $("#passwderr").text(data.form.password);
                            } else {
                                alert(data.msg);
                            }
                        }

                    }
                });
    });

    function hideNickError() {
        $("#nickerr").hide();
    }
    function hideEmailError() {
        $("#emailerr").hide();
    }
    function hideRealErr() {
        $("#realerr").hide();
    }
    function hidePwErr() {
        $("#passwderr").hide();
    }
</script>
</@page>
