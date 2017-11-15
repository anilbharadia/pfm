package com.anil.pfm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import com.anil.pfm.domain.enumeration.RDStatus;

/**
 * A RecurringDeposit.
 */
@Entity
@Table(name = "recurring_deposit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RecurringDeposit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @NotNull
    @Column(name = "installment_date_day", nullable = false)
    private Integer installmentDateDay;

    @NotNull
    @Column(name = "current_balance", precision=10, scale=2, nullable = false)
    private BigDecimal currentBalance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RDStatus status;

    @Column(name = "interest_rate", precision=10, scale=2)
    private BigDecimal interestRate;

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

    public RecurringDeposit accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public RecurringDeposit startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public RecurringDeposit endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getInstallmentDateDay() {
        return installmentDateDay;
    }

    public RecurringDeposit installmentDateDay(Integer installmentDateDay) {
        this.installmentDateDay = installmentDateDay;
        return this;
    }

    public void setInstallmentDateDay(Integer installmentDateDay) {
        this.installmentDateDay = installmentDateDay;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public RecurringDeposit currentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
        return this;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public RDStatus getStatus() {
        return status;
    }

    public RecurringDeposit status(RDStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(RDStatus status) {
        this.status = status;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public RecurringDeposit interestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Bank getBank() {
        return bank;
    }

    public RecurringDeposit bank(Bank bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Goal getGoal() {
        return goal;
    }

    public RecurringDeposit goal(Goal goal) {
        this.goal = goal;
        return this;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Person getOwner() {
        return owner;
    }

    public RecurringDeposit owner(Person person) {
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
        RecurringDeposit recurringDeposit = (RecurringDeposit) o;
        if (recurringDeposit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recurringDeposit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecurringDeposit{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", installmentDateDay='" + getInstallmentDateDay() + "'" +
            ", currentBalance='" + getCurrentBalance() + "'" +
            ", status='" + getStatus() + "'" +
            ", interestRate='" + getInterestRate() + "'" +
            "}";
    }
}
