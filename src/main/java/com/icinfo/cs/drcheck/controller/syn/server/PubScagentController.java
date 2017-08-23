/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.drcheck.controller.syn.server;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.icinfo.cs.base.model.CodeRegunit;
import com.icinfo.cs.base.service.ICodeRegunitService;
import com.icinfo.cs.common.constant.Constants;
import com.icinfo.cs.common.constant.DBAuthorConstants;
import com.icinfo.cs.common.utils.CardUtil;
import com.icinfo.cs.common.utils.DateUtil;
import com.icinfo.cs.common.utils.StringUtil;
import com.icinfo.cs.drcheck.dto.PubScagentDto;
import com.icinfo.cs.drcheck.model.PubScagent;
import com.icinfo.cs.drcheck.service.IPubScagentService;
import com.icinfo.cs.system.dto.SysUserDto;
import com.icinfo.cs.system.model.SysUser;
import com.icinfo.cs.system.service.IDeptDutycodeRefService;
import com.icinfo.cs.system.service.ISysDepartService;
import com.icinfo.cs.system.service.ISysUserService;
import com.icinfo.framework.common.ajax.AjaxResult;
import com.icinfo.framework.core.web.BaseController;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageRequest;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageResponse;
import com.icinfo.framework.tools.utils.StringUtils;

/**
 * 描述: cs_pub_scagent 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2016年10月26日
 */
@Controller
@RequestMapping({"/syn/server/drcheck/pubscagent", "/reg/server/drcheck/pubscagent" })
public class PubScagentController extends BaseController {
	@Autowired
	private IPubScagentService pubScagentService;
	@Autowired
	private ISysDepartService sysDepartService;
	
	@Autowired
	private ICodeRegunitService codeRegunitService;
	
	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private IDeptDutycodeRefService deptDutycodeRefService;

	/**
	 * 描述: to 执法人员维护列表页面
	 * @auther ljx
	 * @date 2016年10月31日
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpSession session) throws Exception {
		ModelAndView view = new ModelAndView("/syn/system/drcheck/scagent/scagent_list");
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		String deptCode = deptDutycodeRefService.selectDeptCodeByAdCodeSubDutyDeptCode(sysUser.getUserType(),sysUser.getDeptCode(), "A058");
	    view.addObject("sysUser", sysUser);
	    view.addObject("sessionDeptCode", deptCode);
		return view;
	}

	/**
	 * 描述: 执法人员分页查询
	 * @auther ljx
	 * @date 2016年10月31日
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listJSON")
	@ResponseBody
	public PageResponse<PubScagentDto> listJSON(PageRequest request, HttpSession session) throws Exception {
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
        if(!"1".equals(sysUser.getIsAdmin())&&!"2".equals(sysUser.getIsAdmin())){
        	String deptCode = "2".equals(sysUser.getUserType()) ? sysUser.getSysDepart().getAdcode() : sysUser.getDepartMent().getDeptCode();
            String deptCodeLike=replaceByUserLevel(deptCode,sysUser.getSearchRangeLevel(),"%");
            request.getParams().put("deptCodeLike",deptCodeLike);
        }
		List<PubScagentDto> data = pubScagentService.selectByManyCationToListJSON(request);
		return new PageResponse<PubScagentDto>(data);
	}
	
	/**
	 * 检查主体名单导入模板下载
	 * @author chenxin
	 * @date 2017年5月10日 
	 * @throws Exception
	 */
	@RequestMapping("/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(HttpServletRequest request, String file_name)throws Exception{
		String path = request.getSession().getServletContext()
				.getRealPath("/template")+File.separator+file_name;
		File file=new File(path);
		HttpHeaders headers = new HttpHeaders();
		//更改下载名称
		String fileName=new String("检查执法人员导入模板.xls".getBytes("GBK"),"iso-8859-1");
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
	}
	
