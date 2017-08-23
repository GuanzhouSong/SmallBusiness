/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.sccheck.dto;

import java.util.List;

import com.icinfo.cs.sccheck.model.PubScGroupMember;

/**
 * 描述:    cs_pub_scgroup_member 对应的DTO类.<br>
 *
 * @author framework generator
 * @date 2017年07月10日
 */
public class PubScGroupMemberDto extends PubScGroupMember {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//用于批量导入执法成员（随机抽取过程）
	private List<PubScGroupMember> pubScGroupMemberList;

	public List<PubScGroupMember> getPubScGroupMemberList() {
		return pubScGroupMemberList;
	}

	public void setPubScGroupMemberList(List<PubScGroupMember> pubScGroupMemberList) {
		this.pubScGroupMemberList = pubScGroupMemberList;
	}
}