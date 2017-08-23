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
    function searchEnt() {
    	var name=$("#keyword").val();
    	if (name.replace(/(^\s*)|(\s*$)/g, "") != "") {
    		$.ajax({
	    		type : "POST",
				url : urlHead+'/noCreditPunish/info/getEnt',
				datatype : 'JSON',
				data : {"entName":name},
				async : true,
				success : function(json) {
					if(json != ""&&json != null){
						infojson= json;
						$("#uniCode").val(json.uniCode);
						$("#leRep").val(json.leRep);
						$("#cerType").val(json.cerType);
						$("#cerTypeDesc").val(cardTrans(json.cerType));
						$("#cardNo").val(json.cardNo);
						$("#priPID").val(json.priPID);
						detailEntExist(json.priPID, "ent");
						$("#confirm").show();
					}else
						layer.msg("没有查询到结果", {time: 1500}, function() {});
				}
    		});
    	}
    }
    
    function cardTrans(code) {
    	if(code=="10"){
    		return "居民身份证";
    	}else if(code=="20"){
    		return "军官证";
    	}else if(code=="30"){
    		return "警官证";
    	}else if(code=="40"){
    		return "外国(地区)护照";
    	}else if(code=="52"){
    		return "香港身份证";
    	}else if(code=="54"){
    		return "澳门身份证";
    	}else if(code=="56"){
    		return "台湾身份证";
    	}else if(code=="90"){
    		return "其他有效身份证件";
    	}
    }
    
    //查询是否有惩戒记录
    function detailEntExist(key, type) {
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
    
    //监听text内容改变事件
    $("#keyword").bind('change paste keyup',function(){
    	$("#confirm").hide();
	});
    
    //事件绑定
    function bind() {
        util.bindEvents([{
            el: '#searchEnt',
            event: 'click',
            handler: function() {
            	searchEnt();
            }
        }, {
            el: '#confirm',
            event: 'click',
            handler: function() {
            	layer.close(infojson);
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