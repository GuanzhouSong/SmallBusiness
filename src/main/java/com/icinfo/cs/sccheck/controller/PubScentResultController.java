/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.sccheck.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icinfo.cs.common.constant.Constants;
import com.icinfo.cs.common.utils.DateUtil;
import com.icinfo.cs.common.utils.StringUtil;
import com.icinfo.cs.ext.dto.MidBaseInfoDto;
import com.icinfo.cs.ext.service.IMidBaseInfoService;
import com.icinfo.cs.login.model.PubEppassword;
import com.icinfo.cs.login.service.IPubEppasswordService;
import com.icinfo.cs.sccheck.dto.PubScentResultDto;
import com.icinfo.cs.sccheck.dto.SccheckCount;
import com.icinfo.cs.sccheck.model.PubScPlanTask;
import com.icinfo.cs.sccheck.model.PubSccheckItem;
import com.icinfo.cs.sccheck.model.PubSccheckItemResult;
import com.icinfo.cs.sccheck.model.PubSccheckType;
import com.icinfo.cs.sccheck.model.PubScentResult;
import com.icinfo.cs.sccheck.model.PubScentResultMaterial;
import com.icinfo.cs.sccheck.service.IPubScPlanTaskService;
import com.icinfo.cs.sccheck.service.IPubSccheckItemResultService;
import com.icinfo.cs.sccheck.service.IPubSccheckItemService;
import com.icinfo.cs.sccheck.service.IPubSccheckTypeService;
import com.icinfo.cs.sccheck.service.IPubScdeptTaskService;
import com.icinfo.cs.sccheck.service.IPubScentAgentService;
import com.icinfo.cs.sccheck.service.IPubScentResultMaterialService;
import com.icinfo.cs.sccheck.service.IPubScentResultService;
import com.icinfo.cs.system.controller.CSBaseController;
import com.icinfo.cs.system.dto.SysUserDto;
import com.icinfo.framework.common.ajax.AjaxResult;
import com.icinfo.framework.core.web.annotation.RepeatSubmit;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageRequest;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageResponse;

/**
 * 描述: cs_pub_scent_result 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2017年05月17日
 */
@Controller
@RequestMapping({ "/syn/sccheck/pubscentresult", "/reg/sccheck/pubscentresult" })
public class PubScentResultController extends CSBaseController {

	@Autowired
	IPubScentResultService pubScentResultService;

	@Autowired
	IPubEppasswordService pubEppasswordService;

	@Autowired
	IPubSccheckTypeService pubSccheckTypeService;

	@Autowired
	IPubSccheckItemResultService pubSccheckItemResultService;

	@Autowired
	IPubSccheckItemService pubSccheckItemService;

	@Autowired
	IPubScdeptTaskService pubScdeptTaskService;

	@Autowired
	IPubScentAgentService pubScentAgentService;

	@Autowired
	IMidBaseInfoService midBaseInfoService;

	@Autowired
	IPubScentResultMaterialService pubScentResultMaterialService;

	@Autowired
	IPubScPlanTaskService pubScPlanTaskService;

