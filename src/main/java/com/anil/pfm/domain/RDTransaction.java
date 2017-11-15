package com.anil.pfm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.anil.pfm.domain.enumeration.RDTransactionType;

/**
 * A RDTransaction.
 */
@Entity
@Table(name = "rd_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RDTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private Instant date;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private RDTransactionType type;

    @ManyToOne
    private RecurringDeposit recurringDeposit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public RDTransaction date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public RDTransactionType getType() {
        return type;
    }

    public RDTransaction type(RDTransactionType type) {
        this.type = type;
        return this;
    }

    public void setType(RDTransactionType type) {
        this.type = type;
    }

    public RecurringDeposit getRecurringDeposit() {
        return recurringDeposit;
    }

    public RDTransaction recurringDeposit(RecurringDeposit recurringDeposit) {
        this.recurringDeposit = recurringDeposit;
        return this;
    }

    public void setRecurringDeposit(RecurringDeposit recurringDeposit) {
        this.recurringDeposit = recurringDeposit;
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
        RDTransaction rDTransaction = (RDTransaction) o;
        if (rDTransaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rDTransaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RDTransaction{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
