package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    //List<Transfer> getPendingTransfersByUserId(int userId);

    //List<Transfer> getPastTransfersByUserId(int userId);

    Transfer getTransferById(int transferId);

    Transfer createTransfer(Transfer transfer);

}
