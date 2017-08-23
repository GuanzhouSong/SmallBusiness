require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http', 'handlebars', 'jquery','jquery.serialize','laydate','jquery.validate','pagination'], function (layer, dataTable, util, http,handlebars) {

    init();
    /**
     * 初始化函数集合
     */
    function init() {
        loadBulletins();
        bind();
    }

//    var viewTemplate = handlebars.compile($('#viewTemplate').html());

    function loadBulletins(){
        //加载通知公告数据
        http.httpRequest({
            type: 'post',
            url: '/syn/system/bulletin/list.json',
            success: function (data) {
                if (data.status == 'success') {
                	var length = data.data.length;
                	var jointHtml="";
                	var moreFlag = $('#moreFlag').val();
                	var linehtml = "";
                	if(length>0){
                		for(var i=0;i<length;i++){
                			//处理
                			var readFormat = data.data[i].isRead;
                            if(readFormat=='1'){
                            	readFormat = '已读';
                            }else{
                            	readFormat = '阅读';
                            }
                            
                            var collectionFormat = data.data[i].isCollection;
                            if(collectionFormat=='1'){
                            	collectionFormat = '已收藏';
                            }else{
                            	collectionFormat = '收藏';
                            }
                    		if(readFormat == '已读'){
                    			linehtml = "<li><a href='javascript:void(0);' bulletinId='"+data.data[i].bulletinID+
                        		"' isRead='"+data.data[i].isRead+"' class='read js-read'>"+"<span>"+data.data[i].setTime+
                        		"</span>"+data.data[i].title+
                        		"[ " +data.data[i].setDeptname+" ]</a></li>";
                    		}else{
                    			linehtml ="<li><a href='javascript:void(0);' bulletinId='"+data.data[i].bulletinID+
                        		"' isRead='"+data.data[i].isRead+"' class='read js-read light'><span>"+data.data[i].setTime+
                        		"</span>"+data.data[i].title+"[ " +data.data[i].setDeptname+" ]</a></li>";
                    		}
                            jointHtml = jointHtml+linehtml;
                    		if(i==2 && moreFlag == 0){
                    			break;
                    		}
                		}
                		jointHtml = jointHtml+"</ul>"

                	}
                	if(length > 3 && moreFlag == 0){
                		$('#bulletinMore').show();
                	}
                	$(".notice-info-list").append(jointHtml);
                }
            }
        });
    }

    function bind() {
        util.bindEvents([
            {
                el: '.js-read',
                event: 'click',
                handler: function (event) {
                	var index=$(this).closest('li').index();
                    var uid=$(this).attr("bulletinId");
                    var isRead=$(this).attr("isRead");
                    var parent =$(this).closest('li');//取到当前元素的父元素
                    var bulletinUrl=window._CONFIG.chooseUrl+'/system/bulletin/read?bulletinId=' + uid;
                    if(isRead=='1'){
                        bulletinUrl=window._CONFIG.chooseUrl+'/system/bulletin/scan?bulletinId=' + uid;
                    }
                    layer.dialog({
                        title: '查看公告',
                        area: ['1024px', '600px'],
                        content: bulletinUrl,
                        callback: function (data) {
                        	debugger
//                            parent.find('.js-read-set').text('已读');
                            $('.notice-info-list li').eq(index).find('a').removeClass('light');
//                            parent.removeClass('no-read');
                            }
                    });
                }
            },
            {
                el: '.collection',
                event: 'click',
                handler: function (event) {
                	var target=$(event.target);
                    var uid=target.attr("bulletinId");
                    var isCollection=target.attr("isCollection");
                    if(isCollection=='1'){
                        layer.msg("已收藏！无需重复收藏~", {time: 1000}, function () {
                        });
                        return;
                    }else{
                        http.httpRequest({
                            type: 'post',
                            url: window._CONFIG.chooseUrl+'/system/bulletin/collection',
                            data:{bulletinId:uid},
                            success: function (data) {
                            	if (data.status == 'success') {
                                    layer.msg("收藏成功！", {time: 1000}, function () {
                                        target.html("已收藏");
                                        target.attr("isCollection","1");
                                    });
                                }
                            }
                        });
                    }
                }
            },
            {
            	el: '#bulletinMore',
                event: 'click',
                handler: function (event) {
                	var url = window._CONFIG.chooseUrl+'/system/bulletin/reglistmore';
	        		window.open(url,'_blank','');
                }
            	
            },
            {
                el: '.js-charts',  //打开可视化页面
                event: 'click',
                handler: function () {
                    var $this = $(this);
                    var tab_id = $this.data('id');
                    if(!tab_id) return;
                    var url = window._CONFIG.chooseUrl+'/charts?tabId='+tab_id;
                    window.open(url);

                }

            }
        ])
    }

})
