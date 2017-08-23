/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved.
 */
package com.icinfo.cs.sment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icinfo.cs.common.constant.Constants;
import com.icinfo.cs.common.utils.AESEUtil;
import com.icinfo.cs.common.utils.DateUtil;
import com.icinfo.cs.ext.dto.MidBaseInfoDto;
import com.icinfo.cs.ext.service.IMidBaseInfoService;
import com.icinfo.cs.opanomaly.dto.PubOpaDetailDto;
import com.icinfo.cs.opanomaly.dto.PubPbopanomalyDto;
import com.icinfo.cs.opanomaly.service.IPubOpaDetailService;
import com.icinfo.cs.opanomaly.service.IPubPbopanomalyService;
import com.icinfo.cs.rpt.rptdto.RptSmEntInfoAllDto;
import com.icinfo.cs.secnocreditsup.model.ExpSeriousCrimeList;
import com.icinfo.cs.secnocreditsup.service.IExpSeriousCrimeListService;
import com.icinfo.cs.simpleesc.service.IErEscAppinfoService;
import com.icinfo.cs.sment.dto.SmBaseinfoDto;
import com.icinfo.cs.sment.service.ISmBaseinfoService;
import com.icinfo.cs.system.controller.CSBaseController;
import com.icinfo.cs.system.dto.SysUserDto;
import com.icinfo.cs.yr.model.BaseInfo;
import com.icinfo.cs.yr.model.YrRegCheck;
import com.icinfo.cs.yr.service.IBaseInfoService;
import com.icinfo.cs.yr.service.IYrRegCheckService;
import com.icinfo.framework.common.ajax.AjaxResult;
import com.icinfo.framework.mybatis.pagehelper.Page;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageRequest;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageResponse;

/**
 * 描述: cs_sm_baseinfo 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2017年05月02日
 */
@Controller
@RequestMapping("/sment/smBaseinfo")
public class SmBaseinfoController extends CSBaseController {

	@Autowired
	private ISmBaseinfoService smBaseinfoService;

	@Autowired
	IMidBaseInfoService midBaseInfoService;

	@Autowired
	IPubPbopanomalyService pubPbopanomalyService;

	@Autowired
	IErEscAppinfoService erEscAppinfoService;

	@Autowired
	IPubOpaDetailService pubOpaDetailService;

	@Autowired
	IExpSeriousCrimeListService expSeriousCrimeListService;
	
	@Autowired
	IYrRegCheckService yrRegCheckService;
	
	@Autowired
	IBaseInfoService baseInfoService;
	
