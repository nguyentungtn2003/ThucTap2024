package com.cinema.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeHttpRequests((authorize) ->
                            authorize.requestMatchers("/register/**").permitAll()
                                    .requestMatchers("/index").permitAll()
                                     .requestMatchers("/upload/**").permitAll()
                                    .requestMatchers("/home").permitAll()
                                    .requestMatchers("/home1").permitAll()  // Cho phép truy cập trang /home1 mà không cần xác thực
                                    .requestMatchers("/postLogin").permitAll()
                                    .requestMatchers("/login.html").permitAll()
                                    .requestMatchers("/movies").hasRole("ADMIN")
                                    .requestMatchers("/movies/addmovie").hasRole("ADMIN")
                                    .requestMatchers("/movies/moviemanagement").hasRole("ADMIN")
                                    .requestMatchers("/movies/save").hasRole("ADMIN")
                                    .requestMatchers("/movies/delete/{id}").hasRole("ADMIN")
                                    .requestMatchers("/movies/update/{id}").hasRole("ADMIN")
                                    .requestMatchers("/movies/updateStatus").hasRole("ADMIN")
                                    .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()  // Cho phép tài nguyên tĩnh
                                    .requestMatchers("/user/home1").hasRole("USER")
                                    .requestMatchers("/user/info").hasRole("USER")
                                    .requestMatchers("/user/update").hasRole("USER")
                                    .requestMatchers("/user/change-password").hasRole("USER")
                                    .requestMatchers("/admin/**").permitAll()
                                    .requestMatchers("/request-reset-password").permitAll()  // Cho phép truy cập trang yêu cầu reset mật khẩu
                                    .requestMatchers("/reset-password").permitAll()




                    ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/postLogin", true)
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}