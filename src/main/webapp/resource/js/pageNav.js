(function($){
	var zp = {
		init:function(obj,pageinit){
			return (function(){
				zp.addhtml(obj,pageinit);
				zp.bindEvent(obj,pageinit);
			}());
		},
		addhtml:function(obj,pageinit){
			if(pageinit.isBackend) {
                return (function(){
                    obj.empty();
					/*上一页*/
                    if (pageinit.current > 1) {
                        var link = pageinit.urlPrefix + "/" + (pageinit.current - 1);
                        var title = "所有博客"
                        obj.append('<a data-href=' + link + ' data-title='+ title +'   class="prebtn">上一页</a>');
                    } else{
                        obj.remove('.prevPage');
                        obj.append('<span class="disabled">上一页</span>');
                    }
					/*中间页*/
                    if (pageinit.current >4 && pageinit.pageNum > 4) {
                        var link1 = pageinit.urlPrefix + "/" +  1;
                        var link2 = pageinit.urlPrefix + "/" + 2;
                        var title1 = "所有博客";
                        var title2 = "所有博客";
                        obj.append('<a data-href=' + link1 + ' data-title='+ title1 +' class="zxfPagenum">'+1+'</a>');
                        obj.append('<a data-href=' + link2 + ' data-title='+ title2 +' class="zxfPagenum">'+2+'</a>');
                        obj.append('<span>...</span>');
                    }
                    if (pageinit.current >4 && pageinit.current <= pageinit.pageNum-5) {
                        var start  = pageinit.current - 2,end = pageinit.current + 2;
                    }else if(pageinit.current >4 && pageinit.current > pageinit.pageNum-5){
                        var start  = pageinit.pageNum - 4,end = pageinit.pageNum;
                    }else{
                        var start = 1,end = 9;
                    }
                    for (;start <= end;start++) {
                        if (start <= pageinit.pageNum && start >=1) {
                            if (start == pageinit.current) {
                                obj.append('<span class="current">'+ start +'</span>');
                            } else if(start == pageinit.current+1){
                                var link = pageinit.urlPrefix + "/" + start;
                                var title = "所有博客";
                                obj.append('<a data-href=' + link + '  data-title='+ title +' class="zxfPagenum nextpage">'+ start +'</a>');
                            }else{
                                var link = pageinit.urlPrefix + "/" + start;
                                var title = "所有博客";
                                obj.append('<a data-href=' + link + '  data-title='+ title +' class="zxfPagenum">'+ start +'</a>');
                            }
                        }
                    }
                    if (end < pageinit.pageNum) {
                        obj.append('<span class="dotspan">...</span>');
                    }
					/*下一页*/
                    if (pageinit.current >= pageinit.pageNum) {
                        obj.remove('.nextbtn');
                        obj.append('<span class="disabled">下一页</span>');
                    } else{
                        var link = pageinit.urlPrefix + "/" + (pageinit.current + 1);
                        var title = "所有博客";
                        obj.append('<a data-href=' + link + ' data-title='+ title +' class="nextbtn">下一页</a>');
                    }
					/*尾部*/
                    obj.append('<span class="sumpagespan">'+'共'+'<b>'+pageinit.pageNum+'</b>'+'页，'+'</span>');
                    obj.append('<span class="tospan">'+'到第'+'<input type="text" class="zxfinput" value=""/>'+'页'+'</span>');
                    obj.append('<span class="zxfokbtn">'+'确定'+'</span>');
                }());
			} else {
                return (function(){
                    obj.empty();
					/*上一页*/
                    if (pageinit.current > 1) {
                        var link = pageinit.urlPrefix + "/" + (pageinit.current - 1);
                        obj.append('<a href=' + link + ' target="_blank" class="prebtn">上一页</a>');
                    } else{
                        obj.remove('.prevPage');
                        obj.append('<span class="disabled">上一页</span>');
                    }
					/*中间页*/
                    if (pageinit.current >4 && pageinit.pageNum > 4) {
                        var link1 = pageinit.urlPrefix + "/" +  1;
                        var link2 = pageinit.urlPrefix + "/" + 2;
                        obj.append('<a href=' + link1 + ' target="_blank" class="zxfPagenum">'+1+'</a>');
                        obj.append('<a href=' + link2 + ' target="_blank" class="zxfPagenum">'+2+'</a>');
                        obj.append('<span>...</span>');
                    }
                    if (pageinit.current >4 && pageinit.current <= pageinit.pageNum-5) {
                        var start  = pageinit.current - 2,end = pageinit.current + 2;
                    }else if(pageinit.current >4 && pageinit.current > pageinit.pageNum-5){
                        var start  = pageinit.pageNum - 4,end = pageinit.pageNum;
                    }else{
                        var start = 1,end = 9;
                    }
                    for (;start <= end;start++) {
                        if (start <= pageinit.pageNum && start >=1) {
                            if (start == pageinit.current) {
                                obj.append('<span class="current">'+ start +'</span>');
                            } else if(start == pageinit.current+1){
                                var link = pageinit.urlPrefix + "/" + start;
                                obj.append('<a href=' + link + '  target="_blank" class="zxfPagenum nextpage">'+ start +'</a>');
                            }else{
                                var link = pageinit.urlPrefix + "/" + start;
                                obj.append('<a href=' + link + '  target="_blank" class="zxfPagenum">'+ start +'</a>');
                            }
                        }
                    }
                    if (end < pageinit.pageNum) {
                        obj.append('<span class="dotspan">...</span>');
                    }
					/*下一页*/
                    if (pageinit.current >= pageinit.pageNum) {
                        obj.remove('.nextbtn');
                        obj.append('<span class="disabled">下一页</span>');
                    } else{
                        var link = pageinit.urlPrefix + "/" + (pageinit.current + 1);
                        obj.append('<a href=' + link + ' target="_blank" class="nextbtn">下一页</a>');
                    }
					/*尾部*/
                    obj.append('<span class="sumpagespan">'+'共'+'<b>'+pageinit.pageNum+'</b>'+'页，'+'</span>');
                    obj.append('<span class="tospan">'+'到第'+'<input type="text" class="zxfinput" value=""/>'+'页'+'</span>');
                    obj.append('<span class="zxfokbtn">'+'确定'+'</span>');
                }());
			}
		},
		bindEvent:function(obj,pageinit){
			return (function(){
				obj.on("click","a.prebtn",function(){
					var cur = parseInt(obj.children("span.current").text());
					var current = $.extend(pageinit, {"current":cur-1});
					zp.addhtml(obj,current);
					if (typeof(pageinit.backfun)=="function") {
						pageinit.backfun(current);
					}
				});
				obj.on("click","a.zxfPagenum",function(){
					var cur = parseInt($(this).text());
					var current = $.extend(pageinit, {"current":cur});
					zp.addhtml(obj,current);
					if (typeof(pageinit.backfun)=="function") {
						pageinit.backfun(current);
					}
				});
				obj.on("click","a.nextbtn",function(){
					var cur = parseInt(obj.children("span.current").text());
					var current = $.extend(pageinit, {"current":cur+1});
					zp.addhtml(obj,current);
					if (typeof(pageinit.backfun)=="function") {
						pageinit.backfun(current);
					}
				});
				obj.on("click","span.zxfokbtn",function(){
                    var cur = parseInt($("input.zxfinput").val());
                    var re = /^[0-9]+.?[0-9]*/;//判断字符串是否为数字//判断正整数/[1−9]+[0−9]∗]∗/
                    if (!re.test(cur)) {
                        alert("请输入数字");
                        $(".zxfinput").val("");
                        return;
                    }
                    var link = pageinit.urlPrefix + "/" + cur;
                    window.location.href = link;
					// var current = $.extend(pageinit, {"current":cur});
					// zp.addhtml(obj,{"current":cur,"pageNum":pageinit.pageNum});
					// if (typeof(pageinit.backfun)=="function") {
					// 	pageinit.backfun(current);
					// }

				});
			}());
		}
	}
	$.fn.createPage = function(options){
		var pageinit = $.extend({
			pageNum : 15,
			current : 1,
			urlPrefix: "",
			isBackend:false,
			backfun : function(){}
		},options);
		if(pageinit.pageNum > 1)
		zp.init(this,pageinit);
	}
}(jQuery));
