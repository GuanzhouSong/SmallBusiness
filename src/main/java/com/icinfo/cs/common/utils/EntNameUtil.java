package com.icinfo.cs.common.utils;

import org.apache.commons.lang3.StringUtils;

public class EntNameUtil {
	
	/**
	 * 描述：获取企业名称的短名称
	 *
	 * @author zhuyong
	 * @date 2015-11-20
	 * @param entName
	 * @return
	 * @throws Exception
	 */
	public static String getShortName(String entName) throws Exception {
		if(StringUtils.isBlank(entName)){
			return "";
		}
		if(entName.equals("有限售条件流通股")||entName.equals("无限售条件流通股")){
			return entName;
		}
		entName = entName.replace("浙江", "").replace("杭州市", "").replace("杭州", "").
				replace("富阳", "").replace("桐庐", "").replace("淳安", "").replace("建德", "").
				replace("余杭", "").replace("萧山", "").replace("临安", "").
				replace("租赁", "").replace("物流", "").
				replace("咨询", "").replace("信息", "").replace("有限", "").
				replace("责任", "").replace("公司", "").replace("企业", "").
				replace("专业合作社", "").replace("合伙", "").
				replace("普通", "").replace("股份","").replace("（","").replace("）","");
//		if(entName.length()>4){
//			entName = entName.substring(0,4);
//		}
		return entName;

	}
	
	/**
	 * 描述：获取企业名称的短名称（针对浙江)
	 *
	 * @author caoxu
	 * @date 2016-06-27
	 * @param entName
	 * @return
	 * @throws Exception
	 */
	public static String getShortNameOfZj(String entName) {
		if (StringUtils.isBlank(entName)) {
			return "";
		}
		if (entName.equals("有限售条件流通股") || entName.equals("无限售条件流通股")) {
			return entName;
		}
		String oldName = entName;
		String ingore[] = { "浙江", "省", "杭州", "市", "富阳", "宁波", "嘉兴", "温州", "湖州", "绍兴", "衢州", "金华", "舟山", "台州", "丽水", "桐庐",
				"淳安", "建德", "余杭", "萧山", "临安", "物流", "信息", "有限", "责任", "分公司", "公司", "企业", "专业合作社", "合伙", "普通", "股份", "(", "（", ")",
				"）", "集团" };
		try {
			if (entName.length() > 4) {
				for (int i = 0; i < ingore.length; i++) {
					entName = entName.replace(ingore[i], "");
				}
			}
		} catch (Exception e) {
			return oldName;
		}
		return entName;
	}

}
