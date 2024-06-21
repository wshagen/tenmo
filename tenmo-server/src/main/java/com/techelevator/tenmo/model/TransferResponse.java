package com.techelevator.tenmo.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransferResponse {


    @NotNull
    private int transferId;
    @NotNull
    private String status;
    @NotNull
    private String type;
    @NotNull(message = "User From must not be null.")
    private String userFrom;
    @NotNull(message = "User To must not be null.")
    private String userTo;
    @NotNull(message = "Amount must not be null.")
    @Positive(message = "Transfer amount must be greater than 0.")
    private BigDecimal amount;

    @NotNull
    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(@NotNull int transferId) {
        this.transferId = transferId;
    }

    public @NotNull String getStatus() {
        return status;
    }

    public void setStatus(@NotNull String status) {
        this.status = status;
    }

    public @NotNull String getType() {
        return type;
    }

    public void setType(@NotNull String type) {
        this.type = type;
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

    public TransferResponse() {}

    public TransferResponse(int transferId, String status, String type, String userFrom, String userTo, BigDecimal amount) {
        this.transferId = transferId;
        this.status = status;
        this.type = type;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
    }
    @Override
    public String toString(){
        return "TransferResponse{transfer_id=" + transferId +
                ", status=" + status +
                ", user_from=" + userFrom +
                ", user_to=" + userTo +
                ", amount=" + amount +
                "}";
    }
}
