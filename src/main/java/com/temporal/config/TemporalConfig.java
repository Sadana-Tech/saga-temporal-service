package com.temporal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.temporal.activity.OrderActivities;
import com.temporal.activity.OrderActivitiesImpl;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.WorkerFactory;

@Configuration
public class TemporalConfig {

	@Bean
	public WorkflowServiceStubs workflowServiceStubs() {
		/*
		 * WorkflowServiceStubsOptions options =
		 * WorkflowServiceStubsOptions.newBuilder().setTarget("localhost:7233")
		 * .build();
		 */
		return WorkflowServiceStubs.newInstance();
	}

	@Bean
	public WorkflowClient workflowClient(WorkflowServiceStubs workflowServiceStubs) {
		return WorkflowClient.newInstance(workflowServiceStubs,
				WorkflowClientOptions.newBuilder().setNamespace("default").build());
	}

	@Bean
	public WorkerFactory workerFactory(WorkflowClient client, WorkflowServiceStubs service) {
		return WorkerFactory.newInstance(client);

	}

	@Bean
	public OrderActivities orderActivities() {
		return new OrderActivitiesImpl();
	}

}
