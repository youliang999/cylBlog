<#assign assets=[
"/resource/ueditor-1.4.3/ueditor.config.js",
"/resource/ueditor-1.4.3/ueditor.all.js",
"/resource/epiceditor-0.2.3/js/epiceditor.min.js"
]/>
<@page isBackend=true assets=assets>
<div class="col-sm-9 col-md-10">
    <ol class="breadcrumb header">
        <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
        <li>文章</li>
        <li class="active">编辑文章</li>
    </ol>
    <div class="row">
        <div class="col-sm-9 col-md-9">
            <div class="panel panel-default">
                <div class="panel-heading"><span class="icon glyphicon glyphicon-edit"></span>标题/内容</div>
                <div class="panel-body">
                    <#if post??>
                        <input type="hidden" id="postid" value="${post.id!}" />
                    <#else >
                        <input type="hidden" id="postid" value="0" />
                    </#if>

                    <input type="text" id="title" class="form-control input-md" placeholder="输入标题" value="<#if post??>${post.title!}</#if>"><br/>
                    <ul class="nav nav-tabs nav-justified" id="editor-nav">

                        <li <#if !post?? || (post.id?? && post.id == '0')>class="active"</#if><a href="#editor-mk">Markdown</a></li>
                        <li><a href="#editor-txt">纯文本</a></li>
                        <li <#if post?? && post.id != '0'>class="active"</#if>><a href="#editor-ue">UEditor</a></li>
                    </ul>
                    <div class="tab-content">
                        <!-- EpicEditor初始化时必须为显示状态 -->
                        <div class="tab-pane <#if !post?? || (post.id?? && post.id == '0')>active</#if> " id="editor-mk"><div id="epiceditor"></div></div>
                        <div class="tab-pane" id="editor-txt">
                            <textarea id="editor-txt-tt" style="width: 100%; height: 400px"><#if post??>${post.content!}</#if></textarea>
                        </div>
                        <div class="tab-pane <#if post?? && post.id != '0'>active</#if>" id="editor-ue">
                            <!-- 必须要添加width:100% -->
                            <script id="ueditor" style="width: 100%; height: 350px;" type="text/plain"><#if post??>${post.content!}</#if></script>
                        </div>
                    </div>
                </div>
                <div class="panel-footer text-success">注:此三种编辑模式相互独立,最终以当前选中标签页内容提交</div>
            </div>
        </div>
        <div class="col-sm-3 .col-md-3">
            <div class="panel panel-default">
                <div class="panel-heading">发布</div>
                <div class="panel-body">
                    <div class="form-group">
                        <label for="categoty">分类</label>
                        <select class="form-control" id="category">
                            <#list categorys as category>
                                <option value="${category.id}" <#if post??><#if post.category.id==category.id>selected = 'selected'</#if></#if>>
                                    ├─└─${category.name}
                                </option>
                            </#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="pstatus">公开度</label><br/>
                        <label class="radio-inline">
                            <input type="radio" name="pstatus" value="1" <#if post?? && post.pstatus==1>checked="checked"</#if>>公开
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="pstatus" value="-1" <#if post?? && post.pstatus!= 1>checked="checked"</#if>>隐藏
                        </label>
                    </div>
                    <div class="form-group">
                        <label for="tags">标签</label>
                        <input type="text" class="form-control" id="tags" value="${tags!}" />
                        <span class="help-block">多个标签请用英文逗号（,）分开</span>
                    </div>
                    <div class="form-group">
                        <label for="cstatus">是否允许评论</label><br>
                        <label class="radio-inline">
                            <input type="radio" name="cstatus" value="open" <#if post?? && post.cstatus=='open'>checked="checked"</#if>>是
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="cstatus" value="close" <#if post?? && post.cstatus=='close'>checked="checked"</#if>>否
                        </label>
                    </div>
                </div>
                <div class="panel-footer">
                    <button type="button" class="btn btn-info btn-block" onclick="zblog.post.insert();">发布</button>
                </div>
            </div>
        </div>
    </div>

</div>
</@page>