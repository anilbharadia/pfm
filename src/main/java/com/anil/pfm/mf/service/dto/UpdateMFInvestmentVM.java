package com.anil.pfm.mf.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the MFInvestment entity.
 */
public class UpdateMFInvestmentVM implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long id;

	private Instant navDate;

	private BigDecimal nav;

	private BigDecimal unit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getNavDate() {
		return navDate;
	}

	public void setNavDate(Instant navDate) {
		this.navDate = navDate;
	}

	public BigDecimal getNav() {
		return nav;
	}

	public void setNav(BigDecimal nav) {
		this.nav = nav;
	}

	public BigDecimal getUnit() {
		return unit;
	}

	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		UpdateMFInvestmentVM mFInvestmentDTO = (UpdateMFInvestmentVM) o;
		if (mFInvestmentDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), mFInvestmentDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

}
