package com.smartclassroom.Smart_Classroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    public SecurityConfig(CustomLoginSuccessHandler customLoginSuccessHandler){
        this.customLoginSuccessHandler = customLoginSuccessHandler;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable()).
                authorizeHttpRequests(auth->auth.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/teacher/**").hasRole("TEACHER")
                        .requestMatchers("/api/student/**").hasRole("STUDENT")
                        .anyRequest().authenticated())
                .formLogin(form->
                        form.loginPage("/api/auth/login")
                        .usernameParameter("email")
                                .passwordParameter("password")
                                .successHandler(customLoginSuccessHandler)
                                .permitAll())
                .logout(logout->
                        logout.logoutSuccessUrl("/api/auth/login?logout"));
   return http.build();
    }
}
