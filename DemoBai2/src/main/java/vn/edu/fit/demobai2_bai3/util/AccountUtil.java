package vn.edu.fit.demobai2_bai3.util;


import vn.edu.fit.demobai2_bai3.entity.Account;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountUtil {

    private final DataSource datasource;

    public AccountUtil(DataSource datasource) {
        this.datasource = datasource;
    }

    // 1. Lấy danh sách account
    public List<Account> getAccounts() throws Exception {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts ORDER BY ID";

        // Sử dụng try-with-resources để tự động đóng Connection, Statement, ResultSet
        try (Connection conn = datasource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String fname = rs.getString("FIRSTNAME");
                String lname = rs.getString("LASTNAME");
                String email = rs.getString("EMAIL");
                String password = rs.getString("PASSWORD");
                Date dateOfBirth = rs.getDate("DATEOFBIRTH"); // java.sql.Date extends java.util.Date

                Account acc = new Account(id, fname, lname, email, password, dateOfBirth);
                accounts.add(acc);
            }
        } catch (SQLException e) {
            throw new Exception("Lỗi khi lấy danh sách tài khoản: " + e.getMessage(), e);
        }

        return accounts;
    }

    // 2. Thêm account
    public void addAccount(Account acc) throws Exception {
        String sql = "INSERT INTO accounts (FIRSTNAME, LASTNAME, EMAIL, PASSWORD, DATEOFBIRTH) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, acc.getFirstname());
            ps.setString(2, acc.getLastname());
            ps.setString(3, acc.getEmail());
            ps.setString(4, acc.getPassword());

            // Chuyển java.util.Date sang java.sql.Date để lưu vào CSDL
            if (acc.getDateOfBirth() != null) {
                ps.setDate(5, new java.sql.Date(acc.getDateOfBirth().getTime()));
            } else {
                ps.setDate(5, null);
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Lỗi khi thêm tài khoản mới: " + e.getMessage(), e);
        }
    }
}