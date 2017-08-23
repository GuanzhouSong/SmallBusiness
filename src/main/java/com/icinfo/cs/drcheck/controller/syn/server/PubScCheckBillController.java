/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.drcheck.controller.syn.server;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icinfo.cs.common.constant.Constants;
import com.icinfo.cs.common.utils.DateUtil;
import com.icinfo.cs.drcheck.dto.PubScCheckBillDto;
import com.icinfo.cs.drcheck.model.PubScCheckBill;
import com.icinfo.cs.drcheck.service.IPubScCheckBillService;
import com.icinfo.cs.system.dto.SysUserDto;
import com.icinfo.cs.system.service.IDeptDutycodeRefService;
import com.icinfo.framework.common.ajax.AjaxResult;
import com.icinfo.framework.core.web.BaseController;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageRequest;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageResponse;
import com.icinfo.framework.tools.utils.StringUtils;

/**
 * 描述:    cs_pub_sccheck_bill 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2017年05月02日
 */
@Controller
@RequestMapping("/syn/server/drcheck/pubsccheckbill")
public class PubScCheckBillController extends BaseController {
	@Autowired
	private IPubScCheckBillService pubScCheckBillService;
	
	@Autowired
	private IDeptDutycodeRefService deptDutycodeRefService;

	/**
	 * 描述: to 事项清单维护页面
	 * @author chenxin
	 * @date 2017年05月02日
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpSession session) throws Exception {
		ModelAndView view = new ModelAndView("/syn/system/drcheck/sccheckbill/sccheckbill_list");
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		String deptCode = deptDutycodeRefService.selectDeptCodeByAdCodeSubDutyDeptCode(sysUser.getUserType(),sysUser.getDeptCode(), "A058");
	    view.addObject("sysUser", sysUser);
	    view.addObject("sessionDeptCode", deptCode);
		return view;
	}

	/**
	 * 描述: 执法人员分页查询
	 * @author chenxin
	 * @date 2017年05月02日
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listJSON")
	@ResponseBody
	public PageResponse<PubScCheckBillDto> listJSON(PageRequest request) throws Exception {
		List<PubScCheckBillDto> data = pubScCheckBillService.selectPubScCheckBillDtoList(request);
		return new PageResponse<PubScCheckBillDto>(data);
	}
	
	
	/**
	 * 描述: to 新增或修改页面
	 * @author chenxin
	 * @date 2017年05月02日
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/show")
	public ModelAndView show(String uid, HttpSession session) throws Exception {
		ModelAndView view = new ModelAndView("/syn/system/drcheck/sccheckbill/sccheckbill_edit");
		PubScCheckBill pubScCheckBill = pubScCheckBillService.selectPubScCheckBillByUid(uid);
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		view.addObject("userType", sysUser.getUserType());
		view.addObject("deptName", sysUser.getDept());
		view.addObject("sysdate", DateUtil.getCurrentDate());
		view.addObject("pubScCheckBill",pubScCheckBill);
		return view;
	}
	

	/**
	 * 
	 * 描述: 保存更新操作
	 * @author chenxin
	 * @date 2017年05月02日
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/save",method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult save(PubScCheckBill pubScCheckBill, HttpSession session) throws Exception {
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		pubScCheckBill.setSetUserUid(sysUser.getId());
		pubScCheckBill.setSetUserName(sysUser.getRealName());
		pubScCheckBill.setSetTime(new Date());
		pubScCheckBill.setSetDeptcode(sysUser.getDeptCode());
		pubScCheckBill.setSetDeptName(sysUser.getDept());
		if (StringUtils.isNotEmpty(pubScCheckBill.getUID())) {
			try{
				if (pubScCheckBillService.updatePubScCheckBill(pubScCheckBill) > 0) {
					return AjaxResult.success("更新成功");
				} else {
					return AjaxResult.error("更新失败");
				}
			}catch(Exception e){
				e.printStackTrace();
				return AjaxResult.error("更新失败");
			}

		} else {
			if (pubScCheckBillService.savePubScCheckBill(pubScCheckBill) > 0) {
				return AjaxResult.success("新增成功");
			} else {
				return AjaxResult.error("新增失败");
			}
		}
	}
	
}