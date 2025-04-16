package com.temporal.activity;

import com.temporal.model.PaymentDetails;

public class OrderActivitiesImpl implements OrderActivities {

	@Override
	public void reserveInventory(String inventoryId) {
		System.out.println("Reserving inventory: " + inventoryId);

	}

	@Override
	public void chargePymt(PaymentDetails paymentDetails) {
		if (paymentDetails.isErrorFlag()) {
			throw new RuntimeException("Payment gateway error");
		}
		System.out.println("Charging payment for: " + paymentDetails.getAmount());

	}

	@Override
	public void cancelInventory(String inventoryId) {
		System.out.println("Cancelling inventory: " + inventoryId);

	}

}
