package com.techelevator.tenmo.controller;



import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping(path = "")
public class TransferController {
    private final JdbcTransferDao jdbcTransferDao;
    private final JdbcUserDao jdbcUserDao;
    private final JdbcAccountDao jdbcAccountDao;

    public TransferController(
        JdbcTransferDao jdbcTransferDao,
        JdbcUserDao jdbcUserDao,
        JdbcAccountDao jdbcAccountDao
    ) {
        this.jdbcTransferDao = jdbcTransferDao;
        this.jdbcUserDao = jdbcUserDao;
        this.jdbcAccountDao = jdbcAccountDao;
    }

    @GetMapping(path = "/transfer/{id}")
    public ResponseEntity<TransferResponse> getTransferById(@PathVariable int id) {
        String currentUsername = SecurityUtils.getCurrentUsername();
        User currentUser = jdbcUserDao.getUserByUsername(currentUsername);
        Transfer transfer = jdbcTransferDao.getTransferById(id);
        Account accountFrom = jdbcAccountDao.getAccountById(transfer.getAccountFrom());
        Account accountTo = jdbcAccountDao.getAccountById(transfer.getAccountTo());
        if (currentUser.getId() == accountFrom.getUserId() || currentUser.getId() == accountTo.getUserId()) {
            User userFrom = jdbcUserDao.getUserById(accountFrom.getUserId());
            User userTo = jdbcUserDao.getUserById(accountTo.getUserId());
            return new ResponseEntity<>(
                new TransferResponse(
                    transfer.getTransferId(),
                    transfer.getTransferStatusId() == 2
                        ? "Approved"
                        : (
                            transfer.getTransferStatusId() == 1
                                ? "Pending"
                                : "Rejected"
                        ),
                    transfer.getTransferTypeId() == 2 ? "Send" : "Request",
                    userFrom.getUsername(),
                    userTo.getUsername(),
                    transfer.getAmount()
                ),
                HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/transfer")
    public ResponseEntity<Void> createTransfer(@Valid @RequestBody TransferRequest transferRequest) {
        if (transferRequest.getUserFrom() == transferRequest.getUserTo()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String currentUsername = SecurityUtils.getCurrentUsername();
        User currentUser = jdbcUserDao.getUserByUsername(currentUsername);
        Account accountFrom = jdbcAccountDao.getAccountByUserId(transferRequest.getUserFrom());
        Account accountTo = jdbcAccountDao.getAccountByUserId(transferRequest.getUserTo());
        if (currentUser.getId() == accountFrom.getUserId()) {
            BigDecimal amountToTransfer = transferRequest.getAmount();
            if (accountFrom.getBalance().compareTo(amountToTransfer) >= 0) {
                Transfer transfer = new Transfer();
                transfer.setTransferTypeId(2);
                transfer.setTransferStatusId(2);
                transfer.setAccountFrom(accountFrom.getId());
                transfer.setAccountTo(accountTo.getId());
                transfer.setAmount(transferRequest.getAmount());
                jdbcTransferDao.createTransfer(transfer);
                BigDecimal newFromBalance = accountFrom.getBalance().subtract(amountToTransfer);
                jdbcAccountDao.updateBalance(transfer.getAccountFrom(), newFromBalance);
                BigDecimal newToBalance = accountTo.getBalance().add(amountToTransfer);
                jdbcAccountDao.updateBalance(transfer.getAccountTo(), newToBalance);

                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
            }
        } else if (currentUser.getId() == accountTo.getUserId()) {
            Transfer transfer = new Transfer();
            transfer.setTransferTypeId(1);
            transfer.setTransferStatusId(1);
            transfer.setAccountFrom(accountFrom.getId());
            transfer.setAccountTo(accountTo.getId());
            transfer.setAmount(transferRequest.getAmount());
            jdbcTransferDao.createTransfer(transfer);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/transfers/completed")
    public List<TransferResponse> getCompletedTransfers(){
        String currentUsername = SecurityUtils.getCurrentUsername();
        User currentUser = jdbcUserDao.getUserByUsername(currentUsername);
        return jdbcTransferDao.getTransferList(currentUser.getId(), 2);
    }

    @GetMapping(path = "/transfers/pending")
    public List<TransferResponse> getPendingTransfers(){
        String currentUsername = SecurityUtils.getCurrentUsername();
        User currentUser = jdbcUserDao.getUserByUsername(currentUsername);
        return jdbcTransferDao.getTransferList(currentUser.getId(), 1);
    }
}