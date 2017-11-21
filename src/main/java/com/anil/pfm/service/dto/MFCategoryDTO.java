package com.anil.pfm.service.dto;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the MFCategory entity.
 */
public class MFCategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private String name;

    private Long parentId;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long mFCategoryId) {
        this.parentId = mFCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MFCategoryDTO mFCategoryDTO = (MFCategoryDTO) o;
        if(mFCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mFCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MFCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
