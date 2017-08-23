/**
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. <br/>
 * 描述: TODO <br/>
 *
 * @author framework generator
 * @date 2017年05月17日
 * @version 2.0
 */
package com.icinfo.cs.sccheck.mapper;

import java.util.List;
import java.util.Map;

import com.icinfo.cs.sccheck.dto.PubScentResultDto;
import com.icinfo.cs.sccheck.dto.SccheckCount;
import com.icinfo.cs.sccheck.model.PubScentResult;
import com.icinfo.framework.mybatis.mapper.common.Mapper;

/**
 * 描述: cs_pub_scent_result 对应的Mapper接口.<br>
 *
 * @author framework generator
 * @date 2017年05月17日
 */
public interface PubScentResultMapper extends Mapper<PubScentResult> {

	/**
	 * 描述：抽查检查结果列表
	 * 
	 * @author baifangfang
	 * @date 2017年5月17日
	 * @param params
	 * @return
	 */
	List<PubScentResultDto> queryPageResult(Map<String, Object> params);

	/**
	 * 描述：抽查查询结果统计
	 * 
	 * @author baifangfang
	 * @date 2017年5月17日
	 * @param params
	 * @return
	 */
	SccheckCount querySccheckCount(Map<String, Object> params);
	
	/**
	 * 描述：抽查查询结果统计
	 * 
	 * @author baifangfang
	 * @date 2017年5月17日
	 * @param params
	 * @return
	 */
	SccheckCount querySccheckSearchCount(Map<String, Object> params);

	/**
	 * 描述：根据任务序号查询抽查检查结果信息
	 * 
	 * @author baifangfang
	 * @date 2017年5月18日
	 * @param params
	 * @return
	 */
	PubScentResultDto selectPubScentResultDtoByTaskUidAndPriPID(Map<String, Object> params);

	/**
	 * 描述：公示系统查询抽查检查结果信息
	 * 
	 * @author baifangfang
	 * @date 2017年6月2日
	 * @param params
	 * @return
	 */
	List<PubScentResultDto> queryPageResultByPriPID(Map<String, Object> params);

	/**
	 * 描述：根据任务序号查询抽查检查结果信息
	 * 
	 * @author baifangfang
	 * @date 2017年6月2日
	 * @param params
	 * @return
	 */
	PubScentResultDto selectPubScentResultDtoByUid(Map<String, Object> params);

	/**
	 * 描述：综合抽查实施准备列表JSON数据列表
	 * 
	 * @author baifangfang
	 * @date 2017年6月12日
	 * @param params
	 * @return
	 */
	List<PubScentResultDto> queryPreParePageResult(Map<String, Object> params);

	/**
	 * 
	 * 描述: 综合抽查结果统计
	 * @auther chenxin
	 * @date 2017年6月26日 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	List<PubScentResultDto> selectScentResultCount(Map<String, Object> parmMap);

	/**
	 * 描述：抽查检查数据列表
	 * 
	 * @author chenxin
	 * @date 2017年6月28日
	 * @param request
	 * @return
	 */
	List<PubScentResultDto> queryPageSearchResult(Map<String, Object> params);
    
	/**
	 * 描述：通过结果表uid查询agentid
	 * 
	 * @author yujingwei
	 * @date 2017年6月28日
	 * @param request
	 * @return
	 */
	List<PubScentResultDto> doGetAgentIdByResultuid(String resultUids);
	
	/**
	 * 描述：调整之后，执法人员、组长和专家赋空值
	 * 
	 * @author chenxin
	 * @date 2017年6月28日
	 * @param request
	 * @return
	 */
	void doUpdateDefaultNull(Map<String,Object> params);
}