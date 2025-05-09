package com.temporal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.temporal.activity.GenericActivityImpl;
import com.temporal.workflow.DynamicWorkflow;
import com.temporal.workflow.DynamicWorkflowImpl;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

@Configuration
@SpringBootApplication
public class SagaTemporalServiceApplication implements CommandLineRunner {

	public static final String TASK_QUEUE = "DSL_TASK_QUEUE";
	@Autowired
	private GenericActivityImpl genericActivity;
	public static void main(String[] args) {
		SpringApplication.run(SagaTemporalServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		WorkerFactory factory = WorkerFactory.newInstance(workflowClient(workflowServiceStubs()));
		Worker worker = factory.newWorker(TASK_QUEUE);

		// Register workflow and activity implementation
		worker.registerWorkflowImplementationTypes(DynamicWorkflowImpl.class);
		worker.registerActivitiesImplementations(genericActivity);

		factory.start();

	}

	@Bean
	public WorkflowServiceStubs workflowServiceStubs() {
		return WorkflowServiceStubs.newInstance(); // default options, or customize here
	}

	@Bean
	public WorkflowClient workflowClient(WorkflowServiceStubs service) {

		WorkflowClient client = WorkflowClient.newInstance(service);

		return client;
	}

	
}