	/**
	 * 描述：综合抽查结果录入列表页面
	 * 
	 * @author baifangfang
	 * @date 2017年5月17日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView addScresultList() throws Exception {
		ModelAndView mav = new ModelAndView("syn/system/sccheck/scresult/scresultinsert_list");
		return mav;
	}

	/**
	 * 描述：综合抽查结果录入列表JSON数据列表
	 *
	 * @author baifangfang
	 * @date 2017年5月17日
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list.json")
	@ResponseBody
	public PageResponse<PubScentResultDto> listJSON(PageRequest request, HttpSession session) throws Exception {
		SysUserDto sysUserDto = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);

		List<PubScentResultDto> pubScentResultDtos = pubScentResultService.queryPageResult(request, sysUserDto);
		return new PageResponse<PubScentResultDto>(pubScentResultDtos);
	}

	/**
	 * 
	 * 描述: 抽查查询结果统计
	 * 
	 * @auther baifangfang
	 * @date 2017年5月17日
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "scresultCount", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult scresultCount(PageRequest request, HttpSession session) throws Exception {
		SysUserDto sysUserDto = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		List<Integer> countList = new ArrayList<Integer>();
		SccheckCount sccheckCount = pubScentResultService.querySccheckCount(request, sysUserDto);
		if (sccheckCount != null) {
			countList.add(sccheckCount.getNotInputNum());
			countList.add(sccheckCount.getToCheckNum());
			countList.add(sccheckCount.getCheckReturnNum());
			countList.add(sccheckCount.getAlreadyPubNum());
			countList.add(sccheckCount.getNotOverNum());
		} else {
			countList.add(0);
			countList.add(0);
			countList.add(0);
			countList.add(0);
			countList.add(0);
		}
		return AjaxResult.success("统计数成功", countList);
	}

	/**
	 * 描述：综合抽查结果录入页面
	 * 
	 * @author baifangfang
	 * @date 2017年5月18日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addScagentPage")
	public ModelAndView addScagentPage() throws Exception {
		ModelAndView mav = new ModelAndView("/syn/system/sccheck/scresult/scagentchoose_list");
		return mav;
	}

	/**
	 * 描述：综合抽查结果录入页面
	 * 
	 * @author baifangfang
	 * @date 2017年5月18日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addscresultpage")
	public ModelAndView addScresultPage(@RequestParam String taskUid, @RequestParam String priPID,
			@RequestParam String uid) throws Exception {
		ModelAndView mav = new ModelAndView("syn/system/sccheck/scresult/scresultinsert_edit");
		PubScentResultDto pubScentResultDto = pubScentResultService.queryPubScentResultDtoByUid(uid);
		String agentNames = pubScentAgentService.doSelectEntAgent(taskUid, priPID,pubScentResultDto.getScentUid());
		String setDeptName = pubScentAgentService.doSelectDeptName(taskUid, priPID,pubScentResultDto.getScentUid());
		pubScentResultDto.setCheckDeptPerson(agentNames);
		pubScentResultDto.setCheckDeptName(setDeptName);
		PubEppassword pubEppassword = pubEppasswordService.selectPubEppasswordByPriPid(priPID);
		List<MidBaseInfoDto> midBaseInfoDtos = midBaseInfoService.doGetListByPriPID(priPID);
		if (pubEppassword != null) {
			if (StringUtil.isEmpty(pubEppassword.getLerep())) {
				if (midBaseInfoDtos.size() > 0) {
					MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
					pubEppassword.setLerep(midBaseInfoDto.getLeRep());
					pubEppassword.setLerepphone(midBaseInfoDto.getTel());
				}
			}
		} else {
			pubEppassword = new PubEppassword();
			if (midBaseInfoDtos.size() > 0) {
				MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
				pubEppassword.setLerep(midBaseInfoDto.getLeRep());
				pubEppassword.setLerepphone(midBaseInfoDto.getTel());
			}
		}
		mav.addObject("pubScentResultDto", pubScentResultDto);
		mav.addObject("pubEppassword", pubEppassword);

		List<PubSccheckItem> pubSccheckItemList = new ArrayList<PubSccheckItem>();
		// 查询是否设置过检查书
		if ("1".equals(pubScentResultDto.getCheckTableFlag())) {
			// 查询检查事项结果表
			List<PubSccheckItemResult> pubSccheckItemResultList = pubSccheckItemResultService
					.queryPubSccheckItemResultByTaskUidAndPriPID(taskUid, priPID);
			if (pubSccheckItemResultList.size() > 0) {
				for (PubSccheckItemResult pubSccheckItemResult : pubSccheckItemResultList) {
					PubSccheckItem pubSccheckItem = new PubSccheckItem();
					pubSccheckItem.setCheckCode(pubSccheckItemResult.getCheckCode());
					pubSccheckItem.setCheckName(pubSccheckItemResult.getCheckName());
					pubSccheckItemList.add(pubSccheckItem);
				}
			}
		} else {
			pubSccheckItemList = pubSccheckItemService.selectEntRlate(taskUid, priPID);
		}
		List<PubSccheckItem> pubSccheckItemList1 = new ArrayList<PubSccheckItem>();
		List<PubSccheckItem> pubSccheckItemList2 = new ArrayList<PubSccheckItem>();
		// 查询36项检查列表
		List<PubSccheckType> pubSccheckTypeList = pubSccheckTypeService.queryPubSccheckTypeList();

		List<Integer> nums = new ArrayList<Integer>();
		// 查询出不在36项检查范围的检查事项
		List<String> list1 = new ArrayList<String>();
		for (PubSccheckType pubSccheckType : pubSccheckTypeList) {
			list1.add(pubSccheckType.getCode());
		}
		List<String> list2 = new ArrayList<String>();
		for (PubSccheckItem pubSccheckItem : pubSccheckItemList) {
			if (pubSccheckItem.getCheckCode().contains("OTHER")) {
				nums.add(Integer.parseInt(pubSccheckItem.getCheckCode().substring(5)));
				pubSccheckItemList2.add(pubSccheckItem);
			} else {
				pubSccheckItemList1.add(pubSccheckItem);
				list2.add(pubSccheckItem.getCheckCode());
			}
		}
		int maxNum = 0;
		if (nums.size() > 0) {
			maxNum = Collections.max(nums);// 其他情况最大的编号
		}
		mav.addObject("maxNum", maxNum);
		mav.addObject("pubSccheckItemList1", pubSccheckItemList1);
		mav.addObject("pubSccheckItemList2", pubSccheckItemList2);

		return mav;
	}

	/**
	 * 描述：录入抽查检查结果
	 * 
	 * @author baifangfang
	 * @date 2017年5月18日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addscresult", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult doCommit(@RequestBody Map<String, Object> map, HttpSession session) throws Exception {
		SysUserDto sysUserDto = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		int i = pubScentResultService.savePubScentResult(map, sysUserDto);
		if (i > 0) {
			return AjaxResult.success("保存成功");
		}
		return AjaxResult.error("保存失败");
	}

	/**
	 * 描述：综合抽查录入修改页面
	 * 
	 * @author baifangfang
	 * @date 2017年5月19日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("alterscresultpage")
	public ModelAndView alterScresultPage(@RequestParam String taskUid, @RequestParam String priPID,
			@RequestParam String uid) throws Exception {
		ModelAndView mav = new ModelAndView("syn/system/sccheck/scresult/scresultinsert_alter");
		PubScentResult pubScentResult = pubScentResultService.queryPubScentResultDtoByUid(uid);
		PubEppassword pubEppassword = pubEppasswordService.selectPubEppasswordByPriPid(priPID);
		List<MidBaseInfoDto> midBaseInfoDtos = midBaseInfoService.doGetListByPriPID(priPID);
		if (pubEppassword != null) {
			if (StringUtil.isEmpty(pubEppassword.getLerep())) {
				if (midBaseInfoDtos.size() > 0) {
					MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
					pubEppassword.setLerep(midBaseInfoDto.getLeRep());
					pubEppassword.setLerepphone(midBaseInfoDto.getTel());
				}
			}
		} else {
			pubEppassword = new PubEppassword();
			if (midBaseInfoDtos.size() > 0) {
				MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
				pubEppassword.setLerep(midBaseInfoDto.getLeRep());
				pubEppassword.setLerepphone(midBaseInfoDto.getTel());
			}
		}
		mav.addObject("pubScentResultDto", pubScentResult);
		mav.addObject("pubEppassword", pubEppassword);

		// 根据taskuid查询检查事项结果表
		List<PubSccheckItemResult> pubSccheckItemResultList = pubSccheckItemResultService
				.queryPubSccheckItemResultByTaskUidAndPriPID(taskUid, priPID);

		List<PubSccheckItemResult> pubSccheckItemResults1 = new ArrayList<PubSccheckItemResult>();
		List<PubSccheckItemResult> pubSccheckItemResults2 = new ArrayList<PubSccheckItemResult>();

		List<Integer> nums = new ArrayList<Integer>();
		for (PubSccheckItemResult pubSccheckItemResult : pubSccheckItemResultList) {
			String checkCode = pubSccheckItemResult.getCheckCode();
			if (checkCode.contains("OTHER")) {
				int num = Integer.parseInt(checkCode.substring(5));
				nums.add(num);
				pubSccheckItemResults2.add(pubSccheckItemResult);
			} else {
				pubSccheckItemResults1.add(pubSccheckItemResult);
			}
		}

		int maxNum = 0;
		if (nums.size() > 0) {
			maxNum = Collections.max(nums);// 其他情况最大的编号
		}
		mav.addObject("maxNum", maxNum);
		mav.addObject("pubSccheckItemResults1", pubSccheckItemResults1);
		mav.addObject("pubSccheckItemResults2", pubSccheckItemResults2);

		return mav;
	}

	/**
	 * 描述：抽查检查结果录入修改
	 * 
	 * @author baifangfang
	 * @date 2017年5月20日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "alterscresult", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult alterScresult(@RequestBody Map<String, Object> map) throws Exception {
		int i = pubScentResultService.alterPubScentResult(map);
		if (i > 0) {
			return AjaxResult.success("修改成功");
		}
		return AjaxResult.error("修改失败");
	}

	/**
	 * 描述：综合抽查录入详情页面
	 * 
	 * @author baifangfang
	 * @date 2017年5月19日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detailscresultpage")
	public ModelAndView detailScresultPage(@RequestParam String taskUid, @RequestParam String priPID,
			@RequestParam String uid) throws Exception {
		ModelAndView mav = new ModelAndView("syn/system/sccheck/scresult/scresultinsert_detail");
		PubScentResult pubScentResult = pubScentResultService.queryPubScentResultDtoByUid(uid);
		PubEppassword pubEppassword = pubEppasswordService.selectPubEppasswordByPriPid(priPID);
		List<MidBaseInfoDto> midBaseInfoDtos = midBaseInfoService.doGetListByPriPID(priPID);
		if (pubEppassword != null) {
			if (StringUtil.isEmpty(pubEppassword.getLerep())) {
				if (midBaseInfoDtos.size() > 0) {
					MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
					pubEppassword.setLerep(midBaseInfoDto.getLeRep());
					pubEppassword.setLerepphone(midBaseInfoDto.getTel());
				}
			}
		} else {
			pubEppassword = new PubEppassword();
			if (midBaseInfoDtos.size() > 0) {
				MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
				pubEppassword.setLerep(midBaseInfoDto.getLeRep());
				pubEppassword.setLerepphone(midBaseInfoDto.getTel());
			}
		}
		mav.addObject("pubScentResultDto", pubScentResult);
		mav.addObject("pubEppassword", pubEppassword);

		// 根据taskuid查询检查事项结果表
		List<PubSccheckItemResult> pubSccheckItemResultList = pubSccheckItemResultService
				.queryPubSccheckItemResultByTaskUidAndPriPID(taskUid, priPID);

		mav.addObject("pubSccheckItemResultList", pubSccheckItemResultList);

		return mav;
	}

	/**
	 * 描述：综合抽查结果审核列表页面
	 * 
	 * @author baifangfang
	 * @date 2017年5月19日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("auditScresultList")
	public ModelAndView auditScresultList() throws Exception {
		ModelAndView mav = new ModelAndView("syn/system/sccheck/scresult/scresultaudit_list");
		return mav;
	}

	/**
	 * 描述：综合抽查结果审核列表JSON数据列表
	 *
	 * @author baifangfang
	 * @date 2017年5月19日
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("auditScresultList.json")
	@ResponseBody
	public PageResponse<PubScentResultDto> auditScresultList(PageRequest request, HttpSession session)
			throws Exception {
		SysUserDto sysUserDto = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		List<PubScentResultDto> pubScentResultDtos = pubScentResultService.queryPageResult(request, sysUserDto);
		return new PageResponse<PubScentResultDto>(pubScentResultDtos);
	}

	/**
	 * 描述：综合抽查结果审核页面
	 * 
	 * @author baifangfang
	 * @date 2017年5月19日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("auditscresultpage")
	public ModelAndView auditScresultPage(@RequestParam String taskUid, @RequestParam String priPID,
			@RequestParam String uid) throws Exception {
		ModelAndView mav = new ModelAndView("syn/system/sccheck/scresult/scresultaudit_edit");
		PubScentResult pubScentResult = pubScentResultService.queryPubScentResultDtoByUid(uid);
		PubEppassword pubEppassword = pubEppasswordService.selectPubEppasswordByPriPid(priPID);
		List<MidBaseInfoDto> midBaseInfoDtos = midBaseInfoService.doGetListByPriPID(priPID);
		if (pubEppassword != null) {
			if (StringUtil.isEmpty(pubEppassword.getLerep())) {
				if (midBaseInfoDtos.size() > 0) {
					MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
					pubEppassword.setLerep(midBaseInfoDto.getLeRep());
					pubEppassword.setLerepphone(midBaseInfoDto.getTel());
				}
			}
		} else {
			pubEppassword = new PubEppassword();
			if (midBaseInfoDtos.size() > 0) {
				MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
				pubEppassword.setLerep(midBaseInfoDto.getLeRep());
				pubEppassword.setLerepphone(midBaseInfoDto.getTel());
			}
		}
		mav.addObject("pubScentResultDto", pubScentResult);
		mav.addObject("pubEppassword", pubEppassword);

		// 根据taskuid查询检查事项结果表
		List<PubSccheckItemResult> pubSccheckItemResultList = pubSccheckItemResultService
				.queryPubSccheckItemResultByTaskUidAndPriPID(taskUid, priPID);

		mav.addObject("pubSccheckItemResultList", pubSccheckItemResultList);
		return mav;
	}

	/**
	 * 描述：抽查检查结果审核
	 * 
	 * @author baifangfang
	 * @date 2017年5月19日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "auditscresult", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult auditScresult(@RequestBody Map<String, Object> map) throws Exception {
		int i = pubScentResultService.auditPubScentResult(map);
		if (i > 0) {
			return AjaxResult.success("保存成功");
		}
		return AjaxResult.error("保存失败");
	}

	/**
	 * 描述：综合抽查结果补录页面
	 * 
	 * @author baifangfang
	 * @date 2017年6月19日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("supplyscresultpage")
	public ModelAndView supplyScresultPage(@RequestParam String taskUid, @RequestParam String priPID,
			@RequestParam String uid) throws Exception {
		ModelAndView mav = new ModelAndView("syn/system/sccheck/scresult/scresultsupply_edit");
		PubScentResult pubScentResult = pubScentResultService.queryPubScentResultDtoByUid(uid);
		PubEppassword pubEppassword = pubEppasswordService.selectPubEppasswordByPriPid(priPID);
		List<MidBaseInfoDto> midBaseInfoDtos = midBaseInfoService.doGetListByPriPID(priPID);
		if (pubEppassword != null) {
			if (StringUtil.isEmpty(pubEppassword.getLerep())) {
				if (midBaseInfoDtos.size() > 0) {
					MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
					pubEppassword.setLerep(midBaseInfoDto.getLeRep());
					pubEppassword.setLerepphone(midBaseInfoDto.getTel());
				}
			}
		} else {
			pubEppassword = new PubEppassword();
			if (midBaseInfoDtos.size() > 0) {
				MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
				pubEppassword.setLerep(midBaseInfoDto.getLeRep());
				pubEppassword.setLerepphone(midBaseInfoDto.getTel());
			}
		}
		mav.addObject("pubScentResultDto", pubScentResult);
		mav.addObject("pubEppassword", pubEppassword);

		// 根据taskuid查询检查事项结果表
		List<PubSccheckItemResult> pubSccheckItemResultList = pubSccheckItemResultService
				.queryPubSccheckItemResultByTaskUidAndPriPID(taskUid, priPID);

		mav.addObject("pubSccheckItemResultList", pubSccheckItemResultList);
		return mav;
	}

	/**
	 * 描述：抽查检查结果补录
	 * 
	 * @author baifangfang
	 * @date 2017年6月19日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "supplyscresult", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult supplyScresult(@RequestBody Map<String, Object> map) throws Exception {
		int i = pubScentResultService.supplyPubScentResult(map);
		if (i > 0) {
			return AjaxResult.success("保存成功");
		}
		return AjaxResult.error("保存失败");
	}

	/**
	 * 描述：进入综合抽查实施准备页面
	 * 
	 * @author chenxin
	 * @date 2017年6月7日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkList")
	public ModelAndView setResultCheckItemList() throws Exception {
		ModelAndView view = new ModelAndView("syn/system/sccheck/scresult/scresultcheck_list");
		return view;
	}

	/**
	 * 描述：综合抽查实施准备列表JSON数据列表
	 *
	 * @author baifangfang
	 * @date 2017年6月12日
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("preParelist.json")
	@ResponseBody
	public PageResponse<PubScentResultDto> listPrePareJSON(PageRequest request, HttpSession session) throws Exception {
		SysUserDto sysUserDto = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);

		List<PubScentResultDto> pubScentResultDtos = pubScentResultService.queryPreParePageResult(request, sysUserDto);
		return new PageResponse<PubScentResultDto>(pubScentResultDtos);
	}

	/**
	 * 描述：进入综合抽查实施准备-检查表页面
	 * 
	 * @author baifangfang
	 * @date 2017年5月18日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkpage")
	public ModelAndView checkScresultPage(@RequestParam String taskUid, @RequestParam String priPID,
			@RequestParam String uid) throws Exception {
		ModelAndView mav = new ModelAndView("syn/system/sccheck/scresult/scresultcheck_edit");
		PubScentResultDto pubScentResultDto = pubScentResultService.queryPubScentResultDtoByUid(uid);

		List<PubSccheckItemResult> pubSccheckItemResultList = pubSccheckItemResultService
				.queryPubSccheckItemResultByTaskUidAndPriPID(taskUid, priPID);
		List<PubSccheckItem> pubSccheckItemList = new ArrayList<PubSccheckItem>();
		if (pubSccheckItemResultList.size() <= 0) {
			pubSccheckItemList = pubSccheckItemService.selectEntRlate(taskUid, priPID);
		} else {
			for (PubSccheckItemResult pubSccheckItemResult : pubSccheckItemResultList) {
				PubSccheckItem pubSccheckItem = new PubSccheckItem();
				pubSccheckItem.setCheckCode(pubSccheckItemResult.getCheckCode());
				pubSccheckItem.setCheckName(pubSccheckItemResult.getCheckName());
				pubSccheckItemList.add(pubSccheckItem);
			}
		}
		List<PubSccheckItem> pubSccheckItemList1 = new ArrayList<PubSccheckItem>();
		List<PubSccheckItem> pubSccheckItemList2 = new ArrayList<PubSccheckItem>();
		// 查询36项检查列表
		List<PubSccheckType> pubSccheckTypeList = pubSccheckTypeService.queryPubSccheckTypeList();

		List<Integer> nums = new ArrayList<Integer>();
		// 查询出不在36项检查范围的检查事项
		List<String> list1 = new ArrayList<String>();
		for (PubSccheckType pubSccheckType : pubSccheckTypeList) {
			list1.add(pubSccheckType.getCode());
		}
		List<String> list2 = new ArrayList<String>();
		for (PubSccheckItem pubSccheckItem : pubSccheckItemList) {
			if (pubSccheckItem.getCheckCode().contains("OTHER")) {
				nums.add(Integer.parseInt(pubSccheckItem.getCheckCode().substring(5)));
				pubSccheckItemList2.add(pubSccheckItem);
			} else {
				pubSccheckItemList1.add(pubSccheckItem);
				list2.add(pubSccheckItem.getCheckCode());
			}
		}
		int maxNum = 0;
		if (nums.size() > 0) {
			maxNum = Collections.max(nums);// 其他情况最大的编号
		}
		mav.addObject("maxNum", maxNum);

		String agentNames = pubScentAgentService.doSelectEntAgent(taskUid, priPID,pubScentResultDto.getScentUid());
		String setDeptName = pubScentAgentService.doSelectDeptName(taskUid, priPID,pubScentResultDto.getScentUid());
		pubScentResultDto.setCheckDeptPerson(agentNames);
		pubScentResultDto.setCheckDeptName(setDeptName);
		PubEppassword pubEppassword = pubEppasswordService.selectPubEppasswordByPriPid(priPID);
		List<MidBaseInfoDto> midBaseInfoDtos = midBaseInfoService.doGetListByPriPID(priPID);
		if (pubEppassword != null) {
			if (StringUtil.isEmpty(pubEppassword.getLerep())) {
				if (midBaseInfoDtos.size() > 0) {
					MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
					pubEppassword.setLerep(midBaseInfoDto.getLeRep());
					pubEppassword.setLerepphone(midBaseInfoDto.getTel());
				}
			}
		} else {
			pubEppassword = new PubEppassword();
			if (midBaseInfoDtos.size() > 0) {
				MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
				pubEppassword.setLerep(midBaseInfoDto.getLeRep());
				pubEppassword.setLerepphone(midBaseInfoDto.getTel());
			}
		}
		mav.addObject("pubScentResultDto", pubScentResultDto);
		mav.addObject("pubEppassword", pubEppassword);
		mav.addObject("pubSccheckItemList1", pubSccheckItemList1);
		mav.addObject("pubSccheckItemList2", pubSccheckItemList2);
		return mav;
	}

	/**
	 * 描述：检查表保存
	 * 
	 * @author baifangfang
	 * @date 2017年6月13日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "doCommitCheckTable", method = RequestMethod.POST)
	@ResponseBody
	@RepeatSubmit(timeout = 15000)
	public AjaxResult doCommitCheckTable(@RequestBody Map<String, Object> map, HttpSession session) throws Exception {
		SysUserDto sysUserDto = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		String name = sysUserDto.getRealName();
		int i = pubSccheckItemResultService.savePubSccheckItemResult(map, name);
		if (i > 0) {
			return AjaxResult.success("保存成功");
		}
		return AjaxResult.error("保存失败");
	}

	/**
	 * 描述：检查表打印页面
	 * 
	 * @author baifangfang
	 * @date 2017年5月18日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkprint")
	public ModelAndView checkPrint(@RequestParam String taskUid, @RequestParam String priPID, @RequestParam String uid)
			throws Exception {
		ModelAndView mav = new ModelAndView("syn/system/sccheck/scresult/scresultcheck_print");
		PubScentResultDto pubScentResultDto = pubScentResultService.queryPubScentResultDtoByUid(uid);

		List<PubSccheckItemResult> pubSccheckItemResultList = pubSccheckItemResultService
				.queryPubSccheckItemResultByTaskUidAndPriPID(taskUid, priPID);
		List<PubSccheckItem> pubSccheckItemList = new ArrayList<PubSccheckItem>();
		for (PubSccheckItemResult pubSccheckItemResult : pubSccheckItemResultList) {
			PubSccheckItem pubSccheckItem = new PubSccheckItem();
			pubSccheckItem.setCheckCode(pubSccheckItemResult.getCheckCode());
			pubSccheckItem.setCheckName(pubSccheckItemResult.getCheckName());
			pubSccheckItemList.add(pubSccheckItem);
		}

		String agentNames = pubScentAgentService.doSelectEntAgent(taskUid, priPID,pubScentResultDto.getScentUid());
		String setDeptName = pubScentAgentService.doSelectDeptName(taskUid, priPID,pubScentResultDto.getScentUid());
		pubScentResultDto.setCheckDeptPerson(agentNames);
		pubScentResultDto.setCheckDeptName(setDeptName);
		PubEppassword pubEppassword = pubEppasswordService.selectPubEppasswordByPriPid(priPID);
		List<MidBaseInfoDto> midBaseInfoDtos = midBaseInfoService.doGetListByPriPID(priPID);
		if (pubEppassword != null) {
			if (StringUtil.isEmpty(pubEppassword.getLerep())) {
				if (midBaseInfoDtos.size() > 0) {
					MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
					pubEppassword.setLerep(midBaseInfoDto.getLeRep());
					pubEppassword.setLerepphone(midBaseInfoDto.getTel());
				}
			}
		} else {
			pubEppassword = new PubEppassword();
			if (midBaseInfoDtos.size() > 0) {
				MidBaseInfoDto midBaseInfoDto = midBaseInfoDtos.get(0);
				pubEppassword.setLerep(midBaseInfoDto.getLeRep());
				pubEppassword.setLerepphone(midBaseInfoDto.getTel());
			}
		}
		mav.addObject("pubScentResultDto", pubScentResultDto);
		mav.addObject("pubEppassword", pubEppassword);
		mav.addObject("pubSccheckItemList", pubSccheckItemList);
		return mav;
	}

	/**
	 * 描述：进入综合抽查实施准备-通知书页面
	 * 
	 * @author baifangfang
	 * @date 2017年5月18日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("noticepage")
	public ModelAndView noticePage(@RequestParam String taskUid, @RequestParam String priPID, @RequestParam String uid,
			HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView("syn/system/sccheck/scresult/scresultnotice_edit");
		SysUserDto sysUserDto = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		PubScentResultDto pubScentResultDto = pubScentResultService.queryPubScentResultDtoByUid(uid);
		// 查询有没有保存的通知书
		PubScentResultMaterial pubScentResultMaterial = pubScentResultMaterialService
				.findPubScentResultMaterialByResultUid(pubScentResultDto.getUid());
		if (pubScentResultMaterial != null) {
			mav.addObject("pubScentResultMaterial", pubScentResultMaterial);
			Date checkDateFrom = pubScentResultMaterial.getCheckDateFrom();
			Date checkDateTo = pubScentResultMaterial.getCheckDateTo();
			Date fillDate = pubScentResultMaterial.getFillDate();
			Calendar c = Calendar.getInstance();
			if (checkDateFrom != null) {
				c.setTime(checkDateFrom);
				mav.addObject("checkDateFromYear", c.get(Calendar.YEAR));
				mav.addObject("checkDateFromMonth", c.get(Calendar.MONTH) + 1);
				mav.addObject("checkDateFromDay", c.get(Calendar.DAY_OF_MONTH));
			}
			if (checkDateTo != null) {
				c.setTime(checkDateTo);
				mav.addObject("checkDateToYear", c.get(Calendar.YEAR));
				mav.addObject("checkDateToMonth", c.get(Calendar.MONTH) + 1);
				mav.addObject("checkDateToDay", c.get(Calendar.DAY_OF_MONTH));
			}
			if (fillDate != null) {
				c.setTime(fillDate);
				mav.addObject("fillDateYear", c.get(Calendar.YEAR));
				mav.addObject("fillDateMonth", c.get(Calendar.MONTH) + 1);
				mav.addObject("fillDateDay", c.get(Calendar.DAY_OF_MONTH));
			}
			String material = pubScentResultMaterial.getMaterial();
			if (!StringUtil.isEmpty(material)) {
				String[] materials = material.split(",");
				List<String> materialList = Arrays.asList(materials);
				mav.addObject("materialList", materialList);
				mav.addObject("num", materialList.size() + 1);
			}
		}

		List<PubSccheckItemResult> pubSccheckItemResultList = pubSccheckItemResultService
				.queryPubSccheckItemResultByTaskUidAndPriPID(taskUid, priPID);
		List<PubSccheckItem> pubSccheckItemList = new ArrayList<PubSccheckItem>();
		if (pubSccheckItemResultList.size() <= 0) {
			pubSccheckItemList = pubSccheckItemService.selectEntRlate(taskUid, priPID);
		} else {
			for (PubSccheckItemResult pubSccheckItemResult : pubSccheckItemResultList) {
				PubSccheckItem pubSccheckItem = new PubSccheckItem();
				pubSccheckItem.setCheckCode(pubSccheckItemResult.getCheckCode());
				pubSccheckItem.setCheckName(pubSccheckItemResult.getCheckName());
				pubSccheckItemList.add(pubSccheckItem);
			}
		}

		mav.addObject("pubScentResultDto", pubScentResultDto);
		mav.addObject("pubSccheckItemList", pubSccheckItemList);
		mav.addObject("deptName", sysUserDto.getDepartMent().getDeptName());
		mav.addObject("realName", sysUserDto.getRealName());
		mav.addObject("phone", sysUserDto.getPhone());

		return mav;
	}

	/**
	 * 描述：通知书保存
	 * 
	 * @author baifangfang
	 * @date 2017年6月13日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "doCommitNotice", method = RequestMethod.POST)
	@ResponseBody
	@RepeatSubmit(timeout = 15000)
	public AjaxResult doCommitNotice(@RequestBody Map<String, Object> map, HttpSession session) throws Exception {
		SysUserDto sysUserDto = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		String name = sysUserDto.getRealName();
		int i = pubScentResultMaterialService.savePubScentResultMaterial(map, name);
		if (i > 0) {
			return AjaxResult.success("保存成功");
		}
		return AjaxResult.error("保存失败");
	}

	/**
	 * 描述：通知书打印页面
	 * 
	 * @author baifangfang
	 * @date 2017年6月14日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("noticeprint")
	public ModelAndView noticePrint(@RequestParam String resultUid, @RequestParam String priPID, HttpSession session)
			throws Exception {
		ModelAndView mav = new ModelAndView("syn/system/sccheck/scresult/scresultnotice_print");
		SysUserDto sysUserDto = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		PubScentResultDto pubScentResultDto = pubScentResultService.queryPubScentResultDtoByUid(resultUid);
		// 查询有没有保存的通知书
		PubScentResultMaterial pubScentResultMaterial = pubScentResultMaterialService
				.findPubScentResultMaterialByResultUid(pubScentResultDto.getUid());
		if (pubScentResultMaterial != null) {
			mav.addObject("pubScentResultMaterial", pubScentResultMaterial);
			Date checkDateFrom = pubScentResultMaterial.getCheckDateFrom();
			Date checkDateTo = pubScentResultMaterial.getCheckDateTo();
			Date fillDate = pubScentResultMaterial.getFillDate();
			Calendar c = Calendar.getInstance();
			if (checkDateFrom != null) {
				c.setTime(checkDateFrom);
				mav.addObject("checkDateFromYear", c.get(Calendar.YEAR));
				mav.addObject("checkDateFromMonth", c.get(Calendar.MONTH) + 1);
				mav.addObject("checkDateFromDay", c.get(Calendar.DAY_OF_MONTH));
			}
			if (checkDateTo != null) {
				c.setTime(checkDateTo);
				mav.addObject("checkDateToYear", c.get(Calendar.YEAR));
				mav.addObject("checkDateToMonth", c.get(Calendar.MONTH) + 1);
				mav.addObject("checkDateToDay", c.get(Calendar.DAY_OF_MONTH));
			}
			if (fillDate != null) {
				c.setTime(fillDate);
				mav.addObject("fillDateYear", c.get(Calendar.YEAR));
				mav.addObject("fillDateMonth", c.get(Calendar.MONTH) + 1);
				mav.addObject("fillDateDay", c.get(Calendar.DAY_OF_MONTH));
			}
			String material = pubScentResultMaterial.getMaterial();
			if (!StringUtil.isEmpty(material)) {
				String[] materials = material.split(",");
				List<String> materialList = Arrays.asList(materials);
				mav.addObject("materialList", materialList);
				mav.addObject("num", materialList.size() + 1);
			}
		}

		List<PubSccheckItemResult> pubSccheckItemResultList = pubSccheckItemResultService
				.queryPubSccheckItemResultByTaskUidAndPriPID(pubScentResultDto.getTaskUid(), priPID);
		List<PubSccheckItem> pubSccheckItemList = new ArrayList<PubSccheckItem>();
		if (pubSccheckItemResultList.size() <= 0) {
			pubSccheckItemList = pubSccheckItemService.selectEntRlate(pubScentResultDto.getTaskUid(), priPID);
		} else {
			for (PubSccheckItemResult pubSccheckItemResult : pubSccheckItemResultList) {
				PubSccheckItem pubSccheckItem = new PubSccheckItem();
				pubSccheckItem.setCheckCode(pubSccheckItemResult.getCheckCode());
				pubSccheckItem.setCheckName(pubSccheckItemResult.getCheckName());
				pubSccheckItemList.add(pubSccheckItem);
			}
		}

		mav.addObject("pubScentResultDto", pubScentResultDto);
		mav.addObject("pubSccheckItemList", pubSccheckItemList);
		mav.addObject("deptName", sysUserDto.getDepartMent().getDeptName());
		mav.addObject("realName", sysUserDto.getRealName());
		mav.addObject("phone", sysUserDto.getPhone());

		return mav;
	}

	/**
	 * 
	 * 描述: 执法人员统计页面
	 * 
	 * @auther gaojinling
	 * @date 2017年5月26日
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resultcount")
	public ModelAndView doCountView(HttpSession session) throws Exception {
		ModelAndView view = new ModelAndView("/syn/system/sccheck/scresult/scentresult_count");
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		// 警示协同的地址 默认警示
		String deptCode = "";
		if (("1".equals(sysUser.getUserType()) && sysUser.getDepartMent() != null)
				|| ("2".equals(sysUser.getUserType()) && sysUser.getSysDepart() != null)) {
			if ("1".equals(sysUser.getUserType())) {
				deptCode = sysUser.getDepartMent().getDeptCode();
			} else {
				deptCode = sysUser.getSysDepart().getAdcode();
			}
			if (deptCode.length() > 4) {
				deptCode = deptCode.substring(0, 4);
			}
		}
		List<PubScPlanTask> taskNameList = pubScPlanTaskService.selectTaskNames(null);
		view.addObject("startCheckPushDate", DateUtil.getYear() + "-01-01"); // 开始日期
		view.addObject("endCheckPushDate", DateUtil.getNdaylater2(DateUtil.getSysDate(), -1)); // 结束日期
		view.addObject("deptCode", deptCode);
		view.addObject("taskNameList", taskNameList);
		view.addObject("sysUser", sysUser);
		view.addObject("uservest", sysUser.getUserVest(sysUser)); // 层级
		view.addObject("year", DateUtil.getYear()); // 当前年份
		view.addObject("month", DateUtil.getMonth()); // 当前月份
		return view;
	}

	/**
	 * 
	 * 描述: 综合抽查结果统计
	 * 
	 * @auther chenxin
	 * @date 2017年6月26日
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/countlist.json", "list.xml" })
	@ResponseBody
	public PageResponse<PubScentResultDto> countJSON(PageRequest request, HttpSession session) throws Exception {
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		if (!"1".equals(sysUser.getIsAdmin()) && !"2".equals(sysUser.getIsAdmin())) {
			String setdeptCode = "2".equals(sysUser.getUserType()) ? sysUser.getSysDepart().getAdcode()
					: sysUser.getDepartMent().getDeptCode();
			String deptCodeLike = replaceByUserLevel(setdeptCode, sysUser.getSearchRangeLevel(), "%");
			request.getParams().put("deptCodeLike", deptCodeLike);
		}
		List<PubScentResultDto> data = pubScentResultService.selectScentResultCount(request);
		return new PageResponse<PubScentResultDto>(data);
	}

	/**
	 * 描述：综合抽查结果录入列表页面
	 * 
	 * @author baifangfang
	 * @date 2017年5月17日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchlist")
	public ModelAndView searchScresultList() throws Exception {
		ModelAndView mav = new ModelAndView("syn/system/sccheck/scresult/scresultsearch_list");
		return mav;
	}

	/**
	 * 描述：综合抽查结果查询列表JSON数据列表
	 *
	 * @author baifangfang
	 * @date 2017年5月17日
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchlist.json")
	@ResponseBody
	public PageResponse<PubScentResultDto> searchlistJSON(PageRequest request) throws Exception {
		creatDefaultDBAuthEnv(request, "c.localAdm", "c.localAdm");
		Map<String, Object> parmMap = request.getParams();
		if (parmMap == null) {
			parmMap = new HashMap<String, Object>();
		}
		StringUtil.paramTrim(parmMap);
		List<PubScentResultDto> pubScentResultDtos = pubScentResultService.queryPageSearchResult(request);
		return new PageResponse<PubScentResultDto>(pubScentResultDtos);
	}

	/**
	 * 
	 * 描述: 抽查查询结果统计
	 * 
	 * @auther baifangfang
	 * @date 2017年5月17日
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "scresultsearchCount", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult searchSccheckCount(PageRequest request) throws Exception {
		creatDefaultDBAuthEnv(request, "c.localAdm", "c.localAdm");
		Map<String, Object> parmMap = request.getParams();
		if (parmMap == null) {
			parmMap = new HashMap<String, Object>();
		}
		StringUtil.paramTrim(parmMap);
		SccheckCount sccheckCount = pubScentResultService.querySccheckSearchCount(request);
		List<Integer> countList = new ArrayList<Integer>();
		if (sccheckCount != null) {
			countList.add(sccheckCount.getNotInputNum());
			countList.add(sccheckCount.getToCheckNum());
			countList.add(sccheckCount.getCheckReturnNum());
			countList.add(sccheckCount.getAlreadyPubNum());
			countList.add(sccheckCount.getNotOverNum());
		} else {
			countList.add(0);
			countList.add(0);
			countList.add(0);
			countList.add(0);
			countList.add(0);
		}
		return AjaxResult.success("统计数成功", countList);
	}
}