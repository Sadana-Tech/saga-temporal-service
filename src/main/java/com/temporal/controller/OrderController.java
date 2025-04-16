package com.temporal.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.temporal.model.Order;
import com.temporal.workflow.OrderWorkflow;

import io.temporal.client.WorkflowClient;

@RestController
public class OrderController {

	private final WorkflowClient workflowClient;
	public static final String TASK_QUEUE = "ORDER_TASK_QUEUE";

	public OrderController(WorkflowClient workflowClient) {
		this.workflowClient = workflowClient;
	}

	@PostMapping("/placeOrder")
	public Order placeOrder(@RequestBody Order order) {

		OrderWorkflow orderWorkflow = workflowClient.newWorkflowStub(OrderWorkflow.class,
				io.temporal.client.WorkflowOptions.newBuilder().setTaskQueue(TASK_QUEUE).build());
		Order orderRes = orderWorkflow.placeOrder(order);
		return orderRes;

	}

}
