package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = null;
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
            if(results.next()) {
                transfer = mapRowToTransfer(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transfer;
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        Transfer newTransfer = null;
        String sql = "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount) Values(?, ?, ?, ?, ?) RETURNING transfer_id;";
        try {
            int newTransferId = jdbcTemplate.queryForObject(sql, int.class,
                    transfer.getTransferTypeId(),
                    transfer.getTransferStatusId(),
                    transfer.getAccountFrom(),
                    transfer.getAccountTo(),
                    transfer.getAmount());
            newTransfer = getTransferById(newTransferId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newTransfer;
    }

    @Override
    public List<TransferResponse> getTransferList(int userId, int statusId){
        List<TransferResponse> transfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id, ts.transfer_status_desc AS status, fu.username AS user_from, tu.username AS user_to, t.amount\n" +
            "FROM transfer t\n" +
            "JOIN transfer_status ts ON (ts.transfer_status_id = t.transfer_status_id)" +
            "JOIN account fa ON (fa.account_id = t.account_from)\n" +
            "JOIN tenmo_user fu ON (fu.user_id = fa.user_id)\n" +
            "JOIN account ta ON (ta.account_id = t.account_to)\n" +
            "JOIN tenmo_user tu ON (tu.user_id = ta.user_id)\n" +
            "WHERE t.transfer_status_id = ? AND (fu.user_id = ? OR tu.user_id = ?)";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, statusId, userId, userId);
            while (results.next()){
                transfers.add(
                    new TransferResponse(
                        results.getInt("transfer_id"),
                        results.getString("status"),
                        results.getString("user_from"),
                        results.getString("user_to"),
                        results.getBigDecimal("amount")
                    )
                );
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to server or database", e);
        }
        return transfers;
    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }

}
