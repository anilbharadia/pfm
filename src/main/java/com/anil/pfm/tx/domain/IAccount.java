package com.anil.pfm.tx.domain;

import java.math.BigDecimal;

public interface IAccount {

	BigDecimal getBalance();

	void setBalance(BigDecimal balance);
	
}
