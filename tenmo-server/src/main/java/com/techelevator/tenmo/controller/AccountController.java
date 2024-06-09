package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "")
public class AccountController {
    private final JdbcAccountDao JdbcAccountDao;

    public AccountController(JdbcAccountDao JdbcAccountDao) {
        this.JdbcAccountDao = JdbcAccountDao;
    }


    @GetMapping(path = "/account/{userId}")
    public Account getAccountByUserId(@PathVariable int userId) {
        return JdbcAccountDao.getAccountByUserId(userId);
    }

}
