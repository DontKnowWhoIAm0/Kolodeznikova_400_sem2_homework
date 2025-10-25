package ru.kpfu.itis.Kolodeznikova.servlet.auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet that handles user logout functionality.
 */
@WebServlet(name="Log Out", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    /**
     * Handles GET requests for user logout.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Retrieve the current session and invalidate it to log the user out if the session exists
        HttpSession session = req.getSession();
        if (session != null) {
            session.invalidate();
        }

        // Redirect to the login page after logout
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
