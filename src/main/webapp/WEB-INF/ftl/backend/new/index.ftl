<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="shortcut icon" href="${g.domain}/resource/img/favicon.ico"/>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${g.domain}/resource/js/newbackend/lib/html5shiv.js"></script>
    <script type="text/javascript" src="${g.domain}/resource/js/newbackend/lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="${g.domain}/resource/js/newbackend/static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="${g.domain}/resource/js/newbackend/static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="${g.domain}/resource/js/newbackend/lib/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="${g.domain}/resource/js/newbackend/static/h-ui.admin/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="${g.domain}/resource/js/newbackend/static/h-ui.admin/css/style.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="${g.domain}/resource/js/newbackend/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>${g.title} Admin</title>
    <meta name="keywords" content="admin">
    <meta name="description" content="admin">
</head>
<body>
<header class="navbar-wrapper">
    <div class="navbar navbar-fixed-top">
        <div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="/aboutHui.shtml">cblog admin后台</a> <a class="logo navbar-logo-m f-l mr-10 visible-xs" href="/aboutHui.shtml">H-ui</a>
            <span class="logo navbar-slogan f-l mr-10 hidden-xs">v1.0</span>
            <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
            <nav class="nav navbar-nav">
                <ul class="cl">
                    <#--<li class="dropDown dropDown_hover"><a href="javascript:;" class="dropDown_A"><i class="Hui-iconfont">&#xe600;</i> 新增 <i class="Hui-iconfont">&#xe6d5;</i></a>-->
                        <#--<ul class="dropDown-menu menu radius box-shadow">-->
                            <#--<li><a href="javascript:;" onclick="article_add('添加资讯','article-add.html')"><i class="Hui-iconfont">&#xe616;</i> 资讯</a></li>-->
                            <#--<li><a href="javascript:;" onclick="picture_add('添加资讯','picture-add.html')"><i class="Hui-iconfont">&#xe613;</i> 图片</a></li>-->
                            <#--<li><a href="javascript:;" onclick="product_add('添加资讯','product-add.html')"><i class="Hui-iconfont">&#xe620;</i> 产品</a></li>-->
                            <#--<li><a href="javascript:;" onclick="member_add('添加用户','member-add.html','','510')"><i class="Hui-iconfont">&#xe60d;</i> 用户</a></li>-->
                        <#--</ul>-->
                    <#--</li>-->
                </ul>
            </nav>
            <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                <ul class="cl">
                    <li><#if currentUser?? >${currentUser.role!}</#if></li>
                    <li class="dropDown dropDown_hover">
                        <a href="#" class="dropDown_A">admin <i class="Hui-iconfont">&#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <#--<li><a href="${g.domain}/backend/users/my" onClick="myselfinfo()">个人信息</a></li>-->
                            <#--<li><a href="#">切换账户</a></li>-->
                            <li><a href="${g.domain}/backend/logout">退出</a></li>
                        </ul>
                    </li>
                    <li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
                    <li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
                            <li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
                            <li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
                            <li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
                            <li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
                            <li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<aside class="Hui-aside">
    <div class="menu_dropdown bk_2">
        <dl id="menu-article">
            <dt><i class="Hui-iconfont">&#xe616;</i> 系统设置<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a data-href="${g.domain}/backend/welcome" id="welcome"  data-title="首页" href="javascript:void(0)">首页</a></li>
                    <li><a data-href="${g.domain}/backend/options/general" id="general"  data-title="常规选项" href="javascript:void(0)">常规选项</a></li>
                    <li><a data-href="${g.domain}/backend/options/post" id="postRead"  data-title="撰写/阅读" href="javascript:void(0)">撰写/阅读</a></li>
                    <li><a data-href="${g.domain}/backend/options/email" id="emailComment"  data-title="邮件评论" href="javascript:void(0)">邮件评论</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-picture">
            <dt><i class="Hui-iconfont">&#xe613;</i> 博客<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a data-href="${g.domain}/backend/blog/markdown" id="postBlogMD" data-title="发布博客-markdown" href="javascript:void(0)">发布博客-markdown</a></li>
                    <#--<li><a data-href="${g.domain}/backend/blog/notepad" data-title="发布博客(notepad)" href="javascript:void(0)">发布博客(notepad)</a></li>-->
                    <li><a data-href="${g.domain}/backend/blog/ueditor" id="postBlogUe" data-title="发布博客-ueditor" href="javascript:void(0)">发布博客-ueditor</a></li>
                    <li><a data-href="${g.domain}/backend/posts/edit" id="writeBlog" data-title="写博客" href="javascript:void(0)">写博客</a></li>
                    <li><a data-href="${g.domain}/backend/posts/1" id="allBlog" data-title="所有博客" href="javascript:void(0)">所有博客</a></li>
                    <li><a data-href="${g.domain}/backend/categorys" id="blogType" data-title="博客分类" href="javascript:void(0)">博客分类</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-product">
            <dt><i class="Hui-iconfont">&#xe620;</i> 多媒体<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a data-href="${g.domain}/backend/uploads/1" id="mediaLib" data-title="媒体库" href="javascript:void(0)">媒体库</a></li>
                    <li><a data-href="${g.domain}/backend/uploads/edit" id="addMedia" data-title="添加" href="javascript:void(0)">添加</a></li>
                </ul>
            </dd>
        </dl>
        <#--<dl id="menu-comments">-->
            <#--<dt><i class="Hui-iconfont">&#xe622;</i> 页面<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>-->
            <#--<dd>-->
                <#--<ul>-->
                    <#--<li><a data-href="http://h-ui.duoshuo.com/admin/" data-title="所有页面" href="javascript:;">所有页面</a></li>-->
                    <#--<li><a data-href="feedback-list.html" data-title="新建页面" href="javascript:void(0)">新建页面</a></li>-->
                <#--</ul>-->
            <#--</dd>-->
        <#--</dl>-->
        <dl id="menu-member">
            <dt><i class="Hui-iconfont">&#xe60d;</i> 评论管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a data-href="${g.domain}/backend/comments/1" id="comments" data-title="评论" href="javascript:;">评论</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-admin">
            <dt><i class="Hui-iconfont">&#xe62d;</i> 链接管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a data-href="${g.domain}/backend/links/edit" id="addLink" data-title="添加链接" href="javascript:void(0)">添加链接</a></li>
                    <li><a data-href="${g.domain}/backend/links/1" id="allLink" data-title="全部链接" href="javascript:void(0)">全部链接</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-tongji">
            <dt><i class="Hui-iconfont">&#xe61a;</i> 用户管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a data-href="${g.domain}/backend/users/1" id="allUser" data-title="所有用户" href="javascript:void(0)">所有用户</a></li>
                    <li><a data-href="${g.domain}/backend/users/edit" id="addUser" data-title="添加用户" href="javascript:void(0)">添加用户</a></li>
                    <li><a data-href="${g.domain}/backend/users/my" id="myInfo" data-title="我的个人资料" href="javascript:void(0)">我的个人资料</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-system">
            <dt><i class="Hui-iconfont">&#xe62e;</i> 工具<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a data-href="${g.domain}/backend/tool/redis" id="redisLook" data-title="缓存监控" href="javascript:void(0)">缓存监控</a></li>
                    <li><a data-title="solr监控" href="http://localhost:8888/index.html?output=embed" target="_blank">solr监控</a></li>
                    <#--<li><a data-href="${g.domain}/backend/tool/import" data-title="导入" href="javascript:void(0)">导入</a></li>-->
                    <#--<li><a data-href="${g.domain}/backend/tool/output" data-title="导出" href="javascript:void(0)">导出</a></li>-->
                </ul>
            </dd>
        </dl>
    </div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
    <div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
        <div class="Hui-tabNav-wp">
            <ul id="min_title_list" class="acrossTab cl">
                <li class="active">
                    <span title="我的桌面" data-href="${g.domain}/backend/welcome">我的桌面</span>
                    <em></em></li>
            </ul>
        </div>
        <div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
    </div>
    <div id="iframe_box" class="Hui-article">
        <div class="show_iframe">
            <div style="display:none" class="loading"></div>
            <iframe scrolling="yes" frameborder="0" src="${g.domain}/backend/welcome" ></iframe>
        </div>
    </div>
