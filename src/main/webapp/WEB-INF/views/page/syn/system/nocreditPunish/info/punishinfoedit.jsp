<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">

    <title>编辑联合惩戒单</title>
    <link rel="stylesheet" href="/css/syn.css">
</head>
<body class="pd10">
<div>
    <div class="creat-copy">
        <div class="tabbale" data-component="tab">
            <div class="tab-header type-tab clear pdb10 border-bottom">
                <label class="right">惩戒单类型：</label>
                <ul class="clearfix">
                    <c:if test="${info.punType == 'ent'}">
                        <li id="ent_type" class="tab-selected"><span class="tab-item"><a
                                href="javascript:void(0);">企业</a></span></li>
                    </c:if>
                    <c:if test="${info.punType == 'man'}">
                        <li id="man_type" class="tab-selected"><span class="tab-item"><a
                                href="javascript:void(0);">自然人</a></span></li>
                    </c:if>
                </ul>
            </div>
            <div class="tab-content">
                <div class="tab-panel tab-panel-show">
                    <div class="">
                        <div class="print-nocard">
                            <h3 class="mt33">
                                <c:if test="${info.punType == 'ent'}">
                                    企业失信惩戒创建单
                                </c:if>
                                <c:if test="${info.punType == 'man'}">
                                    自然人失信惩戒创建单
                                </c:if>
                                <span style="font-size:13px">惩戒单批号： ${info.batchNo}</span>
                            </h3>
                            <p class="right print-data">录入日期：<span><fmt:formatDate value="${info.createTime}"
                                                                                   pattern="yyyy年MM月dd日"/></span></p>

                            <form id="content_form">
                                <input type="hidden" id="batchNo" name="batchNo" value="${info.batchNo}"/>
                                <input type="hidden" id="punType" name="punType" value="${info.punType}"/>
                                <input type="hidden" id="detailJson" name="detailJson" value=""/>

                                <div class="bg-gray3">
                                    <table border="0" cellspacing="0" cellpadding="0" class="table-form mt30">
                                        <tbody>
                                        <tr>
                                            <td class="tb-form-hd" width="12%"><span class="light bold"> *</span>惩戒领域
                                            </td>
                                            <td width="35%">
                                                <div class="ipt-box">
                                                    <select name="punField" id="punField">
                                                        <option value=""
                                                                <c:if test="${info.punField==''}">selected="selected"</c:if>>
                                                            请选择..
                                                        </option>
                                                        <option value="安全生产领域"
                                                                <c:if test="${info.punField=='安全生产领域'}">selected="selected"</c:if>>
                                                            安全生产领域
                                                        </option>
                                                        <option value="旅行社经营领域"
                                                                <c:if test="${info.punField=='旅行社经营领域'}">selected="selected"</c:if>>
                                                            旅行社经营领域
                                                        </option>
                                                        <option value="国有企业监督管理领域"
                                                                <c:if test="${info.punField=='国有企业监督管理领域'}">selected="selected"</c:if>>
                                                            国有企业监督管理领域
                                                        </option>
                                                        <option value="互联网上网服务及娱乐场所经营领域"
                                                                <c:if test="${info.punField=='互联网上网服务及娱乐场所经营领域'}">selected="selected"</c:if>>
                                                            互联网上网服务及娱乐场所经营领域
                                                        </option>
                                                        <option value="食品药品经营领域"
                                                                <c:if test="${info.punField=='食品药品经营领域'}">selected="selected"</c:if>>
                                                            食品药品经营领域
                                                        </option>
                                                        <option value="饲料及兽药经营领域"
                                                                <c:if test="${info.punField=='饲料及兽药经营领域'}">selected="selected"</c:if>>
                                                            饲料及兽药经营领域
                                                        </option>
                                                        <option value="出版经营领域"
                                                                <c:if test="${info.punField=='出版经营领域'}">selected="selected"</c:if>>
                                                            出版经营领域
                                                        </option>
                                                        <option value="电影经营领域"
                                                                <c:if test="${info.punField=='电影经营领域'}">selected="selected"</c:if>>
                                                            电影经营领域
                                                        </option>
                                                        <option value="营业性演出经营领域"
                                                                <c:if test="${info.punField=='营业性演出经营领域'}">selected="selected"</c:if>>
                                                            营业性演出经营领域
                                                        </option>
                                                        <option value="建筑施工领域（含施工、勘察、设计和工程监理）"
                                                                <c:if test="${info.punField=='建筑施工领域（含施工、勘察、设计和工程监理）'}">selected="selected"</c:if>>
                                                            建筑施工领域（含施工、勘察、设计和工程监理）
                                                        </option>
                                                        <option value="电子认证领域"
                                                                <c:if test="${info.punField=='电子认证领域'}">selected="selected"</c:if>>
                                                            电子认证领域
                                                        </option>
                                                        <option value="证券期货市场监督管理领域"
                                                                <c:if test="${info.punField=='证券期货市场监督管理领域'}">selected="selected"</c:if>>
                                                            证券期货市场监督管理领域
                                                        </option>
                                                        <option value="税收管理"
                                                                <c:if test="${info.punField=='税收管理'}">selected="selected"</c:if>>
                                                            税收管理
                                                        </option>
                                                        <option value="普遍性限制措施"
                                                                <c:if test="${info.punField=='普遍性限制措施'}">selected="selected"</c:if>>
                                                            普遍性限制措施
                                                        </option>
                                                    </select>
                                                </div>
                                            </td>
                                            <td class="tb-form-hd" width="15%"><span class="light bold"> *</span>惩戒事由
                                            </td>
                                            <td>
                                                <div class="ipt-box">
                                                    <select name="punCause" id="punCause">
                                                        <option value="">请选择..</option>
                                                        <c:forEach items="${causes}" var="obj">
                                                            <option value="${obj.punCause}"
                                                                    <c:if test="${obj.punCause == info.punCause}">selected="selected"</c:if>>${obj.punCause}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="tb-form-hd">惩戒依据（条文）</td>
                                            <td><span id="legBasis_s">${info.legBasis}</span>
                                                <input type="hidden" id="legBasis" name="legBasis"
                                                       value="${info.legBasis}"/>
                                            </td>

                                            <td class="tb-form-hd">惩戒规则</td>
                                            <td><span id="punRule_s">${info.punRule}</span>
                                                <input type="hidden" id="punRule" name="punRule"
                                                       value="${info.punRule}"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="tb-form-hd">惩戒执行措施</td>
                                            <td colspan="3"><span id="punMea_s">${info.punMea}</span>
                                                <input type="hidden" id="punMea" name="punMea" value="${info.punMea}"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="tb-form-hd" width="140px">法律文书编号</td>
                                            <td>
                                                <div class="ipt-box">
                                                    <input type="text" class="ipt-txt" name="legNo" maxlength="50"
                                                           value="${info.legNo}"/>
                                                </div>
                                            </td>

                                            <td class="tb-form-hd" width="100px">文书时间</td>
                                            <td>
                                                <div class="ipt-box col-5">
                                                    <input type="text" name="legDate" class="ipt-txt"
                                                           onclick="laydate();"
                                                           value="<fmt:formatDate value="${info.legDate}" pattern="yyyy-MM-dd" />"/>
                                                </div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="tb-form-hd"><span class="light bold">* 请选择确认 </span>惩戒执行部门</td>
                                            <td>
                                                <div class="ipt-box clear">
                                                    <div class="col-10">
                                                        <span id="punExeDept_s"
                                                              class="multi-line">${info.punExeDept}</span>
                                                        <input type="hidden" name="punExeDeptCode" id="punExeDeptCode"
                                                               value="${info.punExeDeptCode}"/>
                                                        <input type="hidden" name="punExeDept" id="punExeDept"
                                                               value="${info.punExeDept}"/>
                                                    </div>
                                                    <input type="button" id="selectPunExeDept" class="btn btn-xs chose"
                                                           value="选择">
                                                </div>
                                            </td>

                                            <td class="tb-form-hd">执行有效期</td>
                                            <td class="pd0"><span class="item-txt">从</span>
                                                <div class="ipt-box col-4">
                                                    <input type="text" class="ipt-txt" id="exeBegin" name="exeBegin"
                                                           onclick="laydate();"
                                                           value="<fmt:formatDate value="${info.exeBegin}" pattern="yyyy-MM-dd" />"
                                                           <c:if test="${info.isLife=='1'}">disabled="disabled"</c:if> />
                                                </div>
                                                <span class="item-txt">至</span>
                                                <div class="ipt-box col-4">
                                                    <input type="text" class="ipt-txt" id="exeEnd" name="exeEnd"
                                                           onclick="laydate();"
                                                           value="<fmt:formatDate value="${info.exeEnd}" pattern="yyyy-MM-dd" />"
                                                           <c:if test="${info.isLife=='1'}">disabled="disabled"</c:if> />
                                                </div>
                                                <div class="radio-box">
                                                    <input type="checkbox" class="ml15" id="isLife" name="isLife"
                                                           value="1"
                                                           <c:if test="${info.isLife=='1'}">checked="checked"</c:if>>终身
                                                </div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="padding-left: 10px; padding-top: 10px">
                                                <input type="button" class="btn btn-xs" id="adddetail"
                                                       value="<c:if test="${info.punType == 'ent'}">添加惩戒企业</c:if><c:if test="${info.punType == 'man'}">添加惩戒当事人</c:if>">
                                            </td>

                                            <td colspan="3" class="pd0">
                                                <c:if test="${info.punType == 'ent'}">
                                                    <table class="inner-table center">
                                                        <tbody id="tb_ent_bd">
                                                        <tr>
                                                            <th>企业名称</th>
                                                            <th>统一信用代码</th>
                                                            <th>法定代表人(负责人)</th>
                                                            <th>证件类型</th>
                                                            <th>证件号</th>
                                                            <th>失信惩戒记录</th>
                                                            <th width="90" class="last-col">操作</th>
                                                        </tr>
                                                        <c:forEach items="${details}" var="obj">
                                                            <tr>
                                                                <td>${obj.entName}</td>
                                                                <td style="display:none">${obj.priPID}</td>
                                                                <td style="display:none">${obj.regNo}</td>
                                                                <td>${obj.uniCode}</td>
                                                                <td>${obj.leRep}</td>
                                                                <td style="display:none">${obj.cerType}</td>
                                                                <td>${obj.cerTypeDesc}</td>
                                                                <td>${obj.cardNo}</td>
                                                                <c:if test="${obj.record== '有'}">
                                                                    <td value="有"><a href='javascript:void(0)'
                                                                                     tag='view' priPID='${obj.priPID}'>有/查看</a>
                                                                    </td>
                                                                </c:if>
                                                                <c:if test="${obj.record== '无'}">
                                                                    <td value="无">无</td>
                                                                </c:if>
                                                                <td class="last-col"><a href='javascript:void(0)'
                                                                                        tag='del' name="${obj.entName}">删除</a>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </c:if>
                                                <c:if test="${info.punType == 'man'}">
                                                    <table class="inner-table center">
                                                        <tbody id="tb_man_bd">
                                                        <tr>
                                                            <th>当事人姓名</th>
                                                            <th>证件号</th>
                                                            <th>证件类型</th>
                                                            <th>当事人联系电话</th>
                                                            <th>失信惩戒记录</th>
                                                            <th width="90" class="last-col">操作</th>
                                                        </tr>
                                                        <c:forEach items="${details}" var="obj">
                                                            <tr>
                                                                <td>${obj.litiName}</td>
                                                                <td>${obj.cardNo}</td>
                                                                <td style="display:none">${obj.cerType}</td>
                                                                <td>${obj.cerTypeDesc}</td>
                                                                <td>${obj.phone}</td>
                                                                <c:if test="${obj.record== '有'}">
                                                                    <td value="有"><a href='javascript:void(0)'
                                                                                     tag='view' cardNo='${obj.cardNo}'>有/查看</a>
                                                                    </td>
                                                                </c:if>
                                                                <c:if test="${obj.record== '无'}">
                                                                    <td value="无">无</td>
                                                                </c:if>
                                                                <td class="last-col"><a href='javascript:void(0)'
                                                                                        tag='del'
                                                                                        name="${obj.litiName}">删除</a>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="tb-form-hd">证据资料上传
                                                <div class="light bold">单个文件不超过20M</div>
                                            </td>
                                            <td colspan="3" class="pd1">
                                                <div id="filelabel">
                                                    <c:forEach items="${files}" var="obj">
                                                        <p><span path="${obj}" style="color:blue;cursor:pointer"
                                                                 title="点击下载"><i
                                                                class="xbt-icon file-icon"></i>${fn:substringAfter(obj, '-')}</span>
                                                            &nbsp;&nbsp;<a href="javascript:void(0)"
                                                                           path="${obj}">删除</a></p>
                                                    </c:forEach>
                                                </div>
                                                <p class="pd10 file-upload" id="fileList">
                                                <span id="fileBox">
					                            	<input type="file" id="btnFile" class="btnFile" name="btnFile"/>
					                            </span>
                                                    <input type="hidden" id="files" name="files" value="${info.files}"/>
                                                    <input type="hidden" id="file" value=""/>
                                                    <iframe width="0" height="0" name="uploadframe"></iframe>
                                                    说明：支持图片、文档、照片等上传
                                                    <input type="button" id="btnUpload" value="点击上传"
                                                           class="btn btn-sm"/>
                                                    <span id="uploadState" style="color:red;display:none">上传中....</span>
                                                </p>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="tb-form-hd"><span class="light bold"> *</span>惩戒提请部门</td>
                                            <td>
                                                <div class="ipt-box">
                                                    <span id="punReqDept_s">${info.punReqDept}</span>
                                                    <input type="hidden" name="punReqDeptCode" id="punReqDeptCode" value="${info.punReqDeptCode}"/>
                                                    <input type="hidden" name="punReqDept" id="punReqDept" value="${info.punReqDept}"/>
                                                </div>
                                            </td>

                                            <td class="tb-form-hd"><span class="light bold"> *</span>是否需要执行部门反馈处理情况</td>
                                            <td>
                                                <div class="radio-box">
                                                    <label><input type="radio" name="feedBack" value="1"
                                                                  <c:if test="${info.feedBack == '1'}">checked="checked"</c:if>>是</label>
                                                    <label><input type="radio" name="feedBack" value="0"
                                                                  <c:if test="${info.feedBack == '0'}">checked="checked"</c:if>>否</label>
                                                </div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="tb-form-hd" rowspan="3"><span class="light bold"> *</span>提请部门联系人
                                            </td>
                                            <td rowspan="3" class="pd3">
                                                <div>
                                                    <textarea name="contact" style="resize: none" cols=" "
                                                              rows="7">${info.contact}</textarea>
                                                </div>
                                            </td>

                                            <td class="tb-form-hd"><span class="light bold"> *</span>联系电话</td>
                                            <td class="pd3">
                                                <div class="ipt-box">
                                                    <input type="text" name="phone" class="ipt-txt"
                                                           onblur="isPhone(this)" onpaste="isPhone(this)"
                                                           value="${info.phone}"/>
                                                </div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="tb-form-hd">传真</td>
                                            <td class="pd3">
                                                <div class="ipt-box">
                                                    <input type="text" name="fax" class="ipt-txt" value="${info.fax}"/>
                                                </div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="tb-form-hd">电子邮箱</td>
                                            <td class="pd3">
                                                <div class="ipt-box">
                                                    <input type="text" name="email" class="ipt-txt"
                                                           value="${info.email}" onblur="isEmail(this)"
                                                           onpaste="isEmail(this)"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <script type="text/javascript">
                                            function isEmail(e) {
                                                var val = e.value;
                                                if (val.replace(/(^\s*)|(\s*$)/g, "") == "") {
                                                    return false;
                                                }
                                                var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                                                if (filter.test(val)) {
                                                } else {
                                                    e.value = "";
                                                    alert("邮箱格式有误");
                                                }
                                            }
                                            function isPhone(e) {
                                                var val = e.value;
                                                var re = /^1\d{10}$/;	//手机
                                                var re1 = /^0\d{2,3}-?\d{7,8}$/;	//座机
                                                if (!re.test(val) && !re1.test(val)) {
                                                    e.value = "";
                                                    alert("电话号码有误");
                                                }
                                            }
                                        </script>
                                        </tbody>
                                    </table>

                                    <c:if test="${info.auditRes=='2'}">
                                        <table border="0" cellspacing="0" cellpadding="0" class="table-form mt10">
                                            <tbody>
                                            <tr>
                                                <td class="tb-form-hd" width="12%">审核结果</td>
                                                <td width="35%">
                                                    <div class="radio-box">
                                                        <label><input type="radio"
                                                                      <c:if test="${info.auditRes == '0'}">checked="checked"</c:if>
                                                                      disabled>同意发出</label>
                                                        <label><input type="radio"
                                                                      <c:if test="${info.auditRes == '1'}">checked="checked"</c:if>
                                                                      disabled>不同意发出</label>
                                                        <label><input type="radio"
                                                                      <c:if test="${info.auditRes == '2'}">checked="checked"</c:if>
                                                                      disabled>退回修改</label>
                                                    </div>
                                                </td>
                                                <td class="tb-form-hd" width="9%">审核人</td>
                                                <td>${info.auditor}</td>
                                                <td class="tb-form-hd" width="9%">审核日期</td>
                                                <td><fmt:formatDate value="${info.auditTime}"
                                                                    pattern="yyyy-MM-dd"/></td>
                                            </tr>

                                            <tr>
                                                <td class="tb-form-hd">审核意见</td>
                                                <td colspan="5" class="pd3">
                                                    <span>${info.auditView}</span>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </c:if>

                                </div>
                            </form>
                        </div>
                        <p class="center mt20">
                            <input type="button" class="btn mr20" value="打 印" id="print">
                            <c:if test="${info.state==0}">
	                            <input type="button" id="update" class="btn mr20" value="保存修改并提交审核">
	                            <input type="button" id="cancel" class="btn mr20" value="取消编辑">
                            </c:if>
                        </p>
                    </div>
                </div>
            </div>
        </div>


    </div>
</div>
<script>
	var userType = '${sessionScope.session_sys_user.userType}';
	var url='/common/system/sysdepart/alldeptcheckboxtreeselect';
	var urlHead = '/reg/server';
	if(userType==2){
		url='/syn/system/sysdepart/alldeptcheckboxtreeselect';
		urlHead='/syn';
	}
    window._CONFIG = {
   		select_dept_url: url,
   		urlHead: urlHead
    }
</script>
<script src="/js/lib/laydate/laydate.js"></script>
<script src="/js/lib/require.js"></script>
<script src="/js/config.js"></script>
<script src="/js/syn/system/nocreditPunish/info/edit_main.js"></script>
</body>
</html>