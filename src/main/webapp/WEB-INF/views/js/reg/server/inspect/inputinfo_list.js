require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http',
    'handlebars', 'jquery', 'jquery.serialize', 'laydate', 'jquery.multiselect'], function (layer, dataTable, util, http, handlebars) {

    init();
    /**
     * 初始化函数集合
     */
    function init() {
        initDataTable();
        initNewDataTable();
        bind();
        $("#resResult").multipleSelect({
            selectAllText: '全部',
            allSelected: '全部',
            selectAllDelimiter: '',
            minimumCountSelected: 10
        });
        $('#resResult').multipleSelect('setSelects',['1','2','3','4','5','6','7']);

    }


    var searchParams = {};//查询参数声明!
    var table;
    var newtable;

    /**
     * 初始化dataTable
     */
    function initDataTable() {
        table = dataTable.load({
            //需要初始化dataTable的dom元素
            el: '#user-table',
            //是否加索引值
            showIndex: true,
            scrollX: true, //支持滚动
            ajax: {
                url: '/reg/server/inspect/inputList.json',
                data: function (d) {
                    d.params = searchParams;
                }
            },
            //需注意如果data没有对应的字段的，请设置为null，不然ie下会出错;
            //'className'不要写成class
            columns: [
                {data: null, 'defaultContent': '11'},
                {data: null, 'defaultContent': '11'},
                {data: 'uniCode'},
                {data: 'entName','className': 'left'},
                {data: 'estDate'},
                {data: 'year'},
                {data: 'firstReportTime'},
                {data: 'infoAuditResult'},
                {data: 'infoCheckDate'},
                {data: 'resResult',cut:{length:20,mark:'...'}},
                {data: 'pubDate'},
                {data: 'infoCheckMan'},
                {data: 'departName'},
                {data: 'infoFillDate'},
                {data: 'infoFillMan'},
                {data: 'infoAuditDate'},
                {data: 'infoAuditMan'},
                {data: 'dom','className': 'left'},
                {data: 'regOrgName','className': 'left'},
                {data: 'localAdmName','className': 'left'},
                {data: 'sliceNOName','className': 'left'}
            ],
            columnDefs: [

                {
                    targets: 1,
                    render: function (data, type, row) {
                        if (row.infoAuditResult == '3') {
                            return '<a href="javascript:void(0);" class="js-add">录入</a>';

                        } else if (row.infoAuditResult == '0') {//待审核
                            return '<a href="javascript:void(0);" class="js-edit">修改</a>' +
                                '<a href="javascript:void(0);" class="js-del">删除</a>';
                        } else if (row.infoAuditResult == '2') {//审核不通过
                            return '<a href="javascript:void(0);" class="js-edit">修改</a>' +
                                '<a href="javascript:void(0);" class="js-detail">详情</a>';
                        } else {
                            return '<a href="javascript:void(0);" class="js-detail">详情</a>';
                        }
                    }
                },
                {
                    targets: 2,
                    render: function (data, type, row, meta) {
                        if(data==null||data=='')return row.regNO;
                        else return data;
                    }
                },
                {
                    targets: 7,
                    render: function (data, type, row, meta) {
                        if (row.infoAuditResult == '0') {
                            return '待审核';
                        } else if (row.infoAuditResult == '4') {
                            return '审核通过';
                        } else if (row.infoAuditResult == '2') {
                            return '审核不通过';
                        } else if (row.infoAuditResult == '3') {
                            return '待录入';
                        } else return '';
                    }
                }
            ]
        })
    }
    
     /**
     * 初始化dataTable
     */
    function initNewDataTable() {
        newtable = dataTable.load({
            //需要初始化dataTable的dom元素
            el: '#user-new-table',
            //是否加索引值
            showIndex: true,
            scrollX: true, //支持滚动
            lengthMenu: [10, 20, 30,50, 100 ],
            ajax: {
                url: '/reg/server/inspect/newinputList.json',
                data: function (d) {
                    d.params = searchParams;
                }
            },
            //需注意如果data没有对应的字段的，请设置为null，不然ie下会出错;
            //'className'不要写成class
            columns: [
                {data: '_idx', 'className': 'center'},
                {data: null, 'className': 'center'},
                {data: 'uniCode'},
                {data: 'entName','className': 'left'},
                {data: 'estDate'},
                {data: 'year'},
                {data: 'firstReportTime'},
                {data: 'dom','className': 'left'},
                {data: 'regOrgName','className': 'left'},
                {data: 'localAdmName','className': 'left'},
                {data: 'sliceNOName','className': 'left'}
            ],
            columnDefs: [
                {
                    targets: 1,
                    render: function (data, type, row) {
                        return '<a href="javascript:void(0);" class="js-add">录入</a>';
                    }
                },
                {
                    targets: 2,
                    render: function (data, type, row, meta) {
                        if(data==null||data=='')return row.regNO;
                        else return data;
                    }
                }
            ]
        })
    }

function getFromType(data) {
    var fromtype='edit';//待审核
    if(data.infoAuditResult=='2')//审核不通过
        fromtype='backedit';
    
    return fromtype;
}
    function bind() {
        util.bindEvents([
             {
                el: '.js-add',
                event: 'click',
                handler: function () {
                	var flag = $("#old-new-flag").val();
                    var data;
                	if(flag=="N"){
                		data = newtable.row($(this).closest('td')).data();
                	}else if(flag=="Y"){
                		data = table.row($(this).closest('td')).data();
                	}
                    layer.dialog({
                        title: '年报核查录入',
                        area: ['90%', '90%'],
                        content: '/reg/server/inspect/toShow?fromtype=add&uid=&pripid='+data.priPID+'&year='+data.year,
                        callback: function (data) {
                        	var flag = $("#old-new-flag").val();
                        	if(flag=="Y"){
			                    table.ajax.reload();
		                    }else{
		                    	newtable.ajax.reload();
		                    }
                        }
                    })
                }
            },
            {
                el: '.js-edit',
                event: 'click',
                handler: function () {
                    var data = table.row($(this).closest('td')).data();
                    var fromtype= getFromType(data);
                    layer.dialog({
                        title: '年报核查修改',
                        area: ['90%', '90%'],
                        content: '/reg/server/inspect/toShow?fromtype='+fromtype+'&uid=' + data.uid+'&pripid='+data.priPID+'&year='+data.year,
                        callback: function (data) {
                            if (data.reload) {
                                table.ajax.reload();
                            }
                        }
                    });

                }
            },
            {
                el: '.js-detail',
                event: 'click',
                handler: function () {
                    var data = table.row($(this).closest('td')).data();
                    layer.dialog({
                        title: '年报核查详情',
                        area: ['90%', '90%'],
                        content: '/reg/server/inspect/toShow?fromtype=detail&uid=' + data.uid+'&pripid='+data.priPID+'&year='+data.year,
                        callback: function (data) {
                            if (data.reload) {
                                table.ajax.reload();
                            }
                        }
                    });
                }
            },
            {
                el: '.js-del',
                event: 'click',
                handler: function () {
                    var data = table.row($(this).closest('td')).data();
                    http.httpRequest({
                        url: '/reg/server/inspect/del?uid_fordelete='+data.uid,
                        serializable: true,
                        data: { },
                        type: 'post',
                        success: function (data) {
                            layer.msg(data.msg, {time: 1000}, function () {
                                table.ajax.reload();
                            })
                        }
                    })
                }
            },
            {
                el: '#qry',
                event: 'click',
                handler: function () {
                	var flag = $("#old-new-flag").val();
                    searchParams = $("#qryForm").serializeObject();
                    var subjectarr = [];
                    if($("input:checkbox[name='subject']:checked").length==0){
                        layer.msg('年报主体为必填项！', {time: 1000}, function () { });
                        return;
                    }
                    $("input:checkbox[name='subject']:checked").each(function () {
                        subjectarr.push($(this).val());
                    });
                    searchParams.subject = subjectarr.toString();
                    searchParams.resResult = $("#resResult").multipleSelect('getSelects').toString();
                    //检查结果全选，置为空，全选效果与不选一样，但影响到后台逻辑判断。
                    if(searchParams.resResult=='1,2,3,4,5,6,7'){
                        searchParams.resResult='';
                    }
                    
                    if(flag=="Y"){
	                    table.ajax.reload();
                    }else{
                    	newtable.ajax.reload();
                    }
                }
            },
            {
                el: '#cancel',
                event: 'click',
                handler: function () {
                    $('#qryForm')[0].reset();
                    $('#resResult').multipleSelect('setSelects',['1', '2', '3', '4', '5', '6', '7']);
                    $("#regOrg").val("");
                    $("#localAdm").val("");
                    $("#sliceNO").val("");
                }
            }
            , {
                el: '#choseorgReg',
                event: 'click',
                handler: function () {
                    layer.dialog({
                        title: '选择登记机关',
                        area: ['25%', '60%'],
                        content: '/commom/server/regorg/regorgmutiselectnocheck?isPermissionCheck=true',
                        callback: function (data) {
                            //重新加载列表数据
                            $("#regOrg").val(data.returncode);
                            $("#regOrgName").val(data.returnname);
                        }
                    })
                }
            },

            {
                el: '#choseregUnit',
                event: 'click',
                handler: function () {
                    layer.dialog({
                        title: '选择管辖单位',
                        area: ['25%', '60%'],
                        content: '/commom/server/regunit/regunitmutiselectnocheck?isPermissionCheck=true',
                        callback: function (data) {
                            //重新加载列表数据
                            $("#localAdm").val(data.returncode);
                            $("#localAdmName").val(data.returnname);
                        }
                    })
                }
            }, {
                el: '#choseSilceno',
                event: 'click',
                handler: function () {
                    layer.dialog({
                        title: '选择责任区',
                        area: ['25%', '60%'],
                        content: '/commom/server/sliceno/slicenomutiselectnocheck?isPermissionCheck=true',
                        callback: function (data) {
                            //重新加载列表数据
                            $("#sliceNO").val(data.returncode);
                            $("#sliceNoName").val(data.returnname);
                        }
                    })
                }
            }, {
                el: '#add-new-report',
                event: 'click',
                handler: function () {
                    $("#div-new-history").hide();
                    $("#div-new").show();
                    newtable.ajax.reload();
                    $("#old-new-flag").val("N");
                }
            }, {
                el: '#back-history',
                event: 'click',
                handler: function () {
                    $("#div-new-history").show();
                    $("#div-new").hide();
                    table.ajax.reload();
                    $("#old-new-flag").val("Y");
                }
            }
        ])
    }

})
