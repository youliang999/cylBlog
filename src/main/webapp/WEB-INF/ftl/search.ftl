<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>CBlog首页</title>
    <link rel="stylesheet" href="${g.domain}/resource/css/index-search.css">
    <script type="application/javascript">
    </script>
</head>

<body>
<div id="journal" style="text-align:center; margin:0 auto;">
    <div style="      font: normal 20px/36px 'Arial Normal', 'Arial';
    text-indent: 20px;          border-bottom: 1px solid black;">
        <div style="    width: 938px; height: 40px; margin: 0 auto;">
            <div class="menu-left" style="width: auto; float: left;  margin-left: 15px; height: 50px;">
                <a style="font-size:19px;">welcome to cblog</a>
            </div>
            <div class="menu-right" style="width: auto; float: right; height: 50px;">
                <a href="${g.domain}/blog/index" style=" color:black; " >去博客</a>
                <a href="${g.domain}/update/log" style=" color:black; " >更新日志</a>
                <a href="${g.domain}/link/author" style=" color:black; " >关于作者</a>
            </div>
        </div>
    </div>
    <br/>
    <div class="logo" id="logo">
    </div>
    <!-- 	<div class="logo1" id="logo1">
            <a><h1 style="  font-family:serif,sans-serif,fantasy,cursive;">Search </h1></a>
        </div> -->
    <div class="search-div">
        <a><h1 style="  font-family:serif,sans-serif,fantasy,cursive;margin-bottom:-5px;">Search </h1></a>
        <div class="search-box" id="search-box">
            <form action="${g.domain}/search" id="form" method="get">
                <p class="form-p-one">
                    <span><input type="text" name="keyword" id="J_uid_input"/></span>
                    <span><input style="height: 45px;margin-left: -3px;width: 57px;" type="submit" value="搜索" id="J_clear"/></span>
                </p>
            </form>
        </div>
        <div class="search-tip">
            <span>热门搜索</span>
            <a target="_blank" href="${g.domain}/search?keyword=java">java</a>
            <a target="_blank" href="${g.domain}/search?keyword=java工程师">java工程师</a>
        </div>
    </div>
</div>
<div class="footer">
    <div class="about">
        <a class="abouta">cblog</a>
        <a class="abouta1">关于cblog - About Cblog - 企业推广 - 免责声明 - 意见反馈及投诉 - 隐私政策</a>
    </div>
</div>
</body>
</html>

