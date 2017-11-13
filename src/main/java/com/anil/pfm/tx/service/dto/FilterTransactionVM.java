package com.anil.pfm.tx.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the Transaction entity.
 */
public class FilterTransactionVM implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Long> txTypeIds;

	public List<Long> getTxTypeIds() {
		return txTypeIds;
	}

	public void setTxTypeIds(List<Long> txTypeIds) {
		this.txTypeIds = txTypeIds;
	}

}
