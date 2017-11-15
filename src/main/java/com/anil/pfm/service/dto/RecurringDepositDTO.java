package com.anil.pfm.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.anil.pfm.domain.enumeration.RDStatus;

/**
 * A DTO for the RecurringDeposit entity.
 */
public class RecurringDepositDTO implements Serializable {

    private Long id;

    private String accountNumber;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    @NotNull
    private Integer installmentDateDay;

    @NotNull
    private BigDecimal currentBalance;

    @NotNull
    private RDStatus status;

    private BigDecimal interestRate;

    private Long bankId;

    private Long goalId;

    private Long ownerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getInstallmentDateDay() {
        return installmentDateDay;
    }

    public void setInstallmentDateDay(Integer installmentDateDay) {
        this.installmentDateDay = installmentDateDay;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public RDStatus getStatus() {
        return status;
    }

    public void setStatus(RDStatus status) {
        this.status = status;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long personId) {
        this.ownerId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecurringDepositDTO recurringDepositDTO = (RecurringDepositDTO) o;
        if(recurringDepositDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recurringDepositDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecurringDepositDTO{" +
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
