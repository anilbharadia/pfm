package com.anil.pfm.tx.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.anil.pfm.domain.ExpenseCategory;
import com.anil.pfm.domain.IncomeCategory;
import com.anil.pfm.service.dto.ExpenseCategoryDTO;
import com.anil.pfm.service.dto.IncomeCategoryDTO;
import com.anil.pfm.service.dto.MyAccountDTO;
import com.anil.pfm.service.dto.TransactionTypeDTO;

/**
 * A DTO for the Transaction entity.
 */
public class TransactionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String desc;

    @NotNull
    private Instant date;

    
    private MyAccountDTO account;
    private Long accountId;

    private TransactionTypeDTO txType;
    private Long txTypeId;

    private ExpenseCategoryDTO expenseCategory;
    private Long expenseCategoryId;

    private IncomeCategoryDTO incomeCategory;
    private Long incomeCategoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public MyAccountDTO getAccount() {
		return account;
	}

	public void setAccount(MyAccountDTO account) {
		this.account = account;
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
    
    

    public TransactionTypeDTO getTxType() {
		return txType;
	}

	public void setTxType(TransactionTypeDTO txType) {
		this.txType = txType;
	}

	public ExpenseCategoryDTO getExpenseCategory() {
		return expenseCategory;
	}

	public void setExpenseCategory(ExpenseCategoryDTO expenseCategory) {
		this.expenseCategory = expenseCategory;
	}

	public IncomeCategoryDTO getIncomeCategory() {
		return incomeCategory;
	}

	public void setIncomeCategory(IncomeCategoryDTO incomeCategory) {
		this.incomeCategory = incomeCategory;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionDTO transactionDTO = (TransactionDTO) o;
        if(transactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", amount='" + getAmount() + "'" +
            ", desc='" + getDesc() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
