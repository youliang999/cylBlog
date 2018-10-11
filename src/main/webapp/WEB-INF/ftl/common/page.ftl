<#include "assets.ftl" />
<#assign isBackend=false>
<#assign isRegister=false>
<#macro page
title="大街网_年轻人专属的社交招聘平台！"
isBackend=false
isRegister=false
assets=[]
static={}
useheader="default"
usefooter="default"
currentChannel="NONE"
bodyclass="none"
menu="0"
description=""
keywords=""
flash=[]
crossdomains=[]
footerassets=[]
viewport="0"
metaExtend=[]
linkExtend=[]>
<!DOCTYPE html>
<html>
<head>
    <meta name="referrer" content="always"/>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="saoatech-site-verification" content="N2Y5MzU2M2YyNmVhOTllMGQwYmI4NzAwMTIxYmE2ZDU=" />
    <link rel="shortcut icon" href="${g.domain}/resource/img/favicon.ico"/>
    <link rel="stylesheet" href="${g.domain}/resource/css/style.css">
    <link rel="stylesheet" media="screen and (max-width:770px)" href="${g.domain}/resource/css/responsive.css">
    <script type="text/javascript" src="${g.domain}/resource/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${g.domain}/resource/js/jquery.min.js"></script>
    <script type="text/javascript" src="${g.domain}/resource/js/pageNav.js"></script>
    <link rel="stylesheet" href="${g.domain}/resource/css/zxf_page.css">
    <!--[if IE 6]>
    <script type="text/javascript" src="${g.domain}/resource/js/ie6.js"></script>
    <![endif]-->
    <link rel="alternate" type="application/rss+xml" href="${g.domain}/feed" title="${g.title}" />
    <meta content="${description}" name="description"/>
    <meta content="${keywords}" name="keywords"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="robots" content="nofollow" />
    <script src="${g.domain}/resource/js/zblog.js"></script>
    <script src="${g.domain}/resource/js/jquery.form.js"></script>
    <#if !isBackend>
        <#if isRegister>
            <link rel="stylesheet" href="${g.domain}/resource/bootstrap-3.3.1/css/bootstrap.min.css">
            <link rel="stylesheet" href="${g.domain}/resource/css/backend.css">
        </#if>
        <title>CYL-BLOG</title>
    <#else >
        <title>${g.title} Admin</title>
        <link rel="stylesheet" href="${g.domain}/resource/bootstrap-3.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="${g.domain}/resource/Font-Awesome-3.2.1/css/font-awesome.min.css">
        <link rel="stylesheet" href="${g.domain}/resource/css/backend.css">
        <#--<script src="${g.domain}/resource/js/jquery-1.9.1.min.js"></script>-->
        <script src="${g.domain}/resource/bootstrap-3.3.1/js/bootstrap.min.js"></script>
        <script src="${g.domain}/resource/js/jquery.cookie.js"></script>
        <script src="${g.domain}/resource/js/backend/zblog.admin.js"></script>
        <script type="text/javascript" src="${g.domain}/resource/js/backend/admin.post.js"></script>
        <link rel="stylesheet" type="text/css" href="${g.domain}/resource/js/newbackend/static/h-ui.admin/css/style.css" />
        <script type="text/javascript" src="${g.domain}/resource/js/newbackend/static/h-ui/js/H-ui.min.js"></script>
        <script type="text/javascript" src="${g.domain}/resource/js/newbackend/static/h-ui.admin/js/H-ui.admin.js"></script>
        <style type="text/css">
            .bdiv {
                width: 95%;
                margin-left: 20px;
            }
        </style>
    </#if>
    <@AssetsImport assets />
<body>
    <div class="cyl-content-wrap">
        <div class="cyl-content-inner">
            <#nested/>
        </div>
    </div>
</body>
<#if !isRegister>
    <#if !isBackend>
        <#include "head.ftl"/>
    </#if>
</#if>

</head>
</html>
</#macro>