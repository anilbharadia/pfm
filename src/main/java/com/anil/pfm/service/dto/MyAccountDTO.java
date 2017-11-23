package com.anil.pfm.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the MyAccount entity.
 */
public class MyAccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private String name;

    private BigDecimal balance;

    private Long bankAccountId;
    
    private BankAccountDTO bankAccount;

    private Long ownerId;
    
    private PersonDTO owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long personId) {
        this.ownerId = personId;
    }

    public PersonDTO getOwner() {
		return owner;
	}

	public void setOwner(PersonDTO owner) {
		this.owner = owner;
	}

	public BankAccountDTO getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccountDTO bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyAccountDTO myAccountDTO = (MyAccountDTO) o;
        if(myAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), myAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MyAccountDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", balance='" + getBalance() + "'" +
            "}";
    }
}
