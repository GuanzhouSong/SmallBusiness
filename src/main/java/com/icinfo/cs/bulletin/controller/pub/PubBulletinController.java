package com.icinfo.cs.bulletin.controller.pub;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Encoder;

import com.icinfo.cs.bulletin.model.PubAnnounceMent;
import com.icinfo.cs.bulletin.service.IPubAnnounceMentService;
import com.icinfo.cs.common.utils.AESEUtil;
import com.icinfo.cs.common.utils.OssClient;
import com.icinfo.cs.common.utils.StringUtil;
import com.icinfo.cs.drcheck.dto.PubScresultDto;
import com.icinfo.cs.drcheck.service.IPubScresultService;
import com.icinfo.cs.sccheck.dto.PubScentDto;
import com.icinfo.cs.sccheck.service.IPubScentService;
import com.icinfo.cs.simpleesc.model.ErEscAppinfo;
import com.icinfo.cs.simpleesc.service.IErEscAppinfoService;
import com.icinfo.framework.common.ajax.AjaxResult;
import com.icinfo.framework.core.web.BaseController;
import com.icinfo.framework.core.web.annotation.RepeatSubmit;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageRequest;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageResponse;


@Controller
@RequestMapping("/pub/infobulletin")
public class PubBulletinController extends BaseController{
  
	@Autowired
	private IPubAnnounceMentService PubAnnounceMentService;
	
	@Autowired
	private IPubScresultService pubScresultService;
	
	@Autowired
	private IErEscAppinfoService erEscAppinfoService;
	
	@Autowired
	private IPubScentService pubScentService;
	
	@Autowired
	OssClient ossClient;
	
	@Value("${cloudModel}")
	private String cloudModel;
	
	/**
	 * 
	 * 描述: 进入信息公告公示页面
	 * @auther yujingwei
	 * @date 2016年10月17日
	 * @return view
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public ModelAndView list() throws Exception{
		ModelAndView view = new ModelAndView("pub/infobulletin/bulletinindex");
		return view;
	}
	
	/**
     * 获取信息公示公告列表数据
     * @author yujingwei
     * @date 2016-10-17
     * @param request
     * @return PageResponse
     * @throws Exception
     */
	@RequestMapping(value="/list.json",method= RequestMethod.GET)
    @ResponseBody
    public PageResponse<PubAnnounceMent> bulletinInfo(PageRequest request) throws Exception {
		List<PubAnnounceMent> data = new ArrayList<PubAnnounceMent>();
		if(request.getParams() != null && request.getParams().get("pubType") != null){
			data = PubAnnounceMentService.queryBulletinInfoList(request);
		}
        return new PageResponse<PubAnnounceMent>(data);
    }
	
	/**
     * 获取信息抽查检查列表数据
     * @author yujingwei
     * @date 2016-10-17
     * @param request
     * @return PageResponse
     * @throws Exception
     */
	@RequestMapping(value="/spotcheck",method= RequestMethod.POST)
    @ResponseBody
    public PageResponse<PubScresultDto> spotcheckList(PageRequest request) throws Exception {
		List<PubScresultDto> PubScresultDtoList = pubScresultService.selectScresultInfoForBulletin(request);
		if(CollectionUtils.isNotEmpty(PubScresultDtoList)){
			for(PubScresultDto pubScresultDto : PubScresultDtoList){
				if(StringUtil.isNotBlank(pubScresultDto.getUniCode())){
					pubScresultDto.setRegNO(pubScresultDto.getUniCode());
				}
			}
		}
        return new PageResponse<PubScresultDto>(PubScresultDtoList);
    }
	
