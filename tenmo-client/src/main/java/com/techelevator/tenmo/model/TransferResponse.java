package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferResponse {
    private int transferId;
    private String status;
    private String userFrom;
    private String userTo;
    private BigDecimal amount;



    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransferResponse(){}

    public TransferResponse(String userFrom, String userTo, BigDecimal amount) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
    }
    public TransferResponse(int transferId, String status, String userFrom, String userTo, BigDecimal amount) {
        this.transferId = transferId;
        this.status = status;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
    }
}
