require(['component/iframeLayer','component/dataTable', 'common/util', 'common/http','handlebars','jquery','jquery.serialize','laydate','tab'], function (layer,dataTable, util, http,handlebars) {
	var data = new Object(); 
	init();
	 
	 function init(){
		 bind();
	 }
	 
	 //严违列入申请修改
	 function save(){
		 var formParam = $('#content_form').serializeObject();
         http.httpRequest({
             url: '/reg/server/seriouscrime/doSaveExpSecInEdit',
             serializable: false,
             data: formParam,
             type: 'post',
             success: function (json) {
             	if(json.status=="success"){
             		data.reload = true;
						layer.msg(json.msg, {time: 1000}, function() {});
						layer.close(data);
					}else{
						layer.msg(json.msg, {time: 1000}, function() {});
					}
             }
         });
	 }
	 
	 
	 function check(){
		 var num = $("#secbased option:selected").val();
		 var str = "";
		 var main = "";
		 for(var i=1;i<=num;i++){
			 main = $.trim($(".tr"+i+" input").val());
			 str += main +";";
     		if(main==null||main==""){
     			layer.msg("所选依据不能为空!", {time: 1000}, function() {});
     			return false;
     		}
     	}
		 $("#secBasedStr").val(str);
		 return true;
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
	            	if(check())
	            		save();
	            }
	        }, {
	            el: '#secbased',
	            event: 'change',
	            handler: function() {
	            	var num = $("#secbased option:selected").val();
	            	$(".tr").hide();
	            	for(var i=1;i<=num;i++){
	            		$(".tr"+i).show();
	            	}
	            }
	        }
			])
	    }

})



