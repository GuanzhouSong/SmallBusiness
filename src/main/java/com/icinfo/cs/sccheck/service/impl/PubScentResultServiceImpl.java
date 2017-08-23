/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.sccheck.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icinfo.cs.common.utils.DateUtil;
import com.icinfo.cs.common.utils.StringUtil;
import com.icinfo.cs.sccheck.dto.PubScentResultDto;
import com.icinfo.cs.sccheck.dto.SccheckCount;
import com.icinfo.cs.sccheck.mapper.PubSccheckItemResultMapper;
import com.icinfo.cs.sccheck.mapper.PubScentResultMapper;
import com.icinfo.cs.sccheck.model.PubSccheckItemResult;
import com.icinfo.cs.sccheck.model.PubSccheckType;
import com.icinfo.cs.sccheck.model.PubScentResult;
import com.icinfo.cs.sccheck.service.IPubSccheckTypeService;
import com.icinfo.cs.sccheck.service.IPubScdeptTaskService;
import com.icinfo.cs.sccheck.service.IPubScentResultService;
import com.icinfo.cs.system.dto.SysUserDto;
import com.icinfo.framework.core.service.support.MyBatisServiceSupport;
import com.icinfo.framework.core.transaction.annotation.Transaction;
import com.icinfo.framework.mybatis.mapper.entity.Example;
import com.icinfo.framework.mybatis.pagehelper.PageHelper;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageRequest;

/**
 * 描述: cs_pub_scent_result 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2017年05月17日
 */
@Service
public class PubScentResultServiceImpl extends MyBatisServiceSupport implements IPubScentResultService {

	@Autowired
	PubScentResultMapper pubScentResultMapper;

	@Autowired
	IPubSccheckTypeService pubSccheckTypeService;

	@Autowired
	PubSccheckItemResultMapper pubSccheckItemResultMapper;

	@Autowired
	IPubScdeptTaskService pubScdeptTaskService;

	@Override
	public List<PubScentResultDto> queryPageResult(PageRequest request, SysUserDto sysUserDto) {
		Map<String, Object> params = request.getParams();
		// String deptCode = "2".equals(sysUserDto.getUserType()) ?
		// sysUserDto.getSysDepart().getAdcode()
		// : sysUserDto.getDepartMent().getDeptCode();
		// if(deptCode.length() > 8){
		// deptCode = deptCode.substring(0, 8);
		// }else if (deptCode.length() == 6){
		// deptCode = deptCode+"00";
		// }
		// params.put("unitDeptCode", deptCode);

		String deptCode = "2".equals(sysUserDto.getUserType()) ? sysUserDto.getSysDepart().getAdcode()
				: sysUserDto.getDepartMent().getDeptCode();
		String userid = sysUserDto.getId();
		deptCode = deptCode.substring(0, 6);
		params.put("userId", userid);
		params.put("deptCode", deptCode);
		String checkResult = (String) params.get("checkResult");
		if ("1,2,3,4,5,6,7,8,9,10,11,12".equals(checkResult)) {
			params.put("checkResults", "");
		} else {
			params.put("checkResults", checkResult.split(","));
		}

		String checkDeptPerson = (String) params.get("checkDeptPerson");
		if (!StringUtil.isEmpty(checkDeptPerson)) {
			params.put("checkDeptPersons", checkDeptPerson.split(","));
		}

		PageHelper.startPage(request.getPageNum(), request.getLength());
		return pubScentResultMapper.queryPageResult(params);
	}

	/**
	 * 描述：抽查检查数据列表
	 * 
	 * @author chenxin
	 * @date 2017年6月28日
	 * @param request
	 * @return
	 */
	@Override
	public List<PubScentResultDto> queryPageSearchResult(PageRequest request) {
		Map<String, Object> params = request.getParams();
		String checkResult = (String) params.get("checkResult");
		if ("1,2,3,4,5,6,7,8,9,10,11,12".equals(checkResult)) {
			params.put("checkResults", "");
		} else {
			params.put("checkResults", checkResult.split(","));
		}
		PageHelper.startPage(request.getPageNum(), request.getLength());
		return pubScentResultMapper.queryPageSearchResult(params);
	}

