package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.*;
import com.techelevator.util.BasicLogger;

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
                System.out.println("-------------------------------------------");
                System.out.println("Users");
                System.out.println("ID              Name");
                System.out.println("-------------------------------------------");
                for(int i = 0; i < userService.getUsers().length; i++) {
                    System.out.print(userService.getUsers()[i].getId() + "              ");
                    System.out.println(userService.getUsers()[i].getUsername());

                }
                System.out.println("-----------");
                idSelection = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel): ");
                if(currentUser.getUser().getId() == idSelection){
                    System.out.println("Cannot send money to own account.");
                }
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {

        System.out.println("Your current balance is: $" + accountService.getBalance(currentUser.getUser()));
        //System.out.println(currentUser.getToken());
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
		
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        BigDecimal amountToTransfer = consoleService.promptForBigDecimal("Enter amount: ");
        if(accountService.getBalance(currentUser.getUser()).compareTo(amountToTransfer) < 0){
            Account accountFrom = accountService.getAccount(currentUser.getUser().getId());
            Account accountTo = accountService.getAccount(idSelection);
            Transfer transfer = new Transfer(0, 2,2, accountFrom.getAccountId(), accountTo.getAccountId(), amountToTransfer);
            try {
                transferService.createTransfer(transfer);
                accountFrom.setBalance(accountFrom.getBalance().subtract(amountToTransfer));
                accountTo.setBalance(accountTo.getBalance().add(amountToTransfer));
            } catch (Exception e){
                BasicLogger.log(e.getMessage());
            }
        }
        System.out.println("You Suck!!!");
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
