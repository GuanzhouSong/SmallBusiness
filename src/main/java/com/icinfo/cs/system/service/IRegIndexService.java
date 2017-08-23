/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.system.service;

import com.icinfo.framework.core.service.BaseService;

import java.util.List;
import java.util.Map;


/**
 * 描述:    cs_bulletins_read_record 对应的Service接口.<br>
 *
 * @author framework generator
 * @date 2016年11月21日
 */
public interface IRegIndexService extends BaseService {

	
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
    public List<Integer> getRegCountList(String userid ,Map<String, Object> forBidmap) throws Exception;
    
    /**
     * 
     * 描述:从数据库中查找统计值
     * @auther gaojinling
     * @date 2017年3月21日 
     * @param forBidmap
     * @return
     * @throws Exception
     */
    public List<Integer> getCount(Map<String, Object> forBidmap) throws Exception;
    
    
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
	public void doRedisRegCount(String userid,Map<String, Object>  forBidmap ,String entTypeCatg,String operate) throws Exception;
    
}