package com.example.shoppingcart.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class ActiveUserListener
        implements ServletContextListener, HttpSessionListener {

    // Tên attribute lưu trong Application Scope
    private static final String ACTIVE_USERS_ATTRIBUTE =
            "activeUsersCount";

    // Biến đếm số session đang hoạt động
    private static final AtomicInteger activeSessions =
            new AtomicInteger(0);

    // Khi ứng dụng được khởi động
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        // Đưa biến đếm vào Application Scope
        context.setAttribute(
                ACTIVE_USERS_ATTRIBUTE,
                activeSessions
        );

        System.out.println("Application started!");
    }

    // Khi ứng dụng bị tắt
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        sce.getServletContext()
                .removeAttribute(ACTIVE_USERS_ATTRIBUTE);

        System.out.println("Application stopped!");
    }

    // Khi một session mới được tạo
    @Override
    public void sessionCreated(HttpSessionEvent se) {

        ServletContext context =
                se.getSession().getServletContext();

        AtomicInteger activeUsers =
                (AtomicInteger) context.getAttribute(
                        ACTIVE_USERS_ATTRIBUTE
                );

        if (activeUsers != null) {
            activeUsers.incrementAndGet();
        }
    }

    // Khi session bị hủy / timeout
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        ServletContext context =
                se.getSession().getServletContext();

        AtomicInteger activeUsers =
                (AtomicInteger) context.getAttribute(
                        ACTIVE_USERS_ATTRIBUTE
                );

        if (activeUsers != null && activeUsers.get() > 0) {
            activeUsers.decrementAndGet();
        }
    }
}
