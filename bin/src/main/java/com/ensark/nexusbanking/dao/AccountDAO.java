package com.ensark.nexusbanking.dao;

import com.ensark.nexusbanking.config.DBConnection;
import com.ensark.nexusbanking.model.Account;

import java.sql.*;
import java.util.UUID;

public class AccountDAO {

    public Account createAccount(int userId) throws SQLException, ClassNotFoundException {
        String accountNumber = "AC" + UUID.randomUUID().toString().substring(0, 8);
        String sql = "INSERT INTO accounts (user_id, account_number, balance) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, accountNumber);
            ps.setDouble(3, 0.0);
            ps.executeUpdate();
            return getAccountByNumber(accountNumber);
        } 
    }

    public Account getAccountByUserId(int userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM accounts WHERE user_id = ? LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
					rs.getInt("id"),
					rs.getInt("user_id"),
					rs.getString("account_number"),
					rs.getDouble("balance")
                );
            }
        } 
        return null;
    }

    public Account getAccountByNumber(String accountNumber) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
					rs.getInt("id"),
					rs.getInt("user_id"),
					rs.getString("account_number"),
					rs.getDouble("balance")
                );
            }
        } 
        return null;
    }

    public boolean deposit(int userId, double amount) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } 
    }

    public boolean withdraw(int userId, double amount) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE accounts SET balance = balance - ? WHERE user_id = ? AND balance >= ?";
        try (Connection conn = DBConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, userId);
            ps.setDouble(3, amount);
            int updated = ps.executeUpdate();
            if (updated > 0) {
                return true;
            } else {
                System.out.println("Insufficient balance!");
            }
        } 
        return false;
    }
}
