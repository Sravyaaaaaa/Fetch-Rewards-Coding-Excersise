package com.rewards.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rewards.demo.entity.Balance;
import com.rewards.demo.entity.DeductedBalance;
import com.rewards.demo.entity.Transaction;

@Service
public class ServiceImplLayer implements ServiceLayer {

	Map<String, List<Integer>> transactionMap = new HashMap<>();
	Map<Integer, Transaction> transactionIdMap = new LinkedHashMap<>();

	static int availableBalance;
	static int id = 0;

	public ResponseEntity<String> addTransaction(Transaction transaction) {

		if (transactionMap.containsKey(transaction.getName())) {

			List<Integer> idList = transactionMap.get(transaction.getName());
			if (transaction.getPoints() < 0) {

				int amount = -1 * transaction.getPoints();

				int balance = balance(transaction.getName()).getBody();

				if (balance < amount)
					return new ResponseEntity<String>("Transaction Unsucessful", HttpStatus.BAD_REQUEST);

				List<Integer> idToRemove = new ArrayList<>();

				for (int i : idList) {
					Transaction trans = transactionIdMap.get(i);
					if (trans.getPoints() >= amount) {
						trans.setPoints(trans.getPoints() - amount);
						transactionIdMap.put(i, trans);
						break;
					} else {
						amount -= trans.getPoints();
						trans.setPoints(0);
						transactionIdMap.put(i, trans);
					}

					if (trans.getPoints() == 0) {
						transactionIdMap.remove(i);
						idToRemove.add(i);
					}
				}

				for (int i : idToRemove) {
					idList.remove(new Integer(i));
				}

				transactionMap.put(transaction.getName(), idList);

				availableBalance += transaction.getPoints();
			} else {
				idList.add(++id);
				transactionMap.put(transaction.getName(), idList);
				transactionIdMap.put(id, transaction);
				availableBalance += transaction.getPoints();

			}
		} else {
			if (transaction.getPoints() <= 0)
				return new ResponseEntity<String>("Transaction Unsucessful", HttpStatus.BAD_REQUEST);

			List<Integer> idList = new ArrayList<>();
			idList.add(++id);
			transactionMap.put(transaction.getName(), idList);
			transactionIdMap.put(id, transaction);
			availableBalance += transaction.getPoints();
		}

		return new ResponseEntity<String>("Transaction Sucessful", HttpStatus.OK);

	}

	public ResponseEntity<List<DeductedBalance>> spendPoints(int points) {

		List<DeductedBalance> deductedBalanceList = new ArrayList<DeductedBalance>();
		if (points > availableBalance)
			return new ResponseEntity<List<DeductedBalance>>(deductedBalanceList, HttpStatus.BAD_REQUEST);

		List<Integer> removeIds = new ArrayList<>();
		for (int i : transactionIdMap.keySet()) {

			if (transactionIdMap.get(i).getPoints() >= points) {

				deductedBalanceList
						.add(new DeductedBalance(transactionIdMap.get(i).getName(), -1 * points, LocalDateTime.now()));

				System.out.println(transactionIdMap.get(i).getName() + ", -" + points + ", " + LocalDateTime.now());

				transactionIdMap.get(i).setPoints(transactionIdMap.get(i).getPoints() - points);
				if (transactionIdMap.get(i).getPoints() == 0) {
					transactionMap.get(transactionIdMap.get(i).getName()).remove(new Integer(i));
					removeIds.add(i);
				}
				break;
			} else {
				if (transactionIdMap.get(i).getPoints() > 0) {
					deductedBalanceList.add(new DeductedBalance(transactionIdMap.get(i).getName(),
							-1 * transactionIdMap.get(i).getPoints(), LocalDateTime.now()));
					System.out.println(transactionIdMap.get(i).getName() + ", -" + transactionIdMap.get(i).getPoints()
							+ ", " + LocalDateTime.now());
					points -= transactionIdMap.get(i).getPoints();
					transactionIdMap.get(i).setPoints(0);
					transactionMap.get(transactionIdMap.get(i).getName()).remove(new Integer(i));
					removeIds.add(i);
				}
			}

		}

		for (int i : removeIds) {
			transactionIdMap.remove(i);
		}
		availableBalance -= points;

		return new ResponseEntity<List<DeductedBalance>>(deductedBalanceList, HttpStatus.OK);
	}

	public ResponseEntity<List<Balance>> getAllPayersBalance() {

		List<Balance> allBalance = new ArrayList<Balance>();
		for (String payee : transactionMap.keySet()) {
			int amount = 0;
			List<Integer> ids = transactionMap.get(payee);

			for (int id : ids) {
				amount += transactionIdMap.get(id).getPoints();
			}

			allBalance.add(new Balance(payee, amount));

			System.out.println(payee + " : " + amount);
		}

		return new ResponseEntity<List<Balance>>(allBalance, HttpStatus.OK);
	}

	private ResponseEntity<Integer> balance(String str) {
		List<Integer> ids = transactionMap.get(str);

		int amount = 0;
		for (int id : ids) {
			amount += transactionIdMap.get(id).getPoints();
		}

		return new ResponseEntity<Integer>(amount, HttpStatus.OK);
	}


}
