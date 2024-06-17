package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {

    public static String API_BASE_URL = "http://localhost:8080/account";
    private final RestTemplate restTemplate = new RestTemplate();


    public BigDecimal getBalance(String token){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + token);
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Account> response = restTemplate.exchange(
            API_BASE_URL,
            HttpMethod.GET,
            entity,
            Account.class
        );
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getBalance();
        } else {
            throw new RuntimeException();
        }
    }

    public Account getAccount(int userId) {
        return restTemplate.getForObject(API_BASE_URL + "/" + userId,
                Account.class);
    }


}
