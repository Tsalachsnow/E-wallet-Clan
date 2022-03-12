package com.example.walletapi.services.exception.types;

public class ServiceException extends RuntimeException {

	public ServiceException() {
		super();
	}

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(String msg, Throwable t) {
		super(msg, t);
	}
}
