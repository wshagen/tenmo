package com.techelevator.tenmo.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
public class Account {
    @Id
    @NotNull(message = "Account Id must not be null.")
    private int accountId;
    @NotNull(message = "User Id must not be null.")
    @Column(name = "user_id")
    private int userId;
    @NotNull(message = "Balance must not be null.")
    @Positive
    private double balance;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getId() {
        return userId;
    }

    public void setId(int id) {
        userId = id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public Account() {}

    public Account(int accountId, int userid, double balance) {
        this.accountId = accountId;
        this.userId = userid;
        this.balance = balance;
    }
    @Override
    public String toString() {
        return "Account { " + "account_id=" + accountId +
                " , user_id=" + userId +
                ", balance=" + balance +
                '}';
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId &&
                Objects.equals(userId, account.userId) &&
                Objects.equals(balance, account.balance);
    }
}
