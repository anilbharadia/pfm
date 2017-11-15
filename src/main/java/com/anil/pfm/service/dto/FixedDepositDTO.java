package com.anil.pfm.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.anil.pfm.domain.enumeration.FDStatus;

/**
 * A DTO for the FixedDeposit entity.
 */
public class FixedDepositDTO implements Serializable {

    private Long id;

    private String accountNumber;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Instant openDate;

    @NotNull
    private Instant maturityDate;

    @NotNull
    private FDStatus status;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Instant openDate) {
        this.openDate = openDate;
    }

    public Instant getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Instant maturityDate) {
        this.maturityDate = maturityDate;
    }

    public FDStatus getStatus() {
        return status;
    }

    public void setStatus(FDStatus status) {
        this.status = status;
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

        FixedDepositDTO fixedDepositDTO = (FixedDepositDTO) o;
        if(fixedDepositDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fixedDepositDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FixedDepositDTO{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", amount='" + getAmount() + "'" +
            ", openDate='" + getOpenDate() + "'" +
            ", maturityDate='" + getMaturityDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
