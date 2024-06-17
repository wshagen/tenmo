package com.techelevator.tenmo.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;


public class Account {
    @NotNull(message = "Account Id must not be null.")
    private int id;
    @NotNull(message = "User Id must not be null.")
    private int userId;
    @NotNull(message = "Balance must not be null.")
    @Positive
    private BigDecimal balance;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Account() {}

    public Account(int id, int userid, BigDecimal balance) {
        this.id = id;
        this.userId = userid;
        this.balance = balance;
    }
    @Override
    public String toString() {
        return "Account { " + "id=" + id +
                " , user_id=" + userId +
                ", balance=" + balance +
                '}';
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                Objects.equals(userId, account.userId) &&
                Objects.equals(balance, account.balance);
    }
}
