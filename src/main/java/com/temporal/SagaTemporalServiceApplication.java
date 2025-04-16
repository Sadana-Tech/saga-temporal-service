package com.temporal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.temporal.activity.OrderActivities;
import com.temporal.activity.OrderActivitiesImpl;
import com.temporal.workflow.OrderWorkflowImpl;

import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

@SpringBootApplication
public class SagaTemporalServiceApplication implements CommandLineRunner {

	@Autowired
	private WorkerFactory workerFactory;
	@Autowired
	private OrderActivities orderActivities;
	public static final String TASK_QUEUE = "ORDER_TASK_QUEUE";

	public static void main(String[] args) {
		SpringApplication.run(SagaTemporalServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Worker worker = workerFactory.newWorker(TASK_QUEUE);
		worker.registerWorkflowImplementationTypes(OrderWorkflowImpl.class);
		worker.registerActivitiesImplementations(orderActivities);
		workerFactory.start();

	}

}
