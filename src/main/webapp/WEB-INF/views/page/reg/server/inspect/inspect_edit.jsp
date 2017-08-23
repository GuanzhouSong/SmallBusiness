<%--
   Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved.
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    <title>补报核查记录</title>
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="/css/reg.server.css">
    <link rel="stylesheet" href="/css/vendor/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="/js/lib/jquerymultiselect/jquery.multiselect.css">

    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
<div class="pd10">
    <table cellpadding="0" cellspacing="0" border="0" class="table-horizontal">
        <tbody>
        <tr>
            <td class="bg-gray right" width="10%">企业名称</td>
            <td class="" width="30%">${midBaseInfo.entName}</td>
            <td class="bg-gray right" width="10%">统一代码/注册号</td>
            <td class="" width="20%">${empty midBaseInfo.uniCode ?midBaseInfo.regNO:midBaseInfo.uniCode}</td>
            <td class="bg-gray right">成立日期</td>
            <td class=""><fmt:formatDate value="${midBaseInfo.estDate}" pattern="yyyy-MM-dd"/></td>
        </tr>
        <tr>
            <td class="bg-gray right" width="10%">法定代表人</td>
            <td class="" width="30%">${midBaseInfo.leRep} ${midBaseInfo.tel}</td>
            <td class="bg-gray right" width="10%">补报年度</td>
            <td class="" width="20%"><font color="red">${yrRegCheck.year}</font></td>
            <td class="bg-gray right">登记机关</td>
            <td class="">${midBaseInfo.regOrgName}</td>
        </tr>
        <tr>
            <td class="bg-gray right" width="10%">工商联络员</td>
            <td class="" width="30%">${linkmanAndTel}</td>
            <td class="bg-gray right" width="10%">补报日期</td>
            <td class="" width="20%"><fmt:formatDate value="${yrRegCheck.firstReportTime}" pattern="yyyy-MM-dd"/></td>
            <td class="bg-gray right">管辖单位</td>
            <td class="">${midBaseInfo.localAdmName}</td>
        </tr>
        <tr>
            <td class="bg-gray right" width="10%">住所</td>
            <td class="" width="30%">${midBaseInfo.dom}</td>
            <td class="bg-gray right" width="10%">登记状态</td>
            <td class="" width="20%">
                <c:if test="${midBaseInfo.regState == 'X' || midBaseInfo.regState == 'K' || midBaseInfo.regState == 'A'
            	 || midBaseInfo.regState == 'Q' || midBaseInfo.regState == 'B'}">
                    存续
                </c:if>
                <c:if test="${midBaseInfo.regState == 'C'}">
                    撤销
                </c:if>
                <c:if test="${midBaseInfo.regState == 'D'|| midBaseInfo.regState == 'DA'}">
                    吊销
                </c:if>
                <c:if test="${midBaseInfo.regState == 'QX'}">
                    迁出
                </c:if>
                <c:if test="${midBaseInfo.regState == 'XX' || midBaseInfo.regState == 'DX'}">
                    注销
                </c:if>
            </td>
            <td class="bg-gray right">片区/商圈</td>
            <td class="">${midBaseInfo.sliceNOName}</td>
        </tr>
        </tbody>
    </table>
    <form id="infoForm">
        <input type="hidden" id="fromtype"  name="fromtype" value="${fromtype}"/>
        <input type="hidden" name="uid" value="${reportCheckInfo.UID}"/>
        <input type="hidden" name="entName" value="${yrRegCheck.entName}"/>
        <input type="hidden" name="localAdm" value="${yrRegCheck.localAdm}"/>
        <input type="hidden" name="uniCode" value="${yrRegCheck.uniCode}"/>
        <input type="hidden" name="regNO" value="${yrRegCheck.regNO}"/>
        <input type="hidden" name="priPID" value="${yrRegCheck.priPID}"/>
        <input type="hidden" name="regState" value="${yrRegCheck.regState}"/>
        <input type="hidden" name="estDate" value="<fmt:formatDate value="${midBaseInfo.estDate}" pattern="yyyy-MM-dd"/>"/>
        <input type="hidden" name="checkDep" value="${yrRegCheck.checkDep}"/>
        <input type="hidden" name="entTypeCatg" value="${yrRegCheck.entTypeCatg}"/>
        <input type="hidden" name="year" value="${yrRegCheck.year}"/>
        <input type="hidden" id="codes_com_size" value="${codes_com.size()}"/>
        <input type="hidden" id="codes_key_size" value="${codes_key.size()}"/>
        <input type="hidden" name="firstReportTime" value="<fmt:formatDate value="${yrRegCheck.firstReportTime}" pattern="yyyy-MM-dd"/>"/>


        <div class="light-title-info">补报年度：${yrRegCheck.year}　　　补报日期：<fmt:formatDate
                value="${yrRegCheck.firstReportTime}" pattern="yyyy-MM-dd"/></div>
        <div class="com-info-title pdl10 left mt10 clearfix">
            <span class="fl">一般核查内容</span>
            <div class="fl com-info-subtit ml10">
                <span class="fl"><input type="checkbox" id="check-all-nomal" ${fromtype =='detail'?'disabled="disabled"':''}><span class="light-blue">全选一致</span></span>
                <span class="light-yellow ml10 fl">以下内容核查情况一致的请选择 </span>
                <span class="checkbox-show cs-checked"></span>
                <span class="light-yellow fl">，不一致的请选择 </span>
                <span class="checkbox-show cs-nochecked"></span>
                <span class="light-yellow fl">，并记录实际情况。</span>
            </div>
        </div>
        <!-- 一般核查内容 -->
        <table cellpadding="0" cellspacing="0" border="0" class="table-horizontal">
            <tbody>
            <c:forEach var="o" items="${codes_com}" varStatus="status">
                <c:set value="" var="temStatus"/>
                <c:set value="" var="tempItemContent"/>
                <c:forEach var="r" items="${reportCheckItems_common}">
                    <c:if test="${r.itemCode == o.code && r.status == 'N'}">
                        <c:set value="N" var="temStatus"/>
                        <c:set value="${r.itemContent}" var="tempItemContent"/>
                    </c:if>
                    <c:if test="${r.itemCode == o.code && r.status == 'Y'}">
                        <c:set value="Y" var="temStatus"/>
                    </c:if>
                </c:forEach>

                ${status.index % 2==0 ? ' <tr>':''}
                <td class="bg-gray right" width="17%">
                    <div class="clearfix">
                        <span id="${o.code}" class="checkbox-show f1  class1 ${temStatus == 'N' ? 'cs-nochecked':' '}"></span>
                        <span id="${o.code}"  class="checkbox-show f1   class2 csg-checked ${temStatus == 'Y' ? 'cs-checked':' '}"></span>
                        <em>${o.content}</em>
                    </div>
                </td>
                <td width="33%" ${status.index % 2==1 ||status.last ? ' colspan=3':''}>
                    <div class="ipt-box mr10 item-text-normal" ${temStatus == 'N' ? '':'style="display:none"'} >
                    <input type="text" class="ipt-txt" value="${tempItemContent}" ${fromtype =='detail'?'readonly="readonly"':''}  placeholder="请输入实际情况">
                    </div>
                </td>
                ${status.index % 2==1 ||status.last ? '</tr>':''}
            </c:forEach>

            </tbody>
        </table>
        <div class="com-info-title pdl10 left mt10 clearfix">
            <span class="fl">重要核查内容</span>
            <div class="fl com-info-subtit ml10">
                <span class="fl"><input type="checkbox" id="check-all-nomal-key" ${fromtype =='detail'?'disabled="disabled"':''}><span
                        class="light-blue">全选一致</span></span>
                <span class="light-yellow ml10 fl">以下内容核查情况一致的请选择 </span>
                <span class="checkbox-show cs-checked"></span>
                <span class="light-yellow fl">，不一致的请选择 </span>
                <span class="checkbox-show cs-nochecked"></span>
                <span class="light-yellow fl">，并记录实际情况。</span>
            </div>
        </div>
        <!-- 重点核查内容 -->
        <table cellpadding="0" cellspacing="0" border="0" class="table-horizontal">
            <tbody>
            <c:forEach var="o" items="${codes_key}" varStatus="status">
                <c:set value="" var="temStatus"/>
                <c:set value="" var="tempItemContent"/>
                <c:forEach var="r" items="${reportCheckItems_key}">
                    <c:if test="${r.itemCode == o.code && r.status == 'N'}">
                        <c:set value="N" var="temStatus"/>
                        <c:set value="${r.itemContent}" var="tempItemContent"/>
                    </c:if>
                    <c:if test="${r.itemCode == o.code && r.status == 'Y'}">
                        <c:set value="Y" var="temStatus"/>
                    </c:if>
                </c:forEach>
                ${status.index % 2==0 ? ' <tr>':''}
                <td class="bg-gray right" width="17%">
                    <div class="clearfix">
                        <span id="${o.code}" class="checkbox-show  class1-key f1 ${temStatus == 'N' ? 'cs-nochecked':' '}"></span>
                        <span id="${o.code}"  class="checkbox-show  class2-key f1  csg-checked ${temStatus == 'Y' ? 'cs-checked':' '}"></span>
                        <em>${o.content}</em>
                    </div>
                </td>
                <td width="33%" ${status.index % 2==1 ||status.last ? ' colspan=3':''}>
                    <div class="ipt-box mr10 item-text-key" ${temStatus == 'N' ? '':'style="display:none"'} >
                        <input type="text" class="ipt-txt"  value="${tempItemContent}" ${fromtype =='detail'?'readonly="readonly"':''}  placeholder="请输入实际情况">
                    </div>
                </td>
                ${status.index % 2==1 ||status.last ? '</tr>':''}
            </c:forEach>

            </tbody>
        </table>
        <!-- 补充核查内容 -->
        <div class="tab-content">
            <h5 class="com-info-title pdl10 left mt10">补充核查内容</h5>
            <table cellpadding="0" cellspacing="0" border="0" width="100%" class="table-horizontal table-form">
                <tbody>
                <tr>
                    <td class="bg-gray right" width="12%">发现涉嫌违反企业<br/>登记法律法规情况
                    </td>
                    <td colspan="3" class="h76 pd3">
                        <c:forEach var="o" items="${codes_reglaw}" varStatus="status">
                            ${status.index % 3==0 ? '<div class="radio-box w235">':''}
                            <label><input type="checkbox" class="scResult" name="reglaw"
                                          id="reglaw${o.code}" ${fromtype =='detail'?'disabled="disabled"':''}
                            <c:forEach var="r"
                                       items="${reportCheckItems_reglaw}"> ${r.itemCode == o.code ? 'checked':''} </c:forEach>
                                          value="${o.code}">${o.content}</label>
                            ${status.index % 3==2 ||status.last ? ' </div>':''}
                        </c:forEach>

                        <div><textarea name="reglawCon" id="reglawCon" cols=" "placeholder="请输入具体情况说明"
                                       rows="5"${fromtype =='detail'?'readonly="readonly"':''}>${reportCheckItems_reglaw.size()<1?"":reportCheckItems_reglaw.get(0).itemContent}</textarea>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td class="bg-gray right" width="12%">发现涉嫌违反
                        <br/>其它法律法规情况
                    </td>
                    <td colspan="3" class="h76">
                        <c:forEach var="o" items="${codes_othlaw}" varStatus="status">
                            ${status.index % 3==0 ? '<div class="radio-box w235">':''}
                            <label><input type="checkbox" class="scResult" name="othlaw"
                                          id="othlaw${o.code}"  ${fromtype =='detail'?'disabled="disabled"':''}
                            <c:forEach var="r"
                                       items="${reportCheckItems_othlaw}"> ${r.itemCode == o.code ? 'checked':''} </c:forEach>
                                          value="${o.code}">${o.content}</label>
                            ${status.index % 3==2 ||status.last ? ' </div>':''}
                        </c:forEach>

                        <div><textarea name="othlawCon" id="othlawCon" cols=" "placeholder="请输入具体情况说明"
                                       rows="5" ${fromtype =='detail'?'readonly="readonly"':''}
                        >${reportCheckItems_othlaw.size()<1?"":reportCheckItems_othlaw.get(0).itemContent}</textarea>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            <h5 class="com-info-title mt10  pdl10 left">检查方式</h5>
            <table cellpadding="0" cellspacing="0" border="0" width="100%" class="table-horizontal table-form">
                <tbody>
                <tr>
                    <td>
                        <div class="radio-box w210">

                            <label><input type="checkbox" class="scResult" name="itemWay"
                            ${fromtype =='detail'?'disabled="disabled"':''}
                            <c:forEach var="o" items="${reportItemWays}"> ${o == 1 ? 'checked':''} </c:forEach>
                                          value="1">书面检查</label>
                            <label><input type="checkbox" class="scResult" name="itemWay"
                            ${fromtype =='detail'?'disabled="disabled"':''}
                            <c:forEach var="o" items="${reportItemWays}"> ${o == 2 ? 'checked':''} </c:forEach>
                                          value="2">实地检查</label>
                            <label><input type="checkbox" class="scResult" name="itemWay"
                            ${fromtype =='detail'?'disabled="disabled"':''}
                            <c:forEach var="o" items="${reportItemWays}"> ${o == 3 ? 'checked':''} </c:forEach>
                                          value="3">审计等第三方审验</label>
                            <label><input type="checkbox" class="scResult" name="itemWay"
                            ${fromtype =='detail'?'disabled="disabled"':''}
                            <c:forEach var="o" items="${reportItemWays}"> ${o == 4 ? 'checked':''} </c:forEach>
                                          value="4">向相关部门核实</label>
                            <label><input type="checkbox" class="scResult" name="itemWay"
                            ${fromtype =='detail'?'disabled="disabled"':''}
                            <c:forEach var="o" items="${reportItemWays}"> ${o == 5 ? 'checked':''} </c:forEach>
                                          value="5">网络监测</label>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            <h5 class="com-info-title mt10  pdl10 left">核查情况概论</h5>
            <table cellpadding="0" cellspacing="0" border="0" width="100%" class="table-horizontal table-form">
                <tbody>
                <tr>
                    <td class="bg-gray right" width="12%">信息公示检查结果
                    </td>
                    <td colspan="3" class="h76 pd3">
                        <c:forEach var="o" items="${codes_res}" varStatus="status">
                            ${status.index % 3==0 ? '<div class="radio-box w235">':''}
                            <label><input type="checkbox" class="scResult" name="resResult"
                                          id="resResult${o.code}" ${fromtype =='detail'?'disabled="disabled"':''}
                            <c:forEach var="r"
                                       items="${reportCheckResults}"> ${r.resResult == o.code ? 'checked':''} </c:forEach>
                                          value="${o.code}">${o.content}</label>
                            ${status.index % 3==2 ||status.last ? ' </div>':''}
                        </c:forEach>

                    </td>
                </tr>
                <tr>
                    <td class="bg-gray right" width="12%">后续处置</td>
                    <td colspan="3" class="h76">
                        <c:forEach var="o" items="${codes_dispose}" varStatus="status">
                            ${status.index % 3==0 ? '<div class="radio-box w235">':''}
                            <label><input type="checkbox" class="scResult" name="resDispose"
                                          id="resDispose${o.code}" ${fromtype =='detail'?'disabled="disabled"':''}
                            <c:forEach var="r" items="${reportDispose}"> ${r == o.code ? 'checked':''} </c:forEach>
                                          value="${o.code}">${o.content}</label>
                            ${status.index % 3==2 ||status.last ? ' </div>':''}
                        </c:forEach>

                        <div><textarea name="resContent" id="resContent" cols=" "
                                       rows="5" ${fromtype =='detail'?'readonly="readonly"':''}>${reportCheckInfo.resContent}</textarea></div>
                    </td>
                </tr>
                <tr>
                    <td class="bg-gray right">检查机关</td>
                    <td>
                        <div class="ipt-box">
                            <input type="text" class="ipt-txt" name="infoCheckOrgName"  id="infoCheckOrgName" readonly="readonly"
                                   value="${reportCheckInfo.infoCheckOrgName}">
                            <input type="hidden" id="infoCheckOrg" name="infoCheckOrg" value="${reportCheckInfo.infoCheckOrg}">
                        </div>
                    </td>
                    <td class="bg-gray right" width="12%">检查部门</td>
                    <td>
                        <div class="ipt-box"> <input type="text" class="ipt-txt" readonly="readonly" id="departName"  name="departName" value="${reportCheckInfo.departName}">
                            <input type="hidden" name="depart"  id="depart"  value="${reportCheckInfo.depart}">

                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="bg-gray right">检查人员</td>
                    <td colspan="3">
                        <div class="ipt-box col-3 mr10">
                            <input type="text" class="ipt-txt" name="infoCheckMan"
                                   id="infoCheckMan" ${fromtype =='detail'?'readonly="readonly"':''}
                                   value="${reportCheckInfo.infoCheckMan}">
                        </div>
                        <p class="light-ff9900 mt6">注：实地核查人员不少于2人</p>
                    </td>
                </tr>
                <tr>
                    <td class="bg-gray right">检查日期</td>
                    <td colspan="3">
                        <div class="ipt-box col-3 mr10">
                            <input type="text" class="ipt-txt" name="infoCheckDate" id="infoCheckDate"
                                   readonly="readonly" ${fromtype =='detail'?'':'onclick="laydate()"'}
                                   value="<fmt:formatDate value="${reportCheckInfo.infoCheckDate}"  pattern="yyyy-MM-dd"/>">
                        </div>
                        <p class="light-ff9900 mt6">注：请录入形成有效核查结论的日期，该日期即为公示系统上公示的检查日期。</p>
                    </td>
                </tr>
                <tr>
                    <td class="bg-gray right">录入人员</td>
                    
                    <td>${reportCheckInfo.infoFillMan}</td>
                    <td class="bg-gray right">录入日期</td>
                    <td><fmt:formatDate value="${reportCheckInfo.infoFillDate}" pattern="yyyy-MM-dd"/></td>
                </tr>
                </tbody>
            </table>
            <input type="hidden" name="infoFillMan" value="${reportCheckInfo.infoFillMan}">
            <input type="hidden" name="infoFillDate" id="infoFillDate"
                           value="<fmt:formatDate value="${reportCheckInfo.infoFillDate}" pattern="yyyy-MM-dd"/>">
        </div>

        <c:if test="${fromtype =='checkedit'||fromtype =='detail'}">
            <h5 class="com-info-title left pdl10 mt10">审核情况</h5>
            <table cellpadding="0" cellspacing="0" border="0" width="100%" class="table-horizontal table-form">
                <tbody>
                <tr>
                    <td class="bg-gray right">审核意见</td>
                    <td class="h76" colspan="4">
                        <div class="radio-box radio-box-ft12 fl mr10" id="auditStateDiv">
                            <label><input type="radio" class="auditState" name="infoAuditResult" value="4"
                                ${fromtype =='detail'?'disabled="disabled"':''}
                             ${reportCheckInfo.infoAuditResult=='4'?'checked':''}>同意</label>
                            <label><input type="radio" class="auditState" name="infoAuditResult" value="2"
                                ${fromtype =='detail'?'disabled="disabled"':''}
                                ${reportCheckInfo.infoAuditResult=='2'?'checked':''} >不同意</label>
                        </div>
                        <div class="light-ff9900 fl mt5">注：如发现核查结果有误请选择“不同意”则退回至“补报核查信息录入”功能中重新修改。</div>
                        <div><textarea id="infoAuditOpin" cols=" " ${fromtype =='detail'?'readonly="readonly"':''} name="infoAuditOpin" rows="5">${reportCheckInfo.infoAuditOpin}</textarea></div>
                    </td>
                </tr>
                <tr>
                    <td class="bg-gray right" width="12%">审核人员</td>
                    <td width="38%">${reportCheckInfo.infoAuditMan}</td>
                    <td class="bg-gray right" width="12%">审核日期</td>
                    <td><fmt:formatDate value="${reportCheckInfo.infoAuditDate}" pattern="yyyy-MM-dd"/><span class="light-ff9900 ml20">注：审核通过的日期即为检查信息的公示日期。</span></td>
                </tr>
                </tbody>
            </table>
            <input type="hidden" value="${reportCheckInfo.infoAuditMan}" name="infoAuditMan"/>
            <input type="hidden" value="<fmt:formatDate value="${reportCheckInfo.infoAuditDate}" pattern="yyyy-MM-dd"/>" name="infoAuditDate"/>
        </c:if>

        <c:if test="${fromtype !='detail'}">
            <p class="center mt20 mb30">
                <input type="submit" class="btn mr10" id="save" value="提 交">
                <input type="button" class="btn" id="cancel" value="取 消">
            </p>
        </c:if>
<c:if test="${fromtype =='detail'}">
    <p class="center mt20 mb30">
        <input type="button" class="btn" id="cancel" value="关 闭">
    </p>
</c:if>
    </form>
</div>
<script>
    window._CONFIG = {
        <c:if test="${sessionScope.session_sys_user.isAdmin!='1'}">
        select_dept_url: '<c:url value="/common/system/dept/tree/select"/>',
        </c:if>
        <c:if test="${sessionScope.session_sys_user.isAdmin=='1'}">
        select_dept_url: '<c:url value="/common/system/dept/tree/selectAll"/>',
        </c:if>
}
</script>
<script id="tpl" type="text/x-handlebars-template">
    {{#each func}}
    <button type="button" class="{{this.class}}">{{this.text}}</button>
    {{/each}}
</script>
<script src="/js/lib/require.js"></script>
<script src="/js/config.js"></script>
<script src="/js/reg/server/inspect/inspect_edit.js"></script>
</body>
</html>
