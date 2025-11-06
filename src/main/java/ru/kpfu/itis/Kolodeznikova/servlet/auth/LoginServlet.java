package ru.kpfu.itis.Kolodeznikova.servlet.auth;

import ru.kpfu.itis.Kolodeznikova.service.UserService;
import ru.kpfu.itis.Kolodeznikova.util.PasswordUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet that handles user login functionality.
 * Supports verification of the login and password combination.
 */
@WebServlet(name="Log In", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    /**
     * Initializes the servlet and gets the UserService instance from the servlet context.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    /**
     * Handles GET requests by forwarding the user to the login page.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Авторизация");
        req.getRequestDispatcher("/WEB-INF/templates/auth/auth_page.ftl").forward(req, resp);
    }

    /**
     * Handles POST requests for user authorization.
     * Checks the login and password pair correctness and authorizes the user.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Retrieve user input from the login form
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        // Validate that fields are not empty
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("error", "Все поля должны быть заполнены");
            req.setAttribute("title", "Авторизация");
            req.getRequestDispatcher("/WEB-INF/templates/auth/auth_page.ftl").forward(req, resp);
            return;
        }

        // Check login and password pair correctness
        String passwordHash = PasswordUtil.encrypt(password);
        try {
            if (!userService.checkPasswordAndLogin(login, passwordHash)) {
                req.setAttribute("error", "Неверный логин или пароль");
                req.setAttribute("title", "Авторизация");
                req.getRequestDispatcher("/WEB-INF/templates/auth/auth_page.ftl").forward(req, resp);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Create a new session and store the login name
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(60 * 60);
        try {
            session.setAttribute("userId", userService.findUserIdByLogin(login));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Redirect to main page after successful authorization
        resp.sendRedirect(req.getContextPath() + "/main");
    }
}
