package com.anil.pfm.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MutualFund entity.
 */
public class MutualFundDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String manager;

    private Long amcId;

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
