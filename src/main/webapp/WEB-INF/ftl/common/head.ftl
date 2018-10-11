<div id="header">
    <div id="menu" class="clearfix">
        <ul>
            <li><a href="index.html">首页</a>
                <ul>
                    <li><a href="${g.domain}/backend/login">登录</a></li>
                    <li><a href="${g.domain}/register" target="_blank">注册</a></li>
                </ul>
            </li>

        </ul>
    </div>
    <h1 id="logo" style="
    font-family: &quot;Century Gothic&quot;;
    font-size: 45px;
    font-weight: bold;
    line-height: 100%;
    margin: 62px 0 5px 4%;
"><a href="">Cblog - youliang.cheng</a></h1>
    <h2 id="tagline">just another cblog website</h2>
</div>






<#--=====================================================================-->

<#--<style type="text/css">-->
    <#--*{-->
        <#--margin: 0;-->
        <#--padding: 0;-->
        <#--list-style: none;-->
    <#--}-->
    <#--/** 清除内外边距 **/-->
    <#--body, h1, h2, h3, h4, h5, h6, hr, p, blockquote, /* structural elements 结构元素 */-->
    <#--dl, dt, dd, ul, ol, li, /* list elements 列表元素 */-->
    <#--pre, /* text formatting elements 文本格式元素 */-->
    <#--form, fieldset, legend, button, input, textarea, /* form elements 表单元素 */-->
    <#--th, td /* table elements 表格元素 */ {-->
        <#--margin: 0;-->
        <#--padding: 0;-->
    <#--}-->
    <#--/** 重置列表元素 **/-->
    <#--ul, ol { list-style: none; }-->
    <#--/** 重置文本格式元素 **/-->
    <#--a,a:hover { text-decoration: none; }-->
    <#--/** 重置表单元素 **/-->
    <#--legend { color: #000; } /* for ie6 */-->
    <#--fieldset, img { border: 0; } /* img 搭车：让链接里的 img 无边框 */-->
    <#--button, input, select, textarea { font-size: 100%; } /* 使得表单元素在 ie 下能继承字体大小 */-->
    <#--/** 重置表格元素 **/-->
    <#--table { border-collapse: collapse; border-spacing: 0; }-->

    <#--.searchbox {-->
        <#--position: relative;-->
        <#--z-index: 1;-->
        <#--clear: both;-->
        <#--width: 630px;-->
        <#--height: 40px;-->
        <#--margin: 30px auto 10px;-->
        <#--border: 2px solid #444786;-->
    <#--}-->
    <#--.mod_select {-->
        <#--position: absolute;-->
        <#--left: 0;-->
        <#--top: 0;-->
        <#--width: 98px;-->
    <#--}-->
    <#--.mod_select .select_box {-->
        <#--position: relative;-->
        <#--width: 98px;-->
        <#--height: 36px;-->
    <#--}-->
    <#--.mod_select .select_box .select_txt {-->
        <#--display: inline-block;-->
        <#--width: 98px;-->
        <#--height: 36px;-->
        <#--padding-left: 20px;-->
        <#--overflow: hidden;-->
        <#--line-height: 36px;-->
        <#--font-size: 16px;-->
        <#--cursor: pointer;-->
    <#--}-->
    <#--.mod_select .select_box .select-icon {-->
        <#--position: absolute;-->
        <#--top: 50%;-->
        <#--right: 10px;-->
        <#--transform: translate(0,-50%);-->
        <#--width: 10px;-->
        <#--height: 10px;-->
        <#--background: url(../images/search_ico.png) center no-repeat;-->
    <#--}-->
    <#--.mod_select .select_box .option {-->
        <#--display: none;-->
        <#--position: absolute;-->
        <#--top: 40px;-->
        <#--left: -2px;-->
        <#--width: 100px;-->
        <#--background-color: #fff;-->
        <#--border: 2px solid #444786;-->
        <#--border-top: 0;-->
    <#--}-->
    <#--.mod_select .select_box .option li {-->
        <#--padding-left: 20px;-->
        <#--font-size: 16px;-->
        <#--line-height: 2;-->
        <#--cursor: pointer;-->
    <#--}-->
    <#--.searchbox .import {-->
        <#--width: 426px;-->
        <#--height: 40px;-->
        <#--margin-left: 100px;-->
        <#--padding-left: 20px;-->
        <#--border: none;-->
        <#--outline: none;-->
    <#--}-->
    <#--.searchbox .btn-search {-->
        <#--position: absolute;-->
        <#--right: 0;-->
        <#--width: 100px;-->
        <#--height: 40px;-->
        <#--color: #fff;-->
        <#--background-color: #444786;-->
        <#--border: 0;-->
    <#--}-->