	 /**
     * 根据用户所属层级(查询范围)来拼装查询条件
     * @author ZhuDefeng
     * @date 2016-10-05
     * @param deptCode
     * @param userLevel
     * @param conditionMark
     * @return
     */
    public String replaceByUserLevel(String deptCode,String userLevel,String conditionMark){
        if(StringUtil.isBlank(deptCode))return "";
        StringBuffer adCodeTmp=new StringBuffer(deptCode.substring(0,deptCode.length()));
        if(userLevel.equals(DBAuthorConstants.USER_LEVEL_PROV)||userLevel.equals(DBAuthorConstants.USER_LEVEL_ADMIN)){
            return null;
        }
        if(userLevel.equals(DBAuthorConstants.USER_LEVEL_CITY)){
            return adCodeTmp.replace(4,deptCode.length(),"").append(conditionMark).toString();
        }
        if(userLevel.equals(DBAuthorConstants.USER_LEVEL_COUNTRY)){
            return adCodeTmp.replace(6,deptCode.length(),"").append(conditionMark).toString();
        }
        if(userLevel.equals(DBAuthorConstants.USER_LEVEL_UNIT)){
            return adCodeTmp.append(conditionMark).toString();
        }
        return deptCode+conditionMark;
    }
	
	/**
	 * 描述: to 新增或修改页面
	 * @auther ljx
	 * @date 2016年10月31日
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/show")
	public ModelAndView show(String uid, HttpSession session) throws Exception {
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		String deptCode = deptDutycodeRefService.selectDeptCodeByAdCodeSubDutyDeptCode(sysUser.getUserType(),sysUser.getDeptCode(), "A058");
		ModelAndView view = new ModelAndView("/syn/system/drcheck/scagent/scagent_edit");
		if (StringUtils.isNotEmpty(uid)) {
			PubScagent pubScagent = pubScagentService.selectById(uid);
			if(StringUtils.isNotEmpty(pubScagent.getRelateUserUid())){
				SysUser sysUserInfo = sysUserService.selectSynUserByUId(pubScagent.getRelateUserUid());
				if(sysUserInfo !=null){
					pubScagent.setRelateUserName(sysUserInfo.getUsername());
				}
			}
			CodeRegunit codeRegunit = codeRegunitService.selectCodeRegunitInfo(pubScagent.getSlicenNO());
			if(codeRegunit != null){
				 view.addObject("codeRegunitDesc", codeRegunit.getContent());//管辖单位名称
			}
			view.addObject("pubScagent", pubScagent);
		}
		view.addObject("userType", sysUser.getUserType());
		view.addObject("deptName", sysUser.getDept());
		view.addObject("sessionDeptCode", deptCode);
		view.addObject("sysdate", DateUtil.getCurrentDate());
		return view;
	}
	
	/**
	 * 描述: to 新增或修改页面
	 * @auther ljx
	 * @date 2016年10月31日
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showDetail")
	public ModelAndView showDetail(String uid, HttpSession session) throws Exception {
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		String deptCode = deptDutycodeRefService.selectDeptCodeByAdCodeSubDutyDeptCode(sysUser.getUserType(),sysUser.getDeptCode(), "A058");
		ModelAndView view = new ModelAndView("/syn/system/drcheck/scagent/scagent_detail");
		if (StringUtils.isNotEmpty(uid)) {
			PubScagent pubScagent = pubScagentService.selectById(uid);
			if(StringUtils.isNotEmpty(pubScagent.getRelateUserUid())){
				SysUser sysUserInfo = sysUserService.selectSynUserByUId(pubScagent.getRelateUserUid());
				if(sysUserInfo !=null){
					pubScagent.setRelateUserName(sysUserInfo.getUsername());
				}
			}
			CodeRegunit codeRegunit = codeRegunitService.selectCodeRegunitInfo(pubScagent.getSlicenNO());
			if(codeRegunit != null){
				view.addObject("codeRegunitDesc", codeRegunit.getContent());//管辖单位名称
			}
			view.addObject("pubScagent", pubScagent);
		}
		view.addObject("userType", sysUser.getUserType());
		view.addObject("deptName", sysUser.getDept());
		view.addObject("sessionDeptCode", deptCode);
		view.addObject("sysdate", DateUtil.getCurrentDate());
		return view;
	}
	
	/**
	 * 描述: excel批量导入执法人员
	 * @auther ljx
	 * @date 2016年10月31日
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addexcel")
	public ModelAndView addexcel(String uid, HttpSession session) throws Exception {
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		String deptCode = deptDutycodeRefService.selectDeptCodeByAdCodeSubDutyDeptCode(sysUser.getUserType(),sysUser.getDeptCode(), "A058");
		ModelAndView view = new ModelAndView("/syn/system/drcheck/scagent/scagentexcel_import");
		view.addObject("userType", sysUser.getUserType());
		view.addObject("deptName", sysUser.getDept());
		view.addObject("sessionDeptCode", deptCode);
		return view;
	}
	
	 /**
     * 描述：excel批量导入待抽检库
     * @author chenxin
     * @date 2016-10-26
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/addbatch",method= RequestMethod.POST)
    @ResponseBody
    public void addbatch(@RequestParam("file") MultipartFile file,String localAdm,HttpServletResponse response,HttpSession session) throws Exception {
        InputStream in = file.getInputStream();
        SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
        JSONObject json = pubScagentService.saveExcelBatch(in, sysUser,localAdm);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();//关闭流
    }

	/**
	 * 
	 * 描述: 保存更新操作
	 * 
	 * @auther ljx
	 * @date 2016年10月31日
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/save",method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult save(PubScagent pubScagent, HttpSession session) throws Exception {
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		pubScagent.setSetUserId(sysUser.getId());
		pubScagent.setSetUserName(sysUser.getRealName());
		pubScagent.setSetTime(new Date());
		pubScagent.setSetDeptcode(sysUser.getDeptCode());
		pubScagent.setSetDeptName(sysUser.getDept());
		if(StringUtils.isNotEmpty(pubScagent.getAgentNO())){
			Map<String, Object> map = CardUtil.getCertNOInfo(pubScagent.getAgentNO());
			if(map != null){
				if(map.get("age") != null){
					int age = Integer.parseInt((String)map.get("age"));
					pubScagent.setAgentAge(age);
				}
				if(map.get("birthDay") != null){
					String birthDay = (String)map.get("birthDay");
					String[] arr = birthDay.split("-");
					pubScagent.setBirthDay(arr[0]+"年"+arr[1]+"月");;
				}
			}
		}
		if (StringUtils.isNotEmpty(pubScagent.getUID())) {
			try{
				if (pubScagentService.updatePubScagent(pubScagent) > 0) {
					return AjaxResult.success("更新成功");
				} else {
					return AjaxResult.error("更新失败");
				}
			}catch(Exception e){
				e.printStackTrace();
				return AjaxResult.error("更新失败");
			}

		} else {
			if (pubScagentService.savePubScagentNoRepeat(pubScagent)) {
				return AjaxResult.success("新增成功");
			} else {
				return AjaxResult.error("身份证件号码已存在");
			}
		}
	}

	/**
	 * 
	 * 描述: 删除操作
	 * 
	 * @auther ljx
	 * @date 2016年10月31日
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxResult delete(String uid) throws Exception {
		if (StringUtils.isNotEmpty(uid)) {
			if (pubScagentService.deletePubScagentById(uid) > 0) {
				return AjaxResult.success("删除成功");
			} else {
				return AjaxResult.error("删除失败");
			}

		} else {
			return AjaxResult.error("未选择许可信息");
		}

	}

	// ==============================随机抽取执法人员===================================
	/**
	 * 描述:进入 当前部门下的所有执法人员 页面
	 * 
	 * @author chenxin
	 * @date 2016-11-06
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/choosepage")
	public ModelAndView doChoosePage(String deptTaskUid, String deptCode,String scTypeWay) throws Exception {
		ModelAndView view = new ModelAndView("/syn/system/drcheck/sctack/chooseagent_main");
		view.addObject("deptTaskUid", deptTaskUid);
		view.addObject("deptCode", deptCode);
		view.addObject("scTypeWay", scTypeWay);
		return view;
	}

	/**
	 * 描述:获取当前部门下的所有执法人员JOSN数据
	 * 
	 * @author chenxin
	 * @date 2016-11-06
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/chooseagent")
	@ResponseBody
	public PageResponse<PubScagentDto> doChooseAgentJSON(PageRequest request,HttpSession session) throws Exception {
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
        if(!"1".equals(sysUser.getIsAdmin())&&!"2".equals(sysUser.getIsAdmin())){
        	String deptCode = "2".equals(sysUser.getUserType()) ? sysUser.getSysDepart().getAdcode() : sysUser.getDepartMent().getDeptCode();
            String deptCodeLike=replaceByUserLevel(deptCode,sysUser.getSearchRangeLevel(),"%");
            request.getParams().put("deptCodeLike",deptCodeLike);
        }
		List<PubScagentDto> data = pubScagentService.selectByManyCationToListJSON(request);
		return new PageResponse<PubScagentDto>(data);
	}
	
	
	/**
	 * 描述:进入账号关联选择页面
	 * @auther yujingwei
	 * @date 2016年11月18日
	 * @return view
	 * @throws Exception
	 */
	@RequestMapping("/viewsys")
	public ModelAndView doViewSysUser(HttpSession session) throws Exception {
		ModelAndView view = new ModelAndView("/syn/system/drcheck/scagent/chooseuser_view");
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
	    view.addObject("sysUser", sysUser);
		return view;
	}

