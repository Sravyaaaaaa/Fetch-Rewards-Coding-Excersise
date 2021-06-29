package com.rewards.demo.entity;

import java.time.LocalDateTime;

public class Transaction {
	String name;
	int points;
	LocalDateTime date;

	public Transaction(String name, int points, LocalDateTime date) {
		this.name = name;
		this.points = points;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}