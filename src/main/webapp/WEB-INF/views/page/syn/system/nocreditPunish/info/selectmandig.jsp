<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">

    <title>添加惩戒自然人</title>
    <link rel="stylesheet" href="/css/syn.css">
</head>
<body>
<div class="form-list send-message">

	<input type="hidden" class="ipt-txt" id="type" value="${type}">
	
    <div class="form-item clearfix">
        <label class="item-name col-3"><span style="color:red">*</span>证件号：</label>
        <div class="col-6">
            <div class="ipt-box">
                <input type="text" class="ipt-txt" id="keyword" value="" style="width:61%" placeholder="请输入当事人证件号">
                <input type="button" id="searchMan" class="btn btn-xs chose" value="查询">
            </div>
        </div>
    </div>
    
    <input type="hidden" id="cardNo" value="">
    
    <div class="form-item clearfix">
        <label class="item-name col-3"><span style="color:red">*</span>当事人姓名：</label>
        <div class="col-6">
            <div class="ipt-box">
                <input type="text" class="ipt-txt" id="litiName" value="" placeholder="可自行填写" >
            </div>
        </div>
    </div>
    <div class="form-item clearfix">
        <label class="item-name col-3">证件类型：</label>
        <div class="col-6">
            <div class="ipt-box">
            	<select id="cerType">
            		<option value="10" id="10">居民身份证</option>
            		<option value="20" id="20">军官证</option>
            		<option value="30" id="30">警官证</option>
            		<option value="40" id="40">外国(地区)护照</option>
            		<option value="52" id="52">香港身份证</option>
            		<option value="54" id="54">澳门身份证</option>
            		<option value="56" id="56">台湾身份证</option>
            		<option value="90" id="90">其他有效身份证件</option>
            	</select>
            </div>
        </div>
    </div>
    <div class="form-item clearfix">
        <label class="item-name col-3">当事人联系电话：</label>
        <div class="col-6">
            <div class="ipt-box">
                <input type="text" class="ipt-txt" id="phone" value="" placeholder="可自行填写" >
            </div>
        </div>
    </div>
    <div class="form-item clearfix mb20">
        <label class="item-name col-3">失信联合惩戒记录：</label>
        <div class="col-6">
            <div class="ipt-box">
                <input type="text" class="ipt-txt" id="record" value="" readonly >
            </div>
        </div>
    </div>
    <div class="form-item clearfix col-offset-3">
        <div class="ml60">
            <input type="submit" id="confirm"  value="提 交" class="btn mr20">
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
<script src="/js/syn/system/nocreditPunish/info/select_man.js"></script>
</body>
</html>