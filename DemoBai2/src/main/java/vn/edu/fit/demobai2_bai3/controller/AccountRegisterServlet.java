package vn.edu.fit.demobai2_bai3.controller;
// Thay đổi package tương ứng với dự án của bạn

import vn.edu.fit.demobai2_bai3.entity.Account;
import vn.edu.fit.demobai2_bai3.util.AccountUtil;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/registerform")
public class AccountRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Resource(name = "jdbc/storedb")
    private DataSource dataSource;

    private AccountUtil accountUtil;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Khởi tạo AccountUtil với DataSource đã inject thành công
            accountUtil = new AccountUtil(dataSource);
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo AccountUtil: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Khi người dùng truy cập /registerform bằng phương thức GET, chuyển hướng tới trang JSP chứa Form đăng ký
        RequestDispatcher rd = req.getRequestDispatcher("RegisterForm.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Đảm bảo đọc dữ liệu UTF-8 từ Form tiếng Việt
        req.setCharacterEncoding("UTF-8");

        try {
            // 1. Lấy tham số từ Form
            String firstname = req.getParameter("firstname");
            String lastname = req.getParameter("lastname");
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            int day = Integer.parseInt(req.getParameter("day"));
            int month = Integer.parseInt(req.getParameter("month"));
            int year = Integer.parseInt(req.getParameter("year"));

            // 2. Chuyển đổi ngày tháng
            LocalDate localDate = LocalDate.of(year, month, day);
            java.sql.Date dob = java.sql.Date.valueOf(localDate);

            // 3. Tạo đối tượng Account
            Account account = new Account(firstname, lastname, email, password, dob);

            // 4. Lưu vào CSDL
            accountUtil.addAccount(account);

            // 5. Lấy danh sách mới nhất và đẩy sang trang account.jsp
            List<Account> accounts = accountUtil.getAccounts();
            req.setAttribute("accounts", accounts);

            RequestDispatcher rd = req.getRequestDispatcher("account.jsp");
            rd.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi xử lý đăng ký tài khoản: " + e.getMessage(), e);
        }
    }
}