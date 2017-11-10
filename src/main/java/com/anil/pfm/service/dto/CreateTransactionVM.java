package com.anil.pfm.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the Transaction entity.
 */
public class CreateTransactionVM implements Serializable {

	private static final long serialVersionUID = 1L;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String desc;

    @NotNull
    private Instant date;

    private Long accountId;

    private Long txTypeId;

    private Long expenseCategoryId;

    private Long incomeCategoryId;


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long myAccountId) {
        this.accountId = myAccountId;
    }

    public Long getTxTypeId() {
        return txTypeId;
    }

    public void setTxTypeId(Long transactionTypeId) {
        this.txTypeId = transactionTypeId;
    }

    public Long getExpenseCategoryId() {
        return expenseCategoryId;
    }

    public void setExpenseCategoryId(Long expenseCategoryId) {
        this.expenseCategoryId = expenseCategoryId;
    }

    public Long getIncomeCategoryId() {
        return incomeCategoryId;
    }

    public void setIncomeCategoryId(Long incomeCategoryId) {
        this.incomeCategoryId = incomeCategoryId;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((expenseCategoryId == null) ? 0 : expenseCategoryId.hashCode());
		result = prime * result + ((incomeCategoryId == null) ? 0 : incomeCategoryId.hashCode());
		result = prime * result + ((txTypeId == null) ? 0 : txTypeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreateTransactionVM other = (CreateTransactionVM) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (expenseCategoryId == null) {
			if (other.expenseCategoryId != null)
				return false;
		} else if (!expenseCategoryId.equals(other.expenseCategoryId))
			return false;
		if (incomeCategoryId == null) {
			if (other.incomeCategoryId != null)
				return false;
		} else if (!incomeCategoryId.equals(other.incomeCategoryId))
			return false;
		if (txTypeId == null) {
			if (other.txTypeId != null)
				return false;
		} else if (!txTypeId.equals(other.txTypeId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CreateTransactionVM [amount=" + amount + ", desc=" + desc + ", date=" + date + ", accountId="
				+ accountId + ", txTypeId=" + txTypeId + ", expenseCategoryId=" + expenseCategoryId
				+ ", incomeCategoryId=" + incomeCategoryId + "]";
	}
    
}
