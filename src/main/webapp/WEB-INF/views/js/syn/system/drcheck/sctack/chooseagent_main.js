require(['component/iframeLayer', 'component/dataTable','common/util', 'common/http', 'handlebars', 'jquery',
         'jquery.serialize','laydate','jquery.multiselect'], function (layer, dataTable, util, http, handlebars) {
	init();
	
    /**
	 * 初始化函数集合
	 */
    function init() { 
    	initDataTable();
    	bind();
    }
    
    var table;
    $("#deptCatgM").multipleSelect({
    	selectAllText: '全部',
    	allSelected: '全部',
    	selectAllDelimiter: '',
    	minimumCountSelected: 10
    });
    
    
    $("#stationTermM").multipleSelect({
    	selectAllText: '全部',
    	allSelected: '全部',
    	selectAllDelimiter: '',
    	minimumCountSelected: 10
    });
    
    /**
     * 初始化dataTable
     */
    function initDataTable() {
        table = dataTable.load({
            el: '#agent-table',
            showIndex: true,
            scrollX:true,
            ajax: {
                url:'/syn/server/drcheck/pubscagent/chooseagent',
                data:function(d){
            		d.params = $.extend({}, $("#chooseagentform").serializeObject(), {"agentState" : "1"});               		
//	               	d.params = $("#chooseagentform").serializeObject();
                }
            },
            columns: [
                {data: "id", "className": "center"},
                {data: null, "className": "center"},
                {data: "agentName", "className": "center"},
                {data: "unitName", "className": "center"}, 
                {data: "unitLevel", "className": "center"},
                {data: "unitType", "className": "center"},
                {data: "stationTerm", "className": "center"},
                {data: "deptCatg", "className": "center"},
                {data: "sliceName", "className": "center"},
                {data: "agentRange", "className": "center"}
            ],
            columnDefs: [{
            	targets:1,
    	        render:function(data,type,row,meta){
    	        	return "<input type='checkbox' name='agentId' value='"+data.uid+"' />";
    	       }
	        },{
            	targets:5,
    	        render:function(data,type,row,meta){
    	        	if(row.unitType =='1'){return "局"}
    	        	if(row.unitType =='2'){return "所"}
    	       }
	        },{
	        	targets:4,
	        	render:function(data,type,row,meta){
	        		if(row.unitLevel != null && row.unitLevel != ""){
	        			 if(row.unitLevel=="1"){
	        				 return "市级";
	        			 } else if(row.unitLevel=="2"){
	        				 return "县级";
	        			 } else if(row.unitLevel=="3"){
	        				 return "省级";
	        			 }else{
	        				 return "";
	        			 }
	        			 
	        		}
	        		
	        	}
	        },
			{
				targets:6,
				render:function(data,type,row,meta){
		    		if(data == null || data == "" || data == "undefined"){
		    			return "";
		    		}else if( data == 'N'){
			    		return "<span style='color:red;'>否</span>";
					}else{
				    	return data.replace('Y,','').replace('1','保化检查员').replace('2','药品检查员').replace('3','医疗器械检查员').replace('4','特种设备安全监察员').replace('Y','是');
					}
				}
			},{
	        	targets:7,
	        	render:function(data,type,row,meta){
					if(data!=null&&data!=""){
						var str = data.replace("1","工商行政管理类");
						str = str.replace("2","食品药品管理类");
						str = str.replace("3","质量技术监督类");
						str = str.replace("4","安全生产类");
						str = str.replace("5","物价管理类");
						return str;
					}else{
						return ""; 
					}
	        		
	        	}
	        } 
            ]
        });
    }
    
    //表格之外的查询按钮事件
    $("#search").click(function(){
    	var deptCatgM = $("#deptCatgM").next("div").find("span").html();
    	if(deptCatgM != "全部"){
    		$("#deptCatg").val($("#deptCatgM").val());
    	}else{
    		$("#deptCatg").val("");
    	}
    	var stationTermM = $("#stationTermM").next("div").find("span").html();
    	if(stationTermM != "全部"){
    		$("#stationTerm").val($("#stationTermM").val());
    	}else{
    		$("#stationTerm").val("");
    	}
        table.ajax.reload();
    }); 
    
    function bind() {
        util.bindEvents([{
            el: '#chooseAll',
            event: 'click',
            handler: function () {
            	if($(this).prop("checked")){
					 $("#agent-table input").prop("checked",true);
				 }else{
					 $("#agent-table input").prop("checked",false);
				 }
            }
        },{
            el: '#save',
            event: 'click',
            handler: function () {
            	var uidList = getCheckBoxValues();
            	if(uidList <= 0){
            		layer.msg("请先选择一个执法人员！", {icon: 7,time: 1000});
            		return false;
            	}else{
            		var formParam = {
            				deptTaskUid:$("#deptTaskUid").val(),
    						uidList: uidList
    				};
    				http.httpRequest({
    					url: '/reg/server/sccheck/pubscentAgentBack/add',
    					dataType:"json",  
    					serializable: true,
    					data: formParam,
    					type:"post",
    					success: function (data) {
    						layer.msg(data.msg, {time: 1000}, function () {
    							if(data.status == 'success'){
    								layer.close({reload: true});
    							}
    						});
    					}
    				});
            	}
            }
        },{
        	el: '#choseregUnit',
            event: 'click',
            handler: function () { 
                layer.dialog({
                    title: '选择管辖单位',
                    area: ['350px', '666px'],
                    content: '/commom/server/regunit/regunitsingselect',
                    callback: function (data) {
                    	var returncode=data.returncode;
                      	$("#localAdm").val(returncode);
                      	$("#localAdmName").val(data.returname); 
                    }
                })
            }
        },{
	          el: '#agentRangeChoose',
	          event: 'click',
	          handler: function () { 
	              layer.dialog({
	                  title: '选择检查事项范围',
	                  area: ['400px', '600px'],
	                  content: '/commom/sccheck/pubscchecktype/scChecktypeSelect?isPermissionCheck=true',
	                  callback: function (data) { 
	                  	var returncode=data.returncode;
	                  	if(returncode!=null&&returncode!=""){
	                  		returncode=returncode.substr(0,returncode.length-1); 
	                  	}
	                  	$("#agentRange").val(returncode);
	                  	$("#agentRangeName").val(data.returnname);  
	                  }
	              })
	          }
	      },{
            el: '#close',
            event: 'click',
            handler: function () {
            	layer.close({reload: false});
            }
        },{
            el: '#cancel',
            event: 'click',
            handler: function () {
            	$("#localAdm").val("");
                $("#deptCatg").val("");
                $("#stationTerm").val("");
                $("#agentRange").val("");
                $('#deptCatgM').multipleSelect('setSelects',['']);
                $('#stationTermM').multipleSelect('setSelects',['']);
            }
        }]);
    }
    
    function getCheckBoxValues(){
    	var uidList = new Array();
		$("input:checkbox[name='agentId']:checked").each(function () {
			uidList.push($(this).val());
		});
		return uidList;
    }
});