<#--</style>-->
<#--<div id="header">-->
    <#--<div id="menu" class="clearfix">-->
        <#--<ul>-->
            <#--<li><a href="index.html">首页</a>-->
                <#--<ul>-->
                    <#--<li><a href="backend/login">登录</a></li>-->
                <#--</ul>-->
            <#--</li>-->
        <#--</ul>-->
    <#--</div>-->
    <#--<h1 id="logo"><a href="${g.domain!}">CYL-BLOG - youliang.cheng</a></h1>-->
    <#--&lt;#&ndash;<h2 id="tagline">just another CYL-BLOG website</h2>&ndash;&gt;-->
    <#--<div class="searchbox">-->
        <#--<div class="mod_select">-->
            <#--<div class="select_box">-->
                <#--<span class="select_txt">精确搜索</span>-->
                <#--<span class="select-icon"></span>-->
                <#--<ul class="option">-->
                    <#--<li>精确搜索</li>-->
                    <#--<li>模糊搜索</li>-->
                    <#--<li>搜索内容</li>-->
                <#--</ul>-->
            <#--</div>-->
        <#--</div>-->
        <#--<form action="" >-->
            <#--<input type="hidden" name="" value="精确搜索" id="select_value">-->
            <#--<input type="text" name="" id="searchPlaceholder" class="import" placeholder="搜索一下">-->
            <#--<input type="submit" value="搜   索" class="btn-search">-->
        <#--</form>-->
    <#--</div>-->
    <#--<script>-->
        <#--$(function(){-->
            <#--$(".select_box").click(function(event){-->
                <#--event.stopPropagation();-->
                <#--$(this).find(".option").toggle();-->
                <#--$(this).parent().siblings().find(".option").hide();-->
            <#--});-->
            <#--$(document).click(function(event){-->
                <#--var eo=$(event.target);-->
                <#--if($(".select_box").is(":visible") && eo.attr("class")!="option" && !eo.parent(".option").length)-->
                    <#--$('.option').hide();-->
            <#--});-->
            <#--$(".option li").click(function(){-->
                <#--var check_value=$(this).text();-->
                <#--var zlValue = $('.option li:eq(1)').html();-->
                <#--var bqValue = $('.option li:eq(2)').html();-->
                <#--$(this).parent().siblings(".select_txt").text(check_value);-->
                <#--$("#select_value").val(check_value);-->
                <#--if(check_value == zlValue) {-->
                    <#--$('#searchPlaceholder').prop('placeholder','请输入内容');-->
                <#--}else if(check_value == bqValue) {-->
                    <#--$('#searchPlaceholder').prop('placeholder','请输入内容');-->
                <#--}else {-->
                    <#--$('#searchPlaceholder').prop('placeholder','请输入内容');-->
                <#--}-->
            <#--});-->
        <#--})-->
    <#--</script>-->
    <#--<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">-->
        <#--&lt;#&ndash;<p>适用浏览器：360、FireFox、Chrome、Safari、Opera、傲游、搜狗、世界之窗. 不支持IE8及以下浏览器。</p>&ndash;&gt;-->
        <#--&lt;#&ndash;<p>来源：<a href="http://sc.chinaz.com/" target="_blank">站长素材</a></p>&ndash;&gt;-->
    <#--</div>-->
<#--</div>-->