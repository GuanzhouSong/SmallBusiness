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
        $('#resResult').multipleSelect('setSelects', [ '1', '2', '3', '4', '5', '6', '7']);

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
                url: '/reg/server/inspect/checkList.json',
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
                        if (row.infoAuditResult == '0') {
                            return '<a href="javascript:void(0);" class="js-edit">审核</a>';
                        } else {
                            return '<a href="javascript:void(0);" class="js-detail">详情</a>';
                        }
                    }
                }, {
                    targets: 2,
                    render: function (data, type, row, meta) {
                        if (data == null || data == '')return row.regNO;
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


    function bind() {
        util.bindEvents([
            {
                el: '.js-edit',
                event: 'click',
                handler: function () {
                    var data = table.row($(this).closest('td')).data();
                    layer.dialog({
                        title: '年报核查审核',
                        area: ['90%', '90%'],
                        content: '/reg/server/inspect/toShow?fromtype=checkedit&uid=' + data.uid + '&pripid=' + data.priPID + '&year=' + data.year,
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
                        content: '/reg/server/inspect/toShow?fromtype=detail&uid=' + data.uid + '&pripid=' + data.priPID + '&year=' + data.year,
                        callback: function (data) {
                            if (data.reload) {
                                table.ajax.reload();
                            }
                        }
                    });
                }
            },
            {
                el: '#qry',
                event: 'click',
                handler: function () {
                    searchParams = $("#qryForm").serializeObject();
                    var subjectarr = [];
                    var resresultarr = [];
                    $("input:checkbox[name='subject']:checked").each(function () {
                        subjectarr.push($(this).val());
                    });
                    searchParams.subject = subjectarr.toString();
                    searchParams.resResult = $("#resResult").multipleSelect('getSelects').toString();
                    //检查结果全选，置为空，全选效果与不选一样，但影响到后台逻辑判断。
                    if(searchParams.resResult=='1,2,3,4,5,6,7'){
                        searchParams.resResult='';
                    }
                    table.ajax.reload();
                }
            },
            {
                el: '#cancel',
                event: 'click',
                handler: function () {
                    $('#qryForm')[0].reset();
                    $('#resResult').multipleSelect('setSelects', ['1', '2', '3', '4', '5', '6','7']);
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
                            table.ajax.reload();
                        }
                    })
                }
            }
        ])
    }

})
