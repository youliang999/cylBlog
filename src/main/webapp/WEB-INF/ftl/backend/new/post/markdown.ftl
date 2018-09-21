
<#assign assets=[
"/resource/editor-md/examples/css/style.css",
"/resource/editor-md/css/editormd.css"
]/>

<@page isBackend=true assets=assets>
<style type="text/css">
    .select-control {
        /* display: block; */
        /* width: 100%; */
        width: 150px;
        height: 34px;
        /* padding: 6px 12px; */
        font-size: 14px;
        line-height: 1.42857143;
        color: #555;
        background-color: #fff;
        background-image: none;
        border: 1px solid #ccc;
        border-radius: 4px;
        -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
        box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
        -webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
        -o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
        transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
    }

    .my-btn {
        display: inline-block;
        padding: 6px 12px;
        margin-bottom: 0;
        font-size: 14px;
        font-weight: 400;
        line-height: 1.42857143;
        text-align: center;
        white-space: nowrap;
        vertical-align: middle;
        -ms-touch-action: manipulation;
        touch-action: manipulation;
        cursor: pointer;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
        background-image: none;
        border: 1px solid transparent;
        border-radius: 4px;
    }
</style>

<div class="col-sm-9 col-md-10 bdiv " style="background-color: #f5f5f5;">
    <ol class="breadcrumb header">
        <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
        <li>文章</li>
        <li class="active">发布文章</li>
    </ol>
    <div id="layout">
        <div style="width: fit-content;margin: 0 auto;">
            <div class="panel panel-default">
                <span>工具栏主题： &nbsp; &nbsp;&nbsp;&nbsp;</span>
                    <select class="select-control" id="editormd-theme-select">
                        <!-- <option selected="selected" value="">工具栏主题</option> -->
                    </select>
                    <span>&nbsp;编辑框主题： &nbsp; &nbsp;&nbsp;&nbsp;</span>
                        <select class="select-control" id="editor-area-theme-select">
                            <!-- <option selected="selected" value="">编辑框主题</option> -->
                        </select>
                        <span>&nbsp;展示框主题： &nbsp; &nbsp;&nbsp;&nbsp;</span>
                            <select class="select-control" id="preview-area-theme-select">
                                <!-- <option selected="selected" value="">展示框主题</option> -->
                            </select>
            </div>
        </div>
        <#if post??>
            <input type="hidden" id="postid" value="${post.id!}" />
        <#else >
            <input type="hidden" id="postid" value="0" />
        </#if>
        <div style="width: 90%;     margin: 0 auto;">
            <div class="panel-heading"><span class="icon glyphicon glyphicon-edit"></span>标题/内容</div>
            <input type="text" id="title" class="form-control input-md" placeholder="输入标题" value="<#if post??>${post.title!}</#if>"><br/>
        </div>
    </div>
<#--<div class="btns">-->
<#--<button id="goto-line-btn">Goto line 90</button>-->
<#--<button id="show-btn">Show editor</button>-->
<#--<button id="hide-btn">Hide editor</button>-->
<#--<button id="get-md-btn">Get Markdown</button>-->
<#--<button id="get-html-btn">Get HTML</button>-->
<#--<button id="get-previewed-html-btn">Get previewed HTML</button>-->
<#--<button id="get-preview-html-btn">Get preview HTML</button>-->
<#--<button id="get-preview2-html-btn">Get preview2 HTML</button>-->
<#--<button id="watch-btn">Watch</button>-->
<#--<button id="unwatch-btn">Unwatch</button>-->
<#--<button id="preview-btn">Preview HTML (Press Shift + ESC cancel)</button>-->
<#--<button id="fullscreen-btn">Fullscreen (Press ESC cancel)</button>-->
<#--<button id="show-toolbar-btn">Show toolbar</button>-->
<#--<button id="close-toolbar-btn">Hide toolbar</button>-->
<#--<button id="toc-menu-btn">ToC Dropdown menu</button>-->
<#--<button id="toc-default-btn">ToC default</button>-->
<#--</div>-->
    <div id="test-editormd">
        <textarea style="display:none;">[TOC]

---





#####请在这里开启你的博客之旅!





---
</textarea>
    </div>
    <div style="    width: 90%;
    margin: 0 auto;">
        <div class="panel panel-default" style="background-color:#e6e6e6;">
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
                <button type="button" id="insertBtn" class="my-btn btn-info btn-block" onclick="/*insertMarkDown();*/">发布</button>
            </div>
        </div>
    </div>