	@Override
	public SccheckCount querySccheckCount(PageRequest request, SysUserDto sysUserDto) {
		Map<String, Object> params = request.getParams();
		// String deptCode = "2".equals(sysUserDto.getUserType()) ?
		// sysUserDto.getSysDepart().getAdcode()
		// : sysUserDto.getDepartMent().getDeptCode();
		// if(deptCode.length() > 8){
		// deptCode = deptCode.substring(0, 8);
		// }else if (deptCode.length() == 6){
		// deptCode = deptCode+"00";
		// }
		// params.put("unitDeptCode", deptCode);

		String deptCode = "2".equals(sysUserDto.getUserType()) ? sysUserDto.getSysDepart().getAdcode()
				: sysUserDto.getDepartMent().getDeptCode();
		String userid = sysUserDto.getId();
		deptCode = deptCode.substring(0, 6);
		params.put("userId", userid);
		params.put("deptCode", deptCode);
		String checkResult = (String) params.get("checkResult");
		if ("1,2,3,4,5,6,7,8,9,10,11,12".equals(checkResult)) {
			params.put("checkResult", "");
		} else {
			params.put("checkResult", Arrays.asList(checkResult.split(",")));
		}
		return pubScentResultMapper.querySccheckCount(params);
	}

	/**
	 * 描述：抽查查询结果统计
	 * 
	 * @author baifangfang
	 * @date 2017年5月17日
	 * @param request
	 * @param sysUserDto
	 * @return
	 */
	@Override
	public SccheckCount querySccheckSearchCount(PageRequest request) {
		Map<String, Object> params = request.getParams();
		String checkResult = (String) params.get("checkResult");
		if ("1,2,3,4,5,6,7,8,9,10,11,12".equals(checkResult)) {
			params.put("checkResults", "");
		} else {
			params.put("checkResults", checkResult.split(","));
		}
		return pubScentResultMapper.querySccheckSearchCount(params);
	}

	@Override
	@Transaction
	public int savePubScentResult(Map<String, Object> map, SysUserDto sysUser) throws Exception {
		// 检查结果
		PubScentResultDto pubScentResultDto = packPubScResultDto(map);
		pubScentResultDto.setEnterUserName(sysUser.getRealName());
		pubScentResultDto.setEnterDate(new Date());
		pubScentResultDto.setSetUserName(sysUser.getRealName());
		pubScentResultDto.setSetTime(new Date());
		int m = pubScentResultMapper.updateByPrimaryKeySelective(pubScentResultDto);
		if (m <= 0) {
			throw new Exception();
		}
		// 删除旧的检查事项结果
		pubSccheckItemResultMapper.deletePubSccheckItemResultByTaskUidAndPriPID(map);
		// 检查事项结果
		savePubSccheckItemResult(map, pubScentResultDto);
		return 1;
	}

