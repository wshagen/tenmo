package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AccountController {
    private final JdbcAccountDao jdbcAccountDao;
    private final JdbcUserDao jdbcUserDao;

    public AccountController(
        JdbcAccountDao jdbcAccountDao,
        JdbcUserDao jdbcUserDao
    ) {
        this.jdbcAccountDao = jdbcAccountDao;
        this.jdbcUserDao = jdbcUserDao;
    }

    @GetMapping(path = "/account")
    public ResponseEntity<Account> getAccount() {
        String currentUsername = SecurityUtils.getCurrentUsername();
        User currentUser = jdbcUserDao.getUserByUsername(currentUsername);
        Account account = jdbcAccountDao.getAccountByUserId(currentUser.getId());
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