	/**
	 * 获取检查公告详情信息
	 * @author yujingwei
	 * @date 2016-10-17
	 * @param request
	 * @return PageResponse
	 * @throws Exception
	 */
	@RequestMapping(value="/scentCheck",method= RequestMethod.POST)
	@ResponseBody
	public PageResponse<PubScresultDto> scentCheckList(PageRequest request) throws Exception {
		List<PubScresultDto> PubScresultDtoList = pubScresultService.selectCheckScresultInfoForBulletin(request);
		if(CollectionUtils.isNotEmpty(PubScresultDtoList)){
			for(PubScresultDto pubScresultDto : PubScresultDtoList){
				if(StringUtil.isNotBlank(pubScresultDto.getUniCode())){
					pubScresultDto.setRegNO(pubScresultDto.getUniCode());
				}
			}
		}
		return new PageResponse<PubScresultDto>(PubScresultDtoList);
	}
	
	/**
	 * 获取检查任务公告详情信息
	 * @author yujingwei
	 * @date 2016-10-17
	 * @param request
	 * @return PageResponse
	 * @throws Exception
	 */
	@RequestMapping(value="/spottask",method= RequestMethod.POST)
	@ResponseBody
	public PageResponse<PubScentDto> scentCheckTaskList(PageRequest request) throws Exception {
		List<PubScentDto> PubScresultDtoList = pubScentService.selectPubScentDtoList(request);
		Integer total = pubScentService.selectPubScentTotalForBulletin(request);
		return new PageResponse<PubScentDto>(PubScresultDtoList, total, request);
	}
	
	/**
	 * 简易注销承诺书查看
	 * @author yujingwei
	 * @date 2017年2月15日
	 * @param priPID
	 * @return
	 */
	@RequestMapping("view")
	public ModelAndView viewCommitment(@RequestParam String priPID) throws Exception{
		ModelAndView view = new ModelAndView("/pub/infobulletin/commitment");
		String decodePriPID = AESEUtil.decodeCorpid(priPID);
		ErEscAppinfo erEscAppinfo = erEscAppinfoService.doGetErEscAppinfoByPriPID(decodePriPID);
		if(erEscAppinfo != null){
			String fileName = erEscAppinfo.getCommitment();
			BASE64Encoder encoder = new BASE64Encoder();
			erEscAppinfo.setCommitment(encoder.encode(downFormOSSa(fileName)));
		}
		view.addObject("erEscAppinfo", erEscAppinfo);
		return view;
	}
	
	/**
	 * 描述:进入公示公告详情页
	 * @author yujingwei
	 * @date 2016-10-19
	 * @param UID,pubType
	 * @return ModelAndView
	 */
	@RequestMapping(value="/showDetails",method= RequestMethod.GET)
	public ModelAndView showDetails(String UID,String pubType) throws Exception{
		ModelAndView view = new ModelAndView();
		if(StringUtils.isNotEmpty(UID) && StringUtils.isNotEmpty(pubType)){
			String deCodeType = AESEUtil.decodeCorpid(pubType);
			view = PubAnnounceMentService.doGetViewByPubType(UID, deCodeType, view);
			view.addObject("pubType", deCodeType);
		}
		return view;
	}
	
	/**
	 * 描述:用于手动生成增量公告数据
	 * @author yujingwei
	 * @date 2016-10-19
	 * @param void
	 * @throws Exception
	 */
	@RequestMapping(value="/bulletinDataForCreatAll",method= RequestMethod.GET)
	@ResponseBody
	public AjaxResult creatBulletinDataNoAuto(String updateWay) throws Exception{
		try {
			if("Y".equals(updateWay)){
				PubAnnounceMentService.doCreatBulletinAllInfo();
				return AjaxResult.success("数据生成成功");
			}else{
				return AjaxResult.error("操作失败！");
			}
		} catch (Exception ex) {
			return AjaxResult.error("数据生成发生错误："+ex+"");
		}
	}

	/**
	 * 描述:类型加密
	 * @date 2016-10-19
	 * @param AjaxResult
	 */
	@RequestMapping(value="/enCode",method= RequestMethod.GET)
	@ResponseBody
	public AjaxResult enCodeData(String pubType) throws Exception{
		return AjaxResult.success("", AESEUtil.encodeCorpid(pubType));
	}


