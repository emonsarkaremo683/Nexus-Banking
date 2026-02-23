package model;

import java.util.Date;

public class Transaction {
    private int id;
    private int accountId;
    private double amount;
    private String type; // DEPOSIT or WITHDRAW
    private Date date;

    public Transaction() {}

    public Transaction(int id, int accountId, double amount, String type, Date date) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
