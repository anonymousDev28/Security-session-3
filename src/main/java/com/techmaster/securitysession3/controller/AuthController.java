package com.techmaster.securitysession3.controller;

import com.techmaster.securitysession3.request.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session){
        // create authen object
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        try {
            // authenticating
            Authentication authentication = authenticationManager.authenticate(token);

            // save data in security context

            SecurityContextHolder.getContext().setAuthentication(authentication);
            // create session
            session.setAttribute("MY_SESSION", authentication.getName());

            return ResponseEntity.ok("/index2.html");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
