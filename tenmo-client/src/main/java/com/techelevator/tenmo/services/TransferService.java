package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    public static String API_BASE_URL = "http://localhost:8080/transfer";
    private final RestTemplate restTemplate = new RestTemplate();



    private AccountService accountService;

   public void createTransfer(Transfer transfer, String authToken) {
       HttpEntity<?> entity = makeEntity(transfer, authToken);
       try{
           restTemplate.postForObject(
               API_BASE_URL,
               entity,
               Transfer.class
           );
       } catch(RestClientResponseException e){
           BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
       } catch(ResourceAccessException e) {
           BasicLogger.log(e.getMessage());
       }
   }

   public Transfer[] getTransfers(int userId, String authToken){
       Transfer[] transfer = null;
       try{
           ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "_list/" + userId,
                   HttpMethod.GET,
                   makeAuthEntity(authToken),
                   Transfer[].class);
           transfer = response.getBody();
       } catch (RestClientResponseException | ResourceAccessException e) {
           BasicLogger.log(e.getMessage());
       }
       return transfer;
   }

   private HttpEntity<Transfer> makeEntity(Transfer transfer, String authToken) {
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);
       headers.setBearerAuth(authToken);
       return new HttpEntity<>(transfer, headers);
   }

    private HttpEntity<Void> makeAuthEntity(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
