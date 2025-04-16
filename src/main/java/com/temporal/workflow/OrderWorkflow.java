package com.temporal.workflow;

import com.temporal.model.Order;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderWorkflow {

	@WorkflowMethod
	Order placeOrder(Order order);

}
