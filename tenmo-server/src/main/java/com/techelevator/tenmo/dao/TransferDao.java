package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferResponse;

import java.util.List;

public interface TransferDao {

    Transfer getTransferById(int transferId);

    void createTransfer(Transfer transfer);

    List<TransferResponse> getTransferList(int userId, int statusId);

    void updateStatus(int id, int statusId);
}
