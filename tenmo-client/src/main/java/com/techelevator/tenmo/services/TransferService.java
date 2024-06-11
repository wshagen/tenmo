package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TransferService {
    public static String API_BASE_URL = "http://localhost:8080/transfer";
    private final RestTemplate restTemplate = new RestTemplate();

    private AccountService accountService;

   public Transfer createTransfer(Transfer transfer) {
       HttpEntity<Transfer> entity = makeEntity(transfer);
       Transfer returnedTransfer = null;
       try{
           returnedTransfer = restTemplate.postForObject(API_BASE_URL, entity, Transfer.class);
       } catch(RestClientResponseException e){
           BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
       } catch(ResourceAccessException e) {
           BasicLogger.log(e.getMessage());
       }
       return returnedTransfer;
   }

   //public Transfer checkTransfer(int accountFrom, int accountTo) {

  // }

   private HttpEntity<Transfer> makeEntity(Transfer transfer) {
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);
       return new HttpEntity<>(transfer, headers);
   }
}
