package ru.kpfu.itis.Kolodeznikova.filter;

import javax.servlet.*;
import java.io.IOException;
import javax.servlet.annotation.WebFilter;

/**
 * A servlet filter that ensures UTF-8 encoding for all incoming requests and outgoing responses.
 */
@WebFilter("/*")
public class EncodingFilter implements Filter {

    /**
     * Sets the UTF-8 character encoding for requests and responses.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // Set the character encoding for the request and response
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");

        // Continue with the next filter or target servlet
        filterChain.doFilter(servletRequest, servletResponse);
    }
}