	/**
	 * 描述：保存检查事项结果
	 * 
	 * @author baifangfang
	 * @date 2017年5月20日
	 * @param map
	 * @param pubScentResultDto
	 */
	private void savePubSccheckItemResult(Map<String, Object> map, PubScentResultDto pubScentResultDto) {
		// 36项检查情况
		for (int i = 1; i <= 12; i++) {
			DecimalFormat df = new DecimalFormat("00");
			String problemKey = "problem_A" + df.format(i);
			String disposeKey = "dispose_A" + df.format(i);
			String nameKey = "checkName_A" + df.format(i);
			String chkKey = "chk_A" + df.format(i);
			String problemValue = (String) map.get(problemKey);
			String disposeValue = (String) map.get(disposeKey);
			String nameValue = (String) map.get(nameKey);
			String chkValue = (String) map.get(chkKey);
			if (StringUtil.isEmpty(chkValue)) {
				problemValue = null;
				disposeValue = null;
			}

			if (!StringUtil.isEmpty(nameValue)) {
				PubSccheckItemResult pubSccheckItemResult = new PubSccheckItemResult();
				PubSccheckType pubSccheckType = pubSccheckTypeService.queryPubSccheckTypeByCode("A" + df.format(i));
				pubSccheckItemResult.setCheckCode(pubSccheckType.getCode());
				pubSccheckItemResult.setCheckName(pubSccheckType.getContent());
				pubSccheckItemResult.setCheckType(pubScentResultDto.getCheckType());
				pubSccheckItemResult.setDispose(disposeValue);
				pubSccheckItemResult.setPriPID(pubScentResultDto.getPriPID());
				pubSccheckItemResult.setProblem(problemValue);
				pubSccheckItemResult.setTaskUid(pubScentResultDto.getTaskUid());
				pubSccheckItemResultMapper.insert(pubSccheckItemResult);
			}
		}
		for (int i = 13; i <= 22; i++) {
			DecimalFormat df = new DecimalFormat("00");
			String problemKey = "problem_B" + df.format(i - 12);
			String disposeKey = "dispose_B" + df.format(i - 12);
			String nameKey = "checkName_B" + df.format(i - 12);
			String chkKey = "chk_B" + df.format(i - 12);
			String problemValue = (String) map.get(problemKey);
			String disposeValue = (String) map.get(disposeKey);
			String nameValue = (String) map.get(nameKey);
			String chkValue = (String) map.get(chkKey);
			if (StringUtil.isEmpty(chkValue)) {
				problemValue = null;
				disposeValue = null;
			}

			if (!StringUtil.isEmpty(nameValue)) {
				PubSccheckItemResult pubSccheckItemResult = new PubSccheckItemResult();
				PubSccheckType pubSccheckType = pubSccheckTypeService
						.queryPubSccheckTypeByCode("B" + df.format(i - 12));
				pubSccheckItemResult.setCheckCode(pubSccheckType.getCode());
				pubSccheckItemResult.setCheckName(pubSccheckType.getContent());
				pubSccheckItemResult.setCheckType(pubScentResultDto.getCheckType());
				pubSccheckItemResult.setDispose(disposeValue);
				pubSccheckItemResult.setPriPID(pubScentResultDto.getPriPID());
				pubSccheckItemResult.setProblem(problemValue);
				pubSccheckItemResult.setTaskUid(pubScentResultDto.getTaskUid());
				pubSccheckItemResultMapper.insert(pubSccheckItemResult);
			}
		}
		for (int i = 23; i <= 36; i++) {
			DecimalFormat df = new DecimalFormat("00");
			String problemKey = "problem_C" + df.format(i - 22);
			String disposeKey = "dispose_C" + df.format(i - 22);
			String nameKey = "checkName_C" + df.format(i - 22);
			String chkKey = "chk_C" + df.format(i - 22);
			String problemValue = (String) map.get(problemKey);
			String disposeValue = (String) map.get(disposeKey);
			String nameValue = (String) map.get(nameKey);
			String chkValue = (String) map.get(chkKey);
			if (StringUtil.isEmpty(chkValue)) {
				problemValue = null;
				disposeValue = null;
			}

			if (!StringUtil.isEmpty(nameValue)) {
				PubSccheckItemResult pubSccheckItemResult = new PubSccheckItemResult();
				PubSccheckType pubSccheckType = pubSccheckTypeService
						.queryPubSccheckTypeByCode("C" + df.format(i - 22));
				pubSccheckItemResult.setCheckCode(pubSccheckType.getCode());
				pubSccheckItemResult.setCheckName(pubSccheckType.getContent());
				pubSccheckItemResult.setCheckType(pubScentResultDto.getCheckType());
				pubSccheckItemResult.setDispose(disposeValue);
				pubSccheckItemResult.setPriPID(pubScentResultDto.getPriPID());
				pubSccheckItemResult.setProblem(problemValue);
				pubSccheckItemResult.setTaskUid(pubScentResultDto.getTaskUid());
				pubSccheckItemResultMapper.insert(pubSccheckItemResult);
			}
		}
		// 其他检查情况
		for (int i = 1; i <= 1000; i++) {
			String problemKey = "problem_OTHER" + i;
			String disposeKey = "dispose_OTHER" + i;
			String checkName = "checkName_OTHER" + i;
			String chkKey = "chk_OTHER" + i;
			String problemValue = (String) map.get(problemKey);
			String disposeValue = (String) map.get(disposeKey);
			String checkNameValue = (String) map.get(checkName);
			String chkValue = (String) map.get(chkKey);
			if (StringUtil.isEmpty(chkValue)) {
				problemValue = null;
				disposeValue = null;
			}

			if (!StringUtil.isEmpty(checkNameValue)) {
				PubSccheckItemResult pubSccheckItemResult = new PubSccheckItemResult();
				String checkCode = "OTHER" + i;
				pubSccheckItemResult.setCheckCode(checkCode);
				pubSccheckItemResult.setCheckName(checkNameValue);
				pubSccheckItemResult.setCheckType(pubScentResultDto.getCheckType());
				pubSccheckItemResult.setDispose(disposeValue);
				pubSccheckItemResult.setPriPID(pubScentResultDto.getPriPID());
				pubSccheckItemResult.setProblem(problemValue);
				pubSccheckItemResult.setTaskUid(pubScentResultDto.getTaskUid());
				pubSccheckItemResultMapper.insert(pubSccheckItemResult);
			}
		}
	}

