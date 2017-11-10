package com.anil.pfm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ExpenseCategory.
 */
@Entity
@Table(name = "expense_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExpenseCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private ExpenseCategory parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExpenseCategory> subCategories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ExpenseCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExpenseCategory getParent() {
        return parent;
    }

    public ExpenseCategory parent(ExpenseCategory expenseCategory) {
        this.parent = expenseCategory;
        return this;
    }

    public void setParent(ExpenseCategory expenseCategory) {
        this.parent = expenseCategory;
    }

    public Set<ExpenseCategory> getSubCategories() {
        return subCategories;
    }

    public ExpenseCategory subCategories(Set<ExpenseCategory> expenseCategories) {
        this.subCategories = expenseCategories;
        return this;
    }

    public ExpenseCategory addSubCategories(ExpenseCategory expenseCategory) {
        this.subCategories.add(expenseCategory);
        expenseCategory.setParent(this);
        return this;
    }

    public ExpenseCategory removeSubCategories(ExpenseCategory expenseCategory) {
        this.subCategories.remove(expenseCategory);
        expenseCategory.setParent(null);
        return this;
    }

    public void setSubCategories(Set<ExpenseCategory> expenseCategories) {
        this.subCategories = expenseCategories;
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
        ExpenseCategory expenseCategory = (ExpenseCategory) o;
        if (expenseCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), expenseCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExpenseCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
