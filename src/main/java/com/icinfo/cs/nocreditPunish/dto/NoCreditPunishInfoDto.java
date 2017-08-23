package com.icinfo.cs.nocreditPunish.dto;

import com.icinfo.cs.nocreditPunish.model.NoCreditPunishInfo;

/**
 * cs_nocredit_punishinfo 对应的DTO类.
 * @author caoxu
 * @date 2016年10月18日
 */
public class NoCreditPunishInfoDto extends NoCreditPunishInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6986855496443719745L;
	
	/**
	 * 当前用户部门是否已反馈
	 */
	private Integer hasBack;
	
	private String priPID;
	
	private String regNo;
	
	private String entName;
	
	private String uniCode;
	
	private String leRep;
	
	private String cardNo;
	
	private String litiName;

	private String downState;

	public Integer getHasBack() {
		return hasBack;
	}

	public void setHasBack(Integer hasBack) {
		this.hasBack = hasBack;
	}

	public String getPriPID() {
		return priPID;
	}

	public void setPriPID(String priPID) {
		this.priPID = priPID;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getUniCode() {
		return uniCode;
	}

	public void setUniCode(String uniCode) {
		this.uniCode = uniCode;
	}

	public String getLeRep() {
		return leRep;
	}

	public void setLeRep(String leRep) {
		this.leRep = leRep;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getLitiName() {
		return litiName;
	}

	public void setLitiName(String litiName) {
		this.litiName = litiName;
	}

	public String getDownState() {
		return downState;
	}

	public void setDownState(String downState) {
		this.downState = downState;
	}
}
