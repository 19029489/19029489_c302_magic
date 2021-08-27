package com.example.a19029489_c302_magic;

public class Colour {
    private String colour;
    private int colourId;

    public Colour(int colourId, String colour) {
        this.colour = colour;
        this.colourId = colourId;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getColourId() {
        return colourId;
    }

    public void setColourId(int colourId) {
        this.colourId = colourId;
    }
}
