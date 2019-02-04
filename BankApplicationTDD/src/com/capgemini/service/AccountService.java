package com.capgemini.service;

import com.capgemini.beans.Account;
import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InsufficientOpeningBalanceException;
import com.capgemini.exception.InvalidAccountNumberException;

public interface AccountService {

	Account createAccount(int accNumber, int amount) throws InsufficientOpeningBalanceException;

	int depositAmount(int accNumber, int amount) throws InvalidAccountNumberException;

	int withdrawAmount(int accNumber, int amount) throws InvalidAccountNumberException, InsufficientBalanceException;

}