</div>
<#--<script src="js/jquery.min.js"></script>-->
<script src="${g.domain}/resource/editor-md/editormd.js"></script>
<script type="text/javascript">
    var testEditor;






    $(function() {
        var path = "${g.domain}" + "/resource/editor-md/lib/";
//    var testMd = "/resource/editor-md/examples/test.md";
//        $.get('/resource/editor-md/examples/test.md', function(md){
//        $(function() {
        testEditor = editormd("test-editormd", {
            width: "90%",
            autoHeight: true,
//                //height: 740,
            path : path,
//                theme : "dark",
//                previewTheme : "dark",
//                editorTheme : "pastel-on-dark",
            // Editor.md theme, default or dark, change at v1.5.0
            // You can also custom css class .editormd-preview-theme-xxxx
            theme        : (localStorage.theme) ? localStorage.theme : "dark",

            // Preview container theme, added v1.5.0
            // You can also custom css class .editormd-preview-theme-xxxx
            previewTheme : (localStorage.previewTheme) ? localStorage.previewTheme : "dark",

            // Added @v1.5.0 & after version is CodeMirror (editor area) theme
            editorTheme  : (localStorage.editorTheme) ? localStorage.editorTheme : "pastel-on-dark",

//                markdown : md,
            codeFold : true,
            //syncScrolling : false,
            saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea
            searchReplace : true,
            //watch : false,                // 关闭实时预览
            htmlDecode : "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
            //toolbar  : false,             //关闭工具栏
            //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
            emoji : true,
            taskList : true,
            tocm            : true,         // Using [TOCM]
            tex : true,                   // 开启科学公式TeX语言支持，默认关闭
            flowChart : true,             // 开启流程图支持，默认关闭
            sequenceDiagram : true,       // 开启时序/序列图支持，默认关闭,
            //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
            //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
            //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
            //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
            //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
            imageUpload : true,
            imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL : "./php/upload.php",
            onload : function() {
                console.log('onload', this);
                //this.fullscreen();
                //this.unwatch();
                //this.watch().fullscreen();

                //this.setMarkdown("#PHP");
                //this.width("100%");
                //this.height(480);
                //this.resize("100%", 640);
            }
        });
//        });

        themeSelect("editormd-theme-select", editormd.themes, "theme", function($this, theme) {
            testEditor.setTheme(theme);
        });

        themeSelect("editor-area-theme-select", editormd.editorThemes, "editorTheme", function($this, theme) {
            testEditor.setCodeMirrorTheme(theme);
            // or testEditor.setEditorTheme(theme);
        });

        themeSelect("preview-area-theme-select", editormd.previewThemes, "previewTheme", function($this, theme) {
            testEditor.setPreviewTheme(theme);
        });


        $("#goto-line-btn").bind("click", function(){
            testEditor.gotoLine(90);
        });

        $("#show-btn").bind('click', function(){
            testEditor.show();
        });

        $("#hide-btn").bind('click', function(){
            testEditor.hide();
        });

        $("#get-md-btn").bind('click', function(){
            alert(testEditor.getMarkdown());
        });

        $("#get-html-btn").bind('click', function() {
            console.log(testEditor.getHTML());
            alert(testEditor.getHTML());
        });

        $("#get-previewed-html-btn").bind('click', function() {
            console.log(testEditor.getPreviewedHTML());
            alert(testEditor.getPreviewedHTML())
        });
        function themeSelect(id, themes, lsKey, callback)
        {
            var select = $("#" + id);

            for (var i = 0, len = themes.length; i < len; i ++)
            {
                var theme    = themes[i];
                var selected = (localStorage[lsKey] == theme) ? " selected=\"selected\"" : "";

                select.append("<option value=\"" + theme + "\"" + selected + ">" + theme + "</option>");
            }

            select.bind("change", function(){
                var theme = $(this).val();

                if (theme === "")
                {
                    alert("theme == \"\"");
                    return false;
                }

                console.log("lsKey =>", lsKey, theme);

                localStorage[lsKey] = theme;
                callback(select, theme);
            });

            return select;
        }

        $("#get-preview-html-btn").bind('click', function() {
            console.log(testEditor.getExcPrevieweHTML());
            alert(testEditor.getExcPrevieweHTML());
        });

        $("#insertBtn").bind('click', function() {
            var title = $.trim($("#title").val());
            if(title==""){
                $("#title").focus();
                return ;
            }
            var _getText = testEditor.getExcPrevieweHTML();
            var postid = $("#postid").val();
            var data={title : title,
                content : _getText,
                tags : $("#tags").val(),
                categoryid : $("#category").val(),
                pstatus : $("input:radio[name=pstatus]:checked").val(),
                cstatus : $("input:radio[name=cstatus]:checked").val()
            };
            if(postid.length>0) data.id=postid;
            $.ajax({
                type:postid > 0?"PUT":"POST",
                url:zblog.getDomainLink("posts"),
                data:data,
                dataType:"json",
                success:function(data){
                    if(data&&data.success){
                        if(postid > 0) {
                            alert("update success!");
                        } else {
                            alert("post success!");
                        }
                        // window.location.href="../../../../";
                    }else{
                        alert(data.msg);
                    }
                }
            });

        });

        $("#get-preview2-html-btn").bind('click', function() {
            console.log(testEditor.getPreviewedHightLightHTML());
            alert(testEditor.getPreviewedHightLightHTML());
        });

        $("#watch-btn").bind('click', function() {
            testEditor.watch();
        });

        $("#unwatch-btn").bind('click', function() {
            testEditor.unwatch();
        });

        $("#preview-btn").bind('click', function() {
            testEditor.previewing();
        });

        $("#fullscreen-btn").bind('click', function() {
            testEditor.fullscreen();
        });

        $("#show-toolbar-btn").bind('click', function() {
            testEditor.showToolbar();
        });

        $("#close-toolbar-btn").bind('click', function() {
            testEditor.hideToolbar();
        });

        $("#toc-menu-btn").click(function(){
            testEditor.config({
                tocDropdown   : true,
                tocTitle      : "目录 Table of Contents",
            });
        });

        $("#toc-default-btn").click(function() {
            testEditor.config("tocDropdown", false);
        });
    });