	/**
	 * 描述: 进入检查事项树选择页面
	 * @auther chenxin
	 * @date 2017年4月20日
	 * @return view
	 * @throws Exception
	 */
	@RequestMapping("scPtypeSelect")
	public ModelAndView toOrgTreeView(String isPermissionCheck) throws Exception {
		ModelAndView view = new ModelAndView("/syn/system/drcheck/scagent/scptype_tree");
		view.addObject("isPermissionCheck", isPermissionCheck);
		return view;

	}
	
	/**
	 * 描述: 进入检查事项树选择页面
	 * @auther chenxin
	 * @date 2017年4月20日
	 * @return view
	 * @throws Exception
	 */
	@RequestMapping("scChecktypeSelect")
	public ModelAndView toOrgChooseTreeView(String isPermissionCheck, String sccheckCodeIn) throws Exception {
		ModelAndView view = new ModelAndView("/syn/system/drcheck/scagent/sccheck_tree");
		view.addObject("sccheckCodeIn", sccheckCodeIn);
		view.addObject("isPermissionCheck", isPermissionCheck);
		return view;
		
	}
	
	/**
	 * 描述: 获取检查事项树 数据
	 * @auther yujingwei
	 * @date 2017年4月20日
	 * @return AjaxResult
	 * @throws Exception
	 */
	@RequestMapping("/treeListJSON")
	@ResponseBody
	public AjaxResult treeListJSON(String isPermissionCheck) throws Exception {
		List<Map<String, Object>> data = pubScagentService.selectScPtypeMap();
		return AjaxResult.success("查询成功", data);
	}
	
