package com.cinema.demo.security.config;

import com.cinema.demo.security.service.oauth2.security.CustomOAuth2UserDetails;
import com.cinema.demo.security.service.oauth2.security.handler.CustomOAuth2FailureHandler;
import com.cinema.demo.security.service.oauth2.security.handler.CustomOAuth2SuccessHandler;
import com.cinema.demo.security.service.security.UserDetailsServiceCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Autowired
    private CustomOAuth2UserDetails customOAuth2UserDetails;

    @Autowired
    private CustomOAuth2FailureHandler customOAuth2FailureHandler;

    @Autowired
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceCustom();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

        AuthenticationManager manager = builder.build();

//        http
//                .cors().disable()
//                .csrf().disable()
//                .httpBasic()
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/account/**").permitAll()
//                .requestMatchers("/guest/**").permitAll()
//                .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                .requestMatchers("/user/**").hasAuthority("USER")
//                .requestMatchers("/home/**").authenticated()
//                .anyRequest().authenticated()
//                .and()
//                .authenticationManager(manager)
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeHttpRequests()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/user/**").hasAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/sign-in")
                .defaultSuccessUrl("/home/index", true)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
                .csrf().disable()
                .authenticationManager(manager)
                .httpBasic()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/home/index", true)
                .userInfoEndpoint()
                .userService(customOAuth2UserDetails)
                .and()
                .successHandler(customOAuth2SuccessHandler)
                .failureHandler(customOAuth2FailureHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        ;

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) ->
                web.ignoring()
                        .requestMatchers("/js/**", "/css/**");
    }

}
