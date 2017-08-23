package com.icinfo.cs.common.constant;

import java.util.Map;
import java.util.TreeMap;

public enum BulletinType {
	
	AnomalyIn("1","列入经营异常名录公告"),
	AnomalyOut("2","移出经营异常名录公告"),
	AnomalyRemind("3","经营异常名录提醒公告"),
	
	IlldisIn("4","列入严重违法失信企业名单公告"),
	IlldisOut("5","移出严重违法失信企业名单公告"),
	
	SpotBulletin("6","抽查名单及结果公示表"),
	CheckBulletin("7","抽查公告"),
	
	
	PunishInfo("8","行政处罚公告"),
	
	JusticeInfoFroz("9","司法股权冻结公告"),
	JusticeInfoAlter("10","司法股东变更公告"),
	
	SimpleLogout("12","简易注销登记的公告"),
	
	ReadyRevokeLicense("13","拟吊销企业营业执照听证公告"),
	RevokeLicense("14","吊销企业营业执照公告"),
	RegOrgRevoke("15","企业登记机关注销公告"),
	
	YearCheck("17","年度抽查工作计划公告"),
	SpotTaskBulletin("18","抽查任务公告"),
	
	PressReport("16","催报公告");
	
	private String code;//编码
	private String desc;//描述
	private BulletinType(String code, String desc){
		this.code= code;
		this.desc=desc;
	}
	public String getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
	public static BulletinType getValue(String code) {
		for (BulletinType bulletinType : BulletinType.values()) {
			if (bulletinType.code.equalsIgnoreCase(code)) {
				return bulletinType;
			}
		}
		return null;
	}
    
    public static Map<String,BulletinType> getBulletinTypeMap(){
		Map<String,BulletinType> bulletinTypeMap = new TreeMap<String,BulletinType>();
		for(BulletinType bulletinType :BulletinType.values()){
			bulletinTypeMap.put(bulletinType.getCode(),bulletinType);
		}
		return bulletinTypeMap;
	}
}
