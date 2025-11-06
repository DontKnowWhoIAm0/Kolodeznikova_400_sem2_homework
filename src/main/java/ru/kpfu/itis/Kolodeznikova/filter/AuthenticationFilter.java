package ru.kpfu.itis.Kolodeznikova.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A servlet filter that provides authentication control.
 * Checks if a user session exists and redirects unauthorized users to the login page.
 */
@WebFilter(urlPatterns = {"/profile/*", "/main/*", "/createRequest", "/workouts"}, filterName = "Authentication")
public class AuthenticationFilter implements Filter {

    /**
     * Checks if the current session exists.
     * If not, and the requested URI is not part of authentication pages, redirects user to the login page.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Try to get an existing session without creating a new one
        HttpSession session = ((HttpServletRequest) request).getSession(false);

        // Check if session is null and the requested URI is not an authentication-related page
        // If true, redirect to login page
        if (session == null &&
                (!((HttpServletRequest) request).getRequestURI().contains("login")) &&
                (!((HttpServletRequest) request).getRequestURI().contains("signup")) &&
                (!((HttpServletRequest) request).getRequestURI().contains("checkLogin")) &&
                (!((HttpServletRequest) request).getRequestURI().contains("checkNickname"))) {
            ((HttpServletResponse) response).sendRedirect("login");
        } else {
            // Continue with the next filter or target servlet
            chain.doFilter(request, response);
        }
    }
}