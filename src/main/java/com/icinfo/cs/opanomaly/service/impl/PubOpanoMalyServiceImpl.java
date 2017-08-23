/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.opanomaly.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icinfo.cs.common.utils.DateUtil;
import com.icinfo.cs.common.utils.StringUtil;
import com.icinfo.cs.ext.dto.MidBaseInfoDto;
import com.icinfo.cs.ext.model.MidBaseInfo;
import com.icinfo.cs.ext.service.IMidBaseInfoService;
import com.icinfo.cs.opanomaly.dto.PubOpanoMalyDto;
import com.icinfo.cs.opanomaly.mapper.PubOpanoMalyMapper;
import com.icinfo.cs.opanomaly.model.PubOpanoMaly;
import com.icinfo.cs.opanomaly.service.IPubOpanoMalyService;
import com.icinfo.framework.core.exception.BusinessException;
import com.icinfo.framework.core.service.support.MyBatisServiceSupport;
import com.icinfo.framework.mybatis.mapper.entity.Example;
import com.icinfo.framework.mybatis.pagehelper.PageHelper;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageRequest;
import com.icinfo.framework.tools.utils.DateUtils;

/**
 * 描述:    cs_pub_opanomaly 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2016年10月11日
 */
@Service
public class PubOpanoMalyServiceImpl extends MyBatisServiceSupport implements IPubOpanoMalyService {
	private static final Logger logger = LoggerFactory.getLogger(PubOpanoMalyServiceImpl.class);

	@Autowired
	private PubOpanoMalyMapper pubOpanoMalyMapper; 
	@Autowired
	private IMidBaseInfoService midBaseInfoService;
	
	private final String CS_PUB_OPANOMALY_BUSEXCLIST="busExcList";
	
	/**
	 * 
	 * 描述   保存异常信息
	 * @author 赵祥江
	 * @date 2016年10月11日 上午10:02:32 
	 * @param 
	 * @return int
	 * @throws
	 */
	@Override
	public int insertPubOpanoMaly(PubOpanoMaly pubOpanoMaly) throws Exception {
		try {
			if(pubOpanoMaly!=null){
			  //时间戳
			  pubOpanoMaly.setCreateTime(DateUtils.getSysDate());
			  return pubOpanoMalyMapper.insert(pubOpanoMaly);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "保存异常信息失败!");
            throw new BusinessException("保存异常信息失败!");
		} 
		return 0;
	}

	/**
	 * 
	 * 描述   根据主键busExcList删除异常信息
	 * @author 赵祥江
	 * @date 2016年10月11日 上午10:04:30 
	 * @param 
	 * @return int
	 * @throws
	 */
	@Override
	public int deletePubOpanoMalyByBusExcList(String busExcList)
			throws Exception {
		try {
			if(StringUtil.isNotEmpty(busExcList)){
				PubOpanoMaly pubOpanoMaly=new PubOpanoMaly();
				pubOpanoMaly.setBusExcList(busExcList);
			    return pubOpanoMalyMapper.delete(pubOpanoMaly);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "根据主键busExcList删除异常信息失败!");
            throw new BusinessException("根据主键busExcList删除异常信息失败!");
		} 
		return 0;
	}

	 /**
     * 
     * 描述   修改
     * @author 赵祥江
     * @date 2016年10月11日 上午10:05:54 
     * @param 
     * @return int
     * @throws
     */
	@Override
	public int updatePubOpanoMaly(PubOpanoMaly pubOpanoMaly) throws Exception {
		try {
			if(pubOpanoMaly!=null&&StringUtil.isNotEmpty(pubOpanoMaly.getBusExcList())){
				//时间戳
				pubOpanoMaly.setCreateTime(DateUtils.getSysDate());
				Example example = new Example(PubOpanoMaly.class);
				example.createCriteria().andEqualTo(CS_PUB_OPANOMALY_BUSEXCLIST, pubOpanoMaly.getBusExcList());
			    return pubOpanoMalyMapper.updateByExampleSelective(pubOpanoMaly, example);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "根据主键busExcList修改异常信息失败!");
            throw new BusinessException("根据主键busExcList修改异常信息失败!");
		} 
		return 0;
	}

