package com.anil.pfm.mf.service.dto;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.anil.pfm.service.dto.PersonDTO;

/**
 * A DTO for the MFPortfolio entity.
 */
public class MFPortfolioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private String folioNumber;

    private Long amcId;

    private Long ownerId;
    
    private AMCDTO amc;

    private PersonDTO owner;

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

    public AMCDTO getAmc() {
		return amc;
	}

	public void setAmc(AMCDTO amc) {
		this.amc = amc;
	}

	public PersonDTO getOwner() {
		return owner;
	}

	public void setOwner(PersonDTO owner) {
		this.owner = owner;
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
