/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.sccheck.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.icinfo.framework.core.web.BaseController;

/**
 * 描述:    cs_pub_sccheck_item 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2017年05月17日
 */
@Controller
@RequestMapping({"/reg/pub/server/sccheck/pubsccheckitem","/syn/pub/server/sccheck/pubsccheckitem"})
public class PubSccheckItemController extends BaseController {
	
	/**
	 * 
	 * 描述: 抽查检查事项清单
	 * @auther gaojinling
	 * @date 2017年5月26日 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pubsccheckitemlist")
	public ModelAndView doCountView(HttpSession session) throws Exception{
		ModelAndView view  = new ModelAndView("/syn/system/sccheck/sccheckitem/pubsccheckitem_list");
		return view;
	}
	
	
}