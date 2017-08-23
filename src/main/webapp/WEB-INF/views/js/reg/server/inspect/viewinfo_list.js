require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http',
    'handlebars', 'jquery', 'jquery.serialize', 'laydate', 'jquery.multiselect'], function (layer, dataTable, util, http, handlebars) {

    init();
    /**
     * 初始化函数集合
     */
    function init() {
        initDataTable();
        bind();
        $("#resResult").multipleSelect({
            selectAllText: '全部',
            allSelected: '全部',
            selectAllDelimiter: '',
            minimumCountSelected: 10
        });
        $("#regState").multipleSelect({
            selectAllText: '全部',
            allSelected: '全部',
            selectAllDelimiter: '',
            minimumCountSelected: 10
        });
        $('#resResult').multipleSelect('setSelects', [ '1', '2', '3', '4', '5', '6','7']);
        $('#regState').multipleSelect('setSelects', ['K,B,A,DA,X']);
    }


    var searchParams = {};//查询参数声明!
    var table;

    /**
     * 初始化dataTable
     */
    function initDataTable() {
        var tpl = $('#tpl').html();
        var template = handlebars.compile(tpl);
        table = dataTable.load({
            //需要初始化dataTable的dom元素
            el: '#user-table',
            //是否加索引值
            showIndex: true,
            scrollX: true, //支持滚动
            ajax: {
                url: '/reg/server/inspect/viewList.json',
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
                {data: 'sliceNOName','className': 'left'},
                {data: 'regState'}
            ],
            columnDefs: [
                {
                    targets: 1,
                    render: function (data, type, row) {
                        return '<a href="javascript:void(0);" class="js-detail">详情</a>';
                    }
                }, {
                    targets: 2,
                    render: function (data, type, row, meta) {
                        if (data == null || data == '')return row.regNO;
                        else return data;
                    }
                }, {
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
                }, {
                    targets: 'regState',
                    render: function (data, type, row, meta) {

                        if (data == 'K' || data == 'B' || data == 'A' || data == 'DA' || data == 'X') {
                            return ' 存续';
                        } else if (data == 'D') {
                            return '  吊销';
                        } else if (data == 'C') {
                            return '  撤销';
                        } else if (data == 'Q') {
                            return '迁出';
                        } else if (data == 'XX' || data == 'DX') {
                            return '注销';
                        }


                    }
                }
            ],
            "drawCallback": function (settings) {
                count_downinfo(searchParams);
            }
        })
    }

    //draw事件，统计下载信息
    function count_downinfo(searchParams) {
        
        var total = 0;
        var waitCheckNum = 0;
        var checkNo = 0;
        var checkPass = 0;
        var tinfo = $('#user-table_info').html();
        total = $.trim(tinfo.substring(tinfo.indexOf('共') + 1, tinfo.lastIndexOf('项'))).replace(/,/g, '');
        http.httpRequest({
            url: '/reg/server/inspect/countCheckNum',
            serializable: false,
            data: {params: searchParams},
            type: 'post',
            success: function (data) {
                waitCheckNum = data.data.waitCheckNum;
                checkNo = data.data.checkNo;
                checkPass = total - waitCheckNum - checkNo;
                if (checkPass < 0) checkPass = 0;
                var info = '查询结果：共 ' + total + '家，其中待审核 ' + waitCheckNum + '家，审核不通过 ' + checkNo + '家，审核通过 ' + checkPass + '家';
                $('#viewinfo').html(info);
            }
        });

    }

    // 部门单选
    function doSelectDept() {
        var select_dept_url = window._CONFIG.select_dept_url;
        layer.dialog({
            title: '选择部门',
            area: ['350px', '666px'],
            content: select_dept_url,
            callback: function (data) {
                if (data.deptCode != null && data.deptName != null) {
                    $("#checkDepName").val(data.deptName);
                    $("#checkDep").val(data.deptId);
                }
            }
        })
    }


    function bind() {
        util.bindEvents([
            {
                el: '#qry',
                event: 'click',
                handler: function () {
                    searchParams = $("#qryForm").serializeObject();
                    var subjectarr = [];
                    $("input:checkbox[name='subject']:checked").each(function () {
                        subjectarr.push($(this).val());
                    });
                    searchParams.subject = subjectarr.toString();
                    searchParams.resResult = $("#resResult").multipleSelect('getSelects').toString();
                    searchParams.regState = $("#regState").multipleSelect('getSelects').toString();

                    //检查结果全选，置为空，全选效果与不选一样，但影响到后台逻辑判断。
                    if(searchParams.resResult=='1,2,3,4,5,6,7'){
                        searchParams.resResult='';
                    }

                    table.ajax.reload();
                }
            }, {
                el: '.js-detail',
                event: 'click',
                handler: function () {
                    var data = table.row($(this).closest('td')).data();
                    layer.dialog({
                        title: '年报核查详情',
                        area: ['90%', '90%'],
                        content: '/reg/server/inspect/toShow?fromtype=detail&uid=' + data.uid + '&pripid=' + data.priPID + '&year=' + data.year,
                        callback: function (data) {
                            if (data.reload) {
                                table.ajax.reload();
                            }
                        }
                    });
                }
            }, {
                el: '#cancel',
                event: 'click',
                handler: function () {
                    $('#qryForm')[0].reset();
                    $('#resResult').multipleSelect('setSelects', ['1', '2', '3', '4', '5', '6', '7']);
                    $('#regState').multipleSelect('setSelects', ['K,B,A,DA,X']);
                    $("#regOrg").val("");
                    $("#localAdm").val("");
                    $("#sliceNO").val("");
                    $("#checkDep").val("");
                }
            },{
                el: '.js-more-query',
                event: 'click',
                handler: function () {
                	if($('.more-show').css("display")=="none"){
                		$('.js-more-query').attr('value','收起');
                		$('.more-show').css("display","block");
                	}else{
                		$('.js-more-query').attr('value','更多查询');
                		$('.more-show').css("display","none");
                	}
                }
            }, {
                el: '#choseorgReg',
                event: 'click',
                handler: function () {
                    layer.dialog({
	                    title: '选择登记机关',
	                    area: ['400px', '600px'],
	                    content: '/commom/server/regorg/regorgmutiselect',
	                    callback: function (data) { 
	                    	
	                    	var returncode=data.returncode;
	                    	var returnname=data.returnname;
	                    	$("#regOrg").val(returncode);
	                    	$("#regOrgName").val(returnname);
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
            },

            {
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
            },

            {
                el: '#choseCheckDep',
                event: 'click',
                handler: function () {
                    if ($('#fromtype').val() != 'detail')
                        doSelectDept();
                }
            }
        ])
    }

})
