package com.temporal.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Order {

	private String inventoryId;
	private PaymentDetails paymentDetails;
	private List<String> resultSteps = new ArrayList<>();
	private String error;

	public void addResultStep(String step) {
		resultSteps.add(step);
	}
}
