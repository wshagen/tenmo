package com.techelevator.tenmo.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {

    @NotNull(message = "Transfer Id must not be null.")
    private int transferId;
    @NotNull(message = "Transfer Type Id must not be null.")
    private int transferTypeId;
    @NotNull(message = "Transfer Status must not be null.")
    private int transferStatusId;
    @NotNull(message = "Account From must not be null.")
    private int accountFrom;
    @NotNull(message = "Account To must not be null.")
    private int accountTo;
    @NotNull(message = "Amount must not be null.")
    @Positive
    private BigDecimal amount;

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatus) {
        this.transferStatusId = transferStatus;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Transfer() {}

    public Transfer(int transferId, int transferTypeId, int transferStatus, int accountFrom, int accountTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatus;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }
    @Override
    public String toString(){
        return "Transfer{" + "transfer_id=" + transferId +
                ", transfer_type_id=" + transferTypeId +
                ", transfer_status=" + transferStatusId +
                ", account_from=" + accountFrom +
                ", account_to=" + accountTo +
                ", amount=" + amount +
                '}';
    }
}
