package com.example.showtime;

public class MoviesModel {

    private String genre;
    private String imgReference;
    private String movieName;
    private String rating;
    private String ticketPrice;

    private MoviesModel() {
    }

    private MoviesModel(String genre, String imgReference, String movieName, String rating, String ticketPrice) {
        this.genre = genre;
        this.imgReference = imgReference;
        this.movieName = movieName;
        this.rating = rating;
        this.ticketPrice = ticketPrice;
    }

    public String getGenre() {
        return genre;
    }

    public String getImgReference() {
        return imgReference;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getRating() {
        return rating;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }
}
