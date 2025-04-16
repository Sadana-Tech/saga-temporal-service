package com.temporal.workflow;

import java.time.Duration;

import com.temporal.activity.OrderActivities;
import com.temporal.model.Order;

import io.temporal.activity.Activity;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

public class OrderWorkflowImpl implements OrderWorkflow {

	
	
	private final RetryOptions retryoptions = RetryOptions.newBuilder().setInitialInterval(Duration.ofSeconds(1))
			.setMaximumInterval(Duration.ofSeconds(10)).setBackoffCoefficient(2).setMaximumAttempts(3).build();
	private final ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(30))
			.setRetryOptions(retryoptions).build();
	private final OrderActivities activities = Workflow.newActivityStub(OrderActivities.class, options);

	@Override
	public Order placeOrder(Order order) {

		try {
			
			order.addResultStep("Order placement Started");

			// step1:
			activities.reserveInventory(order.getInventoryId());
			order.addResultStep("Inventory reserved");

			// Step2:

			activities.chargePymt(order.getPaymentDetails());
			order.addResultStep("Payment processed");
			
			order.addResultStep("Order placement Completed");

		} catch (Exception e) {
			try {
				activities.cancelInventory(order.getInventoryId());
				order.addResultStep("Inventory cancellation successful");
			} catch (Exception exe) {
				order.addResultStep("Inventory cancellation failed: " + exe.getMessage());
			}
			order.setError("Workflow failed: " + e.getMessage());
		}
		return order;
	}

}
