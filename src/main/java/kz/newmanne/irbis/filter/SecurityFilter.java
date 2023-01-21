package kz.newmanne.irbis.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityFilter implements Filter {
    public static final String X_CLIENT_API_TOKEN = "X-Client-Api-Token";
    @Value("${client-api-token}")
    private String clientApiToken;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws ServletException, IOException {

        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;
        var token = request.getHeader(X_CLIENT_API_TOKEN);

        if (clientApiToken.equals(token))
            filterChain.doFilter(request, response);
        else {
            response.setContentType("text/plain");
            response.setStatus(401);
            response.getOutputStream().println("Unauthorized! Specify token in header: " + X_CLIENT_API_TOKEN);
        }
    }
}
