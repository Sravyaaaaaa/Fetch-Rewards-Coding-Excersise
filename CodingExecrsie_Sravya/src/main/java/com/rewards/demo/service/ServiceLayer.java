package com.rewards.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rewards.demo.entity.Balance;
import com.rewards.demo.entity.DeductedBalance;
import com.rewards.demo.entity.Transaction;

public interface ServiceLayer {

	public ResponseEntity<String> addTransaction(Transaction transaction);

	public ResponseEntity<List<DeductedBalance>> spendPoints(int points);

	public ResponseEntity<List<Balance>> getAllPayersBalance();

}
