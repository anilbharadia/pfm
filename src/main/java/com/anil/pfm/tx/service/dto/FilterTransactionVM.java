package com.anil.pfm.tx.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * A DTO for the Transaction entity.
 */
public class FilterTransactionVM implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Long> txTypeIds;
	
	private Instant dateFrom;
	
	private Instant dateTo;

	public List<Long> getTxTypeIds() {
		return txTypeIds;
	}

	public void setTxTypeIds(List<Long> txTypeIds) {
		this.txTypeIds = txTypeIds;
	}

	public Instant getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Instant dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Instant getDateTo() {
		return dateTo;
	}

	public void setDateTo(Instant dateTo) {
		this.dateTo = dateTo;
	}
	
}
