package com.example.showtime;

public class PaymentsModel {

    private String cardNumber;
    private String month;
    private String nameOnCard;
    private String year;

    public PaymentsModel() {
    }

    public PaymentsModel(String cardNumber, String month, String nameOnCard, String year) {
        this.cardNumber = cardNumber;
        this.month = month;
        this.nameOnCard = nameOnCard;
        this.year = year;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getMonth() {
        return month;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public String getYear() {
        return year;
    }
}
