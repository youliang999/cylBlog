<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" href="${g.domain}/resource/img/favicon.ico"/>
    <title>LOGIN</title>
    <link href="${g.domain}/resource/css/Wopop_files/style_log.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${g.domain}/resource/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${g.domain}/resource/css/Wopop_files/style.css">
    <#--<link rel="stylesheet" type="text/css" href="${g.domain}/resource/css/Wopop_files/userpanel.css">-->
    <link rel="stylesheet" type="text/css" href="${g.domain}/resource/css/Wopop_files/jquery.ui.all.css">
    <script type="text/javascript">
        function clear() {
//            alert(111);
            $('#tipmessage').hide();
        }
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
            var userpwd = $("#userpwd").val();
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
                    } else {
//                        alert(222);
                        window.location.href=url1 + GetUrlParam("loginInterceptorUrl");
                    }
                }
            });
        }
    </script>
</head>


<body class="login" mycollectionplug="bind">
<div class="login_m">
    <div class="login_logo"><img src="${g.domain}/resource/css/Wopop_files/logo.png" width="196" height="46"></div>
    <div class="login_boder">
        <div class="login_padding" id="login_model">
            <input type="hidden" name="CSRFToken" value="${CSRFToken!}" />
            <h2>USERNAME</h2>
            <label>
                <input name="username" type="text" id="username" class="txt_input txt_input2"  onfocus="clear()"  value="${fh.username!}">
            </label>
            <h2>PASSWORD</h2>
            <label>
                <input type="password" name="userpwd" id="userpwd" class="txt_input"  onfocus="clear()" value="">
            </label>
            <p style="color: red" id="tipmessage"></p>
            <#--<p class="forgot"><a id="iforget" href="javascript:void(0);">Forgot your password?</a></p>-->
            <div class="rem_sub">
                <div class="rem_sub_l">
                    <input type="checkbox" name="checkbox" id="save_me">
                    <label for="checkbox">Remember me</label>
                </div>

                <label>
                    <input type="submit" id="loginbtn" class="sub_button" name="button" id="button" value="SIGN-IN"  onclick="dosubmit()" style="opacity: 0.7;">
                </label>
            </div>
        </div>


        <div class="copyrights">Collect from <a href="http://39.106.151.227:8080/" >cylBlog</a></div>

        <div id="forget_model" class="login_padding" style="display:none">
            <br>

            <h1>Forgot password</h1>
            <br>
            <div class="forget_model_h2">(Please enter your registered email below and the system will automatically reset users’ password and send it to user’s registered email address.)</div>
            <label>
                <input type="text" id="usrmail" class="txt_input txt_input2">
            </label>


            <div class="rem_sub">
                <div class="rem_sub_l">
                </div>
                <label>
                    <input type="submit" class="sub_buttons" name="button" id="Retrievenow" value="Retrieve now" style="opacity: 0.7;">
                    　　　
                    <input type="submit" class="sub_button" name="button" id="denglou" value="Return" style="opacity: 0.7;">　　

                </label>
            </div>
        </div>






        <!--login_padding  Sign up end-->
    </div><!--login_boder end-->
</div><!--login_m end-->
<br> <br>
<#--<p align="center"> More Templates <a href="http://www.cssmoban.com/" target="_blank" title="模板之家">模板之家</a> - Collect from <a href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a></p>-->



</body></html>