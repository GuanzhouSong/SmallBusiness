<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- 视图适配  -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <!-- 360浏览器默认视图 -->
    <meta name="renderer" content="webkit">
    <title>账号验证</title>
    <link rel="stylesheet" href="/css/syn.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/reg.client.css"/>"/>
    <style type="text/css">
         .hq-yzm {
            font: normal 14px/25px 'Microsoft YaHei';
            color: #fff;
            background-color: #ffab30;
            border-radius: 3px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="xt-top">
    <div class="mod2">
        <a href="###" class="logo"></a>
    </div>
</div>
<div class="bg-blue2 pd100">
    <div class="mod2">
        <h1><img src="/img/syn/account-title.jpg" alt=""></h1>

        <div class="account-valid-box">
            <h2>请完善您所在的部门及个人信息：</h2>

            <form action="" id="user_ck_form">
                    <div class="form-account-box">
                    <div class="form-item clearfix">
                        <label class="item-name">部门（全称）：</label>
                        <div class="ipt-box">
                            <input type="text"   readonly value=" ${sysDepart.organizeAllName}" class="ipt-txt clx" style="border: 0;background: none" >
                        </div>
                    </div>
                    <div class="form-item clearfix">
                        <label class="item-name">部门（简称）：</label>
                        <div class="ipt-box">
                            <input type="text"  readonly value=" ${sysDepart.orgName}" class="ipt-txt clx" style="border: 0;background: none" >
                        </div>
                    </div>

                    <div class="form-item clearfix">
                        <label class="item-name">请确认部门（全称）：</label>

                        <div class="ipt-box">
                            <input type="text" name="deptFullName" id="deptFullName" value="" class="ipt-txt clx"   placeholder="请与公章上的单位名称一致">
                        </div>
                    </div>
                    <div class="form-item clearfix">
                        <label class="item-name">请确认部门（简称）：</label>

                        <div class="ipt-box">
                            <input type="hidden" name="userUsername" value="${sysUser.username}"/>
                            <input type="hidden" name="userType" value="${sysUser.userType}"/>
                            <input type="hidden" name="orgcode" value="${sysDepart.orgCoding}"/>
                            <input type="hidden" name="adcode" value="${sysDepart.adcode}"/>
                            <input type="text" class="ipt-txt clx" id="deptShortName" name="deptShortName" value=""   placeholder="请填写规范的部门简称，如浙江省工商行政管理局简称省工商局">
                        </div>

                    </div>
                    <div class="form-item clearfix">
                        <label class="item-name">部门地址：</label>

                        <div class="ipt-box">
                            <input type="text" class="ipt-txt clx" name="deptAddress" placeholder="请填写部门地址">
                        </div>
                    </div>
                    <div class="form-item clearfix">
                        <label class="item-name">本部门对应的审批职能部门：</label>
                        <div class="ipt-box fl">
                            <input type="text" readonly id="dutyCodeNames" name="dutyCodeNames" class="ipt-txt" value=""  placeholder="若选项中无对应的职能部门,请勾选下方的无对应的审批职能部门" />
                            <input type="hidden" name="dutyCodes" id="dutyCodes" />
                            <span class="add-icon" id="selectDutyCodes"><i></i></span>
                            <div>
                                <label>
                                    <input type="checkbox" id="dutyNo"/>无对应的审批职能部门
                                </label>
                            </div>
                        </div>

                    </div>
                    <div class="form-item clearfix">
                        <label class="item-name">本部门涉及的后置审批事项：</label>

                        <div class="ipt-box fl">
                            <input type="text" readonly id="licItemNames" name="licItemNames" class="ipt-txt" value="" placeholder="若选项中无对应的后置审批事项,请勾选下方的无对应的后置审批事项"  />
                            <input type="hidden" name="licItems" id="licItems" />
                            <span class="add-icon" id="selectLicItems"><i></i></span>
                            <div>
                                <label>
                                    <input type="checkbox" id="licItemNo"/>无对应的后置审批事项
                                </label>
                            </div>
                        </div>

                    </div>
                    <div class="form-item clearfix">
                        <label class="item-name fl">姓名：</label>

                        <div class="ipt-box fl">
                            <input type="text" class="ipt-txt ipt-txt2 clx" name="userRealName" value="">
                        </div>
                        <label class="item-name item-name2 fl">性别：</label>

                        <div class="radio-box fl">
                            <label for="nan"><input id="nan" checked name="userSex" type="radio" value="1">男</label>
                            <label for="nv"><input id="nv" name="userSex" type="radio" value="0">女</label>
                        </div>
                    </div>
                    <div class="form-item clearfix">
                        <label class="item-name fl">部门科室：</label>

                        <div class="ipt-box fl">
                            <input type="text" class="ipt-txt ipt-txt2 clx" name="deptName" placeholder="如监管处">
                        </div>
                        <label class="item-name item-name2 fl">职务：</label>

                        <div class="ipt-box fl">
                            <input type="text" class="ipt-txt ipt-txt2 clx" name="post">
                        </div>
                    </div>
                    <div class="form-item clearfix">
                        <label class="item-name fl">办公座机：</label>

                        <div class="ipt-box fl">
                            <input type="text" class="ipt-txt ipt-txt2 clx" name="phone" placeholder="区号 - 固话号码">
                        </div>
                        <label class="item-name item-name2 fl">电子邮箱：</label>

                        <div class="ipt-box fl">
                            <input type="text" class="ipt-txt ipt-txt2 clx" name="userEmail">
                        </div>
                    </div>
                    <div class="form-item clearfix">
                        <label class="item-name">设置登录密码：</label>

                        <div class="ipt-box">
                            <input type="password" class="ipt-txt ipt-txt2 clx" name="userPassword" id="sysUserPswNew"/>
                        </div>
                    </div>
                    <div class="form-item clearfix">
                        <label class="item-name">再次输入登录密码：</label>
                        <div class="ipt-box">
                            <input type="password" id="sysUserPswNewAgain" class="ipt-txt ipt-txt2 clx" >
                        </div>
                    </div>
                    <div class="form-item clearfix">
                        <label class="item-name">手机号码：</label>

                        <div class="ipt-box">
                            <input type="text" class="ipt-txt ipt-txt2 clx" name="telPhone" placeholder="即为登录的用户名" id="telPhone">
                            <span class="hq-yzm" id="genCheckCode">获取验证码</span>
                            <%--<span class="yzm-tips">180s内验证码有效</span>--%>
                        </div>
                    </div>
                    <div class="form-item clearfix">
                        <label class="item-name">手机验证码：</label>
                             <%--<li class="yzm focus-state">--%>
                                 <%--<label>手机验证码：</label>--%>
                                 <%--<div class="ipt-box js-ipt-box">--%>
                                     <%--<input type="text" id="password" name="password" value="" class="ipt-txt yzm">--%>
                                    <%--<i class="icon-close"></i>--%>
                                     <%--<em class="log-tip js-log-tip pwd">验证码出错，请核对后重新输入</em>--%>
                                 <%--</div>--%>
                                 <%--<span class="js-yzm">获取验证码</span>--%>
                             <%--</li>--%>
                        <div class="ipt-box">
                            <input type="text" class="ipt-txt ipt-txt2 clx" name="checkCode" placeholder="请输入收到的手机验证码">
                        </div>
                    </div>
                    <p class="center mt20 opera-btn2">
                        <input type="button" id="save" class="btn mr20" value="提 交">
                        <input type="button" class="btn" value="取 消" id="cancel">
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="tk-box tk-box3" id="ck_success" style="display: none">
    <%--<i class="close-icon"></i>--%>

    <div class="tk-cont clearfix">
        <div class="fl">
            <img src="/img/syn/account-success.png" alt="">
        </div>
        <div class="tk-suc-info fl">
            <h1>请确认以下信息！</h1>
            <h2>
                部门名称:<label id="unitName"></label><br/>对应的后置审批事项为：<label id="licItem"></label>.<br/>
            </h2>
            <h2>您今后可以选择使用帐号<label style="color: red">${sysUser.username}</label>登录。请记住刚才设置的密码哦！<br/>
                点击确认来保存上述信息并完成用户校验，是否确认？
            </h2>


        </div>
    </div>
        <p class="center">
            <input type="button" id="toLogin"  class="btn" value="确 认">
            <input type="button" id="exit" class="btn" value="取 消">
        </p>
</div>
<div class="log-footer log-footer2" >
    <div class="mod2 center">
        <div class="other-tip">
            <a href="">收藏本站</a>|
            <a href="<c:url value='/static_page/about_sys_syn.jsp'/>" target="_blank">关于信息交换共享平台</a>|
            <a href="<c:url value='/static_page/xszn.jsp'/>" target="_blank">新手指南</a>|
            <a href="http://www.icinfo.cn/html/service.action" target="_blank">联系我们</a>

            <select onchange="if(this.value!='')window.open(this.value);this.options[0].selected=true">
                <option value="相关链接">相关链接</option>
                <option value="http://www.zjzwfw.gov.cn/">浙江政务服务网</option>
                <option value="http://www.gsxt.gov.cn/">国家企业信用信息公示系统</option>
                <option value="http://www.zjcredit.gov.cn/">信用浙江网</option>
                <option value="http://www.zj.gov.cn/">浙江省人民政府</option>
                <!--                 <option value="http://online.icinfo.cn/eppnet/">企业数字证书（电子执照）服务</option> -->
            </select>
        </div>
        <p>
            主办单位：浙江省人民政府办公厅&nbsp;&nbsp;&nbsp;&nbsp;承办单位：浙江省工商行政管理局<br/>
            技术支持：浙江汇信科技有限公司&nbsp;&nbsp;&nbsp;&nbsp;<span class="light-black">技术服务热线：400-888-4636</span>&nbsp;&nbsp;&nbsp;&nbsp;建议使用1366*768分辨率/IE9.0或以上浏览器访问达到最佳效果
    </div>
</div>
<script>
    window._CONFIG = {
        isLogin: '${isLogin}',
        isChecked:'${isChecked}'
    }
</script>
<script src="/js/lib/require.js"></script>
<script src="/js/config.js"></script>
<script src="/js/business/sysuser/sysuser_ck.js"></script>
</body>
</html>
