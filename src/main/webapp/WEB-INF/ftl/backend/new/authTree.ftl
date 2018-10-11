<#assign assets=[
"/resource/authtree/dtree.css",
"/resource/authtree/dtree.js"
]/>
<@page isBackend=true assets=assets>
<div class="dtree bdiv" xmlns="http://www.w3.org/1999/html">

    <p><a href="javascript: d.openAll();">展开</a> | <a href="javascript: d.closeAll();">关闭</a></p>
    <div> <input id="userId" name="userId" value="${nickName!}"/></div>
    <script type="text/javascript">

        d = new dTree('d');
        d.add('root', -1,'菜单权限树');
        var url = "${g.domain}" + "/backend/auth/getTree"
        $.ajax({
            cache:true,//保留缓存数据
            type:"GET",//为post请求
            url:url,//这是我在后台接受数据的文件名
            //data: {"username":userName,"userpwd":userpwd},//将该表单序列化
            async:false,//设置成true，这标志着在请求开始后，其他代码依然能够执行。如果把这个选项设置成false，这意味着所有的请求都不再是异步的了，这也会导致浏览器被锁死
            dataType: 'json',
            error:function(request){//请求失败之后的操作
                return;
            },
            success:function(data){//请求成功之后的操作
                if(data.menus != null){
                    var list = data.menus;
                    for(var i=0; i<list.length;i++) {
                        d.add(''+ list[i].mid +'' , '' + list[i].parentMid + '', 'authority', '' + list[i].mid + '', list[i].mname,  list[i].isAuthed);
                    }
                } else {
                    alert("error!");
                }
            }
        });
        document.write(d);
        d.openAll();
    </script>

</div>
<div>
    <input type='button' name='bTest' value='添加权限' onclick='test();'>
</div>
<script type="text/javascript">
    <!--
    function test(){
        var count = 0;
        var obj = document.all.authority;
        var uid = $('#userId').val();
        var auths = "";
        for(i=0;i<obj.length;i++){
            if(obj[i].checked){
                //alert(obj[i].value);
                auths += obj[i].value + ",";
                count ++;
            }
        }
        var url = "${g.domain}" + "/backend/auth/save"
        $.ajax({
            cache:true,//保留缓存数据
            type:"POST",//为post请求
            url:url,//这是我在后台接受数据的文件名
            data: {"uid":uid,"auths":auths},//将该表单序列化
            async:false,//设置成true，这标志着在请求开始后，其他代码依然能够执行。如果把这个选项设置成false，这意味着所有的请求都不再是异步的了，这也会导致浏览器被锁死
            dataType: 'json',
            error:function(request){//请求失败之后的操作
                alert("error!");
                return;
            },
            success:function(data){//请求成功之后的操作
                if(data.msg != null && data.msg != ""){
                    alert(data.msg);
                } else {
                    alert("error!");
                }
            }
        });

    }
    //-->
</script>
</@page>