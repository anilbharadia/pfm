package com.anil.pfm.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MFPortfolio entity.
 */
public class MFPortfolioDTO implements Serializable {

    private Long id;

    @NotNull
    private String folioNumber;

    private Long amcId;

    private Long ownerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolioNumber() {
        return folioNumber;
    }

    public void setFolioNumber(String folioNumber) {
        this.folioNumber = folioNumber;
    }

    public Long getAmcId() {
        return amcId;
    }

    public void setAmcId(Long aMCId) {
        this.amcId = aMCId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long personId) {
        this.ownerId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MFPortfolioDTO mFPortfolioDTO = (MFPortfolioDTO) o;
        if(mFPortfolioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mFPortfolioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MFPortfolioDTO{" +
            "id=" + getId() +
            ", folioNumber='" + getFolioNumber() + "'" +
            "}";
    }
}
