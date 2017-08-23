<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">

    <title>执法人员列表</title>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="/css/reg.server.css">
    <link rel="stylesheet" href="/css/vendor/dataTables.bootstrap.min.css">
	<link rel="stylesheet" href="/js/lib/ztree/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" href="/js/lib/jquerymultiselect/jquery.multiselect.css">
</head>
<body class="pd10">
<div class="form-box">
	<form id="chooseagentform">
	<input type="hidden" name="deptTaskUid" id="deptTaskUid" value="${deptTaskUid }"/>
	<input type="hidden" id="deptCode" value="${deptCode }"/>
	<input type="hidden" name="scTypeWay" id="scTypeWay" value="${scTypeWay }"/>
    <div class="form-list">
        <div class="form-item clearfix">
            <div class="col-4">
                <label class="item-name col-5">姓名：</label>
                <div class="col-6">
                    <div class="ipt-box col-12">
                        <input type="text" name="agentName" value="" class="ipt-txt"/>
                    </div>
                </div>
            </div>
            <div class="col-4">
                <label class="item-name col-5">单位层级：</label>
                <div class="col-6">
                    <div class="ipt-box col-12">
                       <select name="unitLevel">
                     <option value="">全部</option>
                     <option value="3">省级</option>
                     <option value="1">市级</option>
                     <option value="2">县级</option>
                     </select> 
                    </div>
                </div>
            </div>
            <div class="col-4">
                <label class="item-name col-5"> 岗位资格资质：</label>
                <div class="col-6">
                    <div class="ipt-box col-12">
                            <input type="hidden" class="ipt-txt" name="stationTermArr" id="stationTerm" value=""/>
	                        <select id="stationTermM" multiple="multiple">
	                            <option value="N">否</option>
<!-- 	                            <option value="Y">是</option> -->
	                            <option value="1">保化检查员</option>
	                            <option value="2">药品检查员</option>
	                            <option value="3">医疗器械检查员</option>
	                            <option value="4">特种设备安全监察员</option>
	                        </select>                     
                        </span>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-item clearfix">
            <div class="col-4">
                <label class="item-name col-5">岗位管辖片区：</label>
                <div class="col-6">
                   <div class="ipt-box col-12">
                    <input type="hidden" name="slicenNO" id="localAdm"/>
                    <input type="text" class="ipt-txt" id="localAdmName"  placeholder="请选择管辖单位" readonly/>
                     <span class="add-icon" id="choseregUnit">
                         <i></i>
                    </span>
                  </div>
                </div>
            </div>
            <div class="col-4">
                <label class="item-name col-5"> 执法清单事项范围：</label>
                <div class="col-6">
                    <div class="ipt-box col-12">
                          <input type="hidden" class="ipt-txt" name="agentRangeArr" id="agentRange" value=""/>
                          <input type="text" class="ipt-txt" id="agentRangeName"/>
                           <span class="add-icon" id="agentRangeChoose">
                               <i></i>
                        </span>
                    </div>
                </div>
            </div>
            <div class="col-4">
                    <label class="item-name col-5">岗位大类：</label>
                    <div class="col-6">
                        <div class="ipt-box col-12">
                            <input type="hidden" class="ipt-txt" name="deptCatgArr" id="deptCatg" value=""/>
	                        <select id="deptCatgM" multiple="multiple">
	                            <option value="1">工商行政管理类</option>
	                            <option value="2">食品药品管理类</option>
	                            <option value="3">质量技术监督类</option>
	                            <option value="4">安全生产类</option>
	                            <option value="5">物价管理类</option>
	                        </select>
                        </div>
                    </div>
             </div>
        </div>
        <div class="form-item clearfix mt20">
            <div class="btn-box">
                <input type="button" value="查 询" id="search" class="btn mr20">
                <input type="button" id="cancel" value="重 置" class="btn" onclick="$('#chooseagentform')[0].reset();">
            </div>
        </div>
    </div>
    </form>
</div>
<div>
    <table border="0" id="agent-table" cellspacing="0" cellpadding="0" class="table-row perc-100 mt30 nowrap">
        <thead>
        <tr>
            <th width="5%">序号</th>
            <th width="5%"><input type="checkbox" id="chooseAll"/></th>
            <th width="10%">姓名</th>
            <th width="10%">单位 </th>
            <th width="5%">单位层级</th>
            <th width="5%">单位类别</th>
            <th width="10%">岗位资格资质</th>
            <th width="15%">岗位大类</th>
            <th width="15%">岗位管辖片区</th>
            <th width="15%">执法清单事项范围</th>
        </tr>
        </<thead>
    </table>
</div>
<div class="center mb20">
<!--     <input type="button" value="打印" class="btn mr20"/> -->
    <input type="button" value="保存" class="btn"  id="save"/>
    <input type="button" value="关闭" class="btn" id="close"/>
</div>
<script src="/js/lib/require.js"></script>
<script src="/js/config.js"></script>
<script src="/js/syn/system/drcheck/sctack/chooseagent_main.js"></script>
</body>
</html>