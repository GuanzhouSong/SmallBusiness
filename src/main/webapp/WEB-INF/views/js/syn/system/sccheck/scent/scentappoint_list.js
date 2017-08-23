require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http', 'jquery','jquery.serialize','laydate','jquery.multiselect'], function (layer, dataTable, util, http) {
	var searchParams;//查询参数声明!
    init();
    /**
     * 初始化函数集合
     */
    function init() {
        bind();
        initDataTable();
    }
    
    $("#checkResultM").multipleSelect({
		selectAllText: '全部',
		allSelected: '全部',
		selectAllDelimiter: '',
		minimumCountSelected: 20
	});
    
    $('#checkResultM').multipleSelect('checkAll');
    
    var table;

    /**
     * 初始化dataTable
     */
    function initDataTable() {
    	searchParams = $("#taskForm").serializeObject();
        table = dataTable.load({
            el: '#user-table',
            showIndex: true,
            aLengthMenu: [10, 25, 50, 100,1000,2000],
            scrollX:true, //支持滚动
			bScrollInfinite:true,
            ajax: {
            	type : "POST",
                url : window._CONFIG.chooseUrl + '/server/sccheck/pubscent/appointlist.json',
                data:function(d){
                	d.params = searchParams;
                }
            },
            columns: [
            		  {data: "_idx",width:'20px'},
            		  {data: null,width:'20px'},
                      {data: 'checkDeptName'},
                      {data: null},
                      {data: 'entName'},
                      {data: null},//企业类型
                      {data: 'taskCode'},
                      {data: 'taskName',className: 'left'},
                      {data: 'taskLeadDeptName'},
                      {data: 'regOrgName',className: 'left'},
                      {data: 'localAdmName',className: 'left'},
                      {data: 'adjustUserName'},
                      {data: 'adjustTime'}
            ],
            columnDefs: [{
 					targets:1,
 					render:function(data,type,row,meta){
 						return '<input type="checkbox" class="appointent" value="'+row.uid+'">';
 					}
 				 },{
 					targets:3,
 					render:function(data,type,row,meta){
 						if(row.uniCode){
 							return row.uniCode;
 						}else{
 							return row.regNO;
 						}
 					}
 				 },{
 					targets:5,
 					render:function(data,type,row,meta){
 						if("11,13,31,33,12,14,32,34,21,27,24,22,28".indexOf(row.entTypeCatg) != -1) {
				    		return "企业";
				    	}else if("16,17".indexOf(row.entTypeCatg)!= -1){
				    		return "农专社";
				    	}else if(row.entTypeCatg == '50'){
				    		return "个体户";
				    	}else if(row.entTypeCatg == '23'){
				    		return "外企代表";
				    	}else{
				    		return "-";
				    	}
 					}
 				 }
             ]
        })
    }

    //表格之外的查询按钮事件
    $("#js-search").click(function(){
    	var entTypeCatg = "";
    	var flag = false;
    	$(".entCatg").each(function(){
    		if($(this).prop("checked")){
    			if(entTypeCatg == ""){
    				entTypeCatg = $(this).val();
    			}else{
    				entTypeCatg += ","+$(this).val();
    			}
    		}else{
    			flag = true;
    		}
    	});
    	if(flag){
    		$("#entTypeCatg").val(entTypeCatg);
    	}else{
    		$("#entTypeCatg").val("");
    	}
    	searchParams = $("#taskForm").serializeObject();
		table.ajax.reload();
		$("#checkall").prop("checked",false);
    });
    
    function bind() {
        util.bindEvents([
	        {
	            el: '#more',
	            event: 'click',
	            handler: function () { 
	            	var isHideOrShow = $("#hideorshow").is(":hidden");
	            	if(isHideOrShow){
	            		 $("#more").val("收起");
	            		 $("#hideorshow").css("display","block");
						//$("#hideorshow").toggle();
	
					}else{
						$("#more").val("更多查询条件");
	            		$("#hideorshow").css("display","none");
					}
	            }
	        },{
	        	el: '#choseregUnit',
	            event: 'click',
	            handler: function () { 
	                layer.dialog({
	                    title: '选择管辖单位',
	                    area: ['400px', '600px'],
	                    content: '/commom/server/regunit/regunitmutiselect',
	                    callback: function (data) { 
	                    	var returncode=data.returncode;
	                    	if(returncode!=null&&returncode!=""){
	                    		returncode=returncode.substr(0,returncode.length-1); 
	                    	}
	                      	$("#localAdm").val(returncode);
	                      	$("#localAdmName").val(data.returnname); 
	                    }
	                })
	            }
	        },{
	            el: '#cancel',
	            event: 'click',
	            handler: function () { 
	            	$("#regOrg").val("");
	            	$("#localAdm").val("");
	            	$("#entTypeCatg").val("");
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
	                    	if(returncode!=null&&returncode!=""){
	                    		returncode=returncode.substr(0,returncode.length-1); 
	                    	}
	                    	$("#regOrg").val(returncode);
	                    	$("#regOrgName").val(data.returnname);  
	                    }
	                })
	            }
	        }, {
	        	el: '#checkall',
	            event: 'click',
	            handler: function () { 
	            	if($(this).prop("checked")){
	            		$(".appointent").prop("checked",true);
	            	}else{
	            		$(".appointent").prop("checked",false);
	            	}
	            }
	        }, {
	        	el: '#js-appoint',
	            event: 'click',
	            handler: function () { 
	            	var uids = '';
	            	$(".appointent").each(function(){
	            		if($(this).prop("checked")){
	            			if(uids == ''){
	            				uids = $(this).val();
	            			}else{
	            				uids += ","+$(this).val();
	            			}
	            		}
	            	});
	            	if(uids==""){
	            		layer.msg("请先选择一条记录", {time: 2000}, function() {});
	            		return false;
	            	}
	            	layer.dialog({
	                    title: '抽查任务指派名单',
	                    area: ['40%', '30%'],
	                    content:window._CONFIG.chooseUrl+'/server/sccheck/pubscent/appointpage?uids='+uids,
	                    callback: function (data) {
	                        //重新加载列表数据
	                        if (data.reload) {
	                            table.ajax.reload();
	                        }
	                    }
	                })
	            }
	        }
        ])
    }
})
