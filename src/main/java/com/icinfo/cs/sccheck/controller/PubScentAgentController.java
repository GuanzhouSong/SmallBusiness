/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.sccheck.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icinfo.cs.common.constant.Constants;
import com.icinfo.cs.common.utils.StringUtil;
import com.icinfo.cs.sccheck.dto.PubScentAgentDto;
import com.icinfo.cs.sccheck.dto.PubScentDto;
import com.icinfo.cs.sccheck.service.IPubScdeptTaskService;
import com.icinfo.cs.sccheck.service.IPubScentAgentService;
import com.icinfo.cs.sccheck.service.IPubScentService;
import com.icinfo.cs.system.dto.SysUserDto;
import com.icinfo.framework.common.ajax.AjaxResult;
import com.icinfo.framework.core.web.BaseController;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageRequest;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageResponse;

/**
 * 描述:    cs_pub_scent_agent 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2017年05月19日
 */
@Controller
@RequestMapping({"/reg/pub/server/sccheck/entagent","/syn/pub/server/sccheck/entagent"})
public class PubScentAgentController extends BaseController {
	
	@Autowired
	private IPubScentAgentService pubScentAgentService; 
	@Autowired
	private IPubScentService pubScentService; 
	@Autowired
	private IPubScdeptTaskService pubScdeptTaskService; 

	/**
	 * 描述：随机抽取
	 * @author chenxin
	 * @date 2017-05-17
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/random",method= RequestMethod.POST)
	@ResponseBody
	public AjaxResult doRandomEntAgent(HttpSession session,String deptTaskUid,String randomType,int groupNum) throws Exception {
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		if("1".equals(randomType)){
			if(pubScentAgentService.doRandomByGroup(sysUser,deptTaskUid,groupNum)){
				return AjaxResult.success("随机匹配成功");
			}
		}else if("2".equals(randomType)){
			if(pubScentAgentService.doRandomEntAgent(sysUser,deptTaskUid,groupNum,randomType)){
				return AjaxResult.success("随机匹配成功");
			}
		}
		return AjaxResult.error("随机匹配失败");
	}
	
	
	/**
	 * 描述：锁定企业和执法人员匹配结果
	 * @author chenxin
	 * @date 2017-05-17
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/lockagent",method= RequestMethod.POST)
	@ResponseBody
	public AjaxResult doLockEntAgent(HttpSession session,String deptTaskUid,String randomType,int groupNum) throws Exception {
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		if("1".equals(randomType)){
			pubScdeptTaskService.updatePubScdeptTask(deptTaskUid, "2", sysUser,0,randomType);
		}else if("2".equals(randomType)){
			pubScdeptTaskService.updatePubScdeptTask(deptTaskUid, "2", sysUser,groupNum,randomType);
		}
		return AjaxResult.success("锁定成功");
	}
	
	/**
	 * 描述:查看企业和执法人员配对结果
	 * @auther chenxin
	 * @date 2017年06月13日
	 * @return view
	 * @throws Exception
	 */
	@RequestMapping("/view")
	public ModelAndView viewList(HttpSession session,String deptTaskUid) throws Exception {
		ModelAndView view = new ModelAndView("/syn/system/sccheck/scentagent/scentagent_view");
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		view.addObject("sysUser", sysUser);
		view.addObject("deptTaskUid", deptTaskUid);
		return view;
	}
	

	/**
	 * 描述: 查询企业和执法人员配对列表
	 * @auther ljx
	 * @date 2016年10月31日
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listJSON")
	@ResponseBody
	public PageResponse<PubScentDto> listJSON(PageRequest request) throws Exception {
		List<PubScentDto> pubScentDtoList = pubScentService.selectPubScentDtoList(request);
		if(pubScentDtoList != null && pubScentDtoList.size() > 0){
			for(PubScentDto pubScentDto : pubScentDtoList){
				String deptTaskUid = pubScentDto.getDeptTaskUid();
				String priPID = pubScentDto.getPriPID();
				String agentNames = pubScentAgentService.doSelectEntAgent(deptTaskUid, priPID,pubScentDto.getUid());
				pubScentDto.setAgentNames(agentNames);
			}
		}
		return new PageResponse<PubScentDto>(pubScentDtoList);
	}
	
	/**
	 * 描述:进入执法人员调整页面
	 * @auther chenxin
	 * @date 2017年06月13日
	 * @return view
	 * @throws Exception
	 */
	@RequestMapping("/adjust")
	public ModelAndView viewAdjustList(HttpSession session) throws Exception {
		ModelAndView view = new ModelAndView("/syn/system/sccheck/scentagent/scentagent_adjust");
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		view.addObject("sysUser", sysUser);
		return view;
	}
	
	/**
	 * 描述: 查询企业和执法人员配对列表
	 * @auther chenxin
	 * @date 2017年06月13日
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/adjustListJSON")
	@ResponseBody
	public PageResponse<PubScentAgentDto> listPubScentAgentDtoJSON(HttpSession session,PageRequest request) throws Exception {
		SysUserDto sysUserDto = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		Map<String, Object> params = request.getParams();
		String deptCode = "2".equals(sysUserDto.getUserType()) ? sysUserDto.getSysDepart().getAdcode()
				: sysUserDto.getDepartMent().getDeptCode();
		if(deptCode.length() > 8){
			deptCode = deptCode.substring(0, 8);
		}else if (deptCode.length() == 6){
			deptCode = deptCode+"00";
		}
		params.put("unitDeptCode", deptCode);
		if(params != null){
			StringUtil.paramTrim(params);
		}
		List<PubScentAgentDto> pubScentDtoList = pubScentAgentService.selectPubScentAgentDtoList(request);
		return new PageResponse<PubScentAgentDto>(pubScentDtoList);
	}
	
	/**
	 * 描述:进入 当前部门下的所有执法人员 页面
	 * 
	 * @author chenxin
	 * @date 2016-11-06
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/adjustPage")
	public ModelAndView doAdjustAgentPage() throws Exception {
		ModelAndView view = new ModelAndView("/syn/system/sccheck/scentagent/scagentadjust_list");
		return view;
	}
	
	
	/**
	 * 描述:进入 当前部门下的所有执法人员 页面
	 * 
	 * @author chenxin
	 * @date 2016-11-06
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/randomaagent")
	public ModelAndView doRandomAgentPage(String groupUid) throws Exception {
		ModelAndView view = new ModelAndView("/syn/system/sccheck/scentagent/scagentrandom_list");
		view.addObject("groupUid", groupUid);
		return view;
	}
	
	/**
	 * 描述：指定执法人员
	 * @author chenxin
	 * @date 2017-05-17
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sign",method= RequestMethod.POST)
	@ResponseBody
	public AjaxResult doSignEntAgent(HttpSession session,String scentAgentUids,String agentUid) throws Exception {
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		if(pubScentAgentService.doSignEntAgent(sysUser,scentAgentUids,agentUid)){
			return AjaxResult.success("调整成功");
		}
		return AjaxResult.error("调整失败");
	}
}