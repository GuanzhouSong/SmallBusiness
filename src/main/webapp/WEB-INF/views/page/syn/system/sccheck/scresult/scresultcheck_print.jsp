<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="/css/syn.css">
    <link rel="stylesheet" href="/css/vendor/dataTables.bootstrap.min.css">
</head>
<body class="pd10">
<div class="button Noprint" style="width:600px;">
    <a href="javascript:Print();"><strong style="font-size:16px">直接打印</strong></a>
    <a href="javascript:PrintView();"><strong style="font-size:16px">打印预览</strong></a>
    <a href="javascript:PrintSetup();"><strong style="font-size:16px">打印设置</strong></a>
    <a id="closeBtn" href="javascript:window.top.close();"><strong style="font-size:16px">关闭窗口</strong></a>
</div>
<div class="print-nocard">
    <h3>市场监管领域随机抽查情况记录表</h3>
    <div class="mt10">
        <div class="tabbale" data-component="tab">
            <div class="tab-content">
              	<div class="tab-panel tab-panel-show" id="itemPanelDivId">
                    <div class="mt10">
                        <table cellpadding="0" cellspacing="0" border="0" width="100%" class="table-horizontal table-form">
                            <tbody>
                            	<tr>
	                                <td class="bg-gray right" width="12%">任务名称</td>
	                                <td>${pubScentResultDto.taskName }</td>
	                                <td class="bg-gray right" width="12%">任务编号</td>
	                                <td>${pubScentResultDto.taskCode }</td>
	                            </tr>
	                            <tr>
	                                <td class="bg-gray right">被检查对象</td>
	                                <td>${pubScentResultDto.entName }</td>
	                                <td class="bg-gray right">统一信用代码/注册号</td>
	                                <td>${!empty pubScentResultDto.uniCode?pubScentResultDto.uniCode:pubScentResultDto.regNO}</td>
	                            </tr>
	                            <tr>
	                                <td class="bg-gray right" rowspan="2">住所地址</td>
	                                <td rowspan="2">${pubScentResultDto.dom }</td>
	                                <td class="bg-gray right">法定代表人</td>
	                                <td>${pubEppassword.lerep } ${pubEppassword.lerepphone }</td>
	                            </tr>
	                            <tr>
	                                <td class="bg-gray right">工商联络员</td>
	                                <td>${pubEppassword.lianame } ${pubEppassword.tel }</td>
	                            </tr>
	                         </tbody>
	                       </table><br/>
	                       <table id="table2" cellpadding="0" cellspacing="0" border="0" width="100%" class="table-horizontal table-form">
	                       	  <tbody>
	                            <tr>
	                                <td class="bg-gray center" width="30%">检查事项 
	                                <td class="bg-gray center" width="10%">
	                                	是否检查
	                                </td>
	                                <td class="bg-gray center" width="30%">发现问题情况 </td>
	                                <td class="bg-gray center" width="30%">处置情况 </td>
	                            </tr>
	                            <c:forEach var="pubSccheckItem" items="${pubSccheckItemList }" varStatus="status">
		                            <tr>
		                                <td>${status.index + 1}. ${pubSccheckItem.checkName }</td>
		                                <td class="center">
		                                </td>
		                                <td></td>
		                                <td></td>
		                            </tr>
	                            </c:forEach>
	                          </tbody>
	                       </table><br/>
	                       <table cellpadding="0" cellspacing="0" border="0" width="100%" class="table-horizontal table-form">
	                       	  <tbody>
	                            <tr>
	                                <td class="bg-gray center">核查方式(多选)</td>
	                                <td colspan="2">
	                                	<input type="checkbox" value="1" disabled="disabled">实地检查 
		                                <input type="checkbox" value="2" disabled="disabled" >书面检查 
		                                <input type="checkbox" value="3" disabled="disabled" >行政部门检查结果或专业意见
		                                <input type="checkbox" value="4" disabled="disabled" >检验检测 
		                                <input type="checkbox" value="5" disabled="disabled">网络监测
		                                <input type="checkbox" value="6" disabled="disabled">委托专业机构辅助 
	                                 </td>
	                            </tr>
	                            
	                            <tr>
	                                <td class="bg-gray center" rowspan="5" width="20%">检查结果</td>
	                                <td><input class="crt" type="checkbox" value="1" width="40%" disabled="disabled">未发现违法行为</td>
	                                <td><input class="crt" type="checkbox" value="6" width="40%" disabled="disabled">查无下落
	                            </tr>
	                            <tr>
	                                <td><input class="crt" type="checkbox" value="2" disabled="disabled">违反工商行政管理相关规定</td>
	                                <td><input class="crt" type="checkbox" value="7" disabled="disabled">已关闭停业或正在清算</td>
	                            </tr>
	                            <tr>
	                                <td><input class="crt" type="checkbox" value="3" disabled="disabled">违反食品药品管理相关规定</td>
	                                <td><input class="crt" type="checkbox" value="8" disabled="disabled">不予配合情节严重</td>
	                            </tr>
	                            <tr>
	                                <td><input class="crt" type="checkbox" value="4" disabled="disabled">违反质量技术监督相关规定</td>
	                            	<td>
	                                	<input class="crt" type="checkbox" value="9" disabled="disabled">注销
	                                	<input class="crt" type="checkbox" value="10" disabled="disabled">被吊销
	                                	<input class="crt" type="checkbox" value="11" disabled="disabled">被撤销
	                                	<input class="crt" type="checkbox" value="12" disabled="disabled">迁出
	                                </td>
	                            </tr>
	                            <tr>
	                                <td colspan="2"><input class="crt" type="checkbox" value="5" disabled="disabled">违反其他部门相关规定</td>
	                            </tr>
	                            
	                            <tr>
	                                <td class="bg-gray center">后续处置措施</td>
	                                <td colspan="2">
	                                	<input disabled="disabled" class="disaf" type="checkbox" name="disposeMssTmp" value="无须后置处置措施；">无须后置处置措施；<br>
	                                	<input disabled="disabled" class="disaf" type="checkbox" name="disposeMssTmp" value="停止检查，反馈至日常监管部门依法依规处理；">停止检查，反馈至日常监管部门依法依规处理；<br>
	                                	<input disabled="disabled" class="disaf" type="checkbox" name="disposeMssTmp" value="违反本部门相关规定，责令限期整改；后续跟踪由日常监管部门负责；">违反本部门相关规定，责令限期整改；后续跟踪由日常监管部门负责；<br>
	                                	<input disabled="disabled" class="disaf" type="checkbox" name="disposeMssTmp" value="发现案件线索，初步固定相关证据后移送办案机构依法依规处理；">发现案件线索，初步固定相关证据后移送办案机构依法依规处理；<br>
	                                	<input disabled="disabled" class="disaf" type="checkbox" name="disposeMssTmp" value="违反其他部门相关规定，抄告相关部门。">违反其他部门相关规定，抄告相关部门。<br>
	                                	<input disabled="disabled" class="disaf" type="checkbox" name="disposeMssTmp" value="other">其他：<input disabled="disabled" id="other" type="text">
	                                 </td>
	                            </tr>
	                          </tbody>
	                       </table>
	                       <table cellpadding="0" cellspacing="0" border="0" width="100%" class="table-horizontal table-form">
	                       		<tr>
	                       			<td class="bg-gray center" width="20%">检查执行部门</td>
	                       			<td>${pubScentResultDto.checkDeptName }</td>
	                       		</tr>
	                       		<tr>
	                       			<td class="bg-gray center">检查人员签名</td>
	                       			<td>${pubScentResultDto.checkDeptPerson }<div style="float: right;"><fmt:formatDate value="<%=new Date()%>" pattern="yyyy年MM月dd日" /></div></td>
	                       		</tr>
	                       </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value="/js/lib/require.js"/>"></script>
<script src="<c:url value="/js/config.js"/>"></script>
<script src="<c:url value="/js/common/print/printsetup.js"/>"></script>
<script src="<c:url value="/js/common/print/print.js"/>"></script>
</body>
</html>