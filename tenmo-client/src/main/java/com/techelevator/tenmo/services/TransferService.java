package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TransferRequest;
import com.techelevator.tenmo.model.TransferResponse;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    public static String API_BASE_URL = "http://localhost:8080/transfer";
    private final RestTemplate restTemplate = new RestTemplate();



    private AccountService accountService;

   public void createTransfer(TransferRequest transferRequest, String authToken) {
       HttpEntity<?> entity = makeEntity(transferRequest, authToken);
       try{
           restTemplate.postForObject(
               API_BASE_URL,
               entity,
               TransferRequest.class
           );
       } catch(RestClientResponseException e){
           BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
       } catch(ResourceAccessException e) {
           BasicLogger.log(e.getMessage());
       }
   }

   public TransferResponse getTransfer(int id, String authToken) {
       TransferResponse transferResponse = new TransferResponse();
       try{
           ResponseEntity<TransferResponse> response = restTemplate.exchange(API_BASE_URL + "/" + id,
               HttpMethod.GET,
               makeAuthEntity(authToken),
               TransferResponse.class);
           transferResponse = response.getBody();
       } catch (RestClientResponseException | ResourceAccessException e) {
           BasicLogger.log(e.getMessage());
       }
       return transferResponse;
   }

   public TransferResponse[] getTransfers(String authToken){
       TransferResponse[] transferResponses = null;
       try{
           ResponseEntity<TransferResponse[]> response = restTemplate.exchange(API_BASE_URL + "s/completed",
                   HttpMethod.GET,
                   makeAuthEntity(authToken),
                   TransferResponse[].class);
           transferResponses = response.getBody();
       } catch (RestClientResponseException | ResourceAccessException e) {
           BasicLogger.log(e.getMessage());
       }
       return transferResponses;
   }

   private HttpEntity<TransferRequest> makeEntity(TransferRequest transferRequest, String authToken) {
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);
       headers.setBearerAuth(authToken);
       return new HttpEntity<>(transferRequest, headers);
   }

    private HttpEntity<Void> makeAuthEntity(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
