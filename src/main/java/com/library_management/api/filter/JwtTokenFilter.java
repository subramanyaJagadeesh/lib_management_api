package com.library_management.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library_management.api.exception.ErrorResponse;
import com.library_management.api.exception.NotAuthorizedException;
import com.library_management.api.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final String secretKey = "LibraryManagementSecret";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getTokenFromRequest(request);
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            if (token != null && !claims.isEmpty() && validateToken(claims)) {

                User userDetails = getUserDetailsFromToken(claims);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, null);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e){
            handleExceptionsUnderFilter(response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/auth/login") || path.equals("/auth/signup");
    }
    private boolean validateToken(Claims claims) {
        try {
            // Token validation logic
            return Objects.nonNull(claims.get("id"));
        } catch (Exception e) {
            // Log exception, token validation failed
            return false;
        }
    }

    protected void handleExceptionsUnderFilter(HttpServletResponse httpServletResponse) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }

    private User getUserDetailsFromToken(Claims claims) {
        return User.builder()
                .id(Long.valueOf(claims.get("id").toString()))
                .email((String) claims.get("email"))
                .firstName((String) claims.get("firstName"))
                .lastName((String) claims.get("lastName"))
                .build();
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public String generateToken(User user) {
        final long EXPIRATION_TIME = 3600000 * 24 * 7; // 1 hour in milliseconds

        Map<String, Object> claims = new HashMap<>();
        // Add user-specific information to claims
        claims.put("id", user.getId().toString());
        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("email", user.getEmail());
        claims.put("type", user.getType().toString());
        // Add more claims if necessary, like roles

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
