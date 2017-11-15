package com.anil.pfm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MutualFund.
 */
@Entity
@Table(name = "mutual_fund")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MutualFund implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "manager", nullable = false)
    private String manager;

    @ManyToOne
    private AMC amc;

    @ManyToOne
    private MFCategory category;

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

    public MutualFund name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public MutualFund manager(String manager) {
        this.manager = manager;
        return this;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public AMC getAmc() {
        return amc;
    }

    public MutualFund amc(AMC aMC) {
        this.amc = aMC;
        return this;
    }

    public void setAmc(AMC aMC) {
        this.amc = aMC;
    }

    public MFCategory getCategory() {
        return category;
    }

    public MutualFund category(MFCategory mFCategory) {
        this.category = mFCategory;
        return this;
    }

    public void setCategory(MFCategory mFCategory) {
        this.category = mFCategory;
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
        MutualFund mutualFund = (MutualFund) o;
        if (mutualFund.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mutualFund.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MutualFund{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", manager='" + getManager() + "'" +
            "}";
    }
}
