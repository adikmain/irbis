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

        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;
        var token = httpRequest.getHeader(X_CLIENT_API_TOKEN);

        if (clientApiToken.equals(token)) {
            filterChain.doFilter(httpRequest, httpResponse);
        } else {
            httpResponse.setContentType("text/plain");
            httpResponse.setStatus(401);
            httpResponse.getOutputStream().println("Unauthorized! Please specify token in header: " + X_CLIENT_API_TOKEN);
        }
    }
}