	/**
	 * 描述：跳转到年报填报页面
	 *
	 * @author baifangfang
	 * @date 2016年11月2日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="yearreport")
	public ModelAndView yearReport() throws Exception{
		ModelAndView mav = new ModelAndView("pub/infobulletin/fillpublicinformation");
		return mav;
	}
	
	/** 
	 * 描述: 跳转到使用帮助页面
	 * @auther ZhouYan
	 * @date 2016年11月4日 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="help")
	public ModelAndView help() throws Exception{
		ModelAndView view = new ModelAndView("pub/infobulletin/help");
		return view;
	}
	
	/**
	 * 从OSS下载文件接口
	 * @param fileName
	 * @return
	 */
	private byte[] downFormOSSa(String fileName) {
		if (fileName == null || StringUtils.isBlank(fileName)) {
			return null;
		}
		try {// 从OSS获取文件流
			byte[] by = null;
			by = ossClient.getStream(fileName);
			return by;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 描述: 简易注销信息推送
	 * @auther yujingwei
	 * @date 2017年2月13日 
	 * @return ajax
	 * @throws Exception
	 */
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult doSendErEscAppinfo(String priPID) throws Exception{
		try {
			if(!erEscAppinfoService.doSendErEscAppinfo(priPID)){
				return AjaxResult.error("保存失败！"); 
			}
		} catch (Exception ex) {
			return AjaxResult.error("保存失败！"+ex.getMessage()); 
		}
		return AjaxResult.success("保存成功！");
	}
	
	/**
	 * 
	 * 描述: 进入公示简易注销维护页面
	 * @auther yujingwei
	 * @date 2017年04月18日
	 * @return view
	 * @throws Exception
	 */
	@RequestMapping("/simpleesc")
	public ModelAndView simpleescView() throws Exception{
		ModelAndView view = new ModelAndView("pub/simpleesc/upload");
		return view;
	}
	
	/**
	 * 描述: 保存简易注销申请信息（维护用）
	 * @auther yujingwei
	 * @date 2017年2月13日 
	 * @return ajax
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	@ResponseBody
	@RepeatSubmit(timeout=3000)
	public AjaxResult doSaveErEscAppinfo(ErEscAppinfo erEscAppinfo,String saveType) throws Exception{
		try {
			if(!erEscAppinfoService.doMaintainSimpleesc(erEscAppinfo,saveType)){
				return AjaxResult.error("保存失败！"); 
			}
		} catch (Exception ex) {
			return AjaxResult.error("保存失败！"+ex.getMessage()); 
		}
		return AjaxResult.success("保存成功！");
	}
	
	/**
	 * 描述：上传接口（兼容IE8）
	 * 
	 * @author yujingwei
	 * @date 2017年2月14日
	 * @param file
	 * @param prefix
	 * @return
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String upload(@RequestParam(value = "file") MultipartFile file, String prefix) throws Exception{
		ErEscAppinfo erEscAppinfo = erEscAppinfoService.doGetErEscAppinfoByEntName(prefix);
		if(erEscAppinfo == null) return "error";
		String fileName = erEscAppinfo.getPriPID() + "-" + "全体投资人承诺书";
		if(file.getSize() > 5000000){
			return "false";
		}
		String result = null;
		if ("Y".equals(cloudModel)) {
			result = uploadToOSS(file, fileName);
		} else {
			result = "error";
		}
		return result;
	}
	
	/**
	 * 上传到OSS服务器
	 * 
	 * @param files
	 * @param fileName
	 * @return
	 */
	private String uploadToOSS(MultipartFile file, String fileName) {
		boolean flag = false;
		try {
			flag = ossClient.putStream(file, "simpleesc/commitment/" + fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		if (flag) {
			return "simpleesc/commitment/" + fileName;
		} else {
			return "error";
		}
	}
	
	/**
	 * 描述:定时插入公告数据  
	 * @author yujingwei
	 * @date 2016-10-19
	 * @param void
	 * @throws Exception
	 */
	public void creatBulletinByQuartz() throws Exception{
		PubAnnounceMentService.doCreatBulletinAllInfo();
	}
}
