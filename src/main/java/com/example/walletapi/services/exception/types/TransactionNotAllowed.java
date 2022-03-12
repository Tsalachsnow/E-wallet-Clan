package com.example.walletapi.services.exception.types;


import com.example.walletapi.models.BaseClass;


public class TransactionNotAllowed extends RuntimeException {

	private long entityId;
	private String entityName;

	public TransactionNotAllowed() {
		super();
	}

	public TransactionNotAllowed(long entityId, String entityName) {
		super(String.format("Resource %s with id %s does not allow for requested transaction", entityName, entityId));
		this.entityId = entityId;
		this.entityName = entityName;
	}

	public TransactionNotAllowed(Long entityId, String msg, Class<? extends BaseClass> type) {
		super(msg);
		this.entityName = type.getSimpleName();
	}

	public TransactionNotAllowed(long entityId, Class<? extends BaseClass> type) {
		this(entityId, type.getSimpleName());
	}

}
