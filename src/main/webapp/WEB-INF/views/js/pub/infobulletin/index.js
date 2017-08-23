//全局变量state
var state ;
require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http', 'handlebars', 'jquery','jquery.serialize','laydate','pagination'], function (layer, dataTable, util, http, handlebars) {
    init();
    /**
     * 初始化函数集合
     */
    function init() {
    	//初始化展示
    	state = $("#initSelected").attr("state");
    	loadListDataByState(state)
    	
    	bind();
    }
    
	$('#pubTitle').keydown(function(e){
	    if(e.keyCode == 13){
	    	loadListDataByState(state);
	    }
	});
	
	 function loadNoticeListData(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:state,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide").show();
	            		$("#nodata").text("").hide();
	            		$('.pagination').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:5,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadNoticeListData(page,5);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id').html(myTemplate(data));
		                $("#notice-list-id a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,state);
		                });
		                $("#notice-total").text(itemCount);
		                var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
		                $("#notice_pageNum").text(pageNum);
	            	}else{
	            		$(".notice-list").html("");
	            		$("#pageforhide").hide();
	            		if(state !='11'){
	            			$("#nodata").show().text("暂无"+$("#border-title span").text());
	            		}else{
	            			$("#nodata").show().text("暂无正常注销公告");
	            		}
	            	}
	            }
	        });
	    }
	 
	 function loadAnomalyList01(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:1,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide01").show();
	            		$("#nodata01").text("").hide();
	            		$('#pagination01').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:5,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadAnomalyList01(page,5);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id01').html(myTemplate(data));
		                $("#notice-list-id01 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,1);
		                });
		                $("#notice-total01").text(itemCount);
		                var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
		                $("#notice_pageNum01").text(pageNum);
	            	}else{
	            		$("#notice-list-id01").html("");
	            		$("#pageforhide01").hide();
	            		$("#nodata01").show().text("暂无经营异常名录列入公告");
	            	}
	            }
	        });
	    }
	 
	 function loadAnomalyList02(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:2,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide02").show();
	            		$("#nodata02").text("").hide();
	            		$('#pagination02').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:5,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadAnomalyList02(page,5);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id02').html(myTemplate(data));
		                $("#notice-list-id02 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,2);
		                });
		                $("#notice-total02").text(itemCount);
		                var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
		                $("#notice_pageNum02").text(pageNum);
	            	}else{
	            		$("#notice-list-id02").html("");
	            		$("#pageforhide02").hide();
	            		$("#nodata02").show().text("暂无经营异常名录移出公告");
	            	}
	            }
	        });
	    }
	 
	 function loadAnomalyList03(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:3,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide03").show();
	            		$("#nodata03").text("").hide();
	            		$('#pagination03').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:5,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadAnomalyList03(page,5);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list3").html());
		                $('#notice-list-id03').html(myTemplate(data));
		                $("#notice-list-id03 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,3);
		                });
		                $("#notice-total03").text(itemCount);
		                var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
		                $("#notice_pageNum03").text(pageNum);
	            	}else{
	            		$("#notice-list-id03").html("");
	            		$("#pageforhide03").hide();
	            		$("#nodata03").show().text("暂无经营异常名录期满三年提醒公告");
	            	}
	            }
	        });
	    }
	 
	 function loadExpcreList04(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:4,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide04").show();
	            		$("#nodata04").text("").hide();
	            		$('#pagination04').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:5,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadExpcreList04(page,5);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id04').html(myTemplate(data));
		                $("#notice-list-id04 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,4);
		                });
		                $("#notice-total04").text(itemCount);
		                var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
		                $("#notice_pageNum04").text(pageNum);
	            	}else{
	            		$("#notice-list-id04").html("");
	            		$("#pageforhide04").hide();
	            		$("#nodata04").show().text("暂无严重违法失信企业名单列入公告");
	            	}
	            }
	        });
	    }
	 
	 function loadExpcreList05(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:5,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide05").show();
	            		$("#nodata05").text("").hide();
	            		$('#pagination05').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:5,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadExpcreList05(page,5);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id05').html(myTemplate(data));
		                $("#notice-list-id05 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,5);
		                });
		                $("#notice-total05").text(itemCount);
		                var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
		                $("#notice_pageNum05").text(pageNum);
	            	}else{
	            		$("#notice-list-id05").html("");
	            		$("#pageforhide05").hide();
	            		$("#nodata05").show().text("暂无严重违法失信企业名单移出公告");
	            	}
	            }
	        });
	    }
	 
	 function loadSpotList06(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:6,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide06").show();
	            		$("#nodata06").text("").hide();
	            		$('#pagination06').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:5,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadSpotList06(page,5);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id06').html(myTemplate(data));
		                $("#notice-list-id06 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,6);
		                });
		                $("#notice-total06").text(itemCount);
		                var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
		                $("#notice_pageNum06").text(pageNum);
	            	}else{
	            		$("#notice-list-id06").html("");
	            		$("#pageforhide06").hide();
	            		$("#nodata06").show().text("暂无抽查公告");
	            	}
	            }
	        });
	    }
	 
	 function loadSpotList07(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:7,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide07").show();
	            		$("#nodata07").text("").hide();
	            		$('#pagination07').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:5,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadSpotList07(page,5);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id07').html(myTemplate(data));
		                $("#notice-list-id07 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,7);
		                });
		                $("#notice-total07").text(itemCount);
		                var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
		                $("#notice_pageNum07").text(pageNum);
	            	}else{
	            		$("#notice-list-id07").html("");
	            		$("#pageforhide07").hide();
	            		$("#nodata07").show().text("暂无检查公告");
	            	}
	            }
	        });
	    }
	 function loadSpotList17(page, length,callback) {
		 var pubTitle = $("#pubTitle").val();
		 var regOrgLike = $("#regOrgLike").val();
		 var params = {pubType:17,pubTitle:pubTitle,auditArea:regOrgLike};
		 http.httpRequest({
			 url: '/pub/infobulletin/list.json',
			 data: {start: page*length,length: length,params:params},
			 success: function (data) {
				 var itemCount = data.recordsTotal;
				 if(itemCount != 0){
					 $("#pageforhide17").show();
					 $("#nodata07").text("").hide();
					 $('#pagination17').pagination(itemCount, {
						 prev_text: '<i class="icon-triangle-left"></i>上一页',
						 next_text: '下一页<i class="icon-triangle-right"></i>',
						 num_display_entries: 3,
						 num_edge_entries: 2,
						 current_page: page,
						 items_per_page:5,
						 link_to: 'javascript:void(0)',
						 ellipse_text: '...',
						 first_text: '首页',
						 last_text: '末页',
						 callback:function(page,jq){
							 loadSpotList17(page,5);
						 }
					 });
					 var myTemplate = handlebars.compile($("#notice_li_list").html());
					 $('#notice-list-id17').html(myTemplate(data));
					 $("#notice-list-id17 a").click(function(){
						 var linkUID = $(this).attr("linkUID");
						 showdetail(linkUID,17);
					 });
					 $("#notice-total17").text(itemCount);
					 var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
					 $("#notice_pageNum17").text(pageNum);
				 }else{
					 $("#notice-list-id17").html("");
					 $("#pageforhide17").hide();
					 $("#nodata17").show().text("年度抽查工作计划公告");
				 }
			 }
		 });
	 }
	 
	 function loadSpotList18(page, length,callback) {
		 var pubTitle = $("#pubTitle").val();
		 var regOrgLike = $("#regOrgLike").val();
		 var params = {pubType:18,pubTitle:pubTitle,auditArea:regOrgLike};
		 http.httpRequest({
			 url: '/pub/infobulletin/list.json',
			 data: {start: page*length,length: length,params:params},
			 success: function (data) {
				 var itemCount = data.recordsTotal;
				 if(itemCount != 0){
					 $("#pageforhide18").show();
					 $("#nodata18").text("").hide();
					 $('#pagination18').pagination(itemCount, {
						 prev_text: '<i class="icon-triangle-left"></i>上一页',
						 next_text: '下一页<i class="icon-triangle-right"></i>',
						 num_display_entries: 3,
						 num_edge_entries: 2,
						 current_page: page,
						 items_per_page:5,
						 link_to: 'javascript:void(0)',
						 ellipse_text: '...',
						 first_text: '首页',
						 last_text: '末页',
						 callback:function(page,jq){
							 loadSpotList18(page,5);
						 }
					 });
					 var myTemplate = handlebars.compile($("#notice_li_list").html());
					 $('#notice-list-id18').html(myTemplate(data));
					 $("#notice-list-id18 a").click(function(){
						 var linkUID = $(this).attr("linkUID");
						 showdetail(linkUID,18);
					 });
					 $("#notice-total18").text(itemCount);
					 var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
					 $("#notice_pageNum18").text(pageNum);
				 }else{
					 $("#notice-list-id18").html("");
					 $("#pageforhide18").hide();
					 $("#nodata18").show().text("抽查任务公告");
				 }
			 }
		 });
	 }
	 
	 function loadPunishList08(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:8,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide08").show();
	            		$("#nodata08").text("").hide();
	            		$('#pagination08').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:10,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadPunishList08(page,10);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id08').html(myTemplate(data));
		                $("#notice-list-id08 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,8);
		                });
		                $("#notice-total08").text(itemCount);
		                var pageNum = itemCount%10==0?itemCount/10:Math.ceil(itemCount/10);
		                $("#notice_pageNum08").text(pageNum);
	            	}else{
	            		$("#notice-list-id08").html("");
	            		$("#pageforhide08").hide();
	            		$("#nodata08").show().text("暂无行政处罚公告");
	            	}
	            }
	        });
	    }
	 
	 function loadjusticeList09(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:9,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide09").show();
	            		$("#nodata09").text("").hide();
	            		$('#pagination09').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:5,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadjusticeList09(page,5);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id09').html(myTemplate(data));
		                $("#notice-list-id09 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,9);
		                });
		                $("#notice-total09").text(itemCount);
		                var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
		                $("#notice_pageNum09").text(pageNum);
	            	}else{
	            		$("#notice-list-id09").html("");
	            		$("#pageforhide09").hide();
	            		$("#nodata09").show().text("暂无司法股权冻结公告");
	            	}
	            }
	        });
	    }
	 
	 function loadjusticeList10(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:10,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide10").show();
	            		$("#nodata10").text("").hide();
	            		$('#pagination10').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:5,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadjusticeList10(page,5);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id10').html(myTemplate(data));
		                $("#notice-list-id10 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,10);
		                });
		                $("#notice-total10").text(itemCount);
		                var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
		                $("#notice_pageNum10").text(pageNum);
	            	}else{
	            		$("#notice-list-id10").html("");
	            		$("#pageforhide10").hide();
	            		$("#nodata10").show().text("暂无司法股东变更公告");
	            	}
	            }
	        });
	    }
	 
	 function loadlogoutList12(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:12,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide12").show();
	            		$("#nodata12").text("").hide();
	            		$('#pagination12').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:10,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadlogoutList12(page,10);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id12').html(myTemplate(data));
		                $("#notice-list-id12 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,12);
		                });
		                $("#notice-total12").text(itemCount);
		                var pageNum = itemCount%10==0?itemCount/10:Math.ceil(itemCount/10);
		                $("#notice_pageNum12").text(pageNum);
	            	}else{
	            		$("#notice-list-id12").html("");
	            		$("#pageforhide12").hide();
	            		$("#nodata12").show().text("暂无简易注销公告");
	            	}
	            }
	        });
	    }
	 
	 function loadotherbullList13(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:13,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide13").show();
	            		$("#nodata13").text("").hide();
	            		$('#pagination13').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:5,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadotherbullList13(page,5);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id13').html(myTemplate(data));
		                $("#notice-list-id13 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,13);
		                });
		                $("#notice-total13").text(itemCount);
		                var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
		                $("#notice_pageNum13").text(pageNum);
	            	}else{
	            		$("#notice-list-id13").html("");
	            		$("#pageforhide13").hide();
	            		$("#nodata13").show().text("暂无拟吊销企业营业执照听证公告");
	            	}
	            }
	        });
	    }
	 
	 function loadotherbullList14(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:14,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide14").show();
	            		$("#nodata14").text("").hide();
	            		$('#pagination14').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:5,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadotherbullList14(page,5);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id14').html(myTemplate(data));
		                $("#notice-list-id14 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,14);
		                });
		                $("#notice-total14").text(itemCount);
		                var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
		                $("#notice_pageNum14").text(pageNum);
	            	}else{
	            		$("#notice-list-id14").html("");
	            		$("#pageforhide14").hide();
	            		$("#nodata14").show().text("暂无吊销企业营业执照公告");
	            	}
	            }
	        });
	    }
	 
	 function loadotherbullList15(page, length,callback) {
		 var pubTitle = $("#pubTitle").val();
		 var regOrgLike = $("#regOrgLike").val();
		 var params = {pubType:15,pubTitle:pubTitle,auditArea:regOrgLike};
		 http.httpRequest({
			 url: '/pub/infobulletin/list.json',
			 data: {start: page*length,length: length,params:params},
			 success: function (data) {
				 var itemCount = data.recordsTotal;
				 if(itemCount != 0){
					 $("#pageforhide15").show();
					 $("#nodata15").text("").hide();
					 $('#pagination15').pagination(itemCount, {
						 prev_text: '<i class="icon-triangle-left"></i>上一页',
						 next_text: '下一页<i class="icon-triangle-right"></i>',
						 num_display_entries: 3,
						 num_edge_entries: 2,
						 current_page: page,
						 items_per_page:5,
						 link_to: 'javascript:void(0)',
						 ellipse_text: '...',
						 first_text: '首页',
						 last_text: '末页',
						 callback:function(page,jq){
							 loadotherbullList15(page,5);
						 }
					 });
					 var myTemplate = handlebars.compile($("#notice_li_list").html());
					 $('#notice-list-id15').html(myTemplate(data));
					 $("#notice-list-id15 a").click(function(){
						 var linkUID = $(this).attr("linkUID");
						 showdetail(linkUID,15);
					 });
					 $("#notice-total15").text(itemCount);
					 var pageNum = itemCount%5==0?itemCount/5:Math.ceil(itemCount/5);
					 $("#notice_pageNum15").text(pageNum);
				 }else{
					 $("#notice-list-id15").html("");
					 $("#pageforhide15").hide();
					 $("#nodata15").show().text("暂无企业登记机关注销公告");
				 }
			 }
		 });
	 }
	 
	 function loadPressReportList16(page, length,callback) {
			var pubTitle = $("#pubTitle").val();
			var regOrgLike = $("#regOrgLike").val();
	    	var params = {pubType:16,pubTitle:pubTitle,auditArea:regOrgLike};
	        http.httpRequest({
	            url: '/pub/infobulletin/list.json',
	            data: {start: page*length,length: length,params:params},
	            success: function (data) {
	            	var itemCount = data.recordsTotal;
	            	if(itemCount != 0){
	            		$("#pageforhide16").show();
	            		$("#nodata16").text("").hide();
	            		$('#pagination16').pagination(itemCount, {
		                    prev_text: '<i class="icon-triangle-left"></i>上一页',
		                    next_text: '下一页<i class="icon-triangle-right"></i>',
		                    num_display_entries: 3,
		                    num_edge_entries: 2,
		                    current_page: page,
		                    items_per_page:10,
		                    link_to: 'javascript:void(0)',
		                    ellipse_text: '...',
		                    first_text: '首页',
		                    last_text: '末页',
		                    callback:function(page,jq){
		                    	loadPressReportList16(page,10);
		                    }
		                });
		                var myTemplate = handlebars.compile($("#notice_li_list").html());
		                $('#notice-list-id16').html(myTemplate(data));
		                $("#notice-list-id16 a").click(function(){
		                	var linkUID = $(this).attr("linkUID");
		                	showdetail(linkUID,16);
		                });
		                $("#notice-total16").text(itemCount);
		                var pageNum = itemCount%10==0?itemCount/10:Math.ceil(itemCount/10);
		                $("#notice_pageNum16").text(pageNum);
	            	}else{
	            		$("#notice-list-id16").html("");
	            		$("#pageforhide16").hide();
	            		$("#nodata16").show().text("暂无催报公告");
	            	}
	            }
	        });
	    }
	 
	 
	 //进入详情页面
	function showdetail(linkUID,state){
		var pubType = null;
		http.httpRequest({
            url: '/pub/infobulletin/enCode',
            serializable: false,
            data: {pubType:state},
            type: 'get',
            success: function (data) {
            	pubType = data.data;
            	window.open('/pub/infobulletin/showDetails?UID='+linkUID+"&pubType="+pubType);
            }
        });
	}
	
	//列表加载
	function loadListDataByState(state){
			$(".subnavbar a").each(function(){
				var _state = $(this).attr("state");
				if(state == _state){
					$("#border-title span").text("").text($(this).attr("desc"));
				}
	    	})
	    	if(state == '01'){
	    		$(".bulletin-type").hide();
        		$(".info-anomaly").show();
        		loadAnomalyList01(0,5);
        		loadAnomalyList02(0,5);
        		loadAnomalyList03(0,5);
        	}else if(state == '02'){
        		loadExpcreList04(0,5);
        		loadExpcreList05(0,5);
        	}else if(state == '03'){
        		loadSpotList06(0,5);
        		loadSpotList07(0,5);
        		loadSpotList17(0,5);
        		loadSpotList18(0,5);
        	}else if(state == '04'){
        		loadPunishList08(0,10);
        	}else if(state == '05'){
        		loadjusticeList09(0,5);
        		loadjusticeList10(0,5);
        	}else if(state == '06'){
        		loadlogoutList12(0,10);
        	}else if(state == '07'){
        		loadotherbullList13(0,5);
        		loadotherbullList14(0,5);
        		loadotherbullList15(0,5);
        	}else if(state == '08'){
        		loadPressReportList16(0,10);
        	}else{
        		loadNoticeListData(0,5);
        	}
    	}
	
	function restoreDefalut(state){
		//搜索条件置空，区域筛选回复默认
    	$("#pubTitle").val("");
    	$("#regOrgLike").val("");
    	$(".notice-area a").each(function(){
    		$(this).removeClass("selected");
    	});
    	$("#notice-area-id a:first").addClass("selected");
    	//当选择抽查检查公告时，不显示区域筛选
    	if(state == '6' || state == '7' || state == '03'){
    		$(".query-area").hide();
    	}else{
    		$(".query-area").show();
    	}
	}
	
	 function bind() {
	        util.bindEvents([
	        {
	            el: '.subnavbar a',
	            event: 'click',
	            handler: function() {
	            	state =	$(this).attr("state");
	            	$(".sidebar a").each(function(){
	            		$(this).removeClass('selected');
	            	})
	            	$(this).addClass("selected");
	            	$(".bulletin-type").hide();
	            	$("#single-box").show();
	            	//初始化
	            	restoreDefalut(state);
	            	//数据加载
	            	loadListDataByState(state);
	             }
	        },{
	            el: '.info',
	            event: 'click',
	            handler: function() {
	            	state =	$(this).attr("state");
	            	$(".sidebar a").each(function(){
	            		$(this).removeClass('selected');
	            	})
	            	$(this).addClass("selected");
	            	//初始化
	            	restoreDefalut(state);
	            	if(state == '01'){
	            		$(".bulletin-type").hide();
	            		$(".info-anomaly").show();
	            		loadAnomalyList01(0,5);
	            		loadAnomalyList02(0,5);
	            		loadAnomalyList03(0,5);
	            	}
	            	if(state == '02'){
	            		$(".bulletin-type").hide();
	            		$(".info-expcre").show();
	            		loadExpcreList04(0,5);
	            		loadExpcreList05(0,5);
	            	}
	            	if(state == '03'){
	            		$(".bulletin-type").hide();
	            		$(".info-checkspot").show();
	            		loadSpotList06(0,5);
	            		loadSpotList07(0,5);
	            		loadSpotList17(0,5);
	            		loadSpotList18(0,5);
	            	}
	            	if(state == '04'){
	            		$(".bulletin-type").hide();
	            		$(".info-punish").show();
	            		loadPunishList08(0,10);
	            	}
	            	if(state == '05'){
	            		$(".bulletin-type").hide();
	            		$(".info-justice").show();
	            		loadjusticeList09(0,5);
	            		loadjusticeList10(0,5);
	            	}
	            	if(state == '06'){
	            		$(".bulletin-type").hide();
	            		$(".info-logout").show();
	            		loadlogoutList12(0,10);
	            	}
	            	if(state == '07'){
	            		$(".bulletin-type").hide();
	            		$(".info-otherbull").show();
	            		loadotherbullList13(0,5);
	            		loadotherbullList14(0,5);
	            		loadotherbullList15(0,5);
	            	}
	            	if(state == '08'){
	            		$(".bulletin-type").hide();
	            		$(".info-report").show();
	            		loadPressReportList16(0,10);
	            	}
	             }
	        },{
	        	el: '.notice-area a',
	            event: 'click',
	            handler: function() {
	            	var regOrgLike = $(this).attr("regOrgLike");
	            	$(".notice-area a").each(function(){
	            		if($(this).attr("regOrgLike") == regOrgLike){
	            			$(this).addClass('selected');
	            			$("#regOrgLike").val(regOrgLike);
	            		}else{
	            			$(this).removeClass('selected');
	            		}
	            	});
	            	loadListDataByState(state);
	             }
	        },{
	            el: '#search_notice_list',//标题查询
	            event: 'click',
	            handler: function() {
	            	loadListDataByState(state);
	             }
	        }])
	    }

})
