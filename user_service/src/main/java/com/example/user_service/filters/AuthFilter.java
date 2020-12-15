package com.example.user_service.filters;

import com.example.user_service.services.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String token = httpRequest.getHeader("Authorization");

        if (token != null) {
             try {
                Claims claims = Jwts.parser().setSigningKey(TokenService.getApiSecretKey())
                        .parseClaimsJws(token).getBody();
                String email = claims.get("email").toString();
                int role = Integer.parseInt(claims.get("role").toString());
                httpRequest.setAttribute("email", email);
                httpRequest.setAttribute("role", role);
            } catch (Exception e) {
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid/expired token");
                return;
            }
        } else {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}