/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.icinfo.cs.opanomaly.service.IPubOpaDetailService;
import com.icinfo.cs.pbapp.service.impl.TokenManager;
import com.icinfo.cs.system.service.IRegIndexService;
import com.icinfo.cs.yr.service.IModApplicationService;
import com.icinfo.cs.yr.service.IYrRegCheckService;
import com.icinfo.framework.core.service.support.MyBatisServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 描述:    cs_bulletins_read_record 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2016年11月21日
 */
@Service
public class RegIndexServiceImpl extends MyBatisServiceSupport implements IRegIndexService {

    @Autowired
    private IYrRegCheckService yrRegCheckService;
    @Autowired
    private IModApplicationService modApplicationService;
    @Autowired
    private IPubOpaDetailService pubOpaDetailService;
    @Autowired
    private TokenManager tokenManager;
    
    /**
     * 
     * 描述:首先获取当前用户redis缓存中的首页统计项
     * @auther gaojinling
     * @date 2017年3月21日 
     * @param userid
     * @param forBidmap（主要为添加操作权限字段）
     * @return
     * @throws Exception
     */
    public List<Integer> getRegCountList(String userid ,Map<String, Object> forBidmap) throws Exception{
    	List<Integer> countList = new ArrayList<Integer>();	
        List<Integer> temList= tokenManager.getRegCountList(userid);
        if(temList != null && temList.size()>0){ //警示端统计缓存存在，从缓存中取值
        	countList = temList;
        }else{
        	countList = getCount(forBidmap);
            tokenManager.setRegCountToken(userid, countList);  
        };
        return countList;
    }
    
    
    
   /**
    * 
    * 描述:从数据库中查找统计值
    * @auther gaojinling
    * @date 2017年3月21日 
    * @param forBidmap
    * @return
    * @throws Exception
    */
   public List<Integer> getCount(Map<String, Object> forBidmap) throws Exception{
       List<Integer> countList = new ArrayList<Integer>();
	   //敏感词待审核条数
       int forbidCount = yrRegCheckService.selectForbidCount(forBidmap);
       //修改申请待审核条数
       int modCount = modApplicationService.selectModCount(forBidmap);
       countList.add(forbidCount);
       countList.add(modCount);
       //企业
       forBidmap.put("systemType", "1");
       //列入异常待审核
       int qyInCount = pubOpaDetailService.selectPubOpaInCount(forBidmap);
       //移出+撤销异常待审核
       int qyOutCount = pubOpaDetailService.selectPubOpaOutCount(forBidmap);
       //农专社
       forBidmap.put("systemType", "2");
       int nzInCount = pubOpaDetailService.selectPubOpaInCount(forBidmap);
       int nzOutCount = pubOpaDetailService.selectPubOpaOutCount(forBidmap);
       countList.add(qyInCount+qyOutCount);
       countList.add(nzInCount+nzOutCount);
       return countList;
   }
   
    /**
     * 
     * 描述: 用于处理经营异常相关操作更新缓存（列入异常申请提交、移出申请提交 +1）
     * @auther gaojinling
     * @date 2017年3月22日 
     * @param userid 当前用户id reids缓存的key值
     * @param forBidmap 查询map（主要为操作权限）
     * @param entTypeCatg 用于区分异常操作数据为企业还是农专
     * @param operate 1 Add 列入异常申请提交、移出申请提交 +1  2 minus 列入（移出、撤销）申请删除、列入审核成功、移出（撤销）首次审核-1
     * @throws Exception
     */
	public void doRedisRegCount(String userid,Map<String, Object>  forBidmap ,String entTypeCatg,String operate) throws Exception{
		//1.得到当前统计列表
		List<Integer> temList= tokenManager.getRegCountList(userid);
		List<Integer> countTepList = new ArrayList<Integer>();
		if(temList != null && temList.size() >0 ){ //缓存存在
		    countTepList.add(temList.get(0));  //敏感词审核
		    countTepList.add(temList.get(1)); //修改申请审核
		    	if("1".equals(operate)){//add 
					if("16".equals(entTypeCatg) || "17".equals(operate)){ //农专社
				            countTepList.add(temList.get(2)); //企业异常待审核（包括列入待审核+移出待审核+撤销待审核）
				            countTepList.add(temList.get(3)+1); //农专异常待审核（包括列入待审核+移出待审核+撤销待审核）
				   }else{//企业
				            countTepList.add(temList.get(2)+1); //企业异常待审核（包括列入待审核+移出待审核+撤销待审核）
				            countTepList.add(temList.get(3)); //农专异常待审核（包括列入待审核+移出待审核+撤销待审核）
				   }
		    	}else{ //minus
					if("16".equals(entTypeCatg) || "17".equals(operate)){ //农专社
			            countTepList.add(temList.get(2)); //企业异常待审核（包括列入待审核+移出待审核+撤销待审核）
			            countTepList.add(temList.get(3)-1); //农专异常待审核（包括列入待审核+移出待审核+撤销待审核）
				   }else{//企业
				            countTepList.add(temList.get(2)-1); //企业异常待审核（包括列入待审核+移出待审核+撤销待审核）
				            countTepList.add(temList.get(3)); //农专异常待审核（包括列入待审核+移出待审核+撤销待审核）
				   }
		    	}
		}else{ //缓存不存在,数据库取值
			countTepList = getCount(forBidmap);
		}
		//2 重新设置缓存
		tokenManager.setRegCountToken(userid, countTepList);
	}
	
	
    
    
   
}