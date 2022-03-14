package com.example.walletapi.models;


public enum KycLevel {
    NORMAL(20000.00),
    PREMIUM(100000.00);

    private Double transactionLimit;

    KycLevel(Double transactionLimit){
        this.transactionLimit= transactionLimit;
    }

    public Double getTransactionLimit() {
        return transactionLimit;
    }
}
