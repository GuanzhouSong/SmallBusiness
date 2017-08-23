<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">

    <title>添加惩戒企业</title>
    <link rel="stylesheet" href="/css/syn.css">
</head>
<body>
<div class="form-list send-message">

	<input type="hidden" class="ipt-txt" id="type" value="${type}">
	
    <div class="form-item clearfix">
        <label class="item-name col-3">企业名称：</label>
        <div class="col-6">
            <div class="ipt-box">
                <input type="text" id="keyword" class="ipt-txt " style="width:61%"placeholder="请输入企业名称精确查询">
                <input type="button" id="searchEnt" class="btn btn-xs chose" value="查询">
            </div>
        </div>
    </div>
    
    <input type="hidden" class="ipt-txt" id="priPID" value="">
    
    <div class="form-item clearfix">
        <label class="item-name col-3">统一社会信用码：</label>
        <div class="col-6">
            <div class="ipt-box">
                <input type="text" class="ipt-txt" id="uniCode" value="">
            </div>
        </div>
    </div>
    <div class="form-item clearfix">
        <label class="item-name col-3">法定代表人(负责人)：</label>
        <div class="col-6">
            <div class="ipt-box">
                <input type="text" class="ipt-txt" id="leRep" value="">
            </div>
        </div>
    </div>
    <div class="form-item clearfix">
        <label class="item-name col-3">证件类型：</label>
        <div class="col-6">
            <div class="ipt-box">
                <input type="text" class="ipt-txt" id="cerTypeDesc" value="">
                <input type="hidden" class="ipt-txt" id="cerType" value="">
            </div>
        </div>
    </div>
    <div class="form-item clearfix">
        <label class="item-name col-3">证件号：</label>
        <div class="col-6">
            <div class="ipt-box">
                <input type="text" class="ipt-txt" id="cardNo" value="">
            </div>
        </div>
    </div>
    <div class="form-item clearfix mb20">
        <label class="item-name col-3">失信联合惩戒记录：</label>
        <div class="col-6">
            <div class="ipt-box">
                <input type="text" class="ipt-txt" id="record" value="" readonly>
            </div>
        </div>
    </div>
    <div class="form-item clearfix col-offset-3">
        <div class="ml60">
            <input type="submit" id="confirm"  style="display:none" value="提 交" class="btn mr20">
            <input type="submit" id="close" value="关 闭" class="btn">
        </div>
    </div>
</div>
<script>
	var userType = '${sessionScope.session_sys_user.userType}';
	var urlHead = '/syn';
	if(userType==1){
		urlHead='/reg/server';
	}
    window._CONFIG = {
   		urlHead: urlHead
    }
</script>
<script src="/js/lib/require.js"></script>
<script src="/js/config.js"></script>
<script src="/js/syn/system/nocreditPunish/info/select_ent.js"></script>
</body>
</html>