package com.anil.pfm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BankAccount.
 */
@Entity
@Table(name = "bank_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ac_number", nullable = false)
    private String acNumber;

    @Column(name = "ifsc")
    private String ifsc;

    @Column(name = "micr")
    private String micr;

    @ManyToOne
    private Bank bank;

    @OneToOne(mappedBy = "bankAccount")
    @JsonIgnore
    private MyAccount account;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcNumber() {
        return acNumber;
    }

    public BankAccount acNumber(String acNumber) {
        this.acNumber = acNumber;
        return this;
    }

    public void setAcNumber(String acNumber) {
        this.acNumber = acNumber;
    }

    public String getIfsc() {
        return ifsc;
    }

    public BankAccount ifsc(String ifsc) {
        this.ifsc = ifsc;
        return this;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getMicr() {
        return micr;
    }

    public BankAccount micr(String micr) {
        this.micr = micr;
        return this;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }

    public Bank getBank() {
        return bank;
    }

    public BankAccount bank(Bank bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public MyAccount getAccount() {
        return account;
    }

    public BankAccount account(MyAccount myAccount) {
        this.account = myAccount;
        return this;
    }

    public void setAccount(MyAccount myAccount) {
        this.account = myAccount;
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
        BankAccount bankAccount = (BankAccount) o;
        if (bankAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bankAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankAccount{" +
            "id=" + getId() +
            ", acNumber='" + getAcNumber() + "'" +
            ", ifsc='" + getIfsc() + "'" +
            ", micr='" + getMicr() + "'" +
            "}";
    }
}
