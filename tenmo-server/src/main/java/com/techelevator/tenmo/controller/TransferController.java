package com.techelevator.tenmo.controller;



import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "")
public class TransferController {
    private final JdbcTransferDao jdbcTransferDao;

    public TransferController(JdbcTransferDao jdbcTransferDao) {
        this.jdbcTransferDao = jdbcTransferDao;
    }

    @GetMapping(path = "/transfer/{Id}")
    public Transfer getTransferById(@PathVariable int id) {
        return jdbcTransferDao.getTransferById(id);
    }

    @PostMapping(path = "/transfer")
    public Transfer createTransfer(@RequestBody Transfer transfer) {
        return jdbcTransferDao.createTransfer(transfer);
    }
}