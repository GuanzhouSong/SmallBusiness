/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.rpt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icinfo.cs.rpt.rptdto.YrRptDto;
import com.icinfo.cs.rpt.rptmodel.RptOptoInfo;
import com.icinfo.cs.rpt.rptservice.IRptOptoInfoService;
import com.icinfo.cs.rpt.rptservice.IYrRptService;
import com.icinfo.cs.system.controller.CSBaseController;
import com.icinfo.framework.common.ajax.AjaxResult;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageRequest;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageResponse;

/**
 * 描述:    cs_rpt_opto_info 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2017年03月22日
 */
@Controller
@RequestMapping("/reg/server/rptOptoInfo" )
public class RptOptoInfoController extends CSBaseController {
	
	@Autowired
	private IRptOptoInfoService rptOptoInfoService;
	
	
	

	/**
	 * 
	 * 描述: 经营期限统计（警示首页）
	 * @auther gaojinling
	 * @date 2017年3月23日 
	 * @param queryMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/rptOptoInfoCount.json","list.xml"})
	@ResponseBody
	public AjaxResult rptOptoInfoCountJSON(PageRequest request)throws Exception{
	 Map<String, Object> map = new HashMap<String, Object>();
	 creatOptDBAuthEnv(map,"regorg","localadm");
	 RptOptoInfo rptOptoInfo = rptOptoInfoService.selectRptOptoCount(map);
	 return AjaxResult.success("rptOptoInfo",rptOptoInfo);
	}
	
	
	
}