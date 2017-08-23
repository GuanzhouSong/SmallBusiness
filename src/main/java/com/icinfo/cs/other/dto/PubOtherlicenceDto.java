/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.other.dto;

import java.util.List;

import com.icinfo.cs.other.model.PubOtherlicence;
import com.icinfo.cs.other.model.PubServerModrecord;

/**
 * 描述:  cs_pub_otherlicence 对应的DTO类.<br>
 *
 * @author framework generator
 * @date 2016年10月18日
 */
public class PubOtherlicenceDto extends PubOtherlicence {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String regOrgName;//登记机关
	private String localAdmName;
	private String sliceNOName;
	private String source;
	private String  setDateStart;
	private String setDateEnd;
	private String auditDateStart;
	private String auditDateEnd;
	private String leRep;
	
	private String regOrg;
	private String localAdm;
	private String entType;
	private String sliceNO;
	
	private String licValFromStart;
	private String licValFromEnd;
	private String licValToStart;
	private String licValToEnd;

	//变更记录list
	private List<PubServerModrecord> pubOtherlicenceModList;

	public String getRegOrgName() {
		return regOrgName;
	}

	public void setRegOrgName(String regOrgName) {
		this.regOrgName = regOrgName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<PubServerModrecord> getPubOtherlicenceModList() {
		return pubOtherlicenceModList;
	}
     
	public String getLeRep() {
		return leRep;
	}

	public void setLeRep(String leRep) {
		this.leRep = leRep;
	}

	public void setPubOtherlicenceModList(
			List<PubServerModrecord> pubOtherlicenceModList) {
		this.pubOtherlicenceModList = pubOtherlicenceModList;
	}

	public String getSetDateStart() {
		return setDateStart;
	}

	public void setSetDateStart(String setDateStart) {
		this.setDateStart = setDateStart;
	}
  
	public String getLocalAdmName() {
		return localAdmName;
	}

	public void setLocalAdmName(String localAdmName) {
		this.localAdmName = localAdmName;
	}

	public String getSliceNOName() {
		return sliceNOName;
	}

	public void setSliceNOName(String sliceNOName) {
		this.sliceNOName = sliceNOName;
	}

	public String getSetDateEnd() {
		return setDateEnd;
	}

	public void setSetDateEnd(String setDateEnd) {
		this.setDateEnd = setDateEnd;
	}

	public String getAuditDateStart() {
		return auditDateStart;
	}

	public void setAuditDateStart(String auditDateStart) {
		this.auditDateStart = auditDateStart;
	}

	public String getAuditDateEnd() {
		return auditDateEnd;
	}

	public void setAuditDateEnd(String auditDateEnd) {
		this.auditDateEnd = auditDateEnd;
	}
	public String getRegOrg() {
		return regOrg;
	}

	public void setRegOrg(String regOrg) {
		this.regOrg = regOrg;
	}

	public String getLocalAdm() {
		return localAdm;
	}

	public void setLocalAdm(String localAdm) {
		this.localAdm = localAdm;
	}

	public String getEntType() {
		return entType;
	}

	public void setEntType(String entType) {
		this.entType = entType;
	}

	public String getSliceNO() {
		return sliceNO;
	}

	public void setSliceNO(String sliceNO) {
		this.sliceNO = sliceNO;
	}

	public String getLicValFromStart() {
		return licValFromStart;
	}

	public void setLicValFromStart(String licValFromStart) {
		this.licValFromStart = licValFromStart;
	}

	public String getLicValFromEnd() {
		return licValFromEnd;
	}

	public void setLicValFromEnd(String licValFromEnd) {
		this.licValFromEnd = licValFromEnd;
	}

	public String getLicValToStart() {
		return licValToStart;
	}

	public void setLicValToStart(String licValToStart) {
		this.licValToStart = licValToStart;
	}

	public String getLicValToEnd() {
		return licValToEnd;
	}

	public void setLicValToEnd(String licValToEnd) {
		this.licValToEnd = licValToEnd;
	}
    
	
}