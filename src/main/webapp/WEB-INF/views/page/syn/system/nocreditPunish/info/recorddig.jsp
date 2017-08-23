<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">

    <title>惩戒记录</title>
    <link rel="stylesheet" href="/css/syn.css">
    <link rel="stylesheet" href="/css/vendor/dataTables.bootstrap.min.css"> 
</head>
<body class="pd10">
	<input type="hidden" id="type" value="${type}"/>
	<input type="hidden" id="key" value="${key}"/>
	<div class="table-out">
	    <table id="record_table" border="0" cellspacing="0" cellpadding="0" class="table-row perc-100 nowrap">
	        <thead>
	        <tr>
	            <th>序号</th>
	            <th>操作</th>
	            <th>批次编号</th>
	            <th>录入时间</th>
	            <th>是否需反馈</th>
	            <th>惩戒领域</th>
	            <th>惩戒依据</th>
	            <th>惩戒事由</th>
	            <th>惩戒规则</th>
	            <th>惩戒提请部门</th>
	            <th>执行有效期</th>
	            <th>惩戒执行部门</th>
	            <th>惩戒执行措施</th>
	            <th>联系人</th>
	            <th>联系电话</th>
	            <th>联系传真</th>
	            <th>联系邮箱</th>
	        </tr>
	        </thead>
	    </table>
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
<script src="/js/syn/system/nocreditPunish/info/record_list.js"></script>
</body>
</html>