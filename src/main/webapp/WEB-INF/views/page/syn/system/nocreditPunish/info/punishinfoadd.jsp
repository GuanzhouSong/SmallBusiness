<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">

    <title>创建联合惩戒单</title>
    <link rel="stylesheet" href="/css/syn.css">
</head>
<body>
<div class="pd20">
    <div class="creat-copy">
        <div class="tabbale" data-component="tab">
            <div class="tab-header type-tab clear pdb10 border-bottom">
                <label class="right">惩戒单类型：</label>
                <ul class="clearfix">
                	<c:if test="${punType == 'ent'}">
                		<li id="ent_type" class="tab-selected"><span class="tab-item"><a href="javascript:void(0);">企业</a></span></li>
                	</c:if>
                	<c:if test="${punType == 'man'}">
                		<li id="man_type" class="tab-selected"><span class="tab-item"><a href="javascript:void(0);">自然人</a></span></li>
                	</c:if>
                    <!--  <li <c:if test="${punType == 'ent'}">class="tab-selected"</c:if> id="ent_type"><span class="tab-item"><a href="javascript:void(0);">企业</a></span></li>-->
                </ul>
            </div>
            <div class="tab-content">
                <div class="tab-panel tab-panel-show">
                    <div class="">
                        <div class="print-nocard">
                            <h3 class="mt33">
	                            <c:if test="${punType == 'ent'}">
	                            	企业失信惩戒创建单
	                            </c:if>
	                            <c:if test="${punType == 'man'}">
	                            	自然人失信惩戒创建单
	                            </c:if>
                            </h3>
                            <p class="right print-data">录入日期：<span>${date}</span></p>

								<form id="content_form">
									<input type="hidden" id="punType" name="punType"  value="${punType}"/>
									<input type="hidden" id="batchNo" name="batchNo"  value="${batchNo}"/>
									<input type="hidden" id="detailJson" name="detailJson"  value=""/>
									
									<table border="0" cellspacing="0" cellpadding="0" class="table-form mt30">
										<tbody>
											<tr>
												<td class="tb-form-hd" width="13%"><span class="light bold">*</span>惩戒领域</td>
												<td width="36%">
													<div class="ipt-box">
														<select name="punField" id="punField">
															<option value="" >请选择..</option>
															<option value="安全生产领域" >安全生产领域</option>
															<option value="旅行社经营领域" >旅行社经营领域</option>
															<option value="国有企业监督管理领域" >国有企业监督管理领域</option>
															<option value="互联网上网服务及娱乐场所经营领域" >互联网上网服务及娱乐场所经营领域</option>
															<option value="食品药品经营领域" >食品药品经营领域</option>
															<option value="饲料及兽药经营领域" >饲料及兽药经营领域</option>
															<option value="出版经营领域" >出版经营领域</option>
															<option value="电影经营领域" >电影经营领域</option>
															<option value="营业性演出经营领域" >营业性演出经营领域</option>
															<option value="建筑施工领域（含施工、勘察、设计和工程监理）" >建筑施工领域（含施工、勘察、设计和工程监理）</option>
															<option value="电子认证领域" >电子认证领域</option>
															<option value="证券期货市场监督管理领域" >证券期货市场监督管理领域</option>
															<option value="税收管理" >税收管理</option>
															<option value="普遍性限制措施" >普遍性限制措施</option>
														</select>
													</div>
												</td>

												<td class="tb-form-hd" width="13%"><span class="light bold">*</span>惩戒事由</td>
												<td>
													<div class="ipt-box">
														<select name="punCause" id="punCause" style="width:150px">
															<option value="" >请选择..</option>
														</select>
													</div>
												</td>
											</tr>

											<tr>
												<td class="tb-form-hd">惩戒依据（条文）</td>
												<td><span id="legBasis_s"></span>
													<input type="hidden" id="legBasis" name="legBasis" value=""/>
												</td>

												<td class="tb-form-hd">惩戒规则</td>
												<td><span id="punRule_s"></span>
													<input type="hidden" id="punRule" name="punRule" value=""/>
												</td>
											</tr>

											<tr>
												<td class="tb-form-hd">惩戒执行措施</td>
												<td colspan="3"><span id="punMea_s"></span>
													<input type="hidden" id="punMea" name="punMea" value=""/>
												</td>
											</tr>

											<tr>
												<td class="tb-form-hd" width="140px">法律文书编号</td>
												<td>
													<div class="ipt-box">
														<input type="text" class="ipt-txt" name="legNo" maxlength="50" value=""/>
													</div>
												</td>

												<td class="tb-form-hd" width="100px">文书时间</td>
												<td>
													<div class="ipt-box col-5">
														<input type="text" name="legDate" class="ipt-txt" onclick="laydate();" value=""/>
													</div>
												</td>
											</tr>

											<tr>
												<td class="tb-form-hd"><span class="light bold">* 请选择确认 </span>惩戒执行部门</td>
												<td>
													<div class="ipt-box clear">
														<div class="col-10">
															<span id="punExeDept_s" class="multi-line"></span>
															<input type="hidden" name="punExeDeptCode" id="punExeDeptCode" value="" />
															<input type="hidden" name="punExeDept" id="punExeDept" value="" />
														</div>
														<input type="button" id="selectPunExeDept" class="btn btn-xs chose" value="选择">
													</div>
												</td>

												<td class="tb-form-hd">执行有效期</td>
												<td class="pd0"><span class="item-txt">从</span>
													<div class="ipt-box col-4">
														<input type="text" class="ipt-txt" id="exeBegin" name="exeBegin" onclick="laydate();" value=""/>
													</div> <span class="item-txt">至</span>
													<div class="ipt-box col-4">
														<input type="text" class="ipt-txt" id="exeEnd" name="exeEnd" onclick="laydate();" value=""/>
													</div>
													<div class="radio-box col-2">
														<input type="checkbox" class="ml15" id="isLife" name="isLife" value="1">终身
													</div>
												</td>
											</tr>

											<tr>
												<td>
													<div class="center">
														<input type="button" class="btn btn-xs" id="adddetail" value="<c:if test="${punType == 'ent'}">添加惩戒企业</c:if><c:if test="${punType == 'man'}">添加惩戒当事人</c:if>">
													</div>
												</td>
												
												<td colspan="3" class="pd0">
													<c:if test="${punType == 'ent'}">
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
														</tbody>
													</table>
													</c:if>
													<c:if test="${punType == 'man'}">
													<table id="tb_man" class="inner-table center">
														<tbody id="tb_man_bd">
															<tr>
																<th>当事人姓名</th>
																<th>证件号</th>
																<th>证件类型</th>
																<th>当事人联系电话</th>
																<th>失信惩戒记录</th>
																<th width="90" class="last-col">操作</th>
															</tr>
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
													<div id="filelabel"></div>
													<p class="pd10 file-upload" id="fileList">
														<!-- <form target="uploadframe" id="needHide" action="/项目名/project.do?method=fileUpLoad" method="post" enctype="multipart/form-data"> --> 
														<span id="fileBox">
							                            	<input type="file" id="btnFile" class="btnFile" name="btnFile" />
							                            </span>
							                            <input type="hidden" id="files" name="files" value=""/>
							                            <input type="hidden" id="file" value=""/>
								                        <iframe width="0" height="0" name="uploadframe"></iframe> 
														说明：支持图片、文档、照片等上传
														<input type="button" id="btnUpload" value="点击上传" />
														<span id="uploadState" style="color:red;display:none">上传中....</span>
													</p>
												</td>
											</tr>
											
											<tr>
												<td class="tb-form-hd"><span class="light bold">*</span>惩戒提请部门</td>
												<td>
													<div class="ipt-box">
														<span id="punReqDept_s">${user.dept}</span>
														<input type="hidden" name="punReqDeptCode" id="punReqDeptCode" value="<c:if test="${user.sysDepart==null}">${user.deptCode}</c:if><c:if test="${user.sysDepart!=null}">${user.sysDepart.adcode}</c:if>" />
														<input type="hidden" name="punReqDept" id="punReqDept" value="${user.dept}" />
													</div>
												</td>
												
												<td class="tb-form-hd"><span class="light bold">*</span>是否需要执行部门反馈处理情况</td>
												<td>
													<div class="radio-box">
														<label><input type="radio" name="feedBack" value="1">是</label>
														<label><input type="radio" name="feedBack" value="0">否</label>
													</div>
												</td>
											</tr>
											
											<tr>
												<td class="tb-form-hd" rowspan="3"><span class="light bold">*</span>提请部门联系人</td>
												<td rowspan="3" class="pd3">
													<div>
														<textarea name="contact" style="resize: none" cols=" " rows="7"></textarea>
													</div>
												</td>
												
												<td class="tb-form-hd"><span class="light bold">*</span>联系电话</td>
												<td class="pd3">
													<div class="ipt-box">
														<input type="text" name="phone" class="ipt-txt" value="" onblur="isPhone(this)" onpaste="isPhone(this)" />
													</div>
												</td>
											</tr>
											
											<tr>
												<td class="tb-form-hd">传真</td>
												<td class="pd3">
													<div class="ipt-box">
														<input type="text" name="fax" class="ipt-txt" value=""/>
													</div>
												</td>
											</tr>
											
											<tr>
												<td class="tb-form-hd">电子邮箱</td>
												<td class="pd3">
													<div class="ipt-box">
														<input type="text" name="email" class="ipt-txt" value="" onblur="isEmail(this)" onpaste="isEmail(this)" />
													</div>
												</td>
											</tr>
											<script type="text/javascript"> 
												function isEmail(e) {
													var val=e.value;
													if (val.replace(/(^\s*)|(\s*$)/g, "") == "") {
														return false;
													}
												    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
												    if (filter.test(val)) {
												    } else {
												    	e.value="";
												        alert("邮箱格式有误");
												    }
												}
												function isPhone(e) {
													 var val=e.value;
													 var re = /^1\d{10}$/;	//手机
													 var re1 = /^0\d{2,3}-?\d{7,8}$/;	//座机
													 if(!re.test(val) && !re1.test(val)){
														 e.value="";
													     alert("电话号码有误");
													 }
												}
											</script>
										</tbody>
									</table>
								</form>
								
							</div>
                        <p class="center mt20"><input type="button" id="save" class="btn mr20" value="保存并提交审核"><input type="button" id="cancel" class="btn mr20" value="取消编辑"></p>
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
<script src="/js/syn/system/nocreditPunish/info/add_main.js"></script>
</body>
</html>