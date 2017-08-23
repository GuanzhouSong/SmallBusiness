package com.icinfo.cs.quartz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.icinfo.cs.base.constant.QuartzJobName;
import com.icinfo.cs.base.model.CodeRegorg;
import com.icinfo.cs.base.service.ICodeRegorgService;
import com.icinfo.cs.base.service.ICsQuartzJobService;
import com.icinfo.cs.common.utils.DateUtil;
import com.icinfo.cs.common.utils.SleepUtil;
import com.icinfo.cs.es.service.IEntSearchService;
import com.icinfo.cs.es.service.IPanoramaSearchService;
import com.icinfo.cs.ext.dto.MidBaseInfoDto;
import com.icinfo.cs.ext.service.IMidBaseInfoService;
import com.icinfo.cs.opanomaly.dto.PubPbopanomalyDto;
import com.icinfo.cs.opanomaly.model.PubPbopanomaly;
import com.icinfo.cs.opanomaly.service.IPubPbopanomalyService;
import com.icinfo.cs.quartz.service.IPubPbopanomalyIsIndividQuartzJobService;
import com.icinfo.framework.core.service.support.MyBatisServiceSupport;

/**
 * 
 * 描述: 个体户转为企业之前涉及有经营异常信息的，在转为企业后，原有的经营异常信息不再归集于该企业名下进行展示与警示。
 *    所有个体户的经营异常状态在转企后由系统自动处理为“个转企，自动移出”
 * @auther gaojinling
 * @date 2017年1月16日 
 * @return
 * @throws Exception
 */
public class PubPbopanomalyIsIndividQuartzJobService extends MyBatisServiceSupport implements IPubPbopanomalyIsIndividQuartzJobService{
	private static final Logger logger = LoggerFactory.getLogger(PubPbopanomalyIsIndividQuartzJobService.class);
	@Autowired
	private  IPubPbopanomalyService  pubPbopanomalyService;
	@Autowired
	private ICsQuartzJobService csQuartzJobService;
	@Autowired
	private IPanoramaSearchService panoramaSearchService;
	@Autowired
	private IEntSearchService entSearchService;
	@Autowired
	private ICodeRegorgService codeRegorgService;
	@Autowired
	private IMidBaseInfoService midBaseInfoService;

	@Override
	public String  pbopanomalyIsIndividQuartzJob() throws Exception {
		logger.info("个体户 个转企自动移出 定时移出经营异常任务 开始");
		//阻塞任务，防止并发访问数据库
		SleepUtil.threadSleep();
 		if(csQuartzJobService.checkJobIsExecute(QuartzJobName.PBOPANOMALYISINDIVID_JOB.getJobName())){
			return "任务已执行";
		}
		csQuartzJobService.insert(QuartzJobName.PBOPANOMALYISINDIVID_JOB.getJobName());
		Map<String,Object> searchMap=new HashMap<String,Object>();
		//获取已补报且还在经营异常（未按时年报）的个体户
		List<PubPbopanomalyDto> pubPbopanomalyDtoList= pubPbopanomalyService.selectPubPbopanomalyIsIndividualed(searchMap);
		if(pubPbopanomalyDtoList!=null&&pubPbopanomalyDtoList.size()>0){
			for(PubPbopanomalyDto pubPbopanomalyDto:pubPbopanomalyDtoList){ 
				try { 
					    String pripid = pubPbopanomalyDto.getPriPID();
						searchMap.clear();
						PubPbopanomaly pubPbopanomaly=new PubPbopanomaly();
						String busExcList=pubPbopanomalyDto.getBusExcList();
						pubPbopanomaly.setBusExcList(busExcList);
						//注册号
						pubPbopanomaly.setRegNO(pubPbopanomalyDto.getRegNO());
						//恢复标记 1已标记 0 已恢复
						//pubPbopanomaly.setIsRecovery("0");
						//恢复异常原因编码
						//pubPbopanomaly.setNorRea("4");
						//恢复正常的原因中文名称
						//pubPbopanomaly.setNorReaCN("个转企，自动移出");
						//恢复日期
						//pubPbopanomaly.setNorDate(DateUtil.getSysDate());
						//恢复事实和理由
						//pubPbopanomaly.setRecoverRea("个转企");
						//恢复设置人
						//pubPbopanomaly.setResetName("系统自动");
 						MidBaseInfoDto midBaseInfoDto=	midBaseInfoService.selectMidBaseInfoByPripid(pripid, null);
 						//决定机关编码
						String decOrg=midBaseInfoDto==null?"000":midBaseInfoDto.getRegOrg(); 
						CodeRegorg codeRegorg=codeRegorgService.selectRegOrgByCode(decOrg);
						//恢复决定机关中文名称
						//pubPbopanomaly.setNorDecOrgCN(codeRegorg==null?"":codeRegorg.getContentShort());
						//恢复决定机关编码
						//pubPbopanomaly.setNorDecOrg(codeRegorg==null?"":decOrg); 
						//pubPbopanomalyService.updatePubPbopanomalyByBusExcList(pubPbopanomaly);
						
						searchMap.put("priPID", pripid);
						//直接移出异常
						searchMap.put("isOpan", "N"); 
						//搜索列表索引
						panoramaSearchService.updatePanoramaIdx(searchMap);
						searchMap.remove("isOpan");
						//更新公示索引
						searchMap.put("isOpanomaly", "0");
						entSearchService.updatePubIndex(searchMap);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		logger.info("个体户  个转企自动移出 定时移出经营异常任务 结束");
		return ""; 
	} 
}
