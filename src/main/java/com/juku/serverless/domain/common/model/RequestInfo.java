package com.juku.serverless.domain.common.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	int receptionNumber;
	String requestFilePath;
	String responseFilePath;
	String status;
	LocalDateTime receptionTime;
}
