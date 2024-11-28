//package com.cinema.demo.security.config;
//
//import com.cinema.demo.booking_apis.repository.*;
//import com.cinema.demo.entity.*;
//import com.cinema.demo.security.repository.RoleRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.sql.Date;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class DataLoader implements CommandLineRunner {
//
//    private final RoleRepository roleRepository;
//
//    private final IMovieRepository movieRepository;
//    private final ITypeRepository typeRepository;
//    private final IMovieTypeRepository movieTypeRepository;
//
//    private final ICinemaRoomRepository cinemaRoomRepository;
//    private final IShowDateRepository showDateRepository;
//    private final IShowTimeRepository showtimeRepository;
//
//    @Override
//    public void run(String... args) {
//        // Tạo Roles
//        String[] roles = {"ROLE_USER", "ROLE_ADMIN", "ROLE_STAFF"};
//        Arrays.stream(roles).forEach(role -> {
//            roleRepository.findByName(role).orElseGet(() -> {
//                RoleEntity roleEntity = new RoleEntity();
//                roleEntity.setName(role);
//                return roleRepository.save(roleEntity);
//            });
//        });
//
//        // Tạo Types
//        TypeEntity action = new TypeEntity();
//        action.setTypeName("Action");
//        TypeEntity drama = new TypeEntity();
//        drama.setTypeName("Drama");
//        TypeEntity comedy = new TypeEntity();
//        comedy.setTypeName("Comedy");
//        typeRepository.saveAll(Arrays.asList(action, drama, comedy));
//
//        // Tạo Movies
//        MovieEntity movie1 = createMovie("Inception", "Christopher Nolan", "Leonardo DiCaprio",
//                "inception-small.jpg", "inception-large.jpg", "A thief who steals corporate secrets.",
//                "A thief who steals corporate secrets through dream-sharing technology is tasked with planting an idea into a CEO's mind.",
//                "https://youtube.com/inception-trailer", "Warner Bros.", 148, "PG-13", 1);
//
//        MovieEntity movie2 = createMovie("The Dark Knight", "Christopher Nolan", "Christian Bale",
//                "dark-knight-small.jpg", "dark-knight-large.jpg", "Batman faces the Joker.",
//                "When the menace known as the Joker wreaks havoc on Gotham City, Batman must accept one of the greatest psychological tests of his ability to fight injustice.",
//                "https://youtube.com/dark-knight-trailer", "Warner Bros.", 152, "PG-13", 1);
//
//        movieRepository.saveAll(Arrays.asList(movie1, movie2));
//
//        // Link Movies with Types
//        linkMovieWithType(movie1, action);
//        linkMovieWithType(movie2, drama);
//
//        // Tạo Cinema Rooms
//        CinemaRoomEntity room1 = createCinemaRoom("Room A", 100, 200.5, "room-a.jpg");
//        CinemaRoomEntity room2 = createCinemaRoom("Room B", 150, 300.0, "room-b.jpg");
//        cinemaRoomRepository.saveAll(Arrays.asList(room1, room2));
//
//        // Tạo Show Dates và Showtimes
//        ShowDateEntity showDate1 = createShowDateWithShowtimes(LocalDate.now().plusDays(1), List.of(
//                createShowtime(LocalTime.of(10, 0), movie1, room1),
//                createShowtime(LocalTime.of(14, 0), movie1, room2)
//        ));
//
//        ShowDateEntity showDate2 = createShowDateWithShowtimes(LocalDate.now().plusDays(2), List.of(
//                createShowtime(LocalTime.of(16, 0), movie2, room1),
//                createShowtime(LocalTime.of(20, 0), movie2, room2)
//        ));
//
//        showDateRepository.saveAll(Arrays.asList(showDate1, showDate2));
//
//        System.out.println("Sample data loaded successfully!");
//    }
//
//    private MovieEntity createMovie(String title, String director, String actor, String smallImageURL, String largeImageURL,
//                                    String shortDescription, String longDescription, String trailerURL, String productionCompany,
//                                    int runningTime, String rated, int isShowing) {
//        MovieEntity movie = new MovieEntity();
//        movie.setTitle(title);
//        movie.setDirector(director);
//        movie.setActor(actor);
//        movie.setSmallImageURl(smallImageURL);
//        movie.setLargeImageURL(largeImageURL);
//        movie.setShortDescription(shortDescription);
//        movie.setLongDescription(longDescription);
//        movie.setTrailerURL(trailerURL);
//        movie.setProductionCompany(productionCompany);
//        movie.setReleaseDate(new Date(System.currentTimeMillis()));
//        movie.setRunningTime(runningTime);
//        movie.setRated(rated);
//        movie.setIsShowing(isShowing);
//        return movie;
//    }
//
//    private void linkMovieWithType(MovieEntity movie, TypeEntity type) {
//        MovieTypeEntity movieType = new MovieTypeEntity();
//        movieType.setMovie(movie);
//        movieType.setType(type);
//        movieTypeRepository.save(movieType);
//    }
//
//    private CinemaRoomEntity createCinemaRoom(String roomNum, int capacity, double area, String imgURL) {
//        CinemaRoomEntity room = new CinemaRoomEntity();
//        room.setCinemaRoomNum(roomNum);
//        room.setCapacity(capacity);
//        room.setTotalArea(area);
//        room.setImgURL(imgURL);
//        return room;
//    }
//
//    private ShowDateEntity createShowDateWithShowtimes(LocalDate date, List<ShowtimeEntity> showtimes) {
//        ShowDateEntity showDate = new ShowDateEntity();
//        showDate.setStartDate(date);
//        showDate.setShowtime(showtimes);
//        showtimes.forEach(showtime -> showtime.setShowDate(showDate));
//        return showDate;
//    }
//
//    private ShowtimeEntity createShowtime(LocalTime time, MovieEntity movie, CinemaRoomEntity room) {
//        ShowtimeEntity showtime = new ShowtimeEntity();
//        showtime.setStartTime(time);
//        showtime.setMovie(movie);
//        showtime.setCinemaRoom(room);
//        return showtime;
//    }
//}
