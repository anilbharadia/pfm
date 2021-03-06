package com.anil.pfm.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the BankAccount entity.
 */
public class BankAccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull
	private String acNumber;

	private String ifsc;

	private String micr;

	private Long bankId;

	private BankDTO bank;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAcNumber() {
		return acNumber;
	}

	public void setAcNumber(String acNumber) {
		this.acNumber = acNumber;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getMicr() {
		return micr;
	}

	public void setMicr(String micr) {
		this.micr = micr;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public BankDTO getBank() {
		return bank;
	}

	public void setBank(BankDTO bank) {
		this.bank = bank;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		BankAccountDTO bankAccountDTO = (BankAccountDTO) o;
		if (bankAccountDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), bankAccountDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "BankAccountDTO{" + "id=" + getId() + ", acNumber='" + getAcNumber() + "'" + ", ifsc='" + getIfsc() + "'"
				+ ", micr='" + getMicr() + "'" + "}";
	}
}