	/**
	 * 
	 * 描述: 执法人员统计页面
	 * @auther gaojinling
	 * @date 2017年5月26日 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/zfcount")
	public ModelAndView doCountView(HttpSession session) throws Exception{
		ModelAndView view  = new ModelAndView("/syn/system/drcheck/scspecial/zfperson_count");
		SysUserDto sysUser = (SysUserDto) session.getAttribute(Constants.SESSION_SYS_USER);
		//警示协同的地址 默认警示
		String deptCode="";
		if(("1".equals(sysUser.getUserType()) && sysUser.getDepartMent()!=null) || ("2".equals(sysUser.getUserType()) && sysUser.getSysDepart() !=null)){
			if("1".equals(sysUser.getUserType())){
				deptCode=sysUser.getDepartMent().getDeptCode();
			}else{
				deptCode=sysUser.getSysDepart().getAdcode();
			}
			if(deptCode.length()>4){
				deptCode=deptCode.substring(0, 4);
			} 
		}		
		view.addObject("startCheckPushDate", DateUtil.getYear()+"-01-01");  //开始日期
		view.addObject("endCheckPushDate",DateUtil.getNdaylater2(DateUtil.getSysDate(), -1));    //结束日期
		view.addObject("deptCode", deptCode);
		view.addObject("sysUser", sysUser);
		view.addObject("uservest", sysUser.getUserVest(sysUser));       //层级
		view.addObject("year", DateUtil.getYear());       //当前年份
		view.addObject("month", DateUtil.getMonth());       //当前月份
		return view;
	}
	
	/**
	 * 
	 * 描述: 执法人员库统计
	 * @auther gaojinling
	 * @date 2017年6月1日 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/countlist.json","list.xml"})
	@ResponseBody
	public PageResponse<PubScagentDto> countJSON(PageRequest request) throws Exception {
		List<PubScagentDto> data = pubScagentService.selectScAgentCount(request);
		return new PageResponse<PubScagentDto>(data);
	}
	
}