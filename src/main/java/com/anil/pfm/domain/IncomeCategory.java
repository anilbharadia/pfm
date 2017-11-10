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
 * A IncomeCategory.
 */
@Entity
@Table(name = "income_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IncomeCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private IncomeCategory parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IncomeCategory> subCategories = new HashSet<>();

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

    public IncomeCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IncomeCategory getParent() {
        return parent;
    }

    public IncomeCategory parent(IncomeCategory incomeCategory) {
        this.parent = incomeCategory;
        return this;
    }

    public void setParent(IncomeCategory incomeCategory) {
        this.parent = incomeCategory;
    }

    public Set<IncomeCategory> getSubCategories() {
        return subCategories;
    }

    public IncomeCategory subCategories(Set<IncomeCategory> incomeCategories) {
        this.subCategories = incomeCategories;
        return this;
    }

    public IncomeCategory addSubCategories(IncomeCategory incomeCategory) {
        this.subCategories.add(incomeCategory);
        incomeCategory.setParent(this);
        return this;
    }

    public IncomeCategory removeSubCategories(IncomeCategory incomeCategory) {
        this.subCategories.remove(incomeCategory);
        incomeCategory.setParent(null);
        return this;
    }

    public void setSubCategories(Set<IncomeCategory> incomeCategories) {
        this.subCategories = incomeCategories;
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
        IncomeCategory incomeCategory = (IncomeCategory) o;
        if (incomeCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incomeCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncomeCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
