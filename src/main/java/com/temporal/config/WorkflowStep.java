package com.temporal.config;

import lombok.Data;

@Data
public class WorkflowStep {
    private String activity;
    private String endpoint;
    private String payLoad;
    private String order;
    private String httpMethod;
    private int retryCount;
    private int retryMaxInterval;
    private int retryInitialInterval;
    private int retryInterval;
    private int retryPolicyBackOff;
    private int startToCloseTimeout;
    private WorkflowStep compensationStep;
   
}
