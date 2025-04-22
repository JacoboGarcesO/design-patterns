package com.example.patterns_banking.repositories;

import com.example.patterns_banking.models.Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountRepository {
    private static final String DB_URL = "jdbc:h2:mem:patterns-banking";
    private static final String DB_USER = "sa";
    private static final String DB_PW = "";

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS account (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "number VARCHAR(50) NOT NULL UNIQUE, " +
                    "balance FLOAT(50), "+
                    "active BOOLEAN)";

    private static final String INSERT_SQL = "INSERT INTO account (number, balance, active) VALUES (?, ?, ?)";

    private static AccountRepository instance;

    private AccountRepository() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
    }

    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }

        return instance;
    }

    public Account save(Account account) {
        try (Connection conn = getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, account.getNumber());
                pstmt.setBigDecimal(2, account.getBalance());
                pstmt.setBoolean(3, account.getActive());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating account failed, no rows affected.");
                }

                try(ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        account.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Creating account failed, no ID obtained.");
                    }
                }
            }

            return account;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving account", e);
        }
    }
}
