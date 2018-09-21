<#assign assets=[
]/>
<@page isBackend=true assets=assets>
    <div class="col-sm-9 col-md-10 bdiv">
    <ol class="breadcrumb header">
        <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
        <li>链接</li>
        <li class="active">编辑链接</li>
    </ol>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="icon glyphicon glyphicon-wrench"></span>内容</div>
        <div class="panel-body">
            <form action="." method="post" class="form-horizontal text-left" role="form">
                <#if link??>
                    <input type="hidden" name="_method" value="PUT" />
                    <input type="hidden" name="id" value="${link.id!}" />
                </#if>
                <div class="form-group <#if form?? &&  form.name!=null>has-error</#if>">
                    <label class="col-sm-3" for="maxshow">*站点名称</label>
                    <div class="col-sm-6">
                        <input class="form-control" autocomplete="off" type="text" name="name" value="<#if link??>${link.name!}</#if>" />
                        <p class="help-block"><#if form?? && form.name!="">${form.name!}<#else>例如：好用的博客软件</#if></p>
                    </div>
                </div>
                <div class="form-group <#if form?? && form.url!="">has-error</#if>">
                    <label class="col-sm-3" for="defaultType">*站点url</label>
                    <div class="col-sm-6">
                        <input class="form-control" autocomplete="off" type="text" name="url" value="<#if link??>${link.url!}</#if>" placeholder="http://" />
                        <p class="help-block"><#if form?? && form.url!="">${form.url!}<#else>不要忘了http://</#if></p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3" for="defaultType">备注</label>
                    <div class="col-sm-6">
                        <textarea name="notes" rows="3" class="form-control"><#if link??>${link.notes!}</#if></textarea>
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