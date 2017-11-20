package com.anil.pfm.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.anil.pfm.domain.enumeration.PPFTransactionType;

/**
 * A DTO for the PPFTransaction entity.
 */
public class PPFTransactionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private Instant date;

    private String desc;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private PPFTransactionType type;

    private PPFAccountDTO account;
    
    private Long accountId;
    
    private Long fromAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PPFTransactionType getType() {
        return type;
    }

    public void setType(PPFTransactionType type) {
        this.type = type;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long pPFAccountId) {
        this.accountId = pPFAccountId;
    }
    
    public PPFAccountDTO getAccount() {
		return account;
	}

	public void setAccount(PPFAccountDTO account) {
		this.account = account;
	}
	
	public Long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(Long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PPFTransactionDTO pPFTransactionDTO = (PPFTransactionDTO) o;
        if(pPFTransactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pPFTransactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PPFTransactionDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", desc='" + getDesc() + "'" +
            ", amount='" + getAmount() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
