package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.web.bind.annotation.*;


@RestController
public class AccountController {
    private final JdbcAccountDao jdbcAccountDao;

    public AccountController(JdbcAccountDao jdbcAccountDao) {
        this.jdbcAccountDao = jdbcAccountDao;
    }


    @GetMapping(path = "/account/{userId}")
    public Account getAccountByUserId(@PathVariable int userId) {
        return jdbcAccountDao.getAccountByUserId(userId);
    }

}
