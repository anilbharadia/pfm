package com.anil.pfm.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.anil.pfm.domain.enumeration.RDTransactionType;

/**
 * A DTO for the RDTransaction entity.
 */
public class RDTransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant date;

    private RDTransactionType type;

    private Long recurringDepositId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public RDTransactionType getType() {
        return type;
    }

    public void setType(RDTransactionType type) {
        this.type = type;
    }

    public Long getRecurringDepositId() {
        return recurringDepositId;
    }

    public void setRecurringDepositId(Long recurringDepositId) {
        this.recurringDepositId = recurringDepositId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RDTransactionDTO rDTransactionDTO = (RDTransactionDTO) o;
        if(rDTransactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rDTransactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RDTransactionDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