	/**
	 * 描述：组装需要保存的结果信息
	 * 
	 * @author Administrator
	 * @date 2017年5月18日
	 * @param map
	 * @throws ParseException
	 */
	private PubScentResultDto packPubScResultDto(Map<String, Object> map) throws ParseException {
		String checkType = (String) map.get("checkType");// 核查方式
		String checkResult = (String) map.get("checkResult");// 检查结果
		String disposeMss = (String) map.get("disposeMss");// 后续处置措施
		String disposeState = (String) map.get("disposeState");// 后续处置措施是否完结1未完结2已完结
		String disposeFinishDateStr = (String) map.get("disposeFinishDate");// 完结日期
		String checkDeptName = (String) map.get("checkDeptName");// 检查执行部门
		String checkDeptPerson = (String) map.get("checkDeptPerson");// 检查人员
		String checkDateStr = (String) map.get("checkDate");// 检查日期
		String disposeFinishMss = (String) map.get("disposeFinishMss");// 后续处置完结信息
		// String taskUid = (String) map.get("taskUid");// 任务序号
		// String priPID = (String) map.get("priPID");// 企业内部序号
		String uid = (String) map.get("uid");
		PubScentResultDto pubScentResultDto = queryPubScentResultDtoByUid(uid);
		if ("无须后置处置措施；".equals(disposeMss)) {
			pubScentResultDto.setDisposeState("2");
		} else {
			pubScentResultDto.setDisposeState(disposeState);
		}

		pubScentResultDto.setCheckType(checkType);
		pubScentResultDto.setCheckResult(checkResult);
		pubScentResultDto.setDisposeMss(disposeMss);
		pubScentResultDto.setAuditState("2");
		pubScentResultDto.setDisposeFinishMss(disposeFinishMss);
		// 检查结果录入后设置检查表为已设置的状态
		pubScentResultDto.setCheckTableFlag("1");
		if (!StringUtil.isEmpty(disposeFinishDateStr)) {
			Date disposeFinishDate = DateUtil.parseDate(disposeFinishDateStr, "yyyy-MM-dd");
			pubScentResultDto.setDisposeFinishDate(disposeFinishDate);
		}
		pubScentResultDto.setCheckDeptName(checkDeptName);
		pubScentResultDto.setCheckDeptPerson(checkDeptPerson);
		if (!StringUtil.isEmpty(checkDateStr)) {
			Date checkDate = DateUtil.parseDate(checkDateStr, "yyyy-MM-dd");
			pubScentResultDto.setCheckDate(checkDate);
		}
		return pubScentResultDto;
	}

