package com.cartelera.spring.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/",
                                                                "/login",
                                                                "/css/**",
                                                                "/js/**",
                                                                "/content/images/**",
                                                                "/home",
                                                                "/pelicula/**",
                                                                "/error",
                                                                "/search",
                                                                "/favicon.ico")
                                                .permitAll()
                                                .requestMatchers("/peliculas/**").hasAuthority("ADMINISTRADOR")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/home", true)
                                                .failureUrl("/login?error=credentials")
                                                .permitAll())
                                .exceptionHandling(handling -> handling
                                                .accessDeniedPage("/login?error=system"))
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/home?logout")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())
                                .exceptionHandling(handling -> handling
                                                .accessDeniedPage("/access-denied")
                                                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(
                                                                "/login?error=session")));
                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
