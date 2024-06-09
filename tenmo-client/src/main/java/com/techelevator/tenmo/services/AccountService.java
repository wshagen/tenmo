package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    public static String API_BASE_URL = "http://localhost:8080/account";
    private final RestTemplate restTemplate = new RestTemplate();

    private AuthenticatedUser currentUser;

    public BigDecimal getBalance(User user){
        Account account = restTemplate.getForObject(API_BASE_URL + "/" + user.getId(),
                Account.class);
        return account.getBalance();
    }
}
