package com.example.a19029489_c302_magic;

public class Card {
    private int cardId;
    private String cardName;
    private int colourId;
    private int typeId;
    private double price;
    private int quantity;

    public Card(int cardId, String cardName, int colourId, int typeId, double price, int quantity) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.colourId = colourId;
        this.typeId = typeId;
        this.price = price;
        this.quantity = quantity;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getColourId() {
        return colourId;
    }

    public void setColourId(int colourId) {
        this.colourId = colourId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return cardName + "   " + String.format("%.2f", price);
    }
}
