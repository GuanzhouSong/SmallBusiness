/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.sccheck.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icinfo.cs.common.utils.StringUtil;
import com.icinfo.cs.drcheck.dto.pubScSpecialLibraryDto;
import com.icinfo.cs.sccheck.dto.PubScentDto;
import com.icinfo.cs.sccheck.mapper.PubScentMapper;
import com.icinfo.cs.sccheck.model.PubScdeptTask;
import com.icinfo.cs.sccheck.model.PubScent;
import com.icinfo.cs.sccheck.model.PubScentBack;
import com.icinfo.cs.sccheck.model.PubScentResult;
import com.icinfo.cs.sccheck.model.PubScentSpecial;
import com.icinfo.cs.sccheck.service.IPubSccheckItemResultService;
import com.icinfo.cs.sccheck.service.IPubScdeptTaskService;
import com.icinfo.cs.sccheck.service.IPubScentAgentService;
import com.icinfo.cs.sccheck.service.IPubScentResultService;
import com.icinfo.cs.sccheck.service.IPubScentService;
import com.icinfo.cs.system.dto.SysUserDto;
import com.icinfo.framework.core.service.support.MyBatisServiceSupport;
import com.icinfo.framework.mybatis.mapper.entity.Example;
import com.icinfo.framework.mybatis.pagehelper.PageHelper;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageRequest;

/**
 * 描述:    cs_pub_scent 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2017年05月17日
 */
@Service
public class PubScentServiceImpl extends MyBatisServiceSupport implements IPubScentService {
	
	@Autowired 
	private PubScentMapper pubScentMapper;
	
	@Autowired 
	private IPubScdeptTaskService pubScdeptTaskService;
	
	@Autowired 
	private IPubScentResultService pubScentResultService;
	
	@Autowired 
	private IPubScentAgentService pubScentAgentService;
	
	@Autowired 
	private IPubSccheckItemResultService pubSccheckItemResultService;
	
	/**
	 * 
	 * 描述: 通过任务id查询已抽取的企业
	 * @auther gaojinling
	 * @date 2017年5月20日 
	 * @param deptTaskUid
	 * @return
	 * @throws Exception
	 */
	public List<PubScent> selectPubSentByDeptTaskUId(String deptTaskUid) throws Exception{
		Example example = new Example(PubScent.class);
		example.createCriteria().andEqualTo("deptTaskUid", deptTaskUid); 
		return pubScentMapper.selectByExample(example);
	}
	
	/**
	 * 
	 * 描述: 通过部门任务id分页查询已抽取的企业
	 * @auther gaojinling
	 * @date 2017年5月20日 
	 * @param request
	 * @return
	 */
	public List<PubScentDto> selectPubScentDtoList(PageRequest request)  throws Exception {
		Map<String, Object> searchMap =  request.getParams();
		if(searchMap.containsKey("pubTaskUid")){
			int pageSize = request.getLength();
			searchMap.put("numStart", (request.getPageNum() - 1) * pageSize);
			searchMap.put("pageSize", pageSize);
		}else{
			PageHelper.startPage(request.getPageNum(), request.getLength());
		}
		return pubScentMapper.selectPubScentDtoList(searchMap);
	}
	
	/**
	 * 描述：通过任务UID查询个数
	 * @author yujingwei
	 * @date 2017-07-22
	 * @param taskUid
	 * @return Integer
	 */
	public Integer selectPubScentTotalForBulletin(PageRequest request) throws Exception{
		return pubScentMapper.selectPubScentDtoTotal(request.getParams());
	}
	
	/**
	 * 
	 * 描述: 保存抽取结果
	 * @auther chenxin
	 * @date 2017年5月20日 
	 * @param pubScent
	 * @return
	 */
	@Override
	public void savePubScent(PubScentBack pubScentBack) throws Exception {
		if(pubScentBack != null){
			PubScent pubScent = new PubScent();
			pubScent.setEntName(pubScentBack.getEntName());
			pubScent.setPriPID(pubScentBack.getPriPID());
			pubScent.setRegNO(pubScentBack.getRegNO());
			pubScent.setUniCode(pubScentBack.getUniCode());
			pubScent.setRegState(pubScentBack.getRegState());
			pubScent.setRegOrg(pubScentBack.getRegOrg());
			pubScent.setRegOrgName(pubScentBack.getRegOrgName());
			pubScent.setEntTypeCatg(pubScentBack.getEntTypeCatg());
			pubScent.setLocalAdm(pubScentBack.getLocalAdm());
			pubScent.setLocalAdmName(pubScentBack.getLocalAdmName());
			pubScent.setSpecialCode("multiple");
			pubScent.setSpecialName("主体综合库");
			pubScent.setEstDate(pubScentBack.getEstDate());
			pubScent.setTaskUid(pubScentBack.getTaskUid());
			pubScent.setRelateUserUid(pubScentBack.getRelateUserUid());
			pubScentMapper.insertSelective(pubScent);
		}
	}
	
