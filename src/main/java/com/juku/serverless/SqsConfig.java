package com.juku.serverless;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;

/**
 * SQSコネクション設定クラス
 */
@Configuration
public class SqsConfig {

	@Value("${queue.endpoint}")
	private String queueEndpoint;
	@Value("${cloud.aws.region.static}")
	private String region;

	@Autowired
	AmazonSQSAsync amazonSQSAsync;

	@Bean
	public AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
		return new AwsClientBuilder.EndpointConfiguration(queueEndpoint, region);
	}

	@Bean
	public QueueMessagingTemplate queueMessagingTemplate() {
		return new QueueMessagingTemplate(amazonSQSAsync);
	}

}