	@Override
	@Transaction
	public int alterPubScentResult(Map<String, Object> map) throws Exception {
		// 检查结果
		PubScentResultDto pubScentResultDto = packPubScResultDto(map);
		int m = pubScentResultMapper.updateByPrimaryKeySelective(pubScentResultDto);
		if (m <= 0) {
			throw new Exception();
		}

		// 删除旧的检查事项结果
		pubSccheckItemResultMapper.deletePubSccheckItemResultByTaskUidAndPriPID(map);
		// 添加新的检查事项结果
		savePubSccheckItemResult(map, pubScentResultDto);
		return 1;
	}

	@Override
	@Transaction
	public int auditPubScentResult(Map<String, Object> map) throws Exception {
		// 检查结果
		PubScentResultDto pubScentResultDto = packPubScResultDtoToAudit(map);
		String auditState = (String) map.get("auditState");
		int m = pubScentResultMapper.updateByPrimaryKeySelective(pubScentResultDto);

		if ("5".equals(auditState)) {// 审核通过
			pubScdeptTaskService.updatePubScdeptTaskByEnt(pubScentResultDto.getTaskUid());
		}
		if (m <= 0) {
			throw new Exception();
		}

		// 删除旧的检查事项结果
		pubSccheckItemResultMapper.deletePubSccheckItemResultByTaskUidAndPriPID(map);
		// 添加新的检查事项结果
		savePubSccheckItemResult(map, pubScentResultDto);
		return 1;
	}

	/**
	 * 描述：组装需要保存的结果信息(审核功能使用)
	 * 
	 * @author baifangfang
	 * @date 2017年5月18日
	 * @param map
	 * @throws ParseException
	 */
	private PubScentResultDto packPubScResultDtoToAudit(Map<String, Object> map) throws ParseException {
		String checkType = (String) map.get("checkType");// 核查方式
		String checkResult = (String) map.get("checkResult");// 检查结果
		String disposeMss = (String) map.get("disposeMss");// 后续处置措施
		String disposeState = (String) map.get("disposeState");// 后续处置措施是否完结1未完结2已完结
		String disposeFinishDateStr = (String) map.get("disposeFinishDate");// 完结日期
		String checkDeptName = (String) map.get("checkDeptName");// 检查执行部门
		String checkDeptPerson = (String) map.get("checkDeptPerson");// 检查人员
		String checkDateStr = (String) map.get("checkDate");// 检查日期
		// String taskUid = (String) map.get("taskUid");// 任务序号
		String auditState = (String) map.get("auditState");// 审核结果
		String auditUserName = (String) map.get("auditUserName");// 审核员
		String auditOpinion = (String) map.get("auditOpinion");// 审核意见
		String auditDateStr = (String) map.get("auditDate");// 审核日期
		String disposeFinishMss = (String) map.get("disposeFinishMss");// 后续处置完结信息
		// String priPID = (String) map.get("priPID");// 企业内部序号
		if ("无须后置处置措施；".equals(disposeMss)) {
			map.put("disposeState", 2);
		}
		String uid = (String) map.get("uid");
		PubScentResultDto pubScentResultDto = queryPubScentResultDtoByUid(uid);
		pubScentResultDto.setCheckType(checkType);
		pubScentResultDto.setCheckResult(checkResult);
		pubScentResultDto.setDisposeMss(disposeMss);
		pubScentResultDto.setDisposeState(disposeState);
		pubScentResultDto.setAuditState(auditState);
		if (!StringUtil.isEmpty(disposeFinishDateStr)) {
			Date disposeFinishDate = DateUtil.parseDate(disposeFinishDateStr, "yyyy-MM-dd");
			pubScentResultDto.setDisposeFinishDate(disposeFinishDate);
		}
		pubScentResultDto.setCheckDeptName(checkDeptName);
		pubScentResultDto.setCheckDeptPerson(checkDeptPerson);
		pubScentResultDto.setDisposeFinishMss(disposeFinishMss);

		if (!StringUtil.isEmpty(checkDateStr)) {
			Date checkDate = DateUtil.parseDate(checkDateStr, "yyyy-MM-dd");
			pubScentResultDto.setCheckDate(checkDate);
		}
		if (!StringUtil.isEmpty(auditDateStr)) {
			Date auditDate = DateUtil.parseDate(auditDateStr, "yyyy-MM-dd");
			pubScentResultDto.setAuditDate(auditDate);
		}
		pubScentResultDto.setAuditOpinion(auditOpinion);
		pubScentResultDto.setAuditUserName(auditUserName);
		return pubScentResultDto;
	}

