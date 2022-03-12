package com.example.walletapi.services.exception.types;


import com.example.walletapi.models.BaseClass;

public class ResourceNotFound extends RuntimeException {

	private long entityId;
	private String entityName;

	public ResourceNotFound() {
		super();
	}

	public ResourceNotFound(long entityId, String entityName) {
		super(String.format("Resource %s with id %s not found", entityName, entityId));
		this.entityId = entityId;
		this.entityName = entityName;
	}

	public ResourceNotFound(String msg, Class<? extends BaseClass> type) {
		super(msg);
		this.entityName = type.getSimpleName();
	}

	public ResourceNotFound(long entityId, Class<? extends BaseClass> type) {
		this(entityId, type.getSimpleName());
	}
}
