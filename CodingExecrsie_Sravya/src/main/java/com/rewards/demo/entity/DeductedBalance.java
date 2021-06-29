package com.rewards.demo.entity;

import java.time.LocalDateTime;

public class DeductedBalance extends Balance {

	LocalDateTime time;

	public DeductedBalance(String payerName, int balance, LocalDateTime time) {
		super(payerName, balance);
		this.time = time;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