</section>

<div class="contextMenu" id="Huiadminmenu">
    <ul>
        <li id="closethis">关闭当前 </li>
        <li id="closeall">关闭全部 </li>
    </ul>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${g.domain}/resource/js/newbackend/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${g.domain}/resource/js/newbackend/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${g.domain}/resource/js/newbackend/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="${g.domain}/resource/js/newbackend/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${g.domain}/resource/js/newbackend/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
<script type="text/javascript">
    $(function(){
        /*$("#min_title_list li").contextMenu('Huiadminmenu', {
            bindings: {
                'closethis': function(t) {
                    console.log(t);
                    if(t.find("i")){
                        t.find("i").trigger("click");
                    }
                },
                'closeall': function(t) {
                    alert('Trigger was '+t.id+'\nAction was Email');
                },
            }
        });*/
    });
    /*个人信息*/
    function myselfinfo(){
        layer.open({
            type: 1,
            area: ['300px','200px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title: '查看信息',
            content: '<div>管理员信息</div>'
        });
    }

    /*资讯-添加*/
    function article_add(title,url){
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
    /*图片-添加*/
    function picture_add(title,url){
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
    /*产品-添加*/
    function product_add(title,url){
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
    /*用户-添加*/
    function member_add(title,url,w,h){
        layer_show(title,url,w,h);
    }


</script>


</body>
</html>