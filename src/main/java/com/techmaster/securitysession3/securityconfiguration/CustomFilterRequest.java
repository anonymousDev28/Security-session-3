package com.techmaster.securitysession3.securityconfiguration;

import com.techmaster.securitysession3.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class CustomFilterRequest extends OncePerRequestFilter {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // get username from session
        String email = (String) request.getSession().getAttribute("MY_SESSION");
        // check session
        if(email == null){
            filterChain.doFilter(request,response);
            return;
        }
        // find user by username
        UserDetails userDetail = customUserDetailsService.loadUserByUsername(email);
        // Create object for author
        UsernamePasswordAuthenticationToken token = new
                UsernamePasswordAuthenticationToken(
                        //principal
                        userDetail.getUsername(),
                        // password
                        null,
                        //  list role
                        userDetail.getAuthorities()
        );
        // save to context
        SecurityContextHolder.getContext().setAuthentication(token);
        // go to next filter
        filterChain.doFilter(request,response);
    }
}