	/**
	 * 描述：插入结果表记录
	 * 
	 * @author chenxin
	 * @date 2017年5月20日
	 * @param pubScentResult
	 * @return
	 */
	@Override
	public void savePubScentResult(PubScentResult pubScentResult) {
		if (pubScentResult != null) {
			pubScentResultMapper.insertSelective(pubScentResult);
		}
	}

	/**
	 * 描述：查看是否本次任务涉及的所有企业都已经录入结果并且公示
	 * 
	 * @author chenxin
	 * @date 2017年5月27日
	 * @param deptTaskUid
	 * @return
	 */
	@Override
	public int seleteUnfinishedEnt(String deptTaskUid) {
		Example example = new Example(PubScentResult.class);
		example.createCriteria().andEqualTo("taskUid", deptTaskUid).andNotEqualTo("auditState", "5");
		return pubScentResultMapper.selectCountByExample(example);
	}

	@Override
	public List<PubScentResultDto> queryPageResultPub(PageRequest request) {
		PageHelper.startPage(request.getPageNum(), request.getLength());
		return pubScentResultMapper.queryPageResultByPriPID(request.getParams());
	}

	@Override
	public PubScentResultDto queryPubScentResultDtoByUid(String uid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		return pubScentResultMapper.selectPubScentResultDtoByUid(params);
	}

	@Override
	public List<PubScentResultDto> queryPreParePageResult(PageRequest request, SysUserDto sysUserDto) {
		Map<String, Object> params = request.getParams();
		// String deptCode = "2".equals(sysUserDto.getUserType()) ?
		// sysUserDto.getSysDepart().getAdcode()
		// : sysUserDto.getDepartMent().getDeptCode();
		// if(deptCode.length() > 8){
		// deptCode = deptCode.substring(0, 8);
		// }else if (deptCode.length() == 6){
		// deptCode = deptCode+"00";
		// }
		// params.put("unitDeptCode", deptCode);

		String deptCode = "2".equals(sysUserDto.getUserType()) ? sysUserDto.getSysDepart().getAdcode()
				: sysUserDto.getDepartMent().getDeptCode();
		String userid = sysUserDto.getId();
		deptCode = deptCode.substring(0, 6);
		params.put("userId", userid);
		params.put("deptCode", deptCode);
		String checkDeptPerson = (String) params.get("checkDeptPerson");
		if (!StringUtil.isEmpty(checkDeptPerson)) {
			params.put("checkDeptPersons", checkDeptPerson.split(","));
		}
		PageHelper.startPage(request.getPageNum(), request.getLength());
		return pubScentResultMapper.queryPreParePageResult(params);
	}

