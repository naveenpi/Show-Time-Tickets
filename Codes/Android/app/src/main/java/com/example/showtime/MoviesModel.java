package com.example.showtime;

public class MoviesModel {

    private String genre;
    private String imgReference;
    private String movieName;
    private String rating;
    private String ticketPrice;

    public MoviesModel() {
    }

    public MoviesModel(String genre, String imgReference, String movieName, String rating, String ticketPrice) {
        this.genre = genre;
        this.imgReference = imgReference;
        this.movieName = movieName;
        this.rating = rating;
        this.ticketPrice = ticketPrice;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setImgReference(String imgReference) {
        this.imgReference = imgReference;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setTicketPrice(String ticketPrice) {
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
