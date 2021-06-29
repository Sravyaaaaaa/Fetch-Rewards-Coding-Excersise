package com.rewards.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.demo.entity.Balance;
import com.rewards.demo.entity.DeductedBalance;
import com.rewards.demo.entity.Points;
import com.rewards.demo.entity.Transaction;
import com.rewards.demo.service.ServiceLayer;

@RestController
@RequestMapping("/api")
public class ControllerLayer {

	@Autowired
	private ServiceLayer service;

	@PostMapping(value = "/spendPoints")
	public ResponseEntity<List<DeductedBalance>> spendPoints(@RequestBody Points points) {

		return service.spendPoints(points.getPoints());
	}

	@PostMapping(value = "/addTransaction")
	public ResponseEntity<String> addTransaction(@RequestBody Transaction transaction) {
		return service.addTransaction(transaction);
	}

	@GetMapping(value = "/getAllPayersBalance")
	public ResponseEntity<List<Balance>> getAllPayersBalance() {
		return service.getAllPayersBalance();
	}

}
