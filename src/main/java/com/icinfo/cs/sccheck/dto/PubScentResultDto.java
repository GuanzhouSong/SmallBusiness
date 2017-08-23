/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.sccheck.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.icinfo.cs.sccheck.model.PubScentResult;

/**
 * 描述: cs_pub_scent_result 对应的DTO类.<br>
 *
 * @author framework generator
 * @date 2017年05月17日
 */
public class PubScentResultDto extends PubScentResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4700054531048311172L;
	private String regNO;// 注册号
	private String uniCode;// 统一代码
	private String entName;// 企业名称
	private String taskCode;// 任务编号
	private String taskName;// 任务名称
	private String regOrgName;// 登记机关
	private String localAdmName;// 管辖单位
	private String sliceNOName;// 片区
	private String dom;// 住所地址
	private String scType;//方式
	/** 以下用于统计 */
	private String areaName;//地区
	private String deptName;//部门
	private Integer finishedNum;//已检查企业数量
	private Integer disposeNum;//后续处置已完结企业数量
	private Integer totalNum;//企业总量
	private Integer result1;//未发现违法行为
	private Integer result2;//违反工商行政管理相关规定
	private Integer result3;//违反食品药品管理相关规定
	private Integer result4;//违反质量技术监督相关规定
	private Integer result6;//查无下落
	private Integer result7;//已关闭停业或正在清算
	private Integer result8;//不予配合情节严重
	private Integer result9;//注销
	private Integer result10;//被吊销
	private Integer result11;//被撤销
	private Integer result12;//迁出
	private Integer dispose1;//无需后续处置
	private Integer dispose2;//停止检查
	private Integer dispose3;//责令限期整改
	private Integer dispose4;//发现案件线索
	private Integer dispose5;//抄告相关部门
	private Integer dispose6;//其他
	
	private Integer editNum;//待录入
	private Integer auditNum;//待审核和审核不通过
	private Integer endNum;//已公示
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date taskEndTime;//方式
	
	public String getRegNO() {
		return regNO;
	}

	public void setRegNO(String regNO) {
		this.regNO = regNO;
	}

	public String getUniCode() {
		return uniCode;
	}

	public void setUniCode(String uniCode) {
		this.uniCode = uniCode;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
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

	public String getRegOrgName() {
		return regOrgName;
	}

	public void setRegOrgName(String regOrgName) {
		this.regOrgName = regOrgName;
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

	public String getDom() {
		return dom;
	}

	public void setDom(String dom) {
		this.dom = dom;
	}

	public String getScType() {
		return scType;
	}

	public void setScType(String scType) {
		this.scType = scType;
	}

	public Date getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getFinishedNum() {
		return finishedNum;
	}

	public void setFinishedNum(Integer finishedNum) {
		this.finishedNum = finishedNum;
	}

	public Integer getDisposeNum() {
		return disposeNum;
	}

	public void setDisposeNum(Integer disposeNum) {
		this.disposeNum = disposeNum;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getResult1() {
		return result1;
	}

	public void setResult1(Integer result1) {
		this.result1 = result1;
	}

	public Integer getResult2() {
		return result2;
	}

	public void setResult2(Integer result2) {
		this.result2 = result2;
	}

	public Integer getResult3() {
		return result3;
	}

	public void setResult3(Integer result3) {
		this.result3 = result3;
	}

	public Integer getResult4() {
		return result4;
	}

	public void setResult4(Integer result4) {
		this.result4 = result4;
	}

	public Integer getResult6() {
		return result6;
	}

	public void setResult6(Integer result6) {
		this.result6 = result6;
	}

	public Integer getResult7() {
		return result7;
	}

	public void setResult7(Integer result7) {
		this.result7 = result7;
	}

	public Integer getResult8() {
		return result8;
	}

	public void setResult8(Integer result8) {
		this.result8 = result8;
	}

	public Integer getResult9() {
		return result9;
	}

	public void setResult9(Integer result9) {
		this.result9 = result9;
	}

	public Integer getResult10() {
		return result10;
	}

	public void setResult10(Integer result10) {
		this.result10 = result10;
	}

	public Integer getResult11() {
		return result11;
	}

	public void setResult11(Integer result11) {
		this.result11 = result11;
	}

	public Integer getResult12() {
		return result12;
	}

	public void setResult12(Integer result12) {
		this.result12 = result12;
	}

	public Integer getDispose1() {
		return dispose1;
	}

	public void setDispose1(Integer dispose1) {
		this.dispose1 = dispose1;
	}

	public Integer getDispose2() {
		return dispose2;
	}

	public void setDispose2(Integer dispose2) {
		this.dispose2 = dispose2;
	}

	public Integer getDispose3() {
		return dispose3;
	}

	public void setDispose3(Integer dispose3) {
		this.dispose3 = dispose3;
	}

	public Integer getDispose4() {
		return dispose4;
	}

	public void setDispose4(Integer dispose4) {
		this.dispose4 = dispose4;
	}

	public Integer getDispose5() {
		return dispose5;
	}

	public void setDispose5(Integer dispose5) {
		this.dispose5 = dispose5;
	}

	public Integer getDispose6() {
		return dispose6;
	}

	public void setDispose6(Integer dispose6) {
		this.dispose6 = dispose6;
	}

	public Integer getEditNum() {
		return editNum;
	}

	public void setEditNum(Integer editNum) {
		this.editNum = editNum;
	}

	public Integer getAuditNum() {
		return auditNum;
	}

	public void setAuditNum(Integer auditNum) {
		this.auditNum = auditNum;
	}

	public Integer getEndNum() {
		return endNum;
	}

	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
	}

}