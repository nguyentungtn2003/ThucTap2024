package com.cinema.demo;

import com.cinema.demo.booking_apis.repositories.ICinemaRoomRepository;
import com.cinema.demo.booking_apis.repositories.IMovieRepository;
import com.cinema.demo.booking_apis.repositories.ISeatRepository;
import com.cinema.demo.booking_apis.repositories.IShowTimeRepository;
import com.cinema.demo.security.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DemoApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    @Autowired
    private UserService userService;

    @Autowired
    private IMovieRepository movieRepository;

    @Autowired
    private ICinemaRoomRepository cinemaRoomRepository;

    @Autowired
    private IShowTimeRepository showTimeRepository;

    @Autowired
    private ISeatRepository seatRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
