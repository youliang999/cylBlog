<@page isBackend=true >
<div class="col-sm-9 col-md-10 bdiv" xmlns="http://www.w3.org/1999/html">
    <ol class="breadcrumb header">
        <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
        <li>系统设置</li>
    </ol>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="icon glyphicon glyphicon-wrench"></span>撰写/阅读</div>
        <div class="panel-body">
            <#if success?? && success?string("true", "false") = 'true'>
                <div class="alert alert-success" style="padding: 10px 15px;">修改成功</div>
            </#if>
            <form action="post" method="post" class="form-horizontal text-left" role="form">
                <div class="form-group <#if form.maxshow?? && form.maxshow gt 0>'has-error'<#else ></#if>">
                    <label class="col-sm-3" for="maxshow">*博客页面至多显示(文章数/页)</label>
                    <div class="col-sm-6">
                        <input class="form-control" type="number" name="maxshow" value="${form.maxshow!}" />
                        <p class="help-block">${form.maxshow!}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3">评论设置</label>
                    <div class="col-sm-6">
                        <div class="checkbox">
                            <label style="padding-left: 20px;" ><input type="checkbox" name="allowComment" <#if form.allowComment?string == 'true'>'checked'</#if> />允许他人在新文章上发表评论 </label>
                        </div>
                    </div>
                </div>
                <div class="form-group <#if form.defaultCategory?? && form.defaultCategory!="">'has-error'<#else ></#if>">
                    <label class="col-sm-3" for="defaultType">*默认文章分类目录</label>
                    <div class="col-sm-6">
                        <select class="form-control" name="defaultCategory">
                            <#if !form.defaultCategory?? || form.defaultCategory=="">
                                <option>请选择</option>
                            </#if>
                            <#list  categorys as category>
                                <option value="${category.id}" <#if form.defaultCategory?? && form.defaultCategory==category.id>selected = 'selected'</#if>>
                                    <#--<#if &lt;#&ndash;category.level&ndash;&gt;3==3>-->└─<#--</#if>-->${category.name!}
                                </option>
                            </#list>
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