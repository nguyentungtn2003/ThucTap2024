//package com.cinema.demo.security.config;
//
//import com.cinema.demo.booking_apis.repository.*;
//import com.cinema.demo.entity.*;
//import com.cinema.demo.security.repository.RoleRepositorySecurity;
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
//    private final RoleRepositorySecurity roleRepository;
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
//            roleRepository.findByRoleName(role).orElseGet(() -> {
//                RoleEntity roleEntity = new RoleEntity();
//                roleEntity.setRoleName(role);
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
////       MovieEntity movie1 = createMovie("Inception", "Christopher Nolan", "Leonardo DiCaprio",
////                "inception-small.jpg", "A thief who steals corporate secrets.",
////                "https://youtube.com/inception-trailer", "Warner Bros.", 148, "PG-13", 1);
////
////        MovieEntity movie2 = createMovie("The Dark Knight", "Christopher Nolan", "Christian Bale",
////                "dark-knight-small.jpg", "Batman faces the Joker.",
////                "https://youtube.com/dark-knight-trailer", "Warner Bros.", 152, "PG-13", 1);
////
////        movieRepository.saveAll(Arrays.asList(movie1, movie2));
//        // Creating Movies
//        MovieEntity movie1 = createMovie("Titanic", "James Cameron", "Leonardo DiCaprio, Kate Winslet",
//                "https://miro.medium.com/v2/resize:fit:1400/0*5MFFtR8MBLOv1q_Z.jpg",
//                "An epic love story set aboard the ill-fated RMS Titanic.",
//                "https://youtu.be/VcVhbW4CxqY?si=FkV0J95Qj1vjdTv4",
//                "Paramount Pictures", 195, "PG-13", 1);
//
//        MovieEntity movie2 = createMovie("Elysium", "Neill Blomkamp", "Matt Damon, Jodie Foster",
//                "https://m.media-amazon.com/images/M/MV5BNDc2NjU0MTcwNV5BMl5BanBnXkFtZTcwMjg4MDg2OQ@@._V1_FMjpg_UX1000_.jpg",
//                "In a dystopian future, a man embarks on a dangerous mission to save humanity.",
//                "https://youtu.be/oIBtePb-dGY?si=Ox7s0rElBesiNAQg",
//                "TriStar Pictures", 109, "R", 1);
//
//        MovieEntity movie3 = createMovie("Focus", "Glenn Ficarra, John Requa", "Will Smith, Margot Robbie",
//                "https://reelreactions.wordpress.com/wp-content/uploads/2015/02/focus-poster.jpg?w=380&h=564",
//                "A con man teams up with a skilled thief to execute a major heist.",
//                "https://youtu.be/90VkoFV4iSY?si=VNZ1HTpb5UPTlbxV",
//                "Warner Bros.", 105, "R", 1);
//
//        MovieEntity movie4 = createMovie("The Avengers", "Joss Whedon", "Robert Downey Jr., Chris Evans",
//                "https://m.media-amazon.com/images/M/MV5BMjc1ZDMzMjEtYTc2Yy00ZDYzLThlOTMtZTUxYTgwODFiYTBjXkEyXkFqcGc@._V1_.jpg",
//                "Superheroes unite to stop a powerful villain from conquering the world.",
//                "https://youtu.be/NPoHPNeU9fc?si=Bk-e0YD1gxoNmQb3",
//                "Marvel Studios", 143, "PG-13", 1);
//
//        MovieEntity movie5 = createMovie("A Quiet Place", "John Krasinski", "Emily Blunt, John Krasinski",
//                "https://m.media-amazon.com/images/M/MV5BNjRiYjk4ZmItNGQ5NS00MmRhLTk4Y2EtMGQ1MTYxZWJhYjU0XkEyXkFqcGc@._V1_.jpg",
//                "In a world where sound equals death, a family must live in silence to survive.",
//                "https://youtu.be/WR7cc5t7tv8?si=2v2AgFmUS3M5ectn",
//                "Paramount Pictures", 90, "PG-13", 1);
//
//        MovieEntity movie6 = createMovie("Star Wars: A New Hope", "George Lucas", "Harrison Ford, Carrie Fisher",
//                "https://m.media-amazon.com/images/M/MV5BOTAzODEzNDAzMl5BMl5BanBnXkFtZTgwMDU1MTgzNzE@._V1_.jpg",
//                "The epic space saga continues as the Rebels try to destroy the Empire’s Death Star.",
//                "https://youtu.be/sGbxmsDFVnE?si=ftgAYBMhkKxc0z7f",
//                "Lucasfilm", 121, "PG", 1);
//
//        MovieEntity movie7 = createMovie("Guardians of the Galaxy", "James Gunn", "Chris Pratt, Zoe Saldana",
//                "https://cdn.marvel.com/content/1x/assembled_gotg3_digital_keyart_v3_lg.jpg",
//                "A group of intergalactic criminals must pull together to stop a fanatical warrior from taking control of the universe.",
//                "https://youtu.be/cDiREPV3gao?si=iEUGi_2OX9O-0_NL",
//                "Marvel Studios", 121, "PG-13", 1);
//
//        // Save all movies to the database
//        movieRepository.saveAll(Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6, movie7));
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
//    private MovieEntity createMovie(String title, String director, String actor, String smallImageURL,
//                                    String shortDescription, String trailerURL, String productionCompany,
//                                    int runningTime, String rated, int isShowing) {
//        MovieEntity movie = new MovieEntity();
//        movie.setTitle(title);
//        movie.setDirector(director);
//        movie.setActor(actor);
//        movie.setImage(smallImageURL);
////        movie.setLargeImageURL(largeImageURL);
//        movie.setDescription(shortDescription);
////        movie.setLongDescription(longDescription);
//        movie.setTrailerUrl(trailerURL);
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
