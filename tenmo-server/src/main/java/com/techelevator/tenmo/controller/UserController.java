package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final JdbcUserDao jdbcUserDao;

    public UserController(JdbcUserDao jdbcUserDao) {
        this.jdbcUserDao = jdbcUserDao;
    }

    @GetMapping(path = "/tenmo_user")
    public List<User> getUsers(){
        return jdbcUserDao.getUsers();
    }
}
