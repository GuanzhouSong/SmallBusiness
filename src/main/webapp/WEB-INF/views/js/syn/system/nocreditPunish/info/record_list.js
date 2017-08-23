require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http', 'handlebars', 'jquery','jquery.serialize'], function (layer, dataTable, util, http, handlebars) {
	
	var urlHead=window._CONFIG.urlHead;
	var list_tb;
	var punType=$("#type").val();
	var key=$("#key").val();
	init();
	/**
     * 初始化函数集合
     */
    function init() {
        //bind();
        createList();
    }
	
	//惩戒单列表
    function createList(){
    	list_tb = dataTable.load({
            //需要初始化dataTable的dom元素
            el: "#record_table",
            scrollX: true,
            ajax : {
                url : urlHead+'/noCreditPunish/info/recordlist.json?key='+key+'&type='+punType,
            },
            showIndex: true,
            columns: [
              	{data: '_idx', width:"80px", className: 'center'},
                {data: null, width:"80px", className: 'center'},
                {data: 'batchNo', width:"150px"},
                {data: 'createTime', width:"120px"},
                {data: 'feedBack', width:"90px", className: 'center'},
                {data: 'punField', width:"140px"},
                {data: 'legBasis', width:"150px"},
                {data: 'punCause', width:"150px"},
                {data: 'punRule', width:"150px"},
                {data: 'punReqDept', width:"130px"},
                {data: 'exeBegin', width:"150px", className: 'center'},
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
                        var url=urlHead+"/noCreditPunish/info/goToRecordInfo?batchNo="+row.batchNo+"&key="+key+"&type="+punType;
                        return '<a href="'+url+'">查看</a>';
                    }
                },{
                    targets: 4,
                    render: function (data, type, row, meta) {
                    	if(data=="1"){
                    		return "是";
                    	}else
                    		return "否";
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
    
    function bind() {
        util.bindEvents([{
        	el: '#cancel',
            event: 'click',
            handler: function() {
            	window.location.href=urlHead+'/noCreditPunish/info/goToRecordInfo?type='+punType+'&key='+key;
            }
        }]);
    }
    
});