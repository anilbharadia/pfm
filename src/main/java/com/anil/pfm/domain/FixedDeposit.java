package com.anil.pfm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import com.anil.pfm.domain.enumeration.FDStatus;

/**
 * A FixedDeposit.
 */
@Entity
@Table(name = "fixed_deposit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FixedDeposit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "open_date", nullable = false)
    private Instant openDate;

    @NotNull
    @Column(name = "maturity_date", nullable = false)
    private Instant maturityDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FDStatus status;

    @ManyToOne
    private Bank bank;

    @ManyToOne
    private Goal goal;

    @ManyToOne
    private Person owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public FixedDeposit accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public FixedDeposit amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getOpenDate() {
        return openDate;
    }

    public FixedDeposit openDate(Instant openDate) {
        this.openDate = openDate;
        return this;
    }

    public void setOpenDate(Instant openDate) {
        this.openDate = openDate;
    }

    public Instant getMaturityDate() {
        return maturityDate;
    }

    public FixedDeposit maturityDate(Instant maturityDate) {
        this.maturityDate = maturityDate;
        return this;
    }

    public void setMaturityDate(Instant maturityDate) {
        this.maturityDate = maturityDate;
    }

    public FDStatus getStatus() {
        return status;
    }

    public FixedDeposit status(FDStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(FDStatus status) {
        this.status = status;
    }

    public Bank getBank() {
        return bank;
    }

    public FixedDeposit bank(Bank bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Goal getGoal() {
        return goal;
    }

    public FixedDeposit goal(Goal goal) {
        this.goal = goal;
        return this;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Person getOwner() {
        return owner;
    }

    public FixedDeposit owner(Person person) {
        this.owner = person;
        return this;
    }

    public void setOwner(Person person) {
        this.owner = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FixedDeposit fixedDeposit = (FixedDeposit) o;
        if (fixedDeposit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fixedDeposit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FixedDeposit{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", amount='" + getAmount() + "'" +
            ", openDate='" + getOpenDate() + "'" +
            ", maturityDate='" + getMaturityDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
