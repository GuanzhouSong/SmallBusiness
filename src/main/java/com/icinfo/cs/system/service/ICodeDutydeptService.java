/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.system.service;

import com.icinfo.cs.system.model.CodeDutydept;
import com.icinfo.framework.core.service.BaseService;

import java.util.List;

/**
 * 描述:    cs_code_dutydept 对应的Service接口.<br>
 *
 * @author framework generator
 * @date 2016年11月21日
 */
public interface ICodeDutydeptService extends BaseService {
    /**
     * 描述：获取职能部门编码数据
     * @param selectedCodes
     * @return
     * @throws Exception
     */
    List<CodeDutydept> doSelectAllDutyCodes(String selectedCodes)throws Exception;
    
    /**
     * 
     * 描述   根据部门类型编码查询
     * @author 赵祥江
     * @date 2016年11月21日 下午4:13:45 
     * @param 
     * @return List<CodeDutydept>
     * @throws
     */
    CodeDutydept doSelectCodeDutydeptBydeptCode(String deptCode)throws Exception;


}