package com.juku.serverless.domain.common.repository;

import org.apache.ibatis.annotations.Mapper;

import com.juku.serverless.domain.common.model.RequestInfo;

@Mapper
public interface RequestInfoRepository {
	
	public void create(RequestInfo requestInfo);
}
