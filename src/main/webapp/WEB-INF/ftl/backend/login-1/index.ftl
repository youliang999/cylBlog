<!DOCTYPE html>
<html lang="en" class="app">
<head>
    <meta charset="utf-8" />
    <link rel="shortcut icon" href="${g.domain}/resource/img/favicon.ico"/>
    <title>后台登录</title>
    <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <script type="text/javascript" src="${g.domain}/resource/js/jquery.min.js"></script>
    <script type="text/javascript" src="${g.domain}/resource/js/jquery-1.9.1.min.js"></script>
    <link rel="stylesheet" href="${g.domain}/resource/css/login-1/css/bootstrap.css" type="text/css" />
    <link rel="stylesheet" href="${g.domain}/resource/css/login-1/css/simple-line-icons.css" type="text/css" />
    <link rel="stylesheet" href="${g.domain}/resource/css/login-1/css/app.css" type="text/css" />
    <script  type="text/javascript">

        function GetUrlParam(paraName) {
            var url = document.location.toString();
            var arrObj = url.split("?");

            if (arrObj.length > 1) {
                var arrPara = arrObj[1].split("&");
                var arr;

                for (var i = 0; i < arrPara.length; i++) {
                    arr = arrPara[i].split("=");

                    if (arr != null && arr[0] == paraName) {
                        return arr[1];
                    }
                }
                return "/backend/index";
            }
            else {
                return "/backend/index";
            }
        }

        function dosubmit(){
            var baseUrl = "${g.domain}";
            var url1 = baseUrl;
            var url = baseUrl +"/backend/loginsubmit";
            var userName = $("#username").val();
            var userpwd = $("#password").val();
            $.ajax({
                cache:true,//保留缓存数据
                type:"POST",//为post请求
                url:url,//这是我在后台接受数据的文件名
                data: {"username":userName,"userpwd":userpwd},//将该表单序列化
                async:false,//设置成true，这标志着在请求开始后，其他代码依然能够执行。如果把这个选项设置成false，这意味着所有的请求都不再是异步的了，这也会导致浏览器被锁死
                dataType: 'json',
                error:function(request){//请求失败之后的操作
                    return;
                },
                success:function(data){//请求成功之后的操作
//                    alert(data);
                    if(data.msg != null && data.msg != ""){
                        $('#tipmessage').show();
                        $('#tipmessage').text(data.msg);
                        $('#username').addClass("error-input");
                        $('#password').addClass("error-input");
                    } else {
//                        alert(222);
                        window.location.href=url1 + GetUrlParam("loginInterceptorUrl");
                    }
                }
            });
        }
    </script>
    <style type="text/css">
        .ivu-form-item-error-tip {
            position: absolute;
            top: 100%;
            left: 0;
            line-height: 1;
            padding-top: 6px;
            color: #ed3f14;
        }

        .error-input {
            border: 1px solid #ed3f14;
        }
    </style>

</head>
<body background="${g.domain}/resource/css/login-1/images/bodybg.jpg">
<section id="content" class="m-t-lg wrapper-md animated fadeInUp ">
    <div class="container aside-xl" style="margin-top: 48px;">
        <a class="navbar-brand block"><span class="h1 font-bold" style="color: #ffffff">管理员后台登录</span></a>
        <section class="m-b-lg">
            <header class="wrapper text-center">
                <strong class="text-sucess">管理员登录</strong>
            </header>
        <#--<form action="javascript:;" method="post" >-->
            <input type="hidden" name="CSRFToken" value="${CSRFToken!}" />
            <div class="form-group">
                <input type="username" id="username" name="username" placeholder="用户名" onblur="hideError();" value="${nickName!}" class="form-control  input-lg no-border">
            </div>
            <div class="<#--form-group-->">
                <input type="password" id="password" name="password" placeholder="密码"  onblur="hideError();" class="form-control  input-lg no-border">
            </div>
            <p style="color: red;position: absolute;font-size: 12px;padding-top: 5px;" id="tipmessage"></p>
        <#--<div class="ivu-form-item-error-tip">密码不能小于6位</div>-->
            <div style="    margin-top: 24px;">
                <button type="submit" class="btn btn-lg btn-danger lt b-white b-2x btn-block" id="validate-submit" onclick="dosubmit();">
                    <i class="icon-arrow-right pull-right"></i><span class="m-r-n-lg">登录</span></button>
                    <a href="${g.domain}/register" style="font-color:white; ">去注册一下</a>
            </div>
        <#--</form>-->
        </section>
    </div>
</section>
<script type="text/javascript">
    function hideError() {
        $('#tipmessage').hide();
        $('#username').removeClass("error-input");
        $('#password').removeClass("error-input");
    }
</script>
<!-- footer -->
<footer id="footer">
    <div class="text-center padder">

    </div>
</footer>
<!-- / footer -->
<div style="text-align:center;">
<#--<p>更多模板：<a href="http://www.mycodes.net/" target="_blank">源码之家</a></p>-->
</div>
</body>
</html>

