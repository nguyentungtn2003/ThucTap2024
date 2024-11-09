// Sample data of movies (in real case, this data can come from an API or a server)
const movies = {
    quyan: {
        title: "Quỷ Án",
        description: "Khi Dani bị sát hại đã man tại ngôi nhà ở nông thôn hẻo lánh mà cô và chồng Ted đang sửa sang...",
        director: "Damian McCarthy",
        actors: "Carolyn Bracken, Johnny French, Steve Wall",
        genre: "Kinh dị",
        duration: "98 phút",
        language: "Tiếng Anh",
        releaseDate: "13/09/2024",
        poster: "Quyanfont.jpg",
        showtimes: ["11:00", "12:45", "18:30", "20:45", "22:30"]
    },
    transformer: {
        title: "Venom 3",
        description: "A continuation of the Venom saga...",
        director: "Damian McCarthy",
        actors: "Carolyn Bracken, Johnny French, Steve Wall",
        genre: "Kinh dị",
        duration: "98 phút",
        language: "Tiếng Anh",
        releaseDate: "13/09/2024",
        poster: "Transformersfont.jpg",
        showtimes: ["11:00", "12:45", "18:30", "20:45", "22:30"]
    },
    haimuoi: {
        title: "Venom 3",
        description: "",
        director: "Damian McCarthy",
        actors: "Carolyn Bracken, Johnny French, Steve Wall",
        genre: "Kinh dị",
        duration: "98 phút",
        language: "Tiếng Anh",
        releaseDate: "13/09/2024",
        poster: "Haimuoifont.jpg",
        showtimes: ["11:00", "12:45", "18:30", "20:45", "22:30"]
    },
    anlac: {
        title: "Venom 3",
        description: "",
        director: "Damian McCarthy",
        actors: "Carolyn Bracken, Johnny French, Steve Wall",
        genre: "Kinh dị",
        duration: "98 phút",
        language: "Tiếng Anh",
        releaseDate: "13/09/2024",
        poster: "anlacfont.jpg",
        showtimes: ["11:00", "12:45", "18:30", "20:45", "22:30"]
    },
    codauhaomon: {
        title: "Venom 3",
        description: "",
        director: "Damian McCarthy",
        actors: "Carolyn Bracken, Johnny French, Steve Wall",
        genre: "Kinh dị",
        duration: "98 phút",
        language: "Tiếng Anh",
        releaseDate: "13/09/2024",
        poster: "codauhaomonfont.jpg",
        showtimes: ["11:00", "12:45", "18:30", "20:45", "22:30"]
    },
    joker2: {
        title: "Venom 3",
        description: "",
        director: "Damian McCarthy",
        actors: "Carolyn Bracken, Johnny French, Steve Wall",
        genre: "Kinh dị",
        duration: "98 phút",
        language: "Tiếng Anh",
        releaseDate: "13/09/2024",
        poster: "joker2font.jpg",
        showtimes: ["11:00", "12:45", "18:30", "20:45", "22:30"]
    },
    ngayxuacomotchuyentinh: {
        title: "Venom 3",
        description: "",
        director: "Damian McCarthy",
        actors: "Carolyn Bracken, Johnny French, Steve Wall",
        genre: "Kinh dị",
        duration: "98 phút",
        language: "Tiếng Anh",
        releaseDate: "13/09/2024",
        poster: "Ngayxuacomotchuyentinhfont.jpg",
        showtimes: ["11:00", "12:45", "18:30", "20:45", "22:30"]
    },
    robothoangda: {
        title: "Venom 3",
        description: "",
        director: "Damian McCarthy",
        actors: "Carolyn Bracken, Johnny French, Steve Wall",
        genre: "Kinh dị",
        duration: "98 phút",
        language: "Tiếng Anh",
        releaseDate: "13/09/2024",
        poster: "Robothoangdafont.jpg",
        showtimes: ["11:00", "12:45", "18:30", "20:45", "22:30"]
    },
    trochoinhantinh: {
        title: "Venom 3",
        description: "",
        director: "Damian McCarthy",
        actors: "Carolyn Bracken, Johnny French, Steve Wall",
        genre: "Kinh dị",
        duration: "98 phút",
        language: "Tiếng Anh",
        releaseDate: "13/09/2024",
        poster: "Trochoinhantinhfont.jpg",
        showtimes: ["11:00", "12:45", "18:30", "20:45", "22:30"]
    },
    venom3: {
        title: "Venom 3",
        description: "",
        director: "Damian McCarthy",
        actors: "Carolyn Bracken, Johnny French, Steve Wall",
        genre: "Kinh dị",
        duration: "98 phút",
        language: "Tiếng Anh",
        releaseDate: "13/09/2024",
        poster: "Venom3font.jpg",
        showtimes: ["11:00", "12:45", "18:30", "20:45", "22:30"]
    },
    // Add more movies here if needed
};

// Function to retrieve query parameters from the URL
function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

// Function to display movie details
function displayMovieDetails() {
    const movieKey = getQueryParam("movie");
    const movie = movies[movieKey];

    if (movie) {
        // Populate the movie details
        document.getElementById("movie-title").textContent = movie.title;
        document.getElementById("movie-description").textContent = movie.description;
        document.getElementById("movie-director").textContent = movie.director;
        document.getElementById("movie-actors").textContent = movie.actors;
        document.getElementById("movie-genre").textContent = movie.genre;
        document.getElementById("movie-duration").textContent = movie.duration;
        document.getElementById("movie-language").textContent = movie.language;
        document.getElementById("movie-release-date").textContent = movie.releaseDate;
        document.getElementById("movie-poster").src = movie.poster;

        // Populate showtimes
        const timeSlots = document.getElementById("time-slots");
        if (movie.showtimes && Array.isArray(movie.showtimes)) {
            movie.showtimes.forEach(time => {
                const timeSlot = document.createElement("a");
                timeSlot.classList.add("btn-showtime");
                timeSlot.href = "book_ticket.html?time=" + encodeURIComponent(time) + "&movie=" + encodeURIComponent(movieKey); // Pass time and movie key to the booking page
                timeSlot.textContent = time;
                timeSlots.appendChild(timeSlot);
            });
        }
    }
}

// Execute the function to display movie details on page load
document.addEventListener("DOMContentLoaded", displayMovieDetails);
