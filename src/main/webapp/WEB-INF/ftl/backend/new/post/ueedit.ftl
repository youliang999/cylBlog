<#assign assets=[
"/resource/newue-1.4.3/ueditor.config.js",
"/resource/newue-1.4.3/ueditor.all.js",
"/resource/newue-1.4.3/lang/zh-cn/zh-cn.js"
]/>
<@page isBackend=true assets=assets>
    <style type="text/css">
        div{
            width:100%;
        }
        .edui-editor {
            width: 100%;
        }
        .edui-default{
            width: 100%;
        }
    </style>
<div class="col-sm-9 col-md-10 bdiv " style="background-color: #f5f5f5;">
    <ol class="breadcrumb header">
        <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
        <li>文章</li>
        <li class="active">发布文章</li>
    </ol>
<div>
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
    <div style="width: 90%;margin: 0 auto;">
        <script id="editor" type="text/plain" style="width:100%;height:500px;"><#if post??>${post.content!}</#if></script>
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
            <button type="button" id="insertBtn" class="my-btn btn-info btn-block" onclick="insertMarkDown();">发布</button>
        </div>
    </div>
</div>

</div>
<div id="btns">
    <div>
        <button onclick="getAllHtml()">获得整个html的内容</button>
        <button onclick="getContent()">获得内容</button>
        <button onclick="setContent()">写入内容</button>
        <button onclick="setContent(true)">追加内容</button>
        <button onclick="getContentTxt()">获得纯文本</button>
        <button onclick="getPlainTxt()">获得带格式的纯文本</button>
        <button onclick="hasContent()">判断是否有内容</button>
        <button onclick="setFocus()">使编辑器获得焦点</button>
        <button onmousedown="isFocus(event)">编辑器是否获得焦点</button>
        <button onmousedown="setblur(event)" >编辑器失去焦点</button>

    </div>
    <div>
        <button onclick="getText()">获得当前选中的文本</button>
        <button onclick="insertHtml()">插入给定的内容</button>
        <button id="enable" onclick="setEnabled()">可以编辑</button>
        <button onclick="setDisabled()">不可编辑</button>
        <button onclick=" UE.getEditor('editor').setHide()">隐藏编辑器</button>
        <button onclick=" UE.getEditor('editor').setShow()">显示编辑器</button>
        <button onclick=" UE.getEditor('editor').setHeight(300)">设置高度为300默认关闭了自动长高</button>
    </div>

    <div>
        <button onclick="getLocalData()" >获取草稿箱内容</button>
        <button onclick="clearLocalData()" >清空草稿箱</button>
    </div>

</div>
<div>
    <button onclick="createEditor()">
        创建编辑器</button>
    <button onclick="deleteEditor()">
        删除编辑器</button>
</div>

<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');


    function isFocus(e){
        alert(UE.getEditor('editor').isFocus());
        UE.dom.domUtils.preventDefault(e)
    }
    function setblur(e){
        UE.getEditor('editor').blur();
        UE.dom.domUtils.preventDefault(e)
    }
    function insertHtml() {
        var value = prompt('插入html代码', '');
        UE.getEditor('editor').execCommand('insertHtml', value)
    }
    function createEditor() {
        enableBtn();
        UE.getEditor('editor');
    }
    function getAllHtml() {
        alert(UE.getEditor('editor').getAllHtml())
    }
    function insertMarkDown() {
        var title = $.trim($("#title").val());
        if(title==""){
            $("#title").focus();
            return ;
        }
        var _getText = UE.getEditor('editor').getInnerContent();
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

    }


    function getContent() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getContent());
        alert(arr.join("\n"));
    }
    function getPlainTxt() {
        var arr = [];
        arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getPlainTxt());
        alert(arr.join('\n'))
    }
    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
        UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
        alert(arr.join("\n"));
    }
    function setDisabled() {
        UE.getEditor('editor').setDisabled('fullscreen');
        disableBtn("enable");
    }

    function setEnabled() {
        UE.getEditor('editor').setEnabled();
        enableBtn();
    }

    function getText() {
        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
        var range = UE.getEditor('editor').selection.getRange();
        range.select();
        var txt = UE.getEditor('editor').selection.getText();
        alert(txt)
    }

    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UE.getEditor('editor').getContentTxt());
        alert(arr.join("\n"));
    }
    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UE.getEditor('editor').hasContents());
        alert(arr.join("\n"));
    }
    function setFocus() {
        UE.getEditor('editor').focus();
    }
    function deleteEditor() {
        disableBtn();
        UE.getEditor('editor').destroy();
    }
    function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
    }
    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
        }
    }

    function getLocalData () {
        alert(UE.getEditor('editor').execCommand( "getlocaldata" ));
    }

    function clearLocalData () {
        UE.getEditor('editor').execCommand( "clearlocaldata" );
        alert("已清空草稿箱")
    }
</script>
</@page>