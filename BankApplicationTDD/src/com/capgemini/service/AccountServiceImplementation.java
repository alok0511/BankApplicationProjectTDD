package com.capgemini.service;

import java.util.LinkedList;

import com.capgemini.beans.Account;
import com.capgemini.exception.InvalidAccountNumberException;
import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InsufficientOpeningBalanceException;
import com.capgemini.repo.AccountRepo;


public class AccountServiceImplementation implements AccountService {

	AccountRepo accountRepo;
	
	public AccountServiceImplementation(AccountRepo accountRepo) {
		super();
		this.accountRepo = accountRepo;
	}
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	
	@Override
	public Account createAccount(int accNumber, int amount)throws InsufficientOpeningBalanceException{
		if(amount < 500){
			throw new InsufficientOpeningBalanceException();
		}
		Account account = new Account();
		account.setAccNumber(accNumber);
		account.setAmount(amount);
		if(accountRepo.save(account))
		{
			return account;
		}
		return null;
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#depositAmount(int, int)
	 */
	
	@Override
	public int depositAmount(int accNumber, int amount) throws InvalidAccountNumberException{
		Account account = accountRepo.searchAccount(accNumber);
		if(account == null){
		
			throw new InvalidAccountNumberException();
		}
		account.setAmount(account.getAmount() + amount);
		accountRepo.save(account);
		
		return account.getAmount();
	}
	

	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#withdrawAmount(int, int)
	 */
	
	@Override
	public int withdrawAmount(int accNumber, int amount) throws InvalidAccountNumberException, InsufficientBalanceException{
		Account account = accountRepo.searchAccount(accNumber);
		if(account == null){
			
			throw new InvalidAccountNumberException();
		}
		
		if(account.getAmount() < amount){
			throw new InsufficientBalanceException();
		}
			account.setAmount(account.getAmount() - amount);
			accountRepo.save(account);
			return account.getAmount();
	}

}
