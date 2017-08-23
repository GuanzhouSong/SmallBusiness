require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http', 'jquery', 'jquery.serialize', 'laydate', 'tab'], function (layer,dataTable, util, http) {
	
	var params = {};//查询参数
	var table;
	var $form=$("#searchForm");
	
	 init();
	 $(".apply_in").hide();
	 
	// 条件控制
    var status="";
    $("#businessStatus").change(function(){
    	if($(this).val()==status) {
    		return;
    	}
    	if(status=="" && $(this).val() !=""){
        	status=$(this).val();
    		$("#reset").click();
    		$(".apply_in").show();
    	}else if(status!="" && $(this).val() ==""){
        	status=$(this).val();
    		$("#reset").click();
    		$(".apply_in").hide();
    	}else if(status!="" && $(this).val() !=""){
        	status=$(this).val();
    		$("#reset").click();
    	}
    	$(this).val(status);
    })
	 
	 function init(){
		 bind();
		 initDataTable();
	 }
	    
	 
	    /**
	     * 初始化dataTable
	     */
	    function initDataTable() {
	    	params = $form.serializeObject();
	        table = dataTable.load({
	            //需要初始化dataTable的dom元素
	            el: '#qua-table',
	            scrollX:true,	
	            showIndex: true,
	            ajax : {
	                url :  '/reg/server/seriouspunish/doGetExpSecInApplyList',
	                data : function(d){
                		d.params = params;
	                }
	            },
	            columns: [
	                {data: '_idx', className: 'center', width:"50px"},
	                {data: null, className: 'center', width:"80px"},
	                {data: 'uniSCID', className: 'center'},
	                {data: 'entName', className: 'center'},
	                {data: 'leRep', className: 'center'},
	                {data: 'businessStatus', className: 'center'},
	                {data: null, className: 'center'},
	                {data: 'addSecTerm', className: 'center'},
	                {data: 'addSecCause', className: 'center'}
	            ],
	            columnDefs: [{
				      targets: 1,
				      render: function(data, type, row, meta) {
				    	  if(row.uid=='uid'){
				    		  return '<a class="apply" href="javascript:void(0)">申请列入</a>';
				    	  }else{
				    		  if(row.businessStatus=="1"||row.businessStatus=="2"){
				    			  return '<a class="edit" href="javascript:void(0)">修改</a> '+
				    			  			'<a class="delete" href="javascript:void(0)">删除</a>';
				    		  }else{
				    			  return '<a class="view" href="javascript:void(0)">详情</a>';
				    		  }
				    	  }
                      }
				 },{
				      targets: 2,
				      render: function(data, type, row, meta) {
				    	  if(data==null||data==""){
				    		  return row.regNO;
				    	  }else{
				    		  return data;
				    	  }
                      }
				 },{
				      targets: 5,
				      render: function(data, type, row, meta) {
				    	  if(data=="0"){
				    		  return "待列入";
				    	  }else if(data=="1"){
				    		  return "待初审";
				    	  }else if(data=="2"){
				    		  return "初审不通过";
				    	  }else if(data=="3"){
				    		  return "待审核";
				    	  }else if(data=="4"){
				    		  return "审核通过";
				    	  }else if(data=="5"){
				    		  return "审核不通过";
				    	  }else{
				    		  return data;
				    	  }
                      }
				 },{
				      targets: 6,
				      render: function(data, type, row, meta) {
				    	  return "严违失信类型"
                      }
				 }]
	        })
	    }
	    //统计
	    function getTotals(){
	    	$.ajax({
	    		type : "POST",
	    		url : '/reg/server/seriouspunish/applyTotal',
				datatype : 'JSON',
				data: {},
				async : true,
				success : function(json) {
					$("#applySrcTotal").text(json.applySrcTotal);
					$("#applyFirstTotal").text(json.applyFirstTotal);
					$("#applyAuditTotal").text(json.applyAuditTotal);
					$("#applyExpirTotal").text(json.applyExpirTotal);
				}
	    	});
	    }
	    getTotals();
	    
	   //事件绑定
	    function bind() {
	        util.bindEvents([{
	            el: '#search',
	            event: 'click',
	            handler: function() {
	            	params=$form.serializeObject();
	            	table.ajax.reload();
	            }
	        }, {
                el: '#choseregUnit',
                event: 'click',
                handler: function() {
                    layer.dialog({
                        title: '请选择企业管理部门',
                        area: ['25%', '60%'],
                        content: '/commom/server/regunit/regunitmutiselectnocheck',
                        callback: function (data) {
                            //重新加载列表数据
                            $("#localAdm").val(data.returncodes);
                            $("#localAdmName").val(data.returnname);
                        }
                    })
                }
            }, {
                el: '.apply',
                event: 'click',
                handler: function() {
                	var data=table.row($(this).closest('td')).data();
                	layer.dialog({
                        title: '申请列入',
                        area: ['98%', '99%'],
                        content: "/reg/server/seriouspunish/doApply?caseNo="+data.caseNo+"&pid="+data.priPID,
                        callback: function (data) {
                        	if (data.reload) {
                                table.ajax.reload();
                            }
                        }
                    })
                }
            }, {
                el: '.edit',
                event: 'click',
                handler: function() {
                	var data=table.row($(this).closest('td')).data();
                	layer.dialog({
                        title: '编辑',
                        area: ['98%', '99%'],
                        content: "/reg/server/seriouspunish/doApplyEdit?uid="+data.uid,
                        callback: function (data) {
                        	if (data.reload) {
                                table.ajax.reload();
                            }
                        }
                    })
                }
            }, {
                el: '.view',
                event: 'click',
                handler: function() {
                	var data=table.row($(this).closest('td')).data();
                	var url="";
                	if(data.businessStatus=='3'){
                		url="/reg/server/seriouspunish/doApplyView?uid="+data.uid;
                	}else
                		url="/reg/server/seriouspunish/doApplyAuditView?uid="+data.uid;
                	layer.dialog({
                        title: '查看',
                        area: ['98%', '99%'],
                        content: url,
                        callback: function (data) {
                        	if (data.reload) {
                                table.ajax.reload();
                            }
                        }
                    })
                }
            }, {
                el: '.delete',
                event: 'click',
                handler: function() {
                	var data = table.row($(this).closest('td')).data();
                	layer.confirm('确定要删除吗?', {icon: 2, title: '提示'}, function(index) {
                        http.httpRequest({
                            url: "/reg/server/seriouspunish/doExpInApplyDelete?uid="+data.uid,
                            data: {sourceId:data.sourceId},
                            success: function(data) {
                                if (data.status == 'success') {
                                    //重新加载列表数据
                                    layer.msg(data.msg, {time: 2000}, function(){
                                        table.ajax.reload();
                                    });
                                }else{
                                	layer.msg(data.msg, {time: 2500}, function(){});
                                }
                            }
                        });
                    });
                }
            }])
	    }

})