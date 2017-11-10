package com.anil.pfm.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ExpenseCategory entity.
 */
public class ExpenseCategoryDTO implements Serializable {

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

    public void setParentId(Long expenseCategoryId) {
        this.parentId = expenseCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExpenseCategoryDTO expenseCategoryDTO = (ExpenseCategoryDTO) o;
        if(expenseCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), expenseCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExpenseCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