	/**
	 * 
	 * 描述: 删除抽检结果
	 * @auther chenxin
	 * @date 2017年5月20日 
	 * @param pubScent
	 * @return
	 */
	@Override
	public void delPubScent(String taskUid) throws Exception {
		Example example = new Example(PubScent.class);
		example.createCriteria().andEqualTo("taskUid", taskUid); 
		pubScentMapper.deleteByExample(example);
	}
	
	/**
	 * 
	 * 描述: 企业分配给具体的部门
	 * @auther chenxin
	 * @date 2017年5月20日  
	 * @param taskUid
	 * @param deptTaskUid
	 * @param string
	 */
	@Override
	public void updatePubScent(String taskUid, String deptTaskUid, String specialCode) {
		if(StringUtils.isNotEmpty(taskUid) && StringUtils.isNotEmpty(deptTaskUid) && StringUtils.isNotEmpty(specialCode)){
			Example example = new Example(PubScent.class);
			example.createCriteria().andEqualTo("taskUid", taskUid).andEqualTo("specialCode", specialCode); 
			PubScent pubScent = new PubScent();
			pubScent.setDeptTaskUid(deptTaskUid);
			pubScentMapper.updateByExampleSelective(pubScent,example);
		}
	}
	
	/**
	 * 
	 * 描述: 企业随机分配
	 * @auther chenxin
	 * @date 2017年5月20日  
	 * @param taskUid
	 * @param deptTaskUid
	 * @param specialCode
	 * @param regOrg
	 * 
	 */
	@Override
	public void updatePubScent(String taskUid, String deptTaskUid,String specialCode, String regOrg) {
		if(StringUtils.isNotEmpty(taskUid) && StringUtils.isNotEmpty(deptTaskUid) && StringUtils.isNotEmpty(specialCode)&& StringUtils.isNotEmpty(regOrg)){
			Example example = new Example(PubScent.class);
			example.createCriteria().andEqualTo("taskUid", taskUid).andEqualTo("specialCode", specialCode).andEqualTo("regOrg", regOrg); 
			PubScent pubScent = new PubScent();
			pubScent.setDeptTaskUid(deptTaskUid);
			pubScentMapper.updateByExampleSelective(pubScent,example);
		}
		
	}
	
	/**
	 * 
	 * 描述: 企业随机分配
	 * @auther chenxin
	 * @date 2017年5月20日  
	 * @param taskUid
	 * @param deptTaskUid
	 * @param string
	 */
	@Override
	public void updatePubScentByRelateUid(String taskUid, String deptTaskUid,String relateUserUid) {
		Example example = new Example(PubScent.class);
		List<String> specialCodes = new ArrayList<String>();
		specialCodes.add("A01");
		specialCodes.add("multiple");
		example.createCriteria().andEqualTo("taskUid", taskUid)
		.andEqualTo("relateUserUid", relateUserUid)
		.andNotIn("specialCode", specialCodes); 
		PubScent pubScent = new PubScent();
		pubScent.setDeptTaskUid(deptTaskUid);
		pubScentMapper.updateByExampleSelective(pubScent,example);
	}
	
