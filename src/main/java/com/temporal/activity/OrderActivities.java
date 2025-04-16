package com.temporal.activity;

import com.temporal.model.PaymentDetails;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface OrderActivities {

	@ActivityMethod
	void reserveInventory(String inventoryId);

	void chargePymt(PaymentDetails paymentDetails);

	void cancelInventory(String inventoryId);

}