</script>
</div>
</@page>





<#-----------------------------------------------------------------------simple-html--------------------------------------------->

<#--<#assign assets=[-->
<#--"/resource/editor-md/examples/css/style.css",-->
<#--"/resource/editor-md/css/editormd.css"-->
<#--]/>-->

<#--<@page isBackend=true assets=assets>-->
<#--<div id="layout">-->
<#--<header>-->
<#--<h1>Simple example</h1>-->
<#--</header>-->
<#--<div id="test-editormd">-->
<#--<textarea style="display:none;">[TOC]-->

<#--#### Disabled options-->

<#--- TeX (Based on KaTeX);-->
<#--- Emoji;-->
<#--- Task lists;-->
<#--- HTML tags decode;-->
<#--- Flowchart and Sequence Diagram;-->

<#--#### Editor.md directory-->

<#--editor.md/-->
<#--lib/-->
<#--css/-->
<#--scss/-->
<#--tests/-->
<#--fonts/-->
<#--images/-->
<#--plugins/-->
<#--examples/-->
<#--languages/-->
<#--editormd.js-->
<#--...-->

<#--```html-->
<#--&lt;!-- English --&gt;-->
<#--&lt;script src="../dist/js/languages/en.js"&gt;&lt;/script&gt;-->

<#--&lt;!-- 繁體中文 --&gt;-->
<#--&lt;script src="../dist/js/languages/zh-tw.js"&gt;&lt;/script&gt;-->
<#--```-->
<#--</textarea>-->
<#--</div>-->
<#--</div>-->
<#--&lt;#&ndash;<script src="${g.domain}/resource/editor-md/examples/js/jquery.min.js"></script>&ndash;&gt;-->
<#--<script src="${g.domain}/resource/editormd.min.js"></script>-->
<#--<script type="text/javascript">-->
<#--var testEditor;-->

<#--$(function() {-->
<#--var path = "${g.domain}" + "/resource/editor-md/lib/";-->
<#--testEditor = editormd("test-editormd", {-->
<#--width   : "90%",-->
<#--autoHeight      : true,-->
<#--height  : 640,-->
<#--saveHTMLToTextarea: true,-->
<#--syncScrolling : "single",-->
<#--path    : path-->
<#--//            previewTheme : "dark"-->
<#--});-->

<#--/*-->
<#--// or-->
<#--testEditor = editormd({-->
<#--id      : "test-editormd",-->
<#--width   : "90%",-->
<#--height  : 640,-->
<#--path    : "../lib/"-->
<#--});-->
<#--*/-->
<#--});-->
<#--</script>-->
<#--</@page>-->