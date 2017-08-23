/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.rpt.rptdto;

import com.icinfo.cs.rpt.rptmodel.DeptPubInfoRpt;

/**
 * 描述: cs_rpt_public_info 对应的DTO类.<br>
 *
 * @author framework generator
 * @date 2017年08月10日
 */
@SuppressWarnings("serial")
public class DeptPubInfoRptDto extends DeptPubInfoRpt {
	// 部门名称
	private String deptName;
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}