package com.anil.pfm.tx.service.dto;


import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the Transaction entity.
 */
public class UpdateTransactionVM implements Serializable {

	private static final long serialVersionUID = 1L;

    private String desc;

    @NotNull
    private Long id;
    
    @NotNull
    private Instant date;

    private Long accountId;

    private Long txTypeId;

    private Long expenseCategoryId;

    private Long incomeCategoryId;

    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateTransactionVM vm = (UpdateTransactionVM) o;
        if (vm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vm.getId());
    }

	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
