package com.anil.pfm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A LifeInsurancePolicy.
 */
@Entity
@Table(name = "life_insurance_policy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LifeInsurancePolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "policy_number", nullable = false)
    private String policyNumber;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "total_premium_paid", precision=10, scale=2, nullable = false)
    private BigDecimal totalPremiumPaid;

    @NotNull
    @Column(name = "premium", precision=10, scale=2, nullable = false)
    private BigDecimal premium;

    @NotNull
    @Column(name = "sum_assured", precision=10, scale=2, nullable = false)
    private BigDecimal sumAssured;

    @ManyToOne
    private LifeInsuranceCompany company;

    @ManyToOne
    private Person owner;

    @ManyToOne
    private Goal goal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public LifeInsurancePolicy policyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
        return this;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getName() {
        return name;
    }

    public LifeInsurancePolicy name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalPremiumPaid() {
        return totalPremiumPaid;
    }

    public LifeInsurancePolicy totalPremiumPaid(BigDecimal totalPremiumPaid) {
        this.totalPremiumPaid = totalPremiumPaid;
        return this;
    }

    public void setTotalPremiumPaid(BigDecimal totalPremiumPaid) {
        this.totalPremiumPaid = totalPremiumPaid;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public LifeInsurancePolicy premium(BigDecimal premium) {
        this.premium = premium;
        return this;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public BigDecimal getSumAssured() {
        return sumAssured;
    }

    public LifeInsurancePolicy sumAssured(BigDecimal sumAssured) {
        this.sumAssured = sumAssured;
        return this;
    }

    public void setSumAssured(BigDecimal sumAssured) {
        this.sumAssured = sumAssured;
    }

    public LifeInsuranceCompany getCompany() {
        return company;
    }

    public LifeInsurancePolicy company(LifeInsuranceCompany lifeInsuranceCompany) {
        this.company = lifeInsuranceCompany;
        return this;
    }

    public void setCompany(LifeInsuranceCompany lifeInsuranceCompany) {
        this.company = lifeInsuranceCompany;
    }

    public Person getOwner() {
        return owner;
    }

    public LifeInsurancePolicy owner(Person person) {
        this.owner = person;
        return this;
    }

    public void setOwner(Person person) {
        this.owner = person;
    }

    public Goal getGoal() {
        return goal;
    }

    public LifeInsurancePolicy goal(Goal goal) {
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
        LifeInsurancePolicy lifeInsurancePolicy = (LifeInsurancePolicy) o;
        if (lifeInsurancePolicy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lifeInsurancePolicy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LifeInsurancePolicy{" +
            "id=" + getId() +
            ", policyNumber='" + getPolicyNumber() + "'" +
            ", name='" + getName() + "'" +
            ", totalPremiumPaid='" + getTotalPremiumPaid() + "'" +
            ", premium='" + getPremium() + "'" +
            ", sumAssured='" + getSumAssured() + "'" +
            "}";
    }
}
