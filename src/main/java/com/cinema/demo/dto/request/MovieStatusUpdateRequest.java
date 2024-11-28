package com.cinema.demo.dto.request;

public class MovieStatusUpdateRequest {
    private int movieId;
    private int isShowing;

    // Getters and Setters
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getIsShowing() {
        return isShowing;
    }

    public void setIsShowing(int isShowing) {
        this.isShowing = isShowing;
    }
}
