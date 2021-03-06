require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http', 'jquery', 'jquery.serialize', 'laydate', 'tab'], function (layer,dataTable, util, http) {

	init();
	 
	 function init(){
		 bind();
	 }
	 
	 //加载严违失信信息
	 function history(){
		 $.ajax({
    		type : "POST",
    		url : '/reg/server/seriouspunish/getSrcList',
			datatype : 'JSON',
			data: {"pid": $("#priPID").val()},
			async : true,
			success : function(json) {
				if(json !=null || json !=undefined){
					var srcList=$("#srcList");
					for(var i=1; i<json.length+1; i++){
						if(i==1){
							$("#lastPunDate").html(json[i-1].decDate);
						}
						srcList.append("<tr><td>"+i+"</td><td>"+json[i-1].penDecNo+"</td><td>"+json[i-1].decDate+"</td><td>"+json[i-1].decorgCN+"</td></tr>")
					}
				}
			}
    	});
	 }
	 history();
	 
	 //保存
	 function save(){
		 var formParam = $('#applyForm').serializeObject();
         http.httpRequest({
             url: '/reg/server/seriouspunish/applySaveAudit',
             serializable: false,
             data: formParam,
             type: 'post',
             success: function (json) {
             	if(json.status=="success"){
             		var data=new Object(); 
             		data.reload=true;
             		layer.msg(json.msg, {time: 1000}, function() {});
					layer.close(data);
				}else{
					layer.msg(json.msg, {time: 3500}, function() {});
				}
             }
         });
	 }
	    
    //事件绑定
    function bind() {
        util.bindEvents([{
            el: '#save',
            event: 'click',
            handler: function() {
            	save();
            }
        }, {
            el: '#cancel',
            event: 'click',
            handler: function() {
            	layer.close();
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
        }])
    }

})