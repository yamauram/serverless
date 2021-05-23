package com.juku.serverless.functions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.juku.serverless.domain.common.model.RequestDto;
import com.juku.serverless.domain.common.model.ResponseDto;
import com.juku.serverless.domain.common.service.ReceptionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiGatewayEventFunction implements Function<Message<RequestDto>,Message<ResponseDto>> {

	@Autowired
	ReceptionService service;

	@Override
	public Message<ResponseDto> apply(Message<RequestDto> inputMessage) {
		log.info("Message : " + inputMessage.getPayload().getRequestFilePath());
		ResponseDto responseDto = service.accept(inputMessage.getPayload().getRequestFilePath());
		return new Message<ResponseDto>() {
			@Override
			public ResponseDto getPayload() {
				return responseDto;
			}

			@Override
			public MessageHeaders getHeaders() {
				Map<String, Object> headers = new HashMap<>();
				return new MessageHeaders(headers);
			}
		};
	}

}
