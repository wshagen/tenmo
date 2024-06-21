package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService();
    private final TransferService transferService = new TransferService();
    private final UserService userService = new UserService();

    private AuthenticatedUser currentUser;

    private int idSelection = -1;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                listUsers();

                idSelection = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel): ");
                while (idSelection != 0) {
                    if (currentUser.getUser().getId() == idSelection) {
                        System.out.println("Cannot send money to own account.");
                        idSelection = 0;
                    } else {
                        sendBucks();
                        idSelection = 0;
                    }
                }
            } else if (menuSelection == 5) {
                listUsers();
                idSelection = consoleService.promptForInt("Enter ID of user you are requesting from (0 to cancel): ");
                while (idSelection != 0) {
                    if (currentUser.getUser().getId() == idSelection) {
                        System.out.println("Cannot request money from own account.");
                        idSelection = 0;
                    } else {
                        requestBucks();
                        idSelection = 0;
                    }
                }
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void listUsers() {
        System.out.println("-------------------------------------------");
        System.out.println("Users");
        System.out.println("ID              Name");
        System.out.println("-------------------------------------------");
        for(int i = 0; i < userService.getUsers().length; i++) {
            System.out.print(userService.getUsers()[i].getId() + "              ");
            System.out.println(userService.getUsers()[i].getUsername());

        }
        System.out.println("-----------");
    }

    private void viewCurrentBalance() {

        System.out.println("Your current balance is: $" + accountService.getBalance(currentUser.getToken()));
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
        TransferResponse[] transferResponses = transferService.getTransfers(currentUser.getToken());

        if (transferResponses.length != 0){
            System.out.println("View transfers:");
            System.out.println("-------------------------------------------");
            System.out.println("Transfers");
            System.out.println("ID            From/To                Amount");
            System.out.println("-------------------------------------------");
            for (TransferResponse transferResponse : transferResponses) {
                //Transfer mode
                //1 "Sending"
                //2 "Receiving"
                if (transferResponse.getUserFrom().equals(currentUser.getUser().getUsername())) { //1    "Sending to others"
                    System.out.println(transferResponse.getTransferId() + "          " +
                            "To:   " + transferResponse.getUserTo() + "              $" + transferResponse.getAmount());
                } else { //2    "Receiving from others"
                    System.out.println(transferResponse.getTransferId() + "          " +
                            "From: " + transferResponse.getUserFrom() + "              $" + transferResponse.getAmount());

                }
            }
            int select = -1;
            while (select != 0) {
                select = consoleService.promptForInt("Please enter transferRequest ID to view details (0 to cancel):");
                TransferResponse selection = transferService.getTransfer(select, currentUser.getToken());
                System.out.printf("\n" +
                    "--------------------------------------------\n" +
                    "Transfer Details\n" +
                    "--------------------------------------------\n" +
                    " Id: %d\n" +
                    " From: %s\n" +
                    " To: %s\n" +
                    " Type: %s\n" +
                    " Status: %s\n" +
                    " Amount: $%.2f\n\n",
                    selection.getTransferId(),
                    selection.getUserFrom(),
                    selection.getUserTo(),
                    selection.getType(),
                    selection.getStatus(),
                    selection.getAmount()
                );
            }
        } else {
            System.out.println("List is empty!");
        }
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        BigDecimal amountToTransfer = consoleService.promptForBigDecimal("Enter amount: ");
        if (accountService.getBalance(currentUser.getToken()).compareTo(amountToTransfer) > 0){
            TransferRequest transferRequest = new TransferRequest(
                currentUser.getUser().getId(),
                idSelection,
                amountToTransfer
            );
            transferService.createTransfer(transferRequest, currentUser.getToken());
            System.out.println("Success - Transfer completed!");
            System.out.println("Your current balance is: $" + accountService.getBalance(currentUser.getToken()));
        } else {
            System.out.println("Insufficient funds");
        }
	}

	private void requestBucks() {
        BigDecimal amountToTransfer = consoleService.promptForBigDecimal("Enter amount: ");
        TransferRequest transferRequest = new TransferRequest(
            idSelection,
            currentUser.getUser().getId(),
            amountToTransfer
        );
        transferService.createTransfer(transferRequest, currentUser.getToken());
        System.out.println("Success - Transfer Pending");

    }

}
