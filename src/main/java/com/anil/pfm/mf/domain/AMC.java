package com.anil.pfm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AMC.
 */
@Entity
@Table(name = "amc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AMC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "website")
    private String website;

    @Column(name = "logo_url")
    private String logoURL;

    @ManyToOne
    private MFRTAgent mfrtAgent;

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

    public AMC name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public AMC website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public AMC logoURL(String logoURL) {
        this.logoURL = logoURL;
        return this;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public MFRTAgent getMfrtAgent() {
        return mfrtAgent;
    }

    public AMC mfrtAgent(MFRTAgent mFRTAgent) {
        this.mfrtAgent = mFRTAgent;
        return this;
    }

    public void setMfrtAgent(MFRTAgent mFRTAgent) {
        this.mfrtAgent = mFRTAgent;
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
        AMC aMC = (AMC) o;
        if (aMC.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aMC.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AMC{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", website='" + getWebsite() + "'" +
            ", logoURL='" + getLogoURL() + "'" +
            "}";
    }
}
