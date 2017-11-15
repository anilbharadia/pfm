package com.anil.pfm.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the LifeInsurancePolicy entity.
 */
public class LifeInsurancePolicyDTO implements Serializable {

    private Long id;

    @NotNull
    private String policyNumber;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal totalPremiumPaid;

    @NotNull
    private BigDecimal premium;

    @NotNull
    private BigDecimal sumAssured;

    private Long companyId;

    private Long ownerId;

    private Long goalId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalPremiumPaid() {
        return totalPremiumPaid;
    }

    public void setTotalPremiumPaid(BigDecimal totalPremiumPaid) {
        this.totalPremiumPaid = totalPremiumPaid;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public BigDecimal getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(BigDecimal sumAssured) {
        this.sumAssured = sumAssured;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long lifeInsuranceCompanyId) {
        this.companyId = lifeInsuranceCompanyId;
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

        LifeInsurancePolicyDTO lifeInsurancePolicyDTO = (LifeInsurancePolicyDTO) o;
        if(lifeInsurancePolicyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lifeInsurancePolicyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LifeInsurancePolicyDTO{" +
            "id=" + getId() +
            ", policyNumber='" + getPolicyNumber() + "'" +
            ", name='" + getName() + "'" +
            ", totalPremiumPaid='" + getTotalPremiumPaid() + "'" +
            ", premium='" + getPremium() + "'" +
            ", sumAssured='" + getSumAssured() + "'" +
            "}";
    }
}
