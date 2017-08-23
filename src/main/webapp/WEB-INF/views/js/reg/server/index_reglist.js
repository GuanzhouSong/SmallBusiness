require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http', 'handlebars', 'jquery','jquery.serialize','laydate','jquery.validate','pagination'], function (layer, dataTable, util, http,handlebars) {

    init();
    /**
     * 初始化函数集合
     */
    function init() {
        loadBulletins();
        loadRptOptoInfo();
    }

    function loadBulletins(){
        //加载存放在redis中的缓存数据
        http.httpRequest({
            type: 'post',
            url: '/reg/countlist.json',
            success: function (data) {
            	var list = data.data;
                if (data.status == 'success') {
                	//敏感词审核条数
					$("#forbid").text(list[0]);
					//修改申请条数
					$("#modApply").text(list[1]);
					//企业异常列入待审核+移出待审核+撤销待审核
					$("#qy").text(list[2]);
					//农专异常列入待审核+移出待审核+撤销待审核
					$("#nz").text(list[3]);
                }else{
                	$("#forbid").text("0");
					$("#modApply").text("0");
					$("#qy").text("0");
					$("#nz").text("0");
                } 
            }
        });
    }
    
    function loadRptOptoInfo(){
        //加载经营期限统计数据
        http.httpRequest({
            type: 'post',
            url: '/reg/server/rptOptoInfo/rptOptoInfoCount.json',
            success: function (data) {
            	var rptOptoInfo = data.data;
                if (data.status == 'success' && rptOptoInfo != null ) {
                	//经营期限即将到期
					$("#opCome").text(rptOptoInfo.willexpired);
					//经营期限已经到期
					$("#opOver").text(rptOptoInfo.expired);
                }else{
                	$("#opCome").text("0");
					$("#opOver").text("0");
                } 
            }
        });
    }

})
