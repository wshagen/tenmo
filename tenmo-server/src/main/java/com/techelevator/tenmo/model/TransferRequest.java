package com.techelevator.tenmo.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransferRequest {
    @NotNull
    private int id;
    @NotNull(message = "User From must not be null.")
    private int userFrom;
    @NotNull(message = "User To must not be null.")
    private int userTo;
    @NotNull(message = "Amount must not be null.")
    @Positive(message = "Transfer amount must be greater than 0.")
    private BigDecimal amount;

    @NotNull
    public int getId() {
        return id;
    }

    public void setId(@NotNull int id) {
        this.id = id;
    }

    public int getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }

    public int getUserTo() {
        return userTo;
    }

    public void setUserTo(int userTo) {
        this.userTo = userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransferRequest() {}

    @Override
    public String toString() {
        return "TransferRequest{" +
            "amount=" + amount +
            ", id=" + id +
            ", userFrom=" + userFrom +
            ", userTo=" + userTo +
            '}';
    }
}