    /**
     * 
     * 描述   根据主键BusExcList查询异常对象
     * @author 赵祥江
     * @date 2016年10月11日 上午10:06:50 
     * @param 
     * @return PubOpanoMaly
     * @throws
     */
	@Override
	public PubOpanoMaly selectPubOpanoMalyByBusExcList(String busExcList)
			throws Exception {
		try {
			if(StringUtil.isNotEmpty(busExcList)){
				PubOpanoMaly pubOpanoMaly=new PubOpanoMaly();
				pubOpanoMaly.setBusExcList(busExcList);
				return pubOpanoMalyMapper.selectOne(pubOpanoMaly);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "根据主键BusExcList查询异常对象失败!");
            throw new BusinessException("根据主键BusExcList查询异常对象失败!");
		} 
		return null;
	}
	
	 /**
     * 
     * 描述   分页查询已经列入未撤销移出的异常列表
     * @author 赵祥江
     * @date 2016年10月11日 上午11:02:33 
     * @param 
     * @return List<PubOpanoMalyDto>
     * @throws
     */
	@Override
	public List<PubOpanoMalyDto> queryPubOpanoMalyList(PageRequest request) {
		try {
			Map<String, Object> searchMap=  request.getParams();
			int pageSize = request.getLength();
			searchMap.put("numStart", (request.getPageNum() - 1) * pageSize);
			searchMap.put("pageSize", pageSize);
 			return pubOpanoMalyMapper.selectPubOpanoMalyList(searchMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "经营状态查询失败!");
            throw new BusinessException("经营状态查询失败!");
		}
	}
	
	 /**
     * 
     * 描述   
     * @author ylr
     * @date 2016年10月11日 上午11:02:33 
     * @param 
     * @return List<PubOpanoMalyDto>
     * @throws
     */
    public Integer getPubOpanoMalyTotal(Map<String, Object> qryMap)throws Exception{
    	List<PubOpanoMalyDto> list = pubOpanoMalyMapper.selectPubOpanoMalyList(qryMap);
    	return list ==null?0:list.size();
    }

	/**
	 * 
	 * 描述   根据传入的类型查询 EntTypeFlag为0查询农专 EntTypeFlag为1查询企业
	 * @author  赵祥江
	 * @date 2016年10月26日 下午2:46:05 
	 * @param  
	 * @throws
	 */
	@Override
	public List<MidBaseInfoDto> queryMidBasePubOpanoMalyList(PageRequest request)
			throws Exception {
		try {
			Map<String, Object> searchMap=  request.getParams();
			PageHelper.startPage(request.getPageNum(), request.getLength());
			return pubOpanoMalyMapper.selectMidBasePubOpanoMalyList(searchMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "根据传入的类型查询 EntTypeFlag为0查询农专 EntTypeFlag为1查询企业失败!");
            throw new BusinessException("根据传入的类型查询 EntTypeFlag为0查询农专 EntTypeFlag为1查询企业失败!");
		}
	}

	   /**
     * 
     * 描述   根据登记机关获取文号数字
     * @author 赵祥江
     * @date 2016年10月13日 下午7:06:56 
     * @param 
     * @return BigDecimal
     * @throws
     */
	@Override
	public Integer selectPenDecNoByRegOrg(String regOrg) throws Exception {
		Map<String, Object> queryMap=  new HashMap<String, Object>();
		queryMap.put("regOrg", regOrg);
		queryMap.put("year", DateUtils.getYear());
		return pubOpanoMalyMapper.selectPenDecNoByRegOrg(queryMap);
	} 
	
	/**
	 * 
	 * 描述   异常名录查询  
	 * @author 赵祥江
	 * @date 2016年10月26日 下午2:41:50 
	 * @param 
	 * @return List<PubOpanoMalyDto>
	 * @throws
	 */
	@Override
	public List<PubOpanoMalyDto> selectPubOpanoMalySearchList(
			PageRequest request) throws Exception {
		try {
			Map<String, Object> searchMap=  request.getParams();
			int pageSize = request.getLength();
			searchMap.put("numStart", (request.getPageNum() - 1) * pageSize);
			searchMap.put("pageSize", pageSize);
 			return pubOpanoMalyMapper.selectPubOpanoMalySearchList(searchMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "异常名录查询失败!");
            throw new BusinessException("异常名录查询 失败!");
		}
	}

