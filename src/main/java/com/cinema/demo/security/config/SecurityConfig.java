package com.cinema.demo.security.config;

import com.cinema.demo.security.service.oauth2.security.CustomOAuth2UserDetails;
import com.cinema.demo.security.service.oauth2.security.handler.CustomOAuth2FailureHandler;
import com.cinema.demo.security.service.oauth2.security.handler.CustomOAuth2SuccessHandler;
import com.cinema.demo.security.service.security.UserDetailsServiceCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private CustomOAuth2UserDetails customOAuth2UserDetails;
//
//    @Autowired
//    private CustomOAuth2FailureHandler customOAuth2FailureHandler;
//
//    @Autowired
//    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsServiceCustom();
//    }
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//
//        builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//
//        AuthenticationManager manager = builder.build();
//
////        http
////                .cors().disable()
////                .csrf().disable()
////                .httpBasic()
////                .and()
////                .authorizeHttpRequests()
////                .requestMatchers("/account/**").permitAll()
////                .requestMatchers("/guest/**").permitAll()
////                .requestMatchers("/admin/**").hasAuthority("ADMIN")
////                .requestMatchers("/user/**").hasAuthority("USER")
////                .requestMatchers("/home/**").authenticated()
////                .anyRequest().authenticated()
////                .and()
////                .authenticationManager(manager)
////                .sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http
//                .authorizeHttpRequests()
//                .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                .requestMatchers("/user/**").hasAuthority("USER")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/sign-in")
//                .defaultSuccessUrl("/home", true)
//                .permitAll()
//                .and()
//                .logout()
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login?logout")
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/403")
//                .and()
//                .csrf().disable()
//                .authenticationManager(manager)
//                .httpBasic()
//                .and()
//                .oauth2Login()
//                .loginPage("/login")
//                .defaultSuccessUrl("/home", true)
//                .userInfoEndpoint()
//                .userService(customOAuth2UserDetails)
//                .and()
//                .successHandler(customOAuth2SuccessHandler)
//                .failureHandler(customOAuth2FailureHandler)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//        ;
//
//        return http.build();
//
@Autowired
    private CustomOAuth2UserDetails customOAuth2UserDetails;

    @Autowired
    private UserDetailsServiceCustom userDetailService;

    @Autowired
    private CustomOAuth2FailureHandler customOAuth2FailureHandler;

    @Autowired
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    // configuraiton of authentication providerfor spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user detail service ka object:
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        // password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // configuration
        // urls configure kiay hai ki koun se public rangenge aur koun se private
        // rangenge
        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/home", "/register", "/services").permitAll();
            authorize.requestMatchers("/user/**").hasRole("USER");
            authorize.requestMatchers("/admin/**").hasRole("ADMIN");
            authorize.requestMatchers("/index").permitAll()
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
                    .requestMatchers("/user/notification/**").hasRole("USER")
                    .requestMatchers("/user/change-password").hasRole("USER")
                    .requestMatchers("/admin/**").permitAll()
                    .requestMatchers("/request-reset-password").permitAll()  // Cho phép truy cập trang yêu cầu reset mật khẩu
                    .requestMatchers("/reset-password").permitAll()
                    .requestMatchers("/invoices/*/qrcode").permitAll() // Sửa đúng pattern
                    .requestMatchers("/invoices/**").hasRole("USER") // Các endpoint khác yêu cầu vai trò USER
                    .anyRequest().permitAll();
        });

        // form default login
        // agar hame kuch bhi change karna hua to hama yaha ayenge: form login se
        // related
        httpSecurity.formLogin(formLogin -> {

            //
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
            formLogin.failureForwardUrl("/login?error=true");
            formLogin.defaultSuccessUrl("/postLogin");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

            // formLogin.failureHandler(new AuthenticationFailureHandler() {

            // @Override
            // public void onAuthenticationFailure(HttpServletRequest request,
            // HttpServletResponse response,
            // AuthenticationException exception) throws IOException, ServletException {
            // // TODO Auto-generated method stub
            // throw new UnsupportedOperationException("Unimplemented method
            // 'onAuthenticationFailure'");
            // }

            // });

            // formLogin.successHandler(new AuthenticationSuccessHandler() {

            // @Override
            // public void onAuthenticationSuccess(HttpServletRequest request,
            // HttpServletResponse response,
            // Authentication authentication) throws IOException, ServletException {
            // // TODO Auto-generated method stub
            // throw new UnsupportedOperationException("Unimplemented method
            // 'onAuthenticationSuccess'");
            // }

            // });
            formLogin.failureHandler(customOAuth2FailureHandler);

        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        // oauth configurations

        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(customOAuth2SuccessHandler);
        });

        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        return httpSecurity.build();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) ->
                web.ignoring()
                        .requestMatchers("/js/**", "/css/**");
    }

}
