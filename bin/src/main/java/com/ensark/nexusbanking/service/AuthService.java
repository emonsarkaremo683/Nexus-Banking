package com.ensark.nexusbanking.service;

import com.ensark.nexusbanking.dao.UserDAO;
import com.ensark.nexusbanking.dao.AccountDAO;
import com.ensark.nexusbanking.model.User;
import com.ensark.nexusbanking.model.Account;

import java.sql.SQLException;
import java.util.Scanner;

public class AuthService {

    private final UserDAO userDAO;
    private final AccountDAO accountDAO;
    private final Scanner sc;

    public AuthService() {
        userDAO = new UserDAO();
        accountDAO = new AccountDAO();
        sc = new Scanner(System.in);
    }

    public void start() throws SQLException, ClassNotFoundException {
        while (true) {
            System.out.println("\n=== Welcome to Bank App ===");
            System.out.println("1. Login");
            System.out.println("2. New Account");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
				case 1 : login();
					break;
				case 2 : register();
					break;
				case 3 : {
						System.out.println("Goodbye!");
						sc.close();
						return;
					}
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private void login() throws SQLException, ClassNotFoundException {
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        User user = (User) userDAO.findByEmail(email);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password!");
            return;
        }

        if (user.getStatus() != null && !user.getStatus().equalsIgnoreCase("ACTIVE")) {
            System.out.println("Your account is blocked!");
            return;
        }

        // Ensure account exists
        Account acc = (Account) accountDAO.getAccountByUserId(user.getId());
        if (acc == null) {
            System.out.println("No account found. Creating one...");
            acc = (Account) accountDAO.createAccount(user.getId());
        }

        System.out.println("Login Successful!");
        System.out.println("Your Account Number: " + acc.getAccountNumber());

        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            adminMenu();
        } else {
            userMenu(user, acc);
        }
    }

    private void register() throws SQLException, ClassNotFoundException {
        System.out.print("Full Name: ");
        String fullName = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        // check if email already exists
        User existing = (User) userDAO.findByEmail(email);
        if (existing != null) {
            System.out.println("This email is already registered. Please login.");
            return;
        }

        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("USER");
        user.setStatus("ACTIVE");

        boolean saved = userDAO.saveUser(user);
        if (saved) {
            User newUser = (User) userDAO.findByEmail(email);
            Account acc = (Account) accountDAO.createAccount(newUser.getId());
            System.out.println("Account created successfully! You can login now.");
            System.out.println("Your Account Number: " + acc.getAccountNumber());
        }
    }

    private void adminMenu() {
        System.out.println("=== Admin Menu ===");
        // TODO: implement view users, block/unblock
    }

    private void userMenu(User user, Account acc) throws SQLException, ClassNotFoundException {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. View Profile");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            // Always refresh account from DB
            acc = (Account) accountDAO.getAccountByUserId(user.getId());
            if (acc == null) {
                System.out.println("No account found. Creating one...");
                acc = (Account) accountDAO.createAccount(user.getId());
            }

            switch (choice) {
					case 1 -> {
						System.out.println("Name: " + user.getFullName());
						System.out.println("Email: " + user.getEmail());
						System.out.println("Role: " + user.getRole());
						System.out.println("Account Number: " + acc.getAccountNumber());
						System.out.println("Balance: " + acc.getBalance());
						}
					case 2 -> {
						System.out.print("Enter amount to deposit: ");
						double dep = sc.nextDouble();
						accountDAO.deposit(user.getId(), dep);
						System.out.println("Deposit successful!");
						}
					case 3 -> {
						System.out.print("Enter amount to withdraw: ");
						double wit = sc.nextDouble();
						accountDAO.withdraw(user.getId(), wit);
						}
					case 4 -> {
						System.out.println("Logging out...");
						return;
					}
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
