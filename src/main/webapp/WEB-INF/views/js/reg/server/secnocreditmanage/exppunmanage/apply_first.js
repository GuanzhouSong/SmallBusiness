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
             url: '/reg/server/seriouspunish/applySaveFirst',
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
	 
	 $(".close-box").click(function(){
		 var idx=$(this).attr("idx");
		 $(".tr"+idx).hide();
		 $("#secbased option:nth-child("+($(".close-box").length-$(".close-box :hidden").length+1)+")").attr("selected","selected");
	 })
	    
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
            el: '#secbased',
            event: 'change',
            handler: function() {
            	var num = $("#secbased option:selected").val();
            	$(".tr").hide();
            	for(var i=1;i<=num;i++){
            		$(".tr"+i).show();
            	}
            }
        }, {
            el: '.firstCheck',
            event: 'click',
            handler: function() {
            	var check = $(this).val(); 
            	if(check == '0')
            		$("#firstOpin").val("不同意经办人意见，拟不予列入严重违法失信企业名单。");
            	if(check == '1')
            		$("#firstOpin").val("同意经办人意见，拟将其列入严重违法失信企业名单，请局领导审批。");
            }
        }])
    }

})