	/**
	 * 
	 * 描述   统计已移出的企业多少家
	 * @author 赵祥江
	 * @date 2016年10月26日 下午4:22:03 
	 * @param 
	 * @return Integer
	 * @throws
	 */
	@Override
	public Integer selectIsInAndIsOutCount(Map<String, Object> queryMap) {
		try { 
			return pubOpanoMalyMapper.selectIsInAndIsOutCount(queryMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "统计已移出的企业多少家失败!");
            throw new BusinessException("统计已移出的企业多少家失败!");
		}
	}

	/**
	 * 
	 * 描述   统计列入未移出企业多少家
	 * @author 赵祥江
	 * @date 2016年10月26日 下午4:22:08 
	 * @param 
	 * @return Integer
	 * @throws
	 */
	@Override
	public Integer selectIsInAndNotOutCount(Map<String, Object> queryMap) {
		try { 
			return pubOpanoMalyMapper.selectIsInAndNotOutCount(queryMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "统计列入未移出企业多少家失败!");
            throw new BusinessException("统计列入未移出企业多少家失败!");
		}
	}

	/**
	 * 
	 * 描述   统计已公示企业多少家
	 * @author 赵祥江
	 * @date 2016年10月26日 下午4:22:13 
	 * @param 
	 * @return Integer
	 * @throws
	 */
	@Override
	public Integer selectIsPubCount(Map<String, Object> queryMap) {
		try { 
			return pubOpanoMalyMapper.selectIsPubCount(queryMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "统计已公示企业多少家失败!");
            throw new BusinessException("统计已公示企业多少家失败!");
		}
	}
	
	/**
	 * 
	 * 描述   根据公司id查询异常名录
	 * @author chenyu
	 * @date 2016年10月26日 下午2:41:50 
	 * @param 
	 * @return List<PubOpanoMalyDto>
	 * @throws
	 */
	@Override
	public List<PubOpanoMalyDto> selectPubOpanoMalyListBypriPID(PageRequest request){
		PageHelper.startPage(request.getPageNum(), request.getLength());
		Map<String, Object> searchMap = request.getParams();
		return pubOpanoMalyMapper.selectPubOpanoMalyListByPriPID(searchMap);
	}

	/** 
	 * 描述: 纳入/移出经营异常名录信息（公示单个企业展示）
	 * @auther ZhouYan
	 * @date 2016年11月3日 
	 * @param request
	 * @return 
	 */
	@Override
	public List<PubOpanoMalyDto> queryPubOpanoMalyListForPub(PageRequest request) throws Exception {
		PageHelper.startPage(request.getPageNum(), request.getLength());
		String priPID = request.getParams().get("priPID").toString();
		if(StringUtils.isNotBlank(priPID)) {
			return pubOpanoMalyMapper.selectPubOpanoMalyListForPub(request.getParams());
		}else{
			return new ArrayList<PubOpanoMalyDto>();
		}
	}

	@Override
	public String getEntForDatajoinJSON_downednum(Map param) {
		PubOpanoMalyDto dto = pubOpanoMalyMapper.selectPubOpanoMalyList_downnum(param);
		if(dto!=null)
		return dto.getDownState()==null?"0":dto.getDownState();
		return "0";
	}
	
	/** 
	 * 描述: 生成列入，列出公告数据
	 * @auther yujingwei
	 * @date 2016年12月8日 
	 * @param 
	 * @return List<PubOpanoMalyDto>
	 */
	public List<PubOpanoMalyDto> queryPubOpanoMalyListForNotice(String updateDate) throws Exception{
		return pubOpanoMalyMapper.queryPubOpanoMalyListForNotice(updateDate);
	}

	/**
	 * 数据接入的查询
	 * @param request
	 * @return
	 */
	@Override
	public List<PubOpanoMalyDto> selectPubOpanoMalyList_Fordatain(PageRequest request) {
		try {
			Map<String, Object> searchMap=  request.getParams();
			PageHelper.startPage(request.getPageNum(), request.getLength());
			return pubOpanoMalyMapper.selectPubOpanoMalyList_Fordatain(searchMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "经营状态查询失败!");
			throw new BusinessException("经营状态查询失败!");
		}
	}

