package ru.kpfu.itis.Kolodeznikova.servlet.notifications;

import ru.kpfu.itis.Kolodeznikova.dto.NotificationDto;
import ru.kpfu.itis.Kolodeznikova.service.NotificationService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Notifications", urlPatterns = "/notifications")
public class NotificationsPageServlet extends HttpServlet {

    private NotificationService notificationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.notificationService = (NotificationService) config.getServletContext().getAttribute("notificationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Уведомления");
        req.setAttribute("contextPath", req.getContextPath());

        Integer userId = (Integer) req.getSession().getAttribute("userId");

        try {
            List<NotificationDto> notifications = notificationService.getUserNotifications(userId);
            req.setAttribute("notifications", notifications);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.getRequestDispatcher("/WEB-INF/templates/notifications/notification_list.ftl").forward(req, resp);
    }
}