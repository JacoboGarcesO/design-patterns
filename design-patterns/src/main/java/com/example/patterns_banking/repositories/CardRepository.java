package com.example.patterns_banking.repositories;

import com.example.patterns_banking.models.Card;

import java.sql.*;

public class CardRepository {
    private static final String DB_URL = "jdbc:h2:mem:patterns-banking";
    private static final String DB_USER = "sa";
    private static final String DB_PW = "";

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS card (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "number VARCHAR(50) NOT NULL UNIQUE, " +
                    "account_type VARCHAR(50)," +
                    "balance DECIMAL(10, 2)," +
                    "is_active BIT NOT NULL DEFAULT TRUE)";

    private static final String INSERT_SQL = "INSERT INTO card (number, account_type, balance, is_active) VALUES (?, ?, ?, ?)";

    private static CardRepository instance;

    private CardRepository() {
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

    public static CardRepository getInstance() {
        if (instance == null) {
            instance = new CardRepository();
        }

        return instance;
    }

    public Card save(Card card) {
        try (Connection conn = getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, card.getNumber());
                pstmt.setString(2, card.getType().name());
                pstmt.setBigDecimal(3, card.getBalance());
                pstmt.setBoolean(4, card.getIsActive());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating account failed, no rows affected.");
                }

                try(ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        card.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Creating account failed, no ID obtained.");
                    }
                }
            }

            return card;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving account", e);
        }
    }

}
