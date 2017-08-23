require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http', 'jquery','jquery.serialize', 'common/ajaxfileupload'], function(layer, dataTable, util, http) {
	
	var urlHead=window._CONFIG.urlHead;
	init();
	//用于防止重复添加表格内容
	var contentjson={};
	//
	var $tb_ent=$("#tb_ent_bd"), $tb_man=$("#tb_man_bd");
	//类型（企业，自然人）
    var type=$("#punType").val();
	var $cause=$("#punCause");
	var $field=$("#punField");
    //loading
    var load=undefined;
    
	/**初始化函数集合*/
    function init() {
        bind();
    }
    
    // 部门单选
    function doSelectExeDeptCode() {
        layer.dialog({
            title: '选择惩戒执行部门',
            area: ['350px', '666px'],
            content: window._CONFIG.select_dept_url,
            callback: function (data) {
                if(data.orgNamesExcParent!=undefined&&data.adCodesExcParent!=undefined){
                	//adCodesExcParent
                    $("#punExeDept_s").html(data.orgNamesExcParent);
                    $("#punExeDept").val(data.orgNamesExcParent);
                    $("#punExeDeptCode").val(data.adCodesExcParent);
                }
            }
        })
    }
    
    //根据领域加载事由
    function ruleCauseLoad() {
		$cause.html("");
    	$.ajax({
    		type : "POST",
    		url : urlHead+'/noCreditPunish/rule/getCauseByField',
			datatype : 'JSON',
			data: {"punField": $field.val()},
			async : true,
			success : function(json) {
				$cause.append("<option value='' selected='selected'>请选择..</option>")
				for(var i=0; i<json.length; i++) {
					$cause.append("<option value='"+json[i].punCause+"'>"+json[i].punCause+"</option>")
				}
			}
    	});
    }
    
    //加载规则
    function ruleInfoLoad() {
    	if ($field.val() !="" && $cause.val() !="") {
	    	$.ajax({
	    		type : "POST",
				url : urlHead+'/noCreditPunish/rule/getRuleByFieldAndCause',
				datatype : 'JSON',
				data: {"punField": $field.val(), "punCause": $cause.val()},
				async : true,
				success : function(json) {
					if(json !=null && json != "" && json !=undefined) {
						$("#legBasis_s").text(json.legBasis);
						$("#legBasis").val(json.legBasis);
						
						$("#punRule_s").text(json.punRule);
						$("#punRule").val(json.punRule);
						
						$("#punMea_s").text(json.punMea);
						$("#punMea").val(json.punMea);
						
						$("#punExeDept_s").text(json.punExeDept);
						$("#punExeDept").val(json.punExeDept);
						//$("#punExeDeptCode").val(json.punExeDeptCode);
					} else
						layer.msg("当前【领域】跟【事由】形成的组合无对应规则。请重新选择", {time: 4000}, function() {});
				}
	    	})
    	}
    }
    
    //明细操作事件
    $tb_ent.delegate('a','click',function(){
    	var tag=$(this).attr("tag");
    	if(tag=="del"){
	    	var priPID=$(this).attr("priPID");
	    	delete contentjson[priPID];
	    	$(this).parent().parent().remove();
    	}else if(tag=="view"){
    		var priPID=$(this).attr("priPID");
    		recordDialog(priPID, "ent");
    	}
    });
    
    //明细操作事件
    $tb_man.delegate('a','click',function(){
    	var tag=$(this).attr("tag");
    	if(tag=="del"){
	    	var cardNo=$(this).attr("cardNo");
	    	delete contentjson[cardNo];
	    	$(this).parent().parent().remove();
    	}else if(tag=="view"){
    		var cardNo=$(this).attr("cardNo");
    		recordDialog(cardNo, "man");
    	}
    });
    
    //弹出惩戒记录框
    function recordDialog(key, type) {
    	layer.dialog({
    		title: '惩戒记录',
    		area: ['1000px', '600px'],
    		content: urlHead+'/noCreditPunish/info/recordlist?type='+type+'&key='+key,
    		callback: function(data) { }
    	})
    }
    
    //弹出选择框
    function selectEntDialog() {
    	layer.dialog({
    		title: '选择惩戒企业',
    		area: ['720px', '480px'],
    		content: urlHead+'/noCreditPunish/info/selectDig?type=ent',
    		callback: function(data) {
    			//防止重复添加
    			if(data.entName!=undefined && contentjson[data.priPID]==undefined){
    				var line="<td value='无'>无</td>";
    				if(data.record=="有") {
    					line="<td value='有'><a href='javascript:void(0)' tag='view' priPID='"+data.priPID+"'>有/查看</a></td>"
    				}
    				$tb_ent.append("<tr><td>"+data.entName+"</td>"+
    						"<td style='display:none'>"+data.priPID+"</td>"+
    						"<td style='display:none'>"+data.regNo+"</td>"+
							"<td>"+filterNull(data.uniCode)+"</td>"+
							"<td>"+filterNull(data.leRep)+"</td>"+
							"<td style='display:none'>"+filterNull(data.cerType)+"</td>"+
							"<td>"+filterNull(data.cerTypeDesc)+"</td>"+
							"<td>"+filterNull(data.cardNo)+"</td>"+line+
							"<td class='last-col'><a href='javascript:void(0)' tag='del' priPID='"+data.priPID+"'>删除</a></td>"+
							"</tr>");
	    			contentjson[data.priPID]="1";
    			}
    		}
		});
    }
    
    //弹出选择框
    function selectManDialog() {
    	layer.dialog({
    		title: '选择惩戒自然人',
    		area: ['720px', '480px'],
    		content: urlHead+'/noCreditPunish/info/selectDig?type=man',
    		callback: function(data) {
    			//防止重复添加
    			if(data.cardNo!=undefined && contentjson[data.cardNo]==undefined){
    				var line="<td value='无'>无</td>";
    				if(data.record=="有") {
    					line="<td value='有'><a href='javascript:void(0)' tag='view' cardNo='"+data.cardNo+"'>有/查看</a></td>"
    				}
    				$tb_man.append("<tr><td>"+filterNull(data.litiName)+"</td>"+
    						"<td>"+filterNull(data.cardNo)+"</td>"+
							"<td style='display:none'>"+filterNull(data.cerType)+"</td>"+
							"<td>"+data.cerTypeDesc+"</td>"+
							"<td>"+filterNull(data.phone)+"</td>"+line+
							"<td class='last-col'><a href='javascript:void(0)' tag='del' cardNo='"+data.cardNo+"'>删除</a></td>"+
							"</tr>");
	    			contentjson[data.cardNo]="1";
    			}
    		}
		});
    }
    
    function filterNull(str){
    	if(str==null){
    		return "";
    	} else
    		return str;
    }
    
    //遍历表格，生成json结构
    function createJsonStr(){
    	var tableJsonStr=[];
    	if(type=="ent"){
    		$("#tb_ent_bd tr:gt(0)").each(function(i){
    	    	var json={};
	    	    $(this).children("td").each(function(i){
	    	    	if(i==0){
	    	    		json.entName=$(this).text();
	    	    	} else if(i==1){
	    	    		json.priPID=$(this).text();
	    	    	} else if(i==2){
	    	    		json.regNo=$(this).text();
	    	    	} else if(i==3){
	    	    		json.uniCode=$(this).text();
	    	    	} else if(i==4){
	    	    		json.leRep=$(this).text();
	    	    	} else if(i==5){
	    	    		json.cerType=$(this).text();
	    	    	} else if(i==7){
	    	    		json.cardNo=$(this).text();
	    	    	} else if(i==8){
	    	    		json.record=$(this).attr("value");
	    	    	}
	    	    });
    	    	tableJsonStr.push(json);
	    	});
    	}else{
    		$("#tb_man_bd tr:gt(0)").each(function(i){
    	    	var json={};
	    	    $(this).children("td").each(function(i){
	    	    	if(i==0){
	    	    		json.litiName=$(this).text();
	    	    	} else if(i==1){
	    	    		json.cardNo=$(this).text();
	    	    	} else if(i==2){
	    	    		json.cerType=$(this).text();
	    	    	} else if(i==4){
	    	    		json.phone=$(this).text();
	    	    	} else if(i==5){
	    	    		json.record=$(this).attr("value");
	    	    	}
	    	    });
    	    	tableJsonStr.push(json);
	    	});
    	}
    	return tableJsonStr;
    }
    
    var i=0;
    //保存单据
    function save(){
    	var str= JSON.stringify(createJsonStr());
    	$("#detailJson").val(str);
    	if(i==1) return false;
    	$.ajax({
    		type : "POST",
			url : urlHead+'/noCreditPunish/info/save',
			data : $('#content_form').serialize(),
			datatype : 'JSON',
			async : true,
			success : function(json) {
				layer.close(load);
				if(json.status=="success"){
					i=1;
					layer.msg(json.msg, {time: 1500}, function() {
						window.history.back(-1);
					});
				}else
					layer.msg(json.msg, {time: 4500}, function() {
						$("#save").attr("disabled",false);
					});
			}
    	});
    }
    
    //判断附件是否已存在
    function contains_(array, str){
    	var index, text;
    	for(var i=0; i<array.length; i++){
    		index = array[i].indexOf("-");
			text = array[i].substring(index + 1, array[i].length);
    		if(text==str){
    			return true;
    		}
    	}
    	return false;
    }
    
    //上传部分代码
    var $files=$("#files"), filesArr=[], $upstate=$("#uploadState");
    function upload(e){
    	var batchNo=$("#batchNo").val(), file=$("#file").val();
    	if(file==""){
    		layer.msg("请先选择需要上传的文件", {time: 3000}, function() {});
    		return false;
    	}
    	if(contains_(filesArr, file)){
    		return false;
    	}
    	e.hide();$upstate.show();
    	$.ajaxFileUpload({
            url: urlHead+'/noCreditPunish/info/upload',
            type: 'post',
            data: {"batchNo":batchNo, "fileName":file},
            secureuri: false,
            fileElementId: 'btnFile',
            dataType: 'text',
            success: function(data, status){
            	if(data !="error") {
	            	//放入数组
	            	filesArr.push(data);
	            	var str="";
	            	for(var i=0; i<filesArr.length; i++){
	            		str=str+filesArr[i]+"|";
	            	}
	            	$files.val(str);
					var index = data.indexOf("-");
					var text = data.substring(index + 1, data.length);
					$("#filelabel").append("<p class='file-upload-out'><i class='xbt-icon file-icon'></i><label>"+text+"</label> &nbsp;&nbsp;<a href='javascript:void(0)' path='"+data+"'>删除</a></p>");
	            	layer.msg("上传成功", {time: 1200}, function() {});
            	}else {
            		layer.msg("上传失败", {time: 2000}, function() {});
            	}
            	$("#file").val("");
				//重置file选择
            	$("#fileBox").html("").append('<input type="file" id="btnFile" class="btnFile" name="btnFile"/>');
    			$upstate.hide();
            	e.show();
            },error: function(data, status){
            	$("#file").val("");
				//重置file选择
            	$("#fileBox").html("").append('<input type="file" id="btnFile" class="btnFile" name="btnFile"/>');
    			$upstate.hide();
            	e.show();
            	layer.msg("上传失败，请检查网络及文件大小", {time: 3000}, function() {});
            }
        });
    }
    //委托事件
    $("#fileBox").delegate('input','change',function(){
		var text=$(this).val();
		var index = text.lastIndexOf("\\");
		text = text.substring(index + 1, text.length);
		$("#file").val(text);
	})
    //上传
    $("#btnUpload").click(function(){
		upload($(this));
	})
	//删除附件
	$("#filelabel").delegate('a','click',function(){
		var path=$(this).attr("path");
		var str="";
    	for(var i=0; i<filesArr.length; i++){
    		if(filesArr[i] !=path){
        		str=str+filesArr[i]+"|";
    		}
    	}
    	$files.val(str);
    	var val=$files.val().substring(0,$files.val().length-1);
        filesArr=val.split("|");
    	$(this).parent().remove();
	});
    
    /****ui****/
	var exeBegin=$("#exeBegin");
	var exeEnd=$("#exeEnd");
	$("#isLife").click(function(){
		if($(this).is(':checked')){
			exeBegin.val("").attr("disabled","disabled");
			exeEnd.val("").attr("disabled","disabled");
		}else{
			exeBegin.val("").removeAttr("disabled");
			exeEnd.val("").removeAttr("disabled");
		}
	})
    
    //事件绑定
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
        }, {
            el: '#save',
            event: 'click',
            handler: function() {
            	$("#save").attr("disabled",true);
            	load=layer.load();
            	save();
            }
        }, {
            el: '#cancel',
            event: 'click',
            handler: function() {
            	window.history.back(-1);
            }
        }, {
            el: '#punField',
            event: 'change',
            handler: function() {
            	ruleCauseLoad();
            }
        }, {
            el: '#punCause',
            event: 'change',
            handler: function() {
            	ruleInfoLoad();
            }
        }, {
            el: '#adddetail',
            event: 'click',
            handler: function() {
            	if(type=="ent"){
                	selectEntDialog();
            	}else
            		selectManDialog();
            }
        }])
    }

});