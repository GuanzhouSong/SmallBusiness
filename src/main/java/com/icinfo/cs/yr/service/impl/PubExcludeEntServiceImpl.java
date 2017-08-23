/*
 * Copyright© 2003-2016 浙江汇信科技有限公司, All Rights Reserved. 
 */
package com.icinfo.cs.yr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icinfo.cs.yr.dto.PubExcludeEntDto;
import com.icinfo.cs.yr.mapper.PubExcludeEntMapper;
import com.icinfo.cs.yr.model.PubExcludeEnt;
import com.icinfo.cs.yr.service.IPubExcludeEntService;
import com.icinfo.framework.core.service.support.MyBatisServiceSupport;
import com.icinfo.framework.mybatis.pagehelper.PageHelper;
import com.icinfo.framework.mybatis.pagehelper.datatables.PageRequest;
import com.icinfo.framework.tools.utils.DateUtils;

/**
 * 描述: cs_pub_excludeent 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2016年08月30日
 */
@Service
public class PubExcludeEntServiceImpl extends MyBatisServiceSupport implements IPubExcludeEntService {
	@Autowired
	PubExcludeEntMapper pubExcludeEntMapper;

	@Override
	public List<PubExcludeEntDto> queryPageExcludeResult(PageRequest request) {
		PageHelper.startPage(request.getPageNum(), request.getLength());
		return pubExcludeEntMapper.selectPubExcludeEntResult(request.getParams());
	}

	@Override
	public PubExcludeEnt doGetPubExcludeEntById(Integer pubExcludeEntId) {
		return pubExcludeEntMapper.selectByPrimaryKey(pubExcludeEntId);
	}

	@Override
	public int update(PubExcludeEnt pubExcludeEnt) {
		return pubExcludeEntMapper.updateByPrimaryKeySelective(pubExcludeEnt);
	}

	@Override
	public int insert(PubExcludeEnt pubExcludeEnt) {
		pubExcludeEnt.setSetTime(DateUtils.getSysDate());
		return pubExcludeEntMapper.insert(pubExcludeEnt);
	}

	@Override
	public int deleteById(Integer pubExcludeEntId) {
		return pubExcludeEntMapper.deleteByPrimaryKey(pubExcludeEntId);
	}

	@Override
	public List<PubExcludeEntDto> queryPageNotExcludeResult(PageRequest request) {
		PageHelper.startPage(request.getPageNum(), request.getLength());
		return pubExcludeEntMapper.selectNotPubExcludeEntResult(request.getParams());
	}

	@Override
	public PubExcludeEnt doGetPubExcludeEntByPriPID(String priPID) {
		PubExcludeEnt pubExcludeEnt = new PubExcludeEnt();
		pubExcludeEnt.setPriPID(priPID);
		return pubExcludeEntMapper.selectOne(pubExcludeEnt);
	}
}