package com.example.showtime;

public class List {

    private String image;
    private String movieName;
    private String genre;
    private String rating;
    private String ticketPrice;

    public List() {
    }

    public List(String image, String movieName, String genre, String rating, String ticketPrice) {
        this.image = image;
        this.movieName = movieName;
        this.genre = genre;
        this.rating = rating;
        this.ticketPrice = ticketPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
