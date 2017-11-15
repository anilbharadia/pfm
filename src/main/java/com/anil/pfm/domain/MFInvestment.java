package com.anil.pfm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A MFInvestment.
 */
@Entity
@Table(name = "mf_investment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MFInvestment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "purchase_date", nullable = false)
    private Instant purchaseDate;

    @NotNull
    @Column(name = "nav_date", nullable = false)
    private Instant navDate;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @Column(name = "nav", precision=10, scale=2)
    private BigDecimal nav;

    @Column(name = "unit", precision=10, scale=2)
    private BigDecimal unit;

    @ManyToOne
    private MutualFund fund;

    @ManyToOne
    private MFPortfolio folio;

    @ManyToOne
    private Goal goal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPurchaseDate() {
        return purchaseDate;
    }

    public MFInvestment purchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Instant getNavDate() {
        return navDate;
    }

    public MFInvestment navDate(Instant navDate) {
        this.navDate = navDate;
        return this;
    }

    public void setNavDate(Instant navDate) {
        this.navDate = navDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public MFInvestment amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getNav() {
        return nav;
    }

    public MFInvestment nav(BigDecimal nav) {
        this.nav = nav;
        return this;
    }

    public void setNav(BigDecimal nav) {
        this.nav = nav;
    }

    public BigDecimal getUnit() {
        return unit;
    }

    public MFInvestment unit(BigDecimal unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(BigDecimal unit) {
        this.unit = unit;
    }

    public MutualFund getFund() {
        return fund;
    }

    public MFInvestment fund(MutualFund mutualFund) {
        this.fund = mutualFund;
        return this;
    }

    public void setFund(MutualFund mutualFund) {
        this.fund = mutualFund;
    }

    public MFPortfolio getFolio() {
        return folio;
    }

    public MFInvestment folio(MFPortfolio mFPortfolio) {
        this.folio = mFPortfolio;
        return this;
    }

    public void setFolio(MFPortfolio mFPortfolio) {
        this.folio = mFPortfolio;
    }

    public Goal getGoal() {
        return goal;
    }

    public MFInvestment goal(Goal goal) {
        this.goal = goal;
        return this;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
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
        MFInvestment mFInvestment = (MFInvestment) o;
        if (mFInvestment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mFInvestment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MFInvestment{" +
            "id=" + getId() +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", navDate='" + getNavDate() + "'" +
            ", amount='" + getAmount() + "'" +
            ", nav='" + getNav() + "'" +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}
