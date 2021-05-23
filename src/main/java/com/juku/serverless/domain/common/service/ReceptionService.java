package com.juku.serverless.domain.common.service;

import com.juku.serverless.domain.common.model.ResponseDto;

public interface ReceptionService {
	
	public ResponseDto accept(String requestFilepath) ;
}
