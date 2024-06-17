package com.techelevator.tenmo.controller;



import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferRequest;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;


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

    @GetMapping(path = "/transfer/{Id}")
    public Transfer getTransferById(@PathVariable int id) {
        return jdbcTransferDao.getTransferById(id);
    }

    @PostMapping(path = "/transfer")
    public ResponseEntity<Void> createTransfer(@Valid @RequestBody TransferRequest transferRequest) {
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
        } else {
            Transfer transfer = new Transfer();
            transfer.setTransferTypeId(1);
            transfer.setTransferStatusId(1);
            transfer.setAccountFrom(accountFrom.getId());
            transfer.setAccountTo(accountTo.getId());
            transfer.setAmount(transferRequest.getAmount());
            jdbcTransferDao.createTransfer(transfer);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}