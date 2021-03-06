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
	    
    //事件绑定
    function bind() {
        util.bindEvents([{
            el: '#cancel',
            event: 'click',
            handler: function() {
            	layer.close();
            }
        }])
    }

})