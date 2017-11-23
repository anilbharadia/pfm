package com.anil.pfm.mf.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.anil.pfm.domain.Person;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MFPortfolio.
 */
@Entity
@Table(name = "mf_portfolio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MFPortfolio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "folio_number", nullable = false)
    private String folioNumber;

    @ManyToOne
    private AMC amc;

    @ManyToOne
    private Person owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolioNumber() {
        return folioNumber;
    }

    public MFPortfolio folioNumber(String folioNumber) {
        this.folioNumber = folioNumber;
        return this;
    }

    public void setFolioNumber(String folioNumber) {
        this.folioNumber = folioNumber;
    }

    public AMC getAmc() {
        return amc;
    }

    public MFPortfolio amc(AMC aMC) {
        this.amc = aMC;
        return this;
    }

    public void setAmc(AMC aMC) {
        this.amc = aMC;
    }

    public Person getOwner() {
        return owner;
    }

    public MFPortfolio owner(Person person) {
        this.owner = person;
        return this;
    }

    public void setOwner(Person person) {
        this.owner = person;
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
        MFPortfolio mFPortfolio = (MFPortfolio) o;
        if (mFPortfolio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mFPortfolio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MFPortfolio{" +
            "id=" + getId() +
            ", folioNumber='" + getFolioNumber() + "'" +
            "}";
    }
}
