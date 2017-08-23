package com.icinfo.cs.sment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icinfo.cs.common.constant.Constants;
import com.icinfo.cs.common.constant.DBAuthorConstants;
import com.icinfo.cs.common.logintoken.CSLoginToken;
import com.icinfo.cs.common.utils.StringUtil;
import com.icinfo.cs.sment._enum.LogTypeEnum;
import com.icinfo.cs.sment.service.ISmLogService;
import com.icinfo.cs.system.controller.CSBaseController;
import com.icinfo.cs.system.dto.LoginDto;
import com.icinfo.cs.system.dto.SysUserDto;
import com.icinfo.cs.system.model.SysUser;
import com.icinfo.cs.system.service.IRegIndexService;
import com.icinfo.cs.system.service.ISysUserService;
import com.icinfo.cs.yr.model.DepartMent;
import com.icinfo.cs.yr.service.IDepartMentService;
import com.icinfo.framework.captcha.web.DefaultCaptchaServlet;
import com.icinfo.framework.common.ajax.AjaxResult;
import com.icinfo.framework.security.shiro.UserProfile;

/**
 * 描述:小微检测-用户登录控制
 *
 * @author zhuyong
 * @date 2017-04-26
 */
@Controller
@RequestMapping("/sment/login")
public class SmentLoginController extends CSBaseController {

	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private IDepartMentService departMentService;
	@Autowired
	private ISmLogService smLogService;
	@Autowired
	private IRegIndexService regIndexService;
	/**
	 * 进入登录页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/doEnlogin",method=RequestMethod.GET)
	public ModelAndView doEnlogin() throws Exception {
		return new ModelAndView("/sment/sment_login");
	}

	/**
	 * 用户登录验证
	 *
	 * @param session
	 * @param loginDto
	 * @return
	 */
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult doLogin(HttpSession session, HttpServletResponse response, @Valid LoginDto loginDto, BindingResult result) throws Exception {
		/** 参数验证错误 */
		if (result.hasErrors()) {
			AjaxResult error = AjaxResult.error("参数验证错误");
			error.addErrorInfo(result.getAllErrors());
			return error;
		}
		/** 验证码校验 */
		String checkCode = session.getAttribute(DefaultCaptchaServlet.getSessionKey()) + "";
		if (!loginDto.getCheckCode().equalsIgnoreCase(checkCode)) {
			return AjaxResult.error("验证码输入错误");
		}
		session.removeAttribute(DefaultCaptchaServlet.getSessionKey());
		
		/** 登录信息检查 */
		SysUser tmpSysUser = sysUserService.selectByLoginName(loginDto.getUsername(), DBAuthorConstants.USER_TYPE_SMENT);
		SysUserDto sysUser = null;
		if (tmpSysUser == null) {
			return AjaxResult.error("登录失败,系统中没有该用户信息,请检查用户名是否正确!");
		}
		if ("0".equals(tmpSysUser.getStatus())) {
			return AjaxResult.error("登录失败,该用户已失效!");
		}
		
		/** 用户登录 */
		try {
			CSLoginToken loginToken = new CSLoginToken(loginDto.getUsername(), loginDto.getPassword());
			loginToken.setUserType(DBAuthorConstants.USER_TYPE_SMENT);
			Subject subject = SecurityUtils.getSubject();
			subject.login(loginToken);
			session.setAttribute(Constants.SESSION_SYS_USER_KEY, subject.getPrincipal());
			UserProfile userProfile = (UserProfile) subject.getPrincipal();
			sysUser = sysUserService.selectUserByUId(userProfile.getUserId());
		} catch (Exception e) {
			return AjaxResult.error("登录失败,用户名或密码错误!");
		}
		
		try {
            sysUserService.modLastLoginTime(sysUser.getId());
            if(sysUser!=null&&StringUtil.isNotBlank((sysUser.getDeptCode()))){
                DepartMent departMent=(sysUser.getDepartMent()==null)?departMentService.selectOne(sysUser.getDeptCode()):departMentService.selectOne(sysUser.getDepartMent().getDeptCode());
                if(departMent==null){
                	return AjaxResult.error("errorDeptCode","获取不到对应的部门信息!");
                }	
                sysUser.setDepartMent(departMent);
                sysUser.setDeptCode(departMent.getDeptCode());
                session.setAttribute(Constants.SESSION_SYS_USER, sysUser);
            }else {
            	return AjaxResult.error("查询用户数据为空!");
            }
        }catch (Exception e){
        	return AjaxResult.error("获取用户相关信息失败：");
        }
		
		smLogService.save(LogTypeEnum.LOGIN.toString(),"");
		return AjaxResult.success("登录成功!");
	}
	
	
	/**
	 * 管理端首页
	 *
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "sment_index")
	public ModelAndView index(HttpSession session) throws Exception {
		ModelAndView view = new ModelAndView("/sment/sment_index");
		UserProfile userProfile = (UserProfile) session.getAttribute(Constants.SESSION_SYS_USER_KEY);
		if (userProfile != null) {
			// 用户显示菜单
			view.addObject("menus", userProfile.getMenus());
			view.addObject("userProfile", userProfile);
		}
		return view;
	}
	
	/**
	 * 管理端首页
	 *
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "welcome")
	public ModelAndView welcome() throws Exception {
		return new ModelAndView("/sment/sment_welcome");
	}
	

    /**
     * 进入登录页面
     *
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return "redirect:/sment";
    }
}
