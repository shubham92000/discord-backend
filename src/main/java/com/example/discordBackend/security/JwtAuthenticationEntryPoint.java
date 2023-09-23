package com.example.discordBackend.security;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.ErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // authentication related exception
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ApiResponse apiResponse = new ApiResponse(
                false,
                null,
                new ErrorInfo("", "something went wrong")
        );
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String out = ow.writeValueAsString(apiResponse);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setContentLength(out.length());
        response.getWriter().write(out);

    }
}
