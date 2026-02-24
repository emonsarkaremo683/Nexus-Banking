package com.ensark.nexusbanking.dao;

import com.ensark.nexusbanking.config.DBConnection;
import com.ensark.nexusbanking.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public void createTransaction(Transaction tx) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO transactions (account_id, amount, type, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tx.getAccountId());
            ps.setDouble(2, tx.getAmount());
            ps.setString(3, tx.getType());
            ps.setTimestamp(4, new Timestamp(tx.getDate().getTime()));
            ps.executeUpdate();
        } 
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) throws SQLException, ClassNotFoundException {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Transaction(
							 rs.getInt("id"),
							 rs.getInt("account_id"),
							 rs.getDouble("amount"),
							 rs.getString("type"),
							 rs.getTimestamp("date")
						 ));
            }
        } 
        return list;
    }
}
