require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http', 'handlebars', 'jquery','jquery.serialize'], function (layer, dataTable, util, http, handlebars) {
	
	var urlHead=window._CONFIG.urlHead;
	var list_tb;
	init();
	/**
     * 初始化函数集合
     */
    function init() {
        createList();
    }
	
	//规则列表
    function createList(){
    	list_tb = dataTable.load({
            //需要初始化dataTable的dom元素
            el: "#rule_table",
            scrollX: true,
            ajax: {
            	url:  urlHead+'/noCreditPunish/rule/list.json',
            },
            showIndex: true,
            columns: [
              	{data: '_idx', width:"50px", className: 'center'},
                {data: 'punCause', className: 'center'},
                {data: 'punMea'}
            ],
            columnDefs: [
            ]
        })
    }
    
    $('#rule_table tbody').on('click', 'tr', function() {
    	list_tb.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
        var data=new Object();
        data.punCause=$(this).find('td').eq(1).html();
        data.punMea=$(this).find('td').eq(2).html();
        layer.close(data);
    });
    
    function bind() {
        util.bindEvents([{
        	el: '#cancel',
            event: 'click',
            handler: function() {
            	layer.close();
            }
        }]);
    }
    
});