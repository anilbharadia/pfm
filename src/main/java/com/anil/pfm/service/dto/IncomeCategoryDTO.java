package com.anil.pfm.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the IncomeCategory entity.
 */
public class IncomeCategoryDTO implements Serializable {

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

    public void setParentId(Long incomeCategoryId) {
        this.parentId = incomeCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncomeCategoryDTO incomeCategoryDTO = (IncomeCategoryDTO) o;
        if(incomeCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incomeCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncomeCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
