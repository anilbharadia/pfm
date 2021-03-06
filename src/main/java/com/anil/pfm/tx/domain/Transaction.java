package com.anil.pfm.tx.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.anil.pfm.domain.ExpenseCategory;
import com.anil.pfm.domain.IncomeCategory;
import com.anil.pfm.domain.TransactionType;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "opening_balance", precision=10, scale=2, nullable = false)
    private BigDecimal openingBalance;

    @NotNull
    @Column(name = "closing_balance", precision=10, scale=2, nullable = false)
    private BigDecimal closingBalance;

    @NotNull
    @Column(name = "jhi_desc", nullable = false)
    private String desc;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private Instant date;

    @ManyToOne
    private MyAccount account;
    
    @ManyToOne
    private MyAccount transferAccount;

    @ManyToOne
    private TransactionType txType;

    @ManyToOne
    private ExpenseCategory expenseCategory;

    @ManyToOne
    private IncomeCategory incomeCategory;
    
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public Transaction desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Instant getDate() {
        return date;
    }

    public Transaction date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public MyAccount getAccount() {
        return account;
    }

    public Transaction account(MyAccount myAccount) {
        this.account = myAccount;
        return this;
    }

    public void setAccount(MyAccount myAccount) {
        this.account = myAccount;
    }

    public TransactionType getTxType() {
        return txType;
    }

    public Transaction txType(TransactionType transactionType) {
        this.txType = transactionType;
        return this;
    }

    public void setTxType(TransactionType transactionType) {
        this.txType = transactionType;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public Transaction expenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
        return this;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    public Transaction incomeCategory(IncomeCategory incomeCategory) {
        this.incomeCategory = incomeCategory;
        return this;
    }

    public void setIncomeCategory(IncomeCategory incomeCategory) {
        this.incomeCategory = incomeCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	public BigDecimal getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(BigDecimal closingBalance) {
		this.closingBalance = closingBalance;
	}
	
    public MyAccount getTransferAccount() {
		return transferAccount;
	}

	public void setTransferAccount(MyAccount transferAccount) {
		this.transferAccount = transferAccount;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        if (transaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaction.getId());
    }

	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", amount='" + getAmount() + "'" +
            ", desc='" + getDesc() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
