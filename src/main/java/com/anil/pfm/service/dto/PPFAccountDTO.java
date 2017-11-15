package com.anil.pfm.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PPFAccount entity.
 */
public class PPFAccountDTO implements Serializable {

    private Long id;

    @NotNull
    private String accountNumber;

    @NotNull
    private BigDecimal balance;

    private Long bankId;

    private Long ownerId;

    private Long goalId;

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long personId) {
        this.ownerId = personId;
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PPFAccountDTO pPFAccountDTO = (PPFAccountDTO) o;
        if(pPFAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pPFAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PPFAccountDTO{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", balance='" + getBalance() + "'" +
            "}";
    }
}
