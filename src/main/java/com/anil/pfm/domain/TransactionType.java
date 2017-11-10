package com.anil.pfm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TransactionType.
 */
@Entity
@Table(name = "transaction_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransactionType implements Serializable {

	public static final Long INCOME = 1L;
	public static final Long EXPENSE = 2L;
	public static final Long TRANSFER = 3L;
	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    public boolean isExpense() {
    	return this.id == EXPENSE;
    }
    
    public boolean isIncome() {
    	return this.id == INCOME;
    }
    
    public boolean isTransfer() {
    	return this.id == TRANSFER;
    } 
    
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

    public TransactionType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
        TransactionType transactionType = (TransactionType) o;
        if (transactionType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
