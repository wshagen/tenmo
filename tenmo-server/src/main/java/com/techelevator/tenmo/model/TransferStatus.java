package com.techelevator.tenmo.model;


import javax.validation.constraints.NotNull;

public class TransferStatus {

    @NotNull(message = "Transfer Status Id must not be null.")
    private int transferStatusId;
    @NotNull(message = "Transfer Status Desc must not be null.")
    private String transferStatusDesc;

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }

    public TransferStatus(){}

    public TransferStatus(int transferStatusId, String transferStatusDesc) {
        this.transferStatusId = transferStatusId;
        this.transferStatusDesc = transferStatusDesc;
    }

    @Override
    public String toString() {
        return "TransferStatus{" +
                "transferStatusId=" + transferStatusId +
                ", transferStatusDesc='" + transferStatusDesc + '\'' +
                '}';
    }
}
