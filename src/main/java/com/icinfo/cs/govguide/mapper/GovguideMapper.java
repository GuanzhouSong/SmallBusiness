/**
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. <br/>
 * 描述: TODO <br/>
 *
 * @author framework generator
 * @date 2016年10月19日
 * @version 2.0
 */
package com.icinfo.cs.govguide.mapper;

import com.icinfo.cs.govguide.model.Govguide;
import com.icinfo.framework.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 描述:  cs_govguide_set 对应的Mapper接口.<br>
 *
 * @author framework generator
 * @date 2016年10月19日
 */
public interface GovguideMapper extends Mapper<Govguide> {
    List<Govguide> govguide_querypage(Map<String, Object> params);

    List<Govguide> govguidecheck_querypage(Map<String, Object> params);

    /**
     * 计算统计出文号
     * @param govguide
     * @return
     */
    Govguide totalDocNum(Govguide govguide);
}