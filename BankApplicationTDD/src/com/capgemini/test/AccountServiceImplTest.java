package com.capgemini.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.beans.Account;
import com.capgemini.exception.InsufficientBalanceException;
import com.capgemini.exception.InsufficientOpeningBalanceException;
import com.capgemini.exception.InvalidAccountNumberException;
import com.capgemini.repo.AccountRepo;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImplementation;
import static org.mockito.Mockito.when;

public class AccountServiceImplTest {
	
	@Mock
	AccountRepo accountRepo;
	AccountService accountService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImplementation(accountRepo);
	}
	// Create Account
	
	@Test(expected=com.capgemini.exception.InsufficientOpeningBalanceException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowAnExceptiom()throws InsufficientOpeningBalanceException {
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInformationIsPassesAccountShouldBeCreatedSuccessfully()throws InsufficientOpeningBalanceException {
		Account account = new Account();
		account.setAccNumber(101);
		account.setAmount(5000);
		when(accountRepo.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(101, 5000));
	}
	
	//Deposit Amount
	
	@Test(expected=com.capgemini.exception.InvalidAccountNumberException.class)
	public void whenYouDepositAmountAndAccountNumberIsInvalid()throws InvalidAccountNumberException{
		accountService.depositAmount(102, 5000);
	}
	
	@Test
	public void whenTheValidInformationIsPassedAmountIsSuccessfullyCredited()throws InvalidAccountNumberException {
		Account account = new Account();
		account.setAccNumber(101);
		account.setAmount(5000);
		
		when(accountRepo.searchAccount(101)).thenReturn(account);
		
		assertEquals(account.getAmount() + 2000, accountService.depositAmount(101, 2000));
	}
	
	//Withdraw Account
	
	@Test(expected=com.capgemini.exception.InvalidAccountNumberException.class)
	public void whenTheValidAccountNumberIsNotPassedItShouldThrowAnException()throws InvalidAccountNumberException, InsufficientBalanceException{
		
		accountService.withdrawAmount(102, 5000);
	}
	
	
	@Test(expected=com.capgemini.exception.InsufficientBalanceException.class)
	public void whenTheAmountWithdrawnIsMoreThanAmountPresentInAccount()throws InsufficientBalanceException, InvalidAccountNumberException{
		Account account = new Account();
		account.setAccNumber(101);
		account.setAmount(5000);
		when(accountRepo.searchAccount(101)).thenReturn(account);
		accountService.withdrawAmount(101, 6000);
	}
	
	@Test
	public void whenTheValidInformationIsPassedAmountIsSuccessfullyDebited()throws InvalidAccountNumberException, InsufficientBalanceException {
		Account account = new Account();
		account.setAccNumber(101);
		account.setAmount(5000);
		when(accountRepo.searchAccount(101)).thenReturn(account);
		assertEquals(account.getAmount()-1500, accountService.withdrawAmount(101, 1500));
	}
	
	
}
