/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.sccheck.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.icinfo.cs.sccheck.model.PubScent;

/**
 * 描述:    cs_pub_scent 对应的DTO类.<br>
 *
 * @author framework generator
 * @date 2017年05月17日
 */
public class PubScentDto extends PubScent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -280500812222632326L;
	//数量
	private Integer specialNum;
	//执法人员姓名
	private String agentNames;
	//任务编号
	private String taskCode;
	//任务名称
	private String taskName;
	//检查机关描述
	private String checkDeptName;
	//任务组织部门描述
	private String taskLeadDeptName;
	
	//需要调整的企业的uid
	private String uids;
	//指派抽查机关
	private String appointLocalAdm;
	
	/** 以下用于随机抽取 */
	//每个均分块待抽取的企业
	private Integer teamNum;
	//每个均分块需要剔除的企业
	private Integer delNum;
	
	//审核状态度
	private String auditState;
	//检查人员
	private String checkDeptPerson;
	//检查结果
	private String checkResult;
	//审核人员
	private String auditUserName;
	//后续处置状态
	private String disposeState;
	//检查时间
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date checkDate;
	
	
	/** 以下用于统计 */
	//待录入
	private Integer writeNum;
	//待审核
	private Integer auditNum;
	//审核退回
	private Integer backNum;
	//已公示
	private Integer publicNum;
	//后续处置未完结
	private Integer unDisposeNum;
	//总数
	private Integer totalNum;

	public String getAgentNames() {
		return agentNames;
	}

	public void setAgentNames(String agentNames) {
		this.agentNames = agentNames;
	}

	public Integer getSpecialNum() {
		return specialNum;
	}

	public void setSpecialNum(Integer specialNum) {
		this.specialNum = specialNum;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCheckDeptName() {
		return checkDeptName;
	}

	public void setCheckDeptName(String checkDeptName) {
		this.checkDeptName = checkDeptName;
	}

	public String getTaskLeadDeptName() {
		return taskLeadDeptName;
	}

	public void setTaskLeadDeptName(String taskLeadDeptName) {
		this.taskLeadDeptName = taskLeadDeptName;
	}

	public String getUids() {
		return uids;
	}

	public void setUids(String uids) {
		this.uids = uids;
	}

	public String getAppointLocalAdm() {
		return appointLocalAdm;
	}

	public void setAppointLocalAdm(String appointLocalAdm) {
		this.appointLocalAdm = appointLocalAdm;
	}

	public Integer getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(Integer teamNum) {
		this.teamNum = teamNum;
	}

	public Integer getDelNum() {
		return delNum;
	}

	public void setDelNum(Integer delNum) {
		this.delNum = delNum;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getCheckDeptPerson() {
		return checkDeptPerson;
	}

	public void setCheckDeptPerson(String checkDeptPerson) {
		this.checkDeptPerson = checkDeptPerson;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	public String getDisposeState() {
		return disposeState;
	}

	public void setDisposeState(String disposeState) {
		this.disposeState = disposeState;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Integer getWriteNum() {
		return writeNum;
	}

	public void setWriteNum(Integer writeNum) {
		this.writeNum = writeNum;
	}

	public Integer getAuditNum() {
		return auditNum;
	}

	public void setAuditNum(Integer auditNum) {
		this.auditNum = auditNum;
	}

	public Integer getBackNum() {
		return backNum;
	}

	public void setBackNum(Integer backNum) {
		this.backNum = backNum;
	}

	public Integer getPublicNum() {
		return publicNum;
	}

	public void setPublicNum(Integer publicNum) {
		this.publicNum = publicNum;
	}

	public Integer getUnDisposeNum() {
		return unDisposeNum;
	}

	public void setUnDisposeNum(Integer unDisposeNum) {
		this.unDisposeNum = unDisposeNum;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
}