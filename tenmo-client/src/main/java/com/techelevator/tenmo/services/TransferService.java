package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TransferService {
    public static String API_BASE_URL = "http://localhost:8080/transfer";
    private final RestTemplate restTemplate = new RestTemplate();

    private AccountService accountService;

   public void createTransfer(Transfer transfer, String token) {
       HttpHeaders httpHeaders = new HttpHeaders();
       httpHeaders.set("Authorization", "Bearer " + token);
       HttpEntity<?> entity = new HttpEntity<>(transfer, httpHeaders);
       try{
           ResponseEntity<Void> response = restTemplate.exchange(
               API_BASE_URL,
               HttpMethod.POST,
               entity,
               Void.class
           );
       } catch(RestClientResponseException e){
           BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
       } catch(ResourceAccessException e) {
           BasicLogger.log(e.getMessage());
       }
   }

   private HttpEntity<Transfer> makeEntity(Transfer transfer) {
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);
       return new HttpEntity<>(transfer, headers);
   }
}
