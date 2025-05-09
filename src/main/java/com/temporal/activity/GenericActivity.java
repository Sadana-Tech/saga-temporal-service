package com.temporal.activity;

import com.temporal.config.WorkflowStep;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface GenericActivity {
    void invoke(WorkflowStep step);
}
