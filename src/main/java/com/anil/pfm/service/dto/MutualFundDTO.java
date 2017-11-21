package com.anil.pfm.service.dto;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the MutualFund entity.
 */
public class MutualFundDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private String name;

    private String manager;

    private Long amcId;

    private AMCDTO amc;

    private MFCategoryDTO category;

    private Long categoryId;

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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Long getAmcId() {
        return amcId;
    }

    public void setAmcId(Long aMCId) {
        this.amcId = aMCId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long mFCategoryId) {
        this.categoryId = mFCategoryId;
    }

    public AMCDTO getAmc() {
		return amc;
	}

	public void setAmc(AMCDTO amc) {
		this.amc = amc;
	}

	public MFCategoryDTO getCategory() {
		return category;
	}

	public void setCategory(MFCategoryDTO category) {
		this.category = category;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MutualFundDTO mutualFundDTO = (MutualFundDTO) o;
        if(mutualFundDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mutualFundDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MutualFundDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", manager='" + getManager() + "'" +
            "}";
    }
}
