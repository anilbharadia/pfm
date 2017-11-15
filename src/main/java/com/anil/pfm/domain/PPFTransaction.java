package com.anil.pfm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import com.anil.pfm.domain.enumeration.PPFTransactionType;

/**
 * A PPFTransaction.
 */
@Entity
@Table(name = "ppf_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PPFTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private Instant date;

    @Column(name = "jhi_desc")
    private String desc;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private PPFTransactionType type;

    @ManyToOne
    private PPFAccount account;

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

    public PPFTransaction date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public PPFTransaction desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PPFTransaction amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PPFTransactionType getType() {
        return type;
    }

    public PPFTransaction type(PPFTransactionType type) {
        this.type = type;
        return this;
    }

    public void setType(PPFTransactionType type) {
        this.type = type;
    }

    public PPFAccount getAccount() {
        return account;
    }

    public PPFTransaction account(PPFAccount pPFAccount) {
        this.account = pPFAccount;
        return this;
    }

    public void setAccount(PPFAccount pPFAccount) {
        this.account = pPFAccount;
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
        PPFTransaction pPFTransaction = (PPFTransaction) o;
        if (pPFTransaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pPFTransaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PPFTransaction{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", desc='" + getDesc() + "'" +
            ", amount='" + getAmount() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
