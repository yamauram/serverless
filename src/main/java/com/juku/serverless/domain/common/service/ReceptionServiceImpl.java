package com.juku.serverless.domain.common.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juku.serverless.domain.common.model.ReceptionDto;
import com.juku.serverless.domain.common.model.RequestInfo;
import com.juku.serverless.domain.common.model.ResponseDto;
import com.juku.serverless.domain.common.repository.RequestInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ReceptionServiceImpl implements ReceptionService {

	@Value(value = "${reception.queue.name:reception-queue}")
	String queueName;
	
	@Autowired
	QueueMessagingTemplate queueMessagingTemplate;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	RequestInfoRepository repository;

	@Override
	public ResponseDto accept(String requestFilePath) {

		// S3ファイルパスの存在確認
		Resource resource = resourceLoader.getResource(requestFilePath);
		if(resource.exists()) {
			log.info("要求ファイルは存在します。:{}", requestFilePath);
		}else {
			log.error("要求ファイルは存在しません。:{}", requestFilePath);
		}

		// 受付情報をDBに登録
		RequestInfo requestInfo = RequestInfo.builder().requestFilePath(requestFilePath).status("01")
				.receptionTime(LocalDateTime.now()).build();
		repository.create(requestInfo);

		// キューにメッセージを登録
		ReceptionDto receptionDto = ReceptionDto.builder().requestFilePath(requestFilePath)
				.receptionNumber(requestInfo.getReceptionNumber()).build();
		queueMessagingTemplate.convertAndSend(queueName, receptionDto);

		// レスポンス情報を返却
		ResponseDto responseDto = ResponseDto.builder().receptionNumber(requestInfo.getReceptionNumber()).build();
		return responseDto;
	}
}
