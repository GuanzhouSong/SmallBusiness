require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http', 'handlebars', 'jquery','jquery.serialize','common/validateRules','common/ajaxfileupload'], function(layer, dataTable, util, http, handlebars) {
	var data = new Object(); 
	init();
	 
	 function init(){
		 bind();
		 formValid();
	 }
	 
	 function formValid(){
	    	$('#content_form').validate({
	            rules: {
	            	auditName: { 
	                    required: true
	                },
	                auditOpin: { 
	                    required: true
	                }
	            },
	            messages: {},
	            onkeyup:false,
	            onfocusout:function(el){
	                $(el).valid();
	            },
	            showErrors:function(errorMap,errorList){
	                for(var i in errorMap){
	                    layer.tips(errorMap[i],$('#content_form input[name='+i+'],textarea[name='+i+']'),{
	                        tips:3,
	                        tipsMore:true,
	                        ltype:0
	                    });
	                }
	            },
	            submitHandler: function () {
	                	var formParam = $('#content_form').serializeObject();
	                    http.httpRequest({
	                        url: '/reg/server/seriouscrime/doSaveInEndReview',
	                        serializable: false,
	                        data: formParam,
	                        type: 'post',
	                        success: function (json) {
	                        	if(json.status=="success"){
	                        		data.reload = true;
	        						layer.msg(json.msg, {time: 800}, function() {});
	        						layer.close(data);
	        					}else{
	        						layer.msg(json.msg, {time: 800}, function() {});
	        					}
	                        }
	                    });
	            }
	        })
	    	
	    }
	 
	   //事件绑定 
	    function bind() {
	        util.bindEvents([ {
	            el: '#cancel',
	            event: 'click',
	            handler: function() {
	            	layer.msg("您取消了编辑！", {time: 1000}, function() {});
	            	layer.close();
	            }
	        }, {
	            el: '#save',
	            event: 'click',
	            handler: function() {
	            	$("#content_form").submit();
	            }
	        }, {
	            el: '.auditCheck',
	            event: 'click',
	            handler: function() {
	            	var check = $(this).val(); 
	            	if(check == '0')
	            		$("#auditOpin").val("不同意将其列入严重违法失信名单。");
	            	if(check == '1')
	            		$("#auditOpin").val("同意，将其列入严重违法失信名单。");
	            }
	        }
			])
	    }

})



