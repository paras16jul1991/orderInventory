package com.raven.orderInventory.listener;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raven.order.model.OrderDetail;
import com.raven.order.model.ServiceResponse;
import com.raven.order.model.Status;
import com.raven.orderInventory.publisher.ProcesserPublisher;

@Service
public class OrderInventoryListener {

	private final Logger logger = LoggerFactory.getLogger(OrderInventoryListener.class);

	@Autowired
	ProcesserPublisher publisher;

	@KafkaListener(topics = { "inventory" }, groupId = "group_id")
	public void consume(String message) throws IOException {
		logger.info(String.format("#### -> Consumed message -> %s", message));
		OrderDetail orderDetail = new ObjectMapper().readValue(message, OrderDetail.class);

		// Update Inventory

		publisher.sendMessage(ServiceResponse.builder().orderDetail(orderDetail).responseFrom("inventory")
				.status(Status.COMPLETED).build());
	}
}
