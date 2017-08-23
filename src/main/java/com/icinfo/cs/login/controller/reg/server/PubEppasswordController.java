/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.login.controller.reg.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icinfo.cs.base.model.CodeCertype;
import com.icinfo.cs.base.model.CodeRegState;
import com.icinfo.cs.base.service.ICodeCertypeService;
import com.icinfo.cs.base.service.ICodeRegStateService;
import com.icinfo.cs.login.dto.PubEppasswordDto;
import com.icinfo.cs.login.model.PubEppassword;
import com.icinfo.cs.login.model.PubEppasswordHis;
import com.icinfo.cs.login.model.PubPhoneapply;
import com.icinfo.cs.login.service.IPubEppasswordService;
import com.icinfo.cs.login.service.IPubPhoneapplyService;
import com.icinfo.cs.system.controller.CSBaseController;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageRequest;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageResponse;

/**
 * 描述:    cs_pub_eppassword 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2016年08月28日
 */
@Controller
@RequestMapping("/reg/server/backup")
public class PubEppasswordController extends CSBaseController {
	
	@Autowired
	private IPubEppasswordService PubEppasswordService;
	
	@Autowired
	private IPubPhoneapplyService  pubPhoneapplyService;
	
	//证件类型
	@Autowired
	private ICodeCertypeService codeCertypeService;
	
	@Autowired
	private ICodeRegStateService codeRegStateService;
	
	/**
	 * 
	 * 描述: 联络员信息查询页
	 * @auther chenyl
	 * @date 2016年9月10日 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public ModelAndView list() throws Exception{
		ModelAndView view = new ModelAndView("reg/server/backup/liainfosearch_list");
		List<CodeRegState> regStateList = codeRegStateService.selectRegStateParamList();
 		view.addObject("regStateList", regStateList);
		return view;
	}
	
    /**
     * 
     * 描述: 获取联络员信息列表
     * @auther chenyl
     * @date 2016年9月10日 
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping("/list.json")
    @ResponseBody
    public PageResponse<PubEppasswordDto> listJSON(PageRequest request) throws Exception {
		creatDefaultDBAuthEnv(request,"ts.CheckDep","ts.LocalAdm");
		List<PubEppasswordDto> data = PubEppasswordService.selectPubEppasswordList(request);
		return new PageResponse<PubEppasswordDto>(data);
    }
	
	/**
	 * 描述：获取确认书历史列表Json数据
	 * @author yujingwei
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list_his.json")
	@ResponseBody
	public PageResponse<PubEppasswordHis> listForHisJSON(PageRequest request) throws Exception {
		List<PubEppasswordHis> data = PubEppasswordService.queryPageForHis(request);
		return new PageResponse<PubEppasswordHis>(data);
	}

	/**
	 * 
	 * 描述: 联络员信息查询详细页面
	 * @auther chenyl
	 * @date 2016年9月29日 
	 * @param pasid
	 * @param pripid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/option")
	public ModelAndView accept(String pasid,String pripid) throws Exception {
		//证件类型
		List<CodeCertype> Certypes = codeCertypeService.selectAll();
		
		ModelAndView view = new ModelAndView("reg/server/backup/liainfosearch_option");
		PubPhoneapply pubPhoneapply =  pubPhoneapplyService.selectPubPhoneApplyInfo(pasid,pripid);
		PubEppassword pubEppassword = PubEppasswordService.selectPubEppasswordByPriPid(pripid);
		if(pubPhoneapply != null){
			view.addObject(pubPhoneapply);
		}
		if(pubEppassword != null){
			view.addObject(pubEppassword);
		}
		view.addObject("Certypes", Certypes);
		view.addObject("pasid", pasid);
		return view;
	}
	
	/**
	 * 描述: 进入联络员手机校验码查询页面
	 * @auther yujingwei
	 * @date 2017年1月4日 
	 * @return view
	 * @throws Exception
	 */
	@RequestMapping("/passwordlist")
	public ModelAndView passwordlist() throws Exception{
		ModelAndView view = new ModelAndView("system/eppassword/password_list");
		return view;
	}
	
  /* -----------------------------------------------------个体户联络员验证码查询-------------------------------------------------------------*/	
	/**
	 * 
	 * 描述: 个体户登录校验码列表查询页面
	 * @auther gaojinling
	 * @date 2017年1月4日 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pbepasswordlist")
	public ModelAndView pbEpasswordList() throws Exception{
		ModelAndView view = new ModelAndView("reg/server/backup/pbepasswordlist");
		return view;
	}
	
	
	/**
	 * 
	 * 描述: 个体户登录校验码列表查询
	 * @auther gaojinling
	 * @date 2017年1月4日 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pbepasswordList.json")
    @ResponseBody
    public PageResponse<PubEppasswordDto> pbEpasswordListJSON(PageRequest request) throws Exception {
		creatDefaultDBAuthEnv(request,"b.RegOrg","b.LocalAdm");
		List<PubEppasswordDto> data = PubEppasswordService.selectPbEppasswordList(request);
		return new PageResponse<PubEppasswordDto>(data);
    }
	
  	
}