	/**
	 * 描述: 查询抽检结果中的某个专项库管辖单位
	 * @auther chenxin
	 * @date 2017年5月20日
	 * @param taskUid
	 * @param specialCode
	 * @return
	 */
	@Override
	public List<String> selectRegOrgsInSpecial(String taskUid,String specialCode)throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("taskUid", taskUid);
		params.put("specialCode", specialCode);
		return pubScentMapper.selectRegOrgsInSpecial(params);
	}
	
	/**
	 * 描述: 查询抽中企业的企业类型
	 * @auther chenxin
	 * @date 2017年5月20日
	 * @param taskUid
	 * @param specialCode
	 * @return
	 */
	@Override
	public List<String> selectEntTypeCatg(String taskUid) throws Exception {
		return pubScentMapper.selectEntTypeCatg(taskUid);
	}
	
	/**
	 * 描述: 查询抽检结果中的某个专项库的登记机关数量
	 * @auther chenxin
	 * @date 2017年5月20日
	 * @param taskUid
	 * @param specialCode
	 * @return
	 */
	@Override
	public List<PubScentDto> selectRegOrgNumInSpecial(String taskUid,String specialCode) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("taskUid", taskUid);
		params.put("specialCode", specialCode);
		return pubScentMapper.selectRegOrgNumInSpecial(params);
	}
	
	/**
	 * 描述: 查询抽检结果中的某个专项库的监管用户
	 * @auther chenxin
	 * @date 2017年5月20日
	 * @param taskUid
	 * @param specialCode
	 * @return
	 */
	@Override
	public List<PubScentDto> selectUidsNotInSpecial(String taskUid, String[] specialCodes)throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("taskUid", taskUid);
		params.put("specialCodes", specialCodes);
		return pubScentMapper.selectUidsNotInSpecial(params);
	}
	
	/**
	 * 
	 * 描述: 保存抽取结果
	 * @auther chenxin
	 * @date 2017年5月20日 
	 * @param pubScent
	 * @return
	 */
	@Override
	public void savePubScent(PubScentSpecial pubScentSpecial) {
		if(pubScentSpecial != null){
			PubScent pubScent = new PubScent();
			pubScent.setEntName(pubScentSpecial.getEntName());
			pubScent.setPriPID(pubScentSpecial.getPriPID());
			pubScent.setRegNO(pubScentSpecial.getRegNO());
			pubScent.setUniCode(pubScentSpecial.getUniCode());
			pubScent.setRegState(pubScentSpecial.getRegState());
			pubScent.setRegOrg(pubScentSpecial.getRegOrg());
			pubScent.setRegOrgName(pubScentSpecial.getRegOrgName());
			pubScent.setEntTypeCatg(pubScentSpecial.getEntTypeCatg());
			pubScent.setLocalAdm(pubScentSpecial.getLocalAdm());
			pubScent.setLocalAdmName(pubScentSpecial.getLocalAdmName());
			pubScent.setSpecialCode(pubScentSpecial.getSpecialCode());
			pubScent.setSpecialName(pubScentSpecial.getSpecialName());
			pubScent.setEstDate(pubScentSpecial.getEstDate());
			pubScent.setTaskUid(pubScentSpecial.getTaskUid());
			pubScent.setRelateUserUid(pubScentSpecial.getRelateUserUid());
			pubScentMapper.insertSelective(pubScent);
		}
	}
	
	/**
	 * 
	 * 描述: 保存抽取结果
	 * @auther chenxin
	 * @date 2017年5月20日 
	 * @param pubScent
	 * @return
	 */
	@Override
	public void savePubScent(pubScSpecialLibraryDto pubScSpecialLibraryDto,String taskUid) {
		if(pubScSpecialLibraryDto != null){
			PubScent pubScent = new PubScent();
			pubScent.setEntName(pubScSpecialLibraryDto.getEntName());
			pubScent.setPriPID(pubScSpecialLibraryDto.getPriPID());
			pubScent.setRegNO(pubScSpecialLibraryDto.getRegNO());
			pubScent.setUniCode(pubScSpecialLibraryDto.getUniCode());
			pubScent.setRegState(pubScSpecialLibraryDto.getRegState());
			pubScent.setRegOrg(pubScSpecialLibraryDto.getRegOrg());
			pubScent.setRegOrgName(pubScSpecialLibraryDto.getRegOrgName());
			pubScent.setEntTypeCatg(pubScSpecialLibraryDto.getEntType());
			pubScent.setLocalAdm(pubScSpecialLibraryDto.getLocalAdm());
			pubScent.setLocalAdmName(pubScSpecialLibraryDto.getLocalAdmName());
			pubScent.setSpecialCode(pubScSpecialLibraryDto.getSpecialCode());
			pubScent.setSpecialName(pubScSpecialLibraryDto.getSpecialName());
			pubScent.setEstDate(pubScSpecialLibraryDto.getEstDate());
			pubScent.setTaskUid(taskUid);
			pubScent.setRelateUserUid(pubScSpecialLibraryDto.getSetUserUid());
			pubScentMapper.insertSelective(pubScent);
		}
	}
	
	/**
	 * 
	 * 描述: 通过任务id查询已抽取的企业各地市数量
	 * @auther chenxin
	 * @date 2017年5月20日 
	 * @param deptTaskUid
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PubScentDto> selectPubSentDtoListBytaskUId(String taskUid)throws Exception {
		List<PubScentDto> pubScentDtoList =  pubScentMapper.selectPubSentDtoListBytaskUId(taskUid);
		List<PubScentDto> pubScentDtoOrderList = orderBy(pubScentDtoList);
		return pubScentDtoOrderList;
	}
	
	/**
	 * 描述：生成地图数据
	 * @auther chenxin
	 * @date 2017年5月20日 
	 * @param pubScentDtoList
	 * @return
	 */
	private List<PubScentDto> orderBy(List<PubScentDto> pubScentDtoList) {
		List<PubScentDto> pubScentDtoOrderList = new ArrayList<PubScentDto>();
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(PubScentDto pubScentDto : pubScentDtoList){
			if(StringUtils.isNotEmpty(pubScentDto.getRegOrg())){
				map.put(pubScentDto.getRegOrg(), pubScentDto.getSpecialNum());
			}
		}
		PubScentDto pubScentDto = getPubScentDto("3300", map);
		pubScentDtoOrderList.add(pubScentDto);
		pubScentDto = getPubScentDto("3305", map);
		pubScentDtoOrderList.add(pubScentDto);
		pubScentDto = getPubScentDto("3301", map);
		pubScentDtoOrderList.add(pubScentDto);
		pubScentDto = getPubScentDto("3307", map);
		pubScentDtoOrderList.add(pubScentDto);
		pubScentDto = getPubScentDto("3308", map);
		pubScentDtoOrderList.add(pubScentDto);
		pubScentDto = getPubScentDto("3325", map);
		pubScentDtoOrderList.add(pubScentDto);
		pubScentDto = getPubScentDto("3304", map);
		pubScentDtoOrderList.add(pubScentDto);
		pubScentDto = getPubScentDto("3306", map);
		pubScentDtoOrderList.add(pubScentDto);
		pubScentDto = getPubScentDto("3309", map);
		pubScentDtoOrderList.add(pubScentDto);
		pubScentDto = getPubScentDto("3302", map);
		pubScentDtoOrderList.add(pubScentDto);
		pubScentDto = getPubScentDto("3310", map);
		pubScentDtoOrderList.add(pubScentDto);
		pubScentDto = getPubScentDto("3303", map);
		pubScentDtoOrderList.add(pubScentDto);
		return pubScentDtoOrderList;
	}
	
	/**
	 * 描述:按照页面排序生成数据
	 * @auther chenxin
	 * @date 2017年5月20日 
	 * @param regOrg
	 * @param map
	 * @return
	 */
	private PubScentDto getPubScentDto(String regOrg,Map<String,Integer> map){
		PubScentDto pubScentDto = new PubScentDto();
		pubScentDto.setRegOrg(regOrg);
		int num = 0;
		if(map.get(regOrg) != null && map.get(regOrg) > 0){
			num = map.get(regOrg);
		}
		pubScentDto.setSpecialNum(num);
		return pubScentDto;
	}

	/**
	 * 
	 * 描述: 通过查询已抽取的企业进行滚动
	 * @auther chenxin
	 * @date 2017年5月20日 
	 * @param deptTaskUid
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PubScent> selectPubSentListBytaskUId(String taskUid)throws Exception {
		return pubScentMapper.selectPubSentListBytaskUId(taskUid);
	}
	
	/**
	 * 描述：只用来处理人工分配抽查任务
	 * @param taskUid
	 * @param entName
	 * @return
	 */
	@Override
	public PubScent selectEntByEntNameTaskUid(String taskUid, String entName) {
		Example example = new Example(PubScent.class);
		example.createCriteria().andEqualTo("taskUid", taskUid).andEqualTo("entName", entName); 
		return pubScentMapper.selectByExample(example).get(0);
	}
	
	/**
	 * 描述：任务指派列表
	 *
	 * @author chenxin
	 * @date 2017年7月4日
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PubScentDto> queryPageResult(PageRequest request,SysUserDto sysUserDto)throws Exception {
		PageHelper.startPage(request.getPageNum(), request.getLength());
		Map<String, Object> params = request.getParams();
//		String deptCode = "2".equals(sysUserDto.getUserType()) ? sysUserDto.getSysDepart().getAdcode()
//				: sysUserDto.getDepartMent().getDeptCode();
//		if(deptCode.length() > 8){
//			deptCode = deptCode.substring(0, 8);
//		}else if (deptCode.length() == 6){
//			deptCode = deptCode+"00";
//		}
//		params.put("unitDeptCode", deptCode);
		if(params != null){
			StringUtil.paramTrim(params);
		}
		return pubScentMapper.selectAppointEntList(params);
	}
	
	/**
	 * 描述：查询已企业列表
	 *
	 * @author chenxin
	 * @date 2017年7月4日
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PubScentDto> queryEntPage(PageRequest request) throws Exception {
		PageHelper.startPage(request.getPageNum(), request.getLength());
		return pubScentMapper.selectRandomEntList(request.getParams());
	}
	
	/**
	 * 描述：任务指派
	 *
	 * @author chenxin
	 * @date 2017年7月4日 
	 * @param pubScentDto
	 * @param sysUserDto
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean doAppointEnts(String uids,String appointLocalAdm, SysUserDto sysUserDto)throws Exception {
		if(StringUtils.isNotEmpty(uids) && StringUtils.isNotEmpty(appointLocalAdm)){
			String[] uidArr = uids.split(",");
			for(String uid : uidArr){
				doAppointEnt(uid,appointLocalAdm,sysUserDto);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 描述：任务指派
	 *
	 * @author chenxin
	 * @date 2017年7月4日 
	 * @param uid
	 * @param appointLocalAdm
	 * @param sysUserDto
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean doAppointEnt(String uid,String appointLocalAdm,SysUserDto sysUserDto)throws Exception {
		//1.先获取当前企业抽取信息
		PubScent oldPubScent = selectPubScentByUid(uid);
		if(oldPubScent != null){
			/** 老任务处理 */
			//2.查询企业对应的(老的)抽查任务的信息
			String oldDeptTaskUid = oldPubScent.getDeptTaskUid();//检查部门uid
			PubScdeptTask oldPst = pubScdeptTaskService.selectPubScdeptTaskByUid(oldDeptTaskUid);
			//先判断是否调整后的机关和调整前一样,如果一样则无需调整
			if(StringUtils.isEmpty(appointLocalAdm) || oldPst.getUnitDeptCode().equals(appointLocalAdm)){
				return false;
			}
			int oldEntNum = oldPst.getEntNum();
			if(oldEntNum > 1){
				//3.更新任务表检查企业数量(老的)
				oldEntNum = oldEntNum - 1 ;
				oldPst.setEntNum(oldEntNum);
				pubScdeptTaskService.updatePubScdeptTask(oldPst);
			}else if(oldEntNum == 1){
				//4.删除当前任务(老的)
				pubScdeptTaskService.deleteByUid(oldDeptTaskUid);
			}else{
				return false;
			}
			
			/** 新任务处理  */
			//5.根据taskuid和appointLocalAdm查询是否已经有任务存在。
			String taskUid = oldPubScent.getTaskUid();
			String priPID = oldPubScent.getPriPID();
			PubScdeptTask newPst = pubScdeptTaskService.selectByTaskUidAndUnitDeptCode(taskUid,appointLocalAdm);
			String newDeptTaskUid = "";
			String newDeptState = "";
			boolean existFlag = false;
			if(newPst == null){
				//如果不存在则新增任务
				PubScdeptTask params = new PubScdeptTask();
				params.setTaskUid(taskUid);
				params.setEntNum(1);
				params.setDeptCode(appointLocalAdm.substring(0,6));
				params.setUnitDeptCode(appointLocalAdm);
				params.setDeptState("1");
				pubScdeptTaskService.savePubScdeptTask(params);
				newDeptTaskUid = params.getUid();
				newDeptState = "1";
			}else{
				//【特殊的：如果新的任务已经存在企业那么不做修改】
				newDeptTaskUid = newPst.getUid();
				Example example = new Example(PubScent.class);
				example.createCriteria().andEqualTo("deptTaskUid", newDeptTaskUid).andEqualTo("priPID", priPID);
				int count = pubScentMapper.selectCountByExample(example);
				if(count > 0){
					existFlag = true;
					PubScent newPubScent = new PubScent();
					newPubScent.setAdjustTime(new Date());
					newPubScent.setAdjustUserName(sysUserDto.getRealName());
					newPubScent.setAdjustUserUid(sysUserDto.getId());
					pubScentMapper.updateByExampleSelective(newPubScent, example);
				}else{
					//如果存在则更新检查表企业数量
					int newEntNum = newPst.getEntNum();
					newEntNum = newEntNum + 1;
					newPst.setEntNum(newEntNum);
					//如果检查任务状态已经是完成状态，需要恢复任务状态为2
					if("3".equals(newPst.getDeptState())){
						newPst.setDeptState("2");
					}
					pubScdeptTaskService.updatePubScdeptTask(newPst);
				}
				newDeptState = newPst.getDeptState();
			}
			//6.删除当前企业对应的执法人员表信息(cs_pub_scent_agent)
			pubScentAgentService.deleteByTaskUidAndPriPID(oldDeptTaskUid,priPID);
			//7.更新cs_pub_scent表处理（deptTaskUid）调整人，调整时间【特殊的：如果新的任务已经存在该企业，那么直接删除企业】
			Example example = new Example(PubScent.class);
			example.createCriteria().andEqualTo("uid", oldPubScent.getUid());
			if(existFlag){
				pubScentMapper.deleteByExample(example);
			}else{
				PubScent newPubScent = new PubScent();
				newPubScent.setDeptTaskUid(newDeptTaskUid);
				newPubScent.setAdjustTime(new Date());
				newPubScent.setAdjustUserName(sysUserDto.getRealName());
				newPubScent.setAdjustUserUid(sysUserDto.getId());
				pubScentMapper.updateByExampleSelective(newPubScent, example);
			}
			//8.更新结果表指派给新的检查部门（taskUid）
			if("2".equals(oldPst.getDeptState())){
				//如果老的任务已经抽取过执法人员则做更新
				if("2".equals(newDeptState) && !existFlag){//【特殊的：如果新的任务已经存在企业，那么直接删除任务,否则更新】
					pubScentResultService.appointEntResult(oldDeptTaskUid,priPID,newDeptTaskUid,uid);
				}else{
					pubScentResultService.deleteByTaskUidAndPriPID(oldDeptTaskUid,priPID,uid);
				}
			}else if("1".equals(oldPst.getDeptState())){
				//如果没有抽取过，则判断是否新任务已经抽取过执法人员，新任务已抽取过执法人员则新增
				if("2".equals(newDeptState) && !existFlag){//【特殊的：如果新的任务已经存在企业，那么直不需要新增】
					PubScentResult pubScentResult = new PubScentResult();
					pubScentResult.setMainTaskUid(taskUid);
					pubScentResult.setTaskUid(newDeptTaskUid);
					pubScentResult.setPriPID(priPID);
					pubScentResult.setAuditState("1");
					pubScentResult.setScentUid(uid);;
					pubScentResultService.savePubScentResult(pubScentResult);
				}
			}
			//9.清空结果表检查事项记录
			pubSccheckItemResultService.deleteByTaskUidAndPriPID(oldDeptTaskUid,priPID);
			return true;
		}
		return false;
	}
	
	/**
	 * 描述：根据uid查询企业信息
	 *
	 * @author chenxin
	 * @date 2017年7月4日  
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@Override
	public PubScent selectPubScentByUid(String uid)throws Exception {
		PubScent pubScent = new PubScent();
		pubScent.setUid(uid);
		return pubScentMapper.selectOne(pubScent);
	}
	
	/**
	 * 描述：统计一次任务所有企业所属的状态
	 * @author chenxin
	 * @date 22017-07-22
	 * @param taskUid
	 * @return
	 */
	@Override
	public PubScentDto selectEntCountByTaskUid(String taskUid) {
		return pubScentMapper.selectEntCountByTaskUid(taskUid);
	}
}