package com.raven.orderInventory.publisher;

import org.springframework.kafka.core.KafkaTemplate;

import com.raven.kafka.Producer;
import com.raven.order.model.ServiceResponse;

public class ProcesserPublisher extends Producer<String, ServiceResponse> {

	public ProcesserPublisher(String topic, KafkaTemplate<String, String> template) {
		super(topic, template);
	}

}
