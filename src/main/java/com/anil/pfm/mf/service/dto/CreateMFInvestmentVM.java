package com.anil.pfm.mf.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the MFInvestment entity.
 */
public class CreateMFInvestmentVM implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Instant purchaseDate;

	private Instant navDate;

	@NotNull
	private BigDecimal amount;

	private BigDecimal nav;

	private BigDecimal unit;

	@NotNull
	private Long fundId;

	private Long folioId;

	private Long goalId;

	private Long fromAccountId;

	public Instant getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Instant purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Instant getNavDate() {
		return navDate;
	}

	public void setNavDate(Instant navDate) {
		this.navDate = navDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long mutualFundId) {
		this.fundId = mutualFundId;
	}

	public Long getFolioId() {
		return folioId;
	}

	public void setFolioId(Long mFPortfolioId) {
		this.folioId = mFPortfolioId;
	}

	public Long getGoalId() {
		return goalId;
	}

	public void setGoalId(Long goalId) {
		this.goalId = goalId;
	}
	
	public Long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(Long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

}
