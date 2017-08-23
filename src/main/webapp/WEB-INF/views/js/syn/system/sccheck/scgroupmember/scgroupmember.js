require(['component/iframeLayer', 'component/dataTable','common/util', 'common/http', 'handlebars', 'jquery','jquery.serialize','laydate'], function (layer, dataTable, util, http, handlebars) {
	init();
	
    /**
	 * 初始化函数集合
	 */
    function init() {
        initDataTable();
    	bind();
    }
    
    var table;

    /**
     * 初始化dataTable
     */
    function initDataTable() {
        table = dataTable.load({
            el: '#scgroupTable',
            showIndex: true,
            scrollX:true, //支持滚动
			bScrollInfinite:true,
            ajax: {
                url:window._CONFIG.chooseUrl+'/sccheck/pubsgroupmember/list.json',
            },
            columns: [
                {data: "_idx", "className": "center"},
                {data: "agentDeptName", "className": "center"}, 
                {data: "agentName", "className": "center"},
                {data: null, "className": "center"},
                {data: null, "className": "center"},
                {data: null, "className": "center"}
            ],
            columnDefs: [{
            	targets:5,
    	        render:function(data,type,row,meta){
    	           return "<a class='delete' value='"+row.uid+"' >删除</a>";
    	       }
	        },{
            	targets:3,
    	        render:function(data,type,row,meta){
    	           var isChecked = row.leaderFlag =='1'?'checked':'';
    	           return "<input type='radio' value='"+row.uid+"' name='leaderFlag' "+isChecked+"></input>";
    	       }
	        },{
            	targets:4,
    	        render:function(data,type,row,meta){
    	           var isChecked = row.expertFlag =='1'?'checked':'';
    	           return "<input type='checkbox' value='"+row.uid+"' name='expertFlag' "+isChecked+"></input>";
    	       }
	        }]
        });
    }
    
    function bind() {
        util.bindEvents([{
            el: '.addGroup',
            event: 'click',
            handler: function () {
        		layer.dialog({
        			title:"添加检查小组",
        			area:['98%','98%'],
        			content:window._CONFIG.chooseUrl+'/pub/server/sccheck/entagent/adjustPage',
        			callback:function(data){
        				if(data.reload){
        					table.ajax.reload();
        				}
        			}
        		});
            }
        },{
            el: '#save',
            event: 'click',
            handler: function () {
            	var tableData = table.row().data();
            	var resultUids = $("#resultUids").val();
            	var expertArry = []; 
            	var leaderArry = [];
            	$("input[name ='expertFlag']:checked").each(function(){
            		expertArry.push($(this).val());
            	})
            	$("input[name ='leaderFlag']:checked").each(function(){
            		leaderArry.push($(this).val());
            	})
            	if(tableData =='undefinde' || tableData ==null){
            		layer.alert("检查小组成员不能为空！");
            		return false;
            	}
//            	if($("input[name ='leaderFlag']:checked").length == 0){
//            		layer.alert("请先选中一个执法人员作为组长！");
//            		return false;
//            	}
                layer.confirm('是否确认提交？', {icon: 2, title: '提示'}, function (index) {
                    http.httpRequest({
                        url: window._CONFIG.chooseUrl+'/sccheck/pubsgroupmember/commit',
                        serializable: false,
                        data: {uid : resultUids,expertUid:expertArry.toString(),leaderUid:leaderArry.toString()},
                        type: 'post',
                        success: function (data) {
                           layer.msg(data.msg, {time: 1500}, function () {});
                           if(data.status=='success'){
                        	   layer.close({reload:true});
                           }
                        }
                    })
                    layer.close(index);
                });
            }
        },{
        	el: '.delete',
            event: 'click',
            handler: function () {
            	 var data = table.row($(this).closest('td')).data();
                 layer.confirm('是否确认删除该执法人员？', {icon: 2, title: '提示'}, function (index) {
                    http.httpRequest({
                        url: window._CONFIG.chooseUrl+'/sccheck/pubsgroupmember/delete',
                        serializable: false,
                        data: {uid : data.uid},
                        type: 'post',
                        success: function (data) {
                           layer.msg(data.msg, {time: 1500}, function () {});
                           if(data.status=='success'){
                        	   table.ajax.reload();
                           }
                        }
                    })
                    layer.close(index);
                });
            }
        },{
        	el: '#cancel',
            event: 'click',
            handler: function () {
            	layer.close();
            }
        }]);
    }
});