	/**
     * 
     * 描述   异常查询 分页条数
     * @author 赵祥江
     * @date 2016年12月16日 上午9:22:03 
     * @param 
     * @return Integer
     * @throws
     */
	@Override
	public Integer selectPubOpanoMalySearchCount(Map<String,Object> queryMap)
			throws Exception {
		try {  
			return pubOpanoMalyMapper.selectPubOpanoMalySearchCount(queryMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "异常查询 分页条数失败!");
			throw new BusinessException("异常查询 分页条数失败!");
		}
	}

	 /**
     * 
     * 描述   列入申请和列入列表 查询分页条数
     * @author 赵祥江
     * @date 2016年12月16日 上午10:10:26 
     * @param 
     * @return Integer
     * @throws
     */
	@Override
	public Integer selectPubOpanoMalyListCount(Map<String, Object> queryMap)
			throws Exception {
		try {  
			return pubOpanoMalyMapper.selectPubOpanoMalyListCount(queryMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "异常查询 分页条数失败!");
			throw new BusinessException("异常查询 分页条数失败!");
		}
	}

	@Override
	public List<PubOpanoMaly> selectListByCerNO(PageRequest request) {
		PageHelper.startPage(request.getPageNum(), request.getLength());
		return pubOpanoMalyMapper.selectListByCerNO(request.getParams());
	}

	@Override
	public List<PubOpanoMalyDto> selectPubOpanoMalyServicePriPID(String priPID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("priPID", priPID);
		return pubOpanoMalyMapper.selectPubOpanoMalyServicePriPID(map);
	}
	
	 /**
     * 描述   经营异常查询（全景）
     * @author yujingwei
     * @date 2017年06月13日 
     * @param  request
     * @return List<PubOpanoMalyDto>
     * @throws Exception
     */
	public List<PubOpanoMalyDto> selectPubOpanoMalyNewSearchList(PageRequest request) throws Exception{
		try {
			Map<String, Object> searchMap=  request.getParams();
			int pageSize = request.getLength();
			searchMap.put("numStart", (request.getPageNum() - 1) * pageSize);
			searchMap.put("pageSize", pageSize);
			if(searchMap.get("regState") != null && !searchMap.get("regState").equals("")){
				searchMap.put("regStates", searchMap.get("regState").toString().split(","));
			}
			if(searchMap.get("industryCo") != null && !searchMap.get("industryCo").equals("")){
				searchMap.put("industryCos", searchMap.get("industryCo").toString().split(","));
			}
 			return pubOpanoMalyMapper.selectPubOpanoMalyNewSearchList(searchMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "异常名录查询失败!");
            throw new BusinessException("异常名录查询 失败!");
		}
	}
	
	/**
     * 描述   经营异常查询个数（全景）
     * @author yujingwei
     * @date 2017年06月13日 
     * @param  queryMap
     * @return Integer
     * @throws Exception
     */
	public Integer selectPubOpanoMalyNewSearchCount(Map<String, Object> queryMap) throws Exception{
		try {  
			return pubOpanoMalyMapper.selectPubOpanoMalyNewSearchCount(queryMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "异常查询 分页条数失败!");
			throw new BusinessException("异常查询 分页条数失败!");
		}
	}
	
	/**
     * 描述   经营异常记录查询（经营异常管理）
     * @author yujingwei
     * @date 2017年06月13日 
     * @param  request
     * @return List<PubOpanoMalyDto>
     * @throws Exception
     */
	public List<PubOpanoMalyDto> selectPubOpanoMalyNewSearchHisList(PageRequest request) throws Exception{
		try {
			Map<String, Object> searchMap=  request.getParams();
			int pageSize = request.getLength();
			searchMap.put("numStart", (request.getPageNum() - 1) * pageSize);
			searchMap.put("pageSize", pageSize);
			if(searchMap.get("regState") != null && !searchMap.get("regState").equals("")){
				searchMap.put("regStates", searchMap.get("regState").toString().split(","));
			}
			if(searchMap.get("industryCo") != null && !searchMap.get("industryCo").equals("")){
				searchMap.put("industryCos", searchMap.get("industryCo").toString().split(","));
			}
 			return pubOpanoMalyMapper.selectPubOpanoMalyNewSearchHisList(searchMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "异常名录查询失败!");
            throw new BusinessException("异常名录查询 失败!");
		}
	}
	
