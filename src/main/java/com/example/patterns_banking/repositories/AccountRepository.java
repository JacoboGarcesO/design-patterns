package com.example.patterns_banking.repositories;

import com.example.patterns_banking.models.Account;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountRepository {
    private static final String DB_URL  = "jdbc:h2:mem:patterns-banking";
    private static final String DB_USER = "sa";
    private static final String DB_PW   = "";

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS accounts (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "number VARCHAR(100) NOT NULL UNIQUE, " +
                    "type VARCHAR(20), " +
                    "balance DECIMAL(19,2), " +
                    "is_active BOOLEAN DEFAULT TRUE)";

    private static final String INSERT_SQL =
            "INSERT INTO accounts (number, type, balance, is_active) VALUES (?, ?, ?, ?)";

    private static AccountRepository instance;

    private AccountRepository() {
        try (Connection c = getConnection();
             Statement s = c.createStatement()) {
            s.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to init accounts table", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
    }

    public static synchronized AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    public Account save(Account a) {
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString (1, a.getNumber());
            ps.setString (2, a.getType().name());
            ps.setBigDecimal(3, a.getBalance() != null ? a.getBalance() : BigDecimal.ZERO);
            ps.setBoolean(4, a.getIsActive());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Inserting account failed.");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    a.setId(keys.getLong(1));
                } else {
                    throw new SQLException("No ID returned.");
                }
            }
            return a;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving account", e);
        }
    }

}