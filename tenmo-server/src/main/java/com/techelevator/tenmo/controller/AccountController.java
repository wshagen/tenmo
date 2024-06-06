package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "")
public class AccountController {
    private final JdbcUserDao JdbcUserDao;

    public AccountController(JdbcUserDao JDBCUserDao) {
        this.JdbcUserDao = JDBCUserDao;
    }

    @GetMapping(path = "/account/{id}")
    public User get(@PathVariable int id) {
        return JdbcUserDao.getUserById(id);
    }

}
