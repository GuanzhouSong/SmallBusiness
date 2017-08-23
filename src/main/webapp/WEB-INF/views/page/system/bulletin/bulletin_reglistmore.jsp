<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
   Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved.
  --%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    <title>更多</title>
    <link rel="shortcut icon" href="../img/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="/css/reg.server.css">
    <link rel="stylesheet" href="/css/vendor/layout-default-latest.css">
</head>
<body class="pd10 index-v2">
<div class="clearfix">
    <div class="col-7 mr-col-01 pos-rel">
    <input type="hidden" id="moreFlag" value="1"/>
        <h4 class="index-item-h rel-bottom"><i class="isprict info-icon"></i>通知公告</h4>
        <div class="shadow-box">
            <ul class="notice-info-list">
        </div>
    </div>
</div>
</div>

<script src="/js/lib/require.js"></script>
<script src="/js/config.js"></script>
<script src="/js/business/bulletin/bulletin_reglist.js"></script>
<script>
window._CONFIG = {
        chooseUrl:'${sessionScope.session_sys_user.userType == '2'?"/syn":"/reg"}',
        <c:if test="${sessionScope.session_sys_user.userType=='1'}">
        collection:'<c:url value="/reg/system/bulletin/collection"/>',
        read:'<c:url value="/reg/system/bulletin/read"/>'
        </c:if>
        <c:if test="${sessionScope.session_sys_user.userType=='2'}">
        collection:'<c:url value="/syn/system/bulletin/collection"/>',
        read:'<c:url value="/syn/system/bulletin/read"/>'
        </c:if>
    }
</script>
</body>
</html>