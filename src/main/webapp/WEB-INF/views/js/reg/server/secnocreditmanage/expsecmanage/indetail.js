require(['component/iframeLayer','component/dataTable', 'common/util', 'common/http','handlebars','jquery','jquery.serialize','laydate','tab'], function (layer,dataTable, util, http,handlebars) {
	var uid = $("#uid").val();
	init();
	 
	 function init(){
		 bind();
	 }
	 
	   //事件绑定 
	    function bind() {
	        util.bindEvents([ {
	            el: '#close',
	            event: 'click',
	            handler: function() {
	            	layer.msg("已关闭！", {time: 1000}, function() {});
	            	layer.close();
	            }
	        }, {
	            el: '#decide',
	            event: 'click',
	            handler: function() {
	            	window.open("/reg/server/seriouscrime/doEnExpManageDecision?uid="+uid,"_blank");
	            }
	        }, {
	            el: '#approval',
	            event: 'click',
	            handler: function() {
	            	window.open("/reg/server/seriouscrime/doEnExpManageApproval?uid="+uid,"_blank");
	            }
	        }
			])
	    }

})



