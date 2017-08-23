require(['component/iframeLayer', 'component/dataTable', 'common/util', 'common/http', 'handlebars', 'jquery','jquery.serialize','laydate','jquery.validate', 'common/ajaxfileupload'], function (layer, dataTable, util, http,handlebars) {
    var file;

    init();

	function init() {
        _bind();
    }

    function _bind() {
    	util.bindEvents([{
            el: '#js-upload',
            event: 'click',
            handler: function () {
                _upload();
            }
        },{
            el: '#js-file-btn',
            event: 'change',
            handler: function () {
                var filename=$('#js-file-btn').val()
                $('#js-excel-file').val(filename);
            }
        },{
            el: '.js-ipt-btn',
            event: 'click',
            handler: function () {
               $('#js-file-btn').trigger('click')
            }
        },{
            el: '#js-cancel',
            event: 'click',
            handler: function() {
            	layer.close({reload: false});
            }
        },{
            el: '#choseregUnit',
            event: 'click',
            handler: function () { 
                layer.dialog({
                    title: '选择管辖单位',
                    area: ['400px', '600px'],
                    content: '/commom/server/regunit/regunitsingselect?isPermissionCheck=true',
                    callback: function (data) { 
                		var returncode=data.returncode;
                      	$("#localAdm").val(returncode);
                      	$("#localAdmName").val(data.returname); 
                    }
                })
            }
        }]);
    }

    function _upload() {
//        layer.msg('处理中，请稍后', {icon: 16, time: -1, shade: [0.4, '#CCC']});
    	var localAdm = $("#localAdm").val();
    	if(localAdm == ""){
    		layer.tips("请先选择管辖单位!",$("#choseregUnit"),{tips:3, tipsMore:true, ltype:0});
            return;
    	}
        file = $('#js-file-btn').val();
        if(file == '') {
            layer.tips("请先选择要上传的文件!",$("#js-file-btn"),{tips:3, tipsMore:true, ltype:0});
            return;
        }
        if(file.indexOf('.xlsx') != -1 || file.indexOf('.xls') != -1 || file.indexOf('.xlsm') != -1){
        	var indexLayer;
        	indexLayer = layer.msg('导入中，请稍后', {icon: 16, time: -1, shade: [0.4, '#CCC']});
        	$.ajaxFileUpload({
        		url: '/syn/server/drcheck/pubscagent/addbatch',
        		type: 'post',
        		data: {"fileName":file,"localAdm":localAdm},
        		secureuri: false,
        		fileElementId: 'js-file-btn',
        		dataType: 'json',
        		success: function(data){
        			if(data.status == 'success') {
    					layer.msg("导入成功", {time: 1000}, function () {});
        			}else{
        				layer.alert("导入提示如下：<br/>"+data.message);
        			}
        			layer.close({reload: true});
        			indexLayer.close(index);
        		}
        	});
        }else{
        	layer.msg('只能导入excel文件!', {time: 800});
        	$('#js-file-btn').val("");
        	$('#js-excel-file').val("");
        }
    }
});