	@Override
	public int supplyPubScentResult(Map<String, Object> map) throws ParseException {
		String disposeState = (String) map.get("disposeState");// 后续处置措施是否完结1未完结2已完结
		String disposeFinishDateStr = (String) map.get("disposeFinishDate");// 完结日期
		String disposeFinishMss = (String) map.get("disposeFinishMss");// 后续处置完结信息
		String uid = (String) map.get("uid");
		PubScentResultDto pubScentResultDto = queryPubScentResultDtoByUid(uid);
		pubScentResultDto.setDisposeState(disposeState);
		pubScentResultDto.setDisposeFinishMss(disposeFinishMss);
		if (!StringUtil.isEmpty(disposeFinishDateStr)) {
			Date disposeFinishDate = DateUtil.parseDate(disposeFinishDateStr, "yyyy-MM-dd");
			pubScentResultDto.setDisposeFinishDate(disposeFinishDate);
		}
		int m = pubScentResultMapper.updateByPrimaryKeySelective(pubScentResultDto);
		return m;
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
	@Override
	public List<PubScentResultDto> selectScentResultCount(PageRequest request) throws Exception {
		Map<String, Object> parmMap = request.getParams();
		if (parmMap != null) {
			StringUtil.paramTrim(parmMap);
		}
		PageHelper.startPage(request.getPageNum(), 10000);
		return pubScentResultMapper.selectScentResultCount(parmMap);
	}

	/**
	 * 描述: 更新
	 * 
	 * @auther chenxin
	 * @date 2017年6月26日
	 * @param pubScentResult
	 */
	@Override
	public void updatePubScentResultByUid(PubScentResult pubScentResult) {
		Example example = new Example(PubScentResult.class);
		example.createCriteria().andEqualTo("uid", pubScentResult.getUid());
		pubScentResultMapper.updateByExampleSelective(pubScentResult, example);
	}

	/**
	 * 描述: 指派检查机关
	 * 
	 * @auther chenxin
	 * @date 2017年6月26日
	 * @param oldDeptTaskUid
	 * @param priPID
	 * @param newDeptTaskUid
	 * @param deptState
	 */
	@Override
	public void appointEntResult(String oldDeptTaskUid, String priPID, String newDeptTaskUid,String scentUid) throws Exception {
		Example example = new Example(PubScentResult.class);
		example.createCriteria().andEqualTo("taskUid", oldDeptTaskUid).andEqualTo("priPID", priPID).andEqualTo("scentUid", scentUid);
		PubScentResult pubScentResult = new PubScentResult();
		pubScentResult.setTaskUid(newDeptTaskUid);
		pubScentResultMapper.updateByExampleSelective(pubScentResult, example);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskUid", newDeptTaskUid);
		params.put("priPID", priPID);
		params.put("scentUid", scentUid);
		pubScentResultMapper.doUpdateDefaultNull(params);
	}

	/**
	 * 描述: 删除
	 * 
	 * @auther chenxin
	 * @date 2017年6月26日
	 * @param taskUid
	 * @param priPID
	 * @throws Exception
	 */
	@Override
	public void deleteByTaskUidAndPriPID(String taskUid, String priPID,String scentUid) throws Exception {
		Example example = new Example(PubScentResult.class);
		example.createCriteria().andEqualTo("taskUid", taskUid).andEqualTo("priPID", priPID).andEqualTo("scentUid", scentUid);
		pubScentResultMapper.deleteByExample(example);
	}
	
	/**
	 * 描述: 删除
	 * @auther chenxin
	 * @date 2017年6月26日
	 * @param taskUid
	 * @param priPID
	 * @throws Exception
	 */
	@Override
	public void deleteByTaskUid(String deptTaskUid) throws Exception {
		Example example = new Example(PubScentResult.class);
		example.createCriteria().andEqualTo("taskUid", deptTaskUid);
		pubScentResultMapper.deleteByExample(example);
	}
}