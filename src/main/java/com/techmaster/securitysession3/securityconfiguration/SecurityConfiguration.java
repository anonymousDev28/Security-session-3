package com.techmaster.securitysession3.securityconfiguration;

import com.techmaster.securitysession3.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CustomFilterRequest customFilter;
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] PUBLIC = {
                "/", "/css/**","api/v1/login"
        };
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(PUBLIC).permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/user").hasAnyRole("ADMIN", "USER")
                .requestMatchers("author").hasRole("AUTHOR")
                .anyRequest().authenticated()
        .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint)
        .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
        .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);
        return provider;
    }
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("0")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("0")
//                .roles("USER","ADMIN")
//                .build();
//        UserDetails author = User.withDefaultPasswordEncoder()
//                .username("author")
//                .password("0")
//                .roles("AUTHOR")
//                .build();
//        return new InMemoryUserDetailsManager(user,admin,author);
//    }
}
