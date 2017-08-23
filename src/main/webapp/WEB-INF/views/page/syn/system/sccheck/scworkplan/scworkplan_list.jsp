<%--
   Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved.
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=8" >
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8"/>
    <title>年度抽查工作计划</title>
    <link rel="stylesheet" href="/css/vendor/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="/css/reg.server.css">
    <link rel="stylesheet" href="/js/lib/jquerymultiselect/jquery.multiselect.css">
</head>
<body class="pd10">
<div class="tabbale" data-component="tab">
    <form id="taskForm" class="form-box pd14-mr"> 
    	 <input type="hidden" id="searchFlag" name="searchFlag" value="0" >
         <div class="form-list">
            <div class="form-item clearfix">
                <div id="uniCode" class="col-4">
                    <label class="item-name col-5"><span class="long-label">抽查活动名称：</span></label>
                    <div class="col-7">
                        <div class="ipt-box col-12">
                             <input type="text" class="ipt-txt" name="planName" value="" placeholder="请输入抽查活动名称">
                        </div>
                    </div>
                </div>
                <div class="col-4" id="entName">
                    <label class="item-name col-5">活动任务编号：</label>
                    <div class="col-7">
                        <div class="ipt-box col-12">
                           <input type="text" class="ipt-txt" name="planCode" value="" placeholder=" 请输入活动任务编号">
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <label class="item-name col-5">计划制定部门：</label>
                    <div class="col-7">
                        <div class="ipt-box col-11">
                            <input type="text" class="ipt-txt" name="setDeptName" value="" placeholder="请输入计划制定部门"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-item clearfix">
                <div class="col-4">
                    <label class="item-name col-5">工作计划年度：</label>
                    <div class="col-7">
                        <div class="ipt-box col-12">
                            <select name="planYear">
                                <option value="">全部</option>
                                <c:forEach var="nowYear" items="${yearList}" varStatus="status" >
			        		    <option value="${nowYear}"   ${thisYear==nowYear?'selected':''}>${nowYear}</option>
		        		        </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <label class="item-name col-5">计划活动期限：</label>
                    <div class="col-7">
                        <div class="ipt-box col-12">
                         		<div class="ipt-box col-575">
	                            <input type="text" class="ipt-txt laydate-icon" readonly="readonly" name="planStartTime"
	                                   id="startInfaustlDate" value=""/>
	
		                        </div>
		                        <span class="item-line col-05">-</span>
		                        <div class="ipt-box col-575">
		                            <input type="text" class="ipt-txt laydate-icon" readonly="readonly" name="planEndTime"
		                                   id="endInfaustlDate" value=""/>
		
		                        </div>
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <label class="item-name col-5">活动责任单位：</label>
                    <div class="col-7">
                        <div class="ipt-box col-11">
                            <input type="text" class="ipt-txt" name="planLeadDeptName" value="" placeholder="请输入活动责任单位"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-item clearfix">
                <div class="col-4">
                    <label class="item-name col-5">抽查方式：</label>
                    <div class="col-7">
                        <div class="ipt-box col-12">
                           <select name="planType">
                                <option value="">全部</option>
                                <option value="1">定向</option>
                                <option value="2">不定向</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <label class="item-name col-5">计划执行状态：</label>
                    <div class="col-7">
                        <div class="ipt-box col-12">
                            <select name="planState">
                                <option value="">全部</option>
                                <option value="01">待执行</option>
                                <option value="02">执行中</option>
                                <option value="03">已完成</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <label class="item-name col-5">设置人：</label>
                    <div class="col-7">
                        <div class="ipt-box col-11">
                           <input type="text" class="ipt-txt" name="setUserName" value="" placeholder="请输入设置人"/>
                        </div>
                    </div>
                </div>
            </div>
              
            <div class="center mt10">
            <input type="button" id="search" value="查询" class="btn mr20">
            <input type="button" id="cancel" onclick="$('#taskForm')[0].reset();" value="重置" class="btn mr20">
       		</div>
        </div>
    </form>
    <div class="clearfix mb5 mt5">
        <p class="fl"><input type="button" id="add" class="btn btn-sm mr5" value="添加抽查工作计划"></p>
    </div>
    <div class="light-info mt5">
        <span class="light">提示：对计划的抽查活动进行具体的抽查任务设置后即有对应的活动任务编号。
    </div> 
    <div class="tab-content mb20">
        <div class="tab-panel tab-panel-show">
            <div class="">
                <div class="table-out">
                    <table id="pubopanomalylist_table"  border="0" cellspacing="0" cellpadding="0" class="table-row mt30 nowrap" width="100%">
                        <thead>
                        <tr>
                            <th style="padding:0 20px;">序号</th>
	                        <th>操作</th>
	                        <th>年度</th>
	                        <th>活动任务编号</th>
	                        <th>抽查活动名称</th>
	                        <th>抽查方式</th>
	                        <th>活动开始年月</th>
	                        <th>活动结束年月</th>
	                        <th>计划执行状态</th>
	                        <th>责任单位（牵头处室）</th>
	                        <th>抽查范围及数量比例（地区）</th> 
	                        <th>计划制定部门</th> 
	                        <th>设置人</th> 
	                        <th>设置日期</th> 
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <script src="<c:url value="/js/lib/require.js"/>"></script>
    <script src="<c:url value="/js/config.js"/>"></script>
    <script>
    window._CONFIG = { 
       _sysUrl:'${sysUrl}'
    }
	</script>
    <script src="/js/syn/system/sccheck/scworkplan/scworkplan_list_main.js"></script>
</body>
</html>