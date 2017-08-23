require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http', 'jquery','jquery.serialize'], function(layer, dataTable, util, http) {
	
	var urlHead=window._CONFIG.urlHead;
	init();
	
	/**
     * 初始化函数集合
     */
    function init() {
        //formValid();
        bind();
    }
    
    //类型（企业，自然人）
    var type=$("#type").val();
    
    //信息json，以便确定后给dialog外部使用
    var infojson={};
    
    //查询
    function searchMan() {
    	var keyword=$("#keyword").val();
    	if (keyword.replace(/(^\s*)|(\s*$)/g, "") != "") {
    		$.ajax({
	    		type : "POST",
				url : urlHead+'/noCreditPunish/info/getMan',
				datatype : 'JSON',
				data : {"keyword":keyword},
				async : true,
				success : function(json) {
					if(json != ""&&json != null){
						$("#litiName").val(json.litiName);
						cardTrans(json.cerType);
						$("#phone").val(json.phone);
						$("#cardNo").val(json.cardNo);
						detailManExist(json.cardNo, "man");
					}else
						layer.msg("没有查询到结果", {time: 1500}, function() {});
				}
    		});
    	}
    }
    
    function cardTrans(code) {
    	$("#"+code).attr("selected","selected");
    }
    
  //监听text内容改变事件
    $("#keyword").bind('blur',function(){
    	detailManExist($("#keyword").val().replace(/(^\s*)|(\s*$)/g, ""), "man");
	});
    
    //查询是否有惩戒记录
    function detailManExist(key, type) {
    	$.ajax({
    		type : "POST",
			url : urlHead+'/noCreditPunish/info/detailExist',
			datatype : 'JSON',
			data : {"key":key, "type":type},
			async : true,
			success : function(json) {
				if(json=="exist"){
					infojson.record="有";
					$("#record").val("有")
				}else {
					$("#record").val("无");
					infojson.record="无";
				}
			}
    	});
    }
    
    //提交
    function submit(){
    	var card=$("#keyword").val().replace(/(^\s*)|(\s*$)/g, "");
    	var name=$("#litiName").val().replace(/(^\s*)|(\s*$)/g, "");
    	var cerType=$("#cerType").val().replace(/(^\s*)|(\s*$)/g, "");
    	var cerTypeDesc=$("#cerType").find("option:selected").text().replace(/(^\s*)|(\s*$)/g, "");
    	var phone=$("#phone").val().replace(/(^\s*)|(\s*$)/g, "");
    	infojson.litiName=name;
    	infojson.cardNo=card;
    	infojson.cerType=cerType;
    	infojson.cerTypeDesc=cerTypeDesc;
    	infojson.phone=phone;
    	if(card==""||name==""){
    		layer.msg("请填写 红色* 标注的内容", {time: 1500}, function() {
    		});
    	}else{
        	layer.close(infojson);
    	}
    }
    
    //事件绑定
    function bind() {
        util.bindEvents([{
            el: '#searchMan',
            event: 'click',
            handler: function() {
            	searchMan();
            }
        }, {
            el: '#confirm',
            event: 'click',
            handler: function() {
            	submit();
            }
        }, {
            el: '#close',
            event: 'click',
            handler: function() {
            	layer.close();
            }
        }])
    }
   
});