	/**
	 * 描述: 培育库分页列表
	 * 
	 * @author 张文男
	 * @date 2017年5月3日
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/queryPageListForEntarchives.json", method = RequestMethod.POST)
	@ResponseBody
	public PageResponse<SmBaseinfoDto> queryPageListForEntarchives(PageRequest request) throws Exception {
	 creatDefaultDBAuthEnv(request, "sb.RegOrg", "sb.LocalAdm");
	 PageResponse<SmBaseinfoDto> list = smBaseinfoService.queryPageListForEntarchives(request);
		return list;
	}

	/**
	 * 描述: 获取企业、培育库分页列表
	 * 
	 * @author 张文男
	 * @date 2017年5月4日
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/queryEntAndEntarchivesPageList.json", method = RequestMethod.POST)
	@ResponseBody
	public PageResponse<SmBaseinfoDto> queryEntAndEntarchivesPageList(PageRequest request) throws Exception {
	 creatDefaultDBAuthEnv(request, "sb.RegOrg", "sb.LocalAdm");
		List<SmBaseinfoDto> list = smBaseinfoService.queryEntAndEntarchivesPageList(request);
		return new PageResponse<SmBaseinfoDto>(list);
	}

	/**
	 * 描述：小微企业信息查询页面
	 *
	 * @author baifangfang
	 * @date 2017年5月2日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list() throws Exception {
		ModelAndView mav = new ModelAndView("sment/smentsearch/smentsearch_view");
		return mav;
	}

	/**
	 * 描述：小微企业信息查询数据列表
	 * 
	 * @author baifangfang
	 * @date 2017年5月2日
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list.json")
	@ResponseBody
	public PageResponse<SmBaseinfoDto> smEntQueryPage(PageRequest request) throws Exception {
		creatDefaultDBAuthEnv(request, "A.RegOrg", "A.LocalAdm");
		Page<SmBaseinfoDto> data = smBaseinfoService.doGetSmBaseinfoList(request);
		return new PageResponse<SmBaseinfoDto>(data);
	}

	/**
	 * 描述：小微企业查询详情页面
	 * 
	 * @author baifangfang
	 * @date 2017年5月4日
	 * @param priPID
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doSmEntSearchDetail/{priPID}")
	public ModelAndView doEnAppSearchDetails(@PathVariable(value = "priPID") String priPID, HttpSession session)
			throws Exception {
		priPID = AESEUtil.decodeCorpid(priPID);
		ModelAndView mav = new ModelAndView("sment/smentsearch/smentsearch_detail");
		Map<String, Object> qryMap = new HashMap<String, Object>();
		qryMap.put("priPID", priPID);
		MidBaseInfoDto midBaseInfoDto = midBaseInfoService.selectMidBaseInfoByPripid(priPID);
		mav.addObject("dto", midBaseInfoDto);
		mav.addObject("encryPriPID", AESEUtil.encodeCorpid(priPID));
		mav.addObject("isOpanomaly", checkIsOpanomaly(midBaseInfoDto.getEntTypeCatg(), priPID));
		mav.addObject("isEscApp", erEscAppinfoService.getErEscAppinfoByPriPID(priPID));
		return mav;
	}

	/**
	 * 描述：检查企业是否是异常名录和严重违法
	 * 
	 * @author Administrator
	 * @date 2017年5月4日
	 * @param entTypeCatg
	 * @param priPID
	 * @return
	 * @throws Exception
	 */
	private String checkIsOpanomaly(String entTypeCatg, String priPID) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("priPID", priPID);
		String opanomaly = "0";
		String seriousCrime = "0";
		if ("50".equals(entTypeCatg)) {
			List<PubPbopanomalyDto> dataList = pubPbopanomalyService.pubPbopanomalySearchRecoverList(map);
			if (dataList != null && dataList.size() > 0) {
				opanomaly = "1";
			}
		} else {
			List<PubOpaDetailDto> dataList = pubOpaDetailService.selectAddMoveOutSearch(map);
			if (dataList != null && dataList.size() > 0) {
				opanomaly = "1";
			}
		}
		List<ExpSeriousCrimeList> expSeriousCrimeList = expSeriousCrimeListService
				.selectSeriousCrimeInfoByPriPID(priPID);
		if (expSeriousCrimeList != null && expSeriousCrimeList.size() > 0) {
			seriousCrime = "1";
		}
		// 同时被列入异常和严重违法
		if ("1".equals(opanomaly) && "1".equals(seriousCrime)) {
			return "1";
		} else if ("1".equals(opanomaly) && !"1".equals(seriousCrime)) {// 只列入异常
			return "2";
		} else if (!"1".equals(opanomaly) && "1".equals(seriousCrime)) {// 只列入严重违法
			return "3";
		} else {
			return "0";
		}
	}

	/**
	 * 描述：小微企业年报信息查看详情列表页
	 * 
	 * @author baifangfang
	 * @date 2017年5月11日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("yrinfolist.json")
	@ResponseBody
	public PageResponse<SmBaseinfoDto> yrinfoJSON(PageRequest request) throws Exception {
		List<SmBaseinfoDto> smBaseinfoDtos = smBaseinfoService.queryPageResult(request);
		return new PageResponse<SmBaseinfoDto>(smBaseinfoDtos);
	}

	/**
	 * 描述：年报信息详情页
	 * 
	 * @author baifangfang
	 * @date 2017年5月11日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("smyr")
	public ModelAndView smyr(@RequestParam String encryPriPID, @RequestParam String year, @RequestParam String currency)
			throws Exception {
		String priPID = AESEUtil.decodeCorpid(encryPriPID);
		// 查询年报主表
		YrRegCheck yrRegCheck = yrRegCheckService.selectCheckInfoByPripidAndYear(priPID, Integer.parseInt(year));
		if (yrRegCheck == null) {
			yrRegCheck = new YrRegCheck();
		}
		ModelAndView mav = new ModelAndView("sment/smentsearch/smentsearchyr_detail");
		mav.addObject("yrRegCheck", yrRegCheck);

		BaseInfo baseInfo = baseInfoService.selectInfoByPripidAndYear(priPID, Integer.parseInt(year));
		mav.addObject("baseInfo", baseInfo);
		// 年份及priPID加密用于打印
		mav.addObject("encodeYear", AESEUtil.encodeYear(year));
		mav.addObject("encodePriPID", AESEUtil.encodeCorpid(priPID));
		mav.addObject("currency", currency);// 币种类型

		return mav;
	}
	
	
	 /**
     * 
     * 描述: 登记信息统计（普通统计）
     * @auther gaojinling
     * @date 2017年5月4日 
     * @param session
     * @return
     * @throws Exception
     */
	@RequestMapping("/rptYrSmNormalCount")
	public ModelAndView rptYrSmNormalCount(HttpSession session,String normalFlag) throws Exception{
		ModelAndView view;
		if("1".equals(normalFlag)){
			view  = new ModelAndView("/sment/rpt/rptyrsmnormal_list"); 
		}else{
			view  = new ModelAndView("/sment/rpt/rptyrsmimport_list"); 
		}
	    SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		//警示协同的地址 默认警示
		String deptCode="";
		if(sysUser.getDepartMent()!=null){
			deptCode=sysUser.getDepartMent().getDeptCode();
			if(deptCode.length()>4){
				deptCode=deptCode.substring(0, 4);
			} 
		}		
		view.addObject("startCheckPushDate", DateUtil.stringToDate(DateUtil.getYear()+"-01-01", "YYYY-MM-dd"));  //开始日期
		view.addObject("endCheckPushDate",DateUtil.getNdaylaterDate(DateUtil.getSysDate(), -1));    //结束日期
		view.addObject("deptCode", deptCode);
		view.addObject("sysUser", sysUser);
		view.addObject("uservest", sysUser.getUserVest(sysUser));       //层级
		view.addObject("year", DateUtil.getYear());       //当前年份
		view.addObject("month", DateUtil.getMonth());       //当前月份
		return view;
	}
	
	/**
	 * 
	 * 描述:  年报信息统计（普通统计）
	 * @auther gaojinling
	 * @date 2017年5月5日 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/rptYrSmNormalCount.json","list.xml"})
	@ResponseBody
	public PageResponse<SmBaseinfoDto> rptYrSmNormalCountJSON(PageRequest request)throws Exception{
	 creatDefaultDBAuthEnv(request,"t.regOrg","t.localAdm");
	 List<SmBaseinfoDto> data= smBaseinfoService.selectYrSmNormalCount(request);
	 return new PageResponse<SmBaseinfoDto>(data);
	}
	
	/**
	 * 
	 * 描述:   年报信息统计（八大重点产业统计）
	 * @auther gaojinling
	 * @date 2017年5月5日 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/rptYrSmImportCount.json","list.xml"})
	@ResponseBody
	public PageResponse<SmBaseinfoDto> rptYrSmImportCountJSON(PageRequest request)throws Exception{
	 creatDefaultDBAuthEnv(request,"t.regOrg","t.localAdm");
	 List<SmBaseinfoDto> data= smBaseinfoService.selectYrRptImportCount(request);
	 return new PageResponse<SmBaseinfoDto>(data);
	}
		
	
	
	
	

}