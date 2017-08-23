require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http', 'handlebars', 'jquery','jquery.serialize'], function (layer, dataTable, util, http, handlebars) {

	var urlHead=window._CONFIG.urlHead;
    var myent_tb;
	var $searchForm=$("#searchForm");
	//loading
    var load=undefined;
    var params =$searchForm.serializeObject();//查询参数
    init();
    /**
     * 初始化函数集合
     */
    function init() {
        bind();
        createMyEntList();
    }
    
    //我的惩戒单列表
    function createMyEntList(){
    	myent_tb = dataTable.load({
            //需要初始化dataTable的dom元素
            el: "#myent_table",
            scrollX: true,
            ajax : {
                url :  urlHead+'/noCreditPunish/info/mylist.json',
                data : function(d) {
                	d.params = params;
                }
            },
            showIndex: true,
            columns: [
              	{data: '_idx', width:"80px", className: 'center'},
                {data: null, width:"80px", className: 'center'},
                {data: 'state', width:"60px"},
                {data: 'batchNo', width:"150px"},
                {data: 'createTime', width:"120px"},
                {data: 'punField', width:"140px"},
                {data: 'legBasis', width:"150px"},
                {data: 'punCause', width:"150px"},
                {data: 'punRule', width:"150px"},
                {data: 'punReqDept', width:"130px"},
                {data: 'exeBegin', width:"160px", className: 'center'},
                {data: 'punExeDept', width:"130px"},
                {data: 'punMea', width:"150px"},
                {data: 'contact', width:"110px"},
                {data: 'phone', width:"110px"},
                {data: 'fax', width:"110px"},
                {data: 'email', width:"130px"}
            ],
            columnDefs: [{
                    targets: 1,
                    render: function (data, type, row, meta) {
	                	if(row.state<1){
	                		var url1=urlHead+"/noCreditPunish/info/edit?deal=1&batchNo="+row.batchNo;
	                		return '<a href="'+url1+'" style="color:#003399">审核</a>';
	                    }else if(row.state>=1){
	                    	var url=urlHead+"/noCreditPunish/info/edit?deal=3&batchNo="+row.batchNo;
	                    	return '<a href="'+url+'">查看</a>';
	                    }
                    }
                },{
                    targets: 2,
                    render: function (data, type, row, meta) {
                    	if(data=="0"){
                    		if (row.auditRes!="2") {
                    			return "<span style='color:red'>待审核</span>";
                    		}else
                    			return "<span style='color:red'>审核退回</span>";
                    	}else {
                    		return "<span style='color:green'>已审核</span>";
                    	}
                    }
                },{
                    targets: 10,
                    render: function (data, type, row, meta) {
                    	if(row.exeBegin != null && row.exeEnd !=null){
                    		return row.exeBegin+"至"+row.exeEnd;
                    	}else
                    		return "-"
                    }
                }
            ]
        })
    }
    
    //表格之外的查询按钮事件
    $("#search").click(function(){
    	load=layer.load();
    	params=$searchForm.serializeObject();
    	myent_tb.ajax.reload();
    	layer.close(load);
    });
    $("#rest").click(function() {
    	$searchForm[0].reset();
    	$("#punReqDeptCode").val("");
    	$("#punExeDeptCode").val("");
	});
    
    // 部门单选
    function doSelectExeDeptCode() {
        layer.dialog({
            title: '选择惩戒执行部门',
            area: ['350px', '666px'],
            content: window._CONFIG.select_dept_url,
            callback: function(data) {
            	if(data.adCode!=null&&data.orgName!=null){
                    $("#punExeDept").val(data.orgName);
                    $("#punExeDeptCode").val(data.adCode);
                }
            }
        })
    }
    
    function doSelectReqDeptCode() {
        layer.dialog({
            title: '选择惩戒提请部门',
            area: ['350px', '666px'],
            content: window._CONFIG.select_dept_url,
            callback: function(data) {
            	if(data.adCode!=null&&data.orgName!=null){
                    $("#punReqDept").val(data.orgName);
                    $("#punReqDeptCode").val(data.adCode);
                }
            }
        })
    }
    
    $("#selectRule").click(function(){
    	layer.dialog({
            title: '选择事由..',
            area: ['600px', '350px'],
            content: urlHead+'/noCreditPunish/rule/selectrule',
            callback: function(data) {
            	if(data.punCause !=undefined){
                	$("#punCause").val(data.punCause);
            	}
            }
        })
    })
    $("#selectMea").click(function(){
    	layer.dialog({
            title: '选择措施..',
            area: ['600px', '350px'],
            content: urlHead+'/noCreditPunish/rule/selectrule',
            callback: function(data) {
            	if(data.punMea !=undefined){
                	$("#punMea").val(data.punMea);
            	}
            }
        })
    })

    function bind() {
        util.bindEvents([{
            el: '#selectPunReqDept',
            event: 'click',
            handler: function() {
            	doSelectReqDeptCode();
            }
        }, {
            el: '#selectPunExeDept',
            event: 'click',
            handler: function() {
            	doSelectExeDeptCode();
            }
        }])
    }

})

