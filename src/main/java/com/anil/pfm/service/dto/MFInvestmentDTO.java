package com.anil.pfm.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MFInvestment entity.
 */
public class MFInvestmentDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant purchaseDate;

    @NotNull
    private Instant navDate;

    @NotNull
    private BigDecimal amount;

    private BigDecimal nav;

    private BigDecimal unit;

    private Long fundId;

    private Long folioId;

    private Long goalId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MFInvestmentDTO mFInvestmentDTO = (MFInvestmentDTO) o;
        if(mFInvestmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mFInvestmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MFInvestmentDTO{" +
            "id=" + getId() +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", navDate='" + getNavDate() + "'" +
            ", amount='" + getAmount() + "'" +
            ", nav='" + getNav() + "'" +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}