	/**
     * 描述   经营异常记录查询个数（经营异常管理）
     * @author yujingwei
     * @date 2017年06月13日 
     * @param  queryMap
     * @return Integer
     * @throws Exception
     */
	public Integer selectPubOpanoMalyNewSearchHisCount(Map<String, Object> queryMap) throws Exception{
		try {  
			return pubOpanoMalyMapper.selectPubOpanoMalyNewSearchHisCount(queryMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "异常查询 分页条数失败!");
			throw new BusinessException("异常查询 分页条数失败!");
		}
	}
	
	/**
	 * 描述: 经营异常名录查询统计（全景及管理模块）
	 * @auther yujingwei
	 * @date 2017年6月19日
	 * @return view
	 * @throws Exception
	 */
	public List<PubOpanoMalyDto> doGetOpanaMalyCountForSyn(Map<String, Object> parmMap) throws Exception{
		try {  
			if(parmMap.get("regState") != null && !parmMap.get("regState").equals("")){
				parmMap.put("regStates", parmMap.get("regState").toString().split(","));
			}
			return pubOpanoMalyMapper.selectOpanaMalyCountForSyn(parmMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception {}", "异常查询 统计失败!");
			throw new BusinessException("异常查询 统计失败!");
		}
	}

	/**
	 * 
	 * 描述: 批量列入列表查询
	 * @auther gaojinling
	 * @date 2017年6月13日 
	 * @param map
	 * @return
	 */
	public List<PubOpanoMalyDto> selectBatchInList(PageRequest request) throws Exception{
		PageHelper.startPage(request.getPageNum(), request.getLength());
		return pubOpanoMalyMapper.selectBatchInList(request.getParams());
	}
	
	/**
	 * 
	 * 描述: 批量列入（单页）
	 * @auther gaojinling
	 * @date 2017年6月16日 
	 * @return
	 * @throws Exception
	 */
	public int batchInsertIn(PubOpanoMaly pubOpanoMaly) throws Exception{
	   if(pubOpanoMaly != null && StringUtil.isNotBlank(pubOpanoMaly.getPriPID())){
		   int res = 0;
		   int count = 1;
		   String[] pripids = pubOpanoMaly.getPriPID().split(",");
		   String penDecNo = pubOpanoMaly.getPenDecNo();
		   for(String priPID : pripids){ //循环列入
		         //查询在册企业
		         MidBaseInfo midBaseInfo=midBaseInfoService.selectByPripid(priPID);
		         //若在册灭有  查询 所有状态企业
		         if(midBaseInfo == null ){
		        	 midBaseInfo = midBaseInfoService.selectMidBaseInfoByPripid(priPID) ;
 		         }
		           //设置文号
		           pubOpanoMaly.setPenDecNo(penDecNo+"-"+count);
				   pubOpanoMaly.setFirstdate(new Date());
				   pubOpanoMaly.setAuditDate(new Date());
				   pubOpanoMaly.setCreateTime(new Date());
				   //设置列入时间
				   pubOpanoMaly.setAbnTime(DateUtil.getAllDate(pubOpanoMaly.getAbnTime()));
				   //设置列入年份
				   pubOpanoMaly.setSeqYear(Integer.parseInt((DateUtil.getCurrentYear())));
				   pubOpanoMaly.setPriPID(priPID);
				   pubOpanoMaly.setEntName(midBaseInfo.getEntName());
				   pubOpanoMaly.setRegNO(midBaseInfo.getRegNO());
				   pubOpanoMaly.setUniSCID(midBaseInfo.getUniCode());
				   pubOpanoMaly.setLeRep(midBaseInfo.getLeRep());
				   pubOpanoMaly.setCerType("10");
				   pubOpanoMaly.setLocalAdm(midBaseInfo.getLocalAdm());
				   pubOpanoMaly.setEstDate(midBaseInfo.getEstDate());
				   pubOpanoMaly.setRegState(midBaseInfo.getRegState());
				   res = res + pubOpanoMalyMapper.insert(pubOpanoMaly);
				   count++;
		   }
           return res;
	   }
		return 0;
	}
	
	/**
	 * 
	 * 描述: 批量列入
	 * @auther gaojinling
	 * @date 2017年6月19日 
	 * @param map
	 * @throws Exception
	 */
	public void insertIntoPubopaNomalyInALl(Map<String, Object> map) throws Exception{
		pubOpanoMalyMapper.insertIntoPubopaNomalyInALl(map);
	}
	
	/**
	 * 
	 * 描述: 利用批列入文号查询该文号下的企业
	 * @auther gaojinling
	 * @date 2017年6月19日 
	 * @param penDecNo
	 * @return
	 * @throws Exception
	 */
	public List<PubOpanoMalyDto> selectBatchInListByPenDecNo(String penDecNo) throws Exception{
		if(StringUtil.isNotBlank(penDecNo)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("penDecNo", penDecNo);
			return pubOpanoMalyMapper.selectBatchInListByPenDecNo(map);
		}else{
			return new ArrayList<PubOpanoMalyDto>();
		}

	}
	
	/**
	 * 描述: 列入总数
	 * @auther yujingwei
	 * @date 2017年6月19日
	 * @return view
	 * @throws Exception
	 */
	public PubOpanoMalyDto selectOpanoMalyTotalAll(Map<String, Object> dataParmMap) throws Exception{
		if(dataParmMap.isEmpty()){
			return null;
		}else{
			dataParmMap.remove("busExcList");
			dataParmMap.remove("priPID");
			return pubOpanoMalyMapper.selectOpanoMalyTotalAll(dataParmMap);
		}
	}
    
	/**
	 * 描述: 未移出
	 * @auther yujingwei
	 * @date 2017年6月19日
	 * @return view
	 * @throws Exception
	 */
	public PubOpanoMalyDto selectOpanoMalyNoOutTotal(Map<String, Object> dataParmMap) throws Exception{
		if(dataParmMap.isEmpty()){
			return null;
		}else{
			dataParmMap.remove("busExcList");
			dataParmMap.remove("priPID");
			return pubOpanoMalyMapper.selectOpanoMalyNoOutTotal(dataParmMap);
		}
	}
     
	/**
	 * 描述: 移出
	 * @auther yujingwei
	 * @date 2017年6月19日
	 * @return view
	 * @throws Exception
	 */
	public PubOpanoMalyDto selectOpanoMalyOutTotal(Map<String, Object> dataParmMap) throws Exception{
		if(dataParmMap.isEmpty()){
			return null;
		}else{
			dataParmMap.remove("busExcList");
			dataParmMap.remove("priPID");
			return pubOpanoMalyMapper.selectOpanoMalyOutTotal(dataParmMap);
		}
	}
   
	/**
	 * 描述: 撤销
	 * @auther yujingwei
	 * @date 2017年6月19日
	 * @return view
	 * @throws Exception
	 */
	public PubOpanoMalyDto selectOpanoMalyRevokeTotal(Map<String, Object> dataParmMap) throws Exception{
		if(dataParmMap.isEmpty()){
			return null;
		}else{
			dataParmMap.remove("busExcList");
			dataParmMap.remove("priPID");
			return pubOpanoMalyMapper.selectOpanoMalyRevokeTotal(dataParmMap);
		}
	}

	/**
	 * 描述：查询所有数据
	 * @return
     */
	public List<PubOpanoMaly> selectFindAll(){
		return pubOpanoMalyMapper.selectAll();
	}
	
	
	/**
	 * 
	 * 描述   根据主体身份代码查询已经审核通过，不管是否移出，但不包括撤销
	 * @author  赵祥江
	 * @date 2017年7月5日 下午8:07:46 
	 * @param  
	 * @throws
	 */
	@Override
	public List<PubOpanoMaly> selectOpanoMalyByPripId(String pripId)
			throws Exception {
		Example example = new Example(PubOpanoMaly.class);
		example.createCriteria().andEqualTo("auditState", "1")
		.andEqualTo("priPID", pripId)
		.andIsNull("revokeFlag"); 
		return pubOpanoMalyMapper.selectByExample(example);
	}
}