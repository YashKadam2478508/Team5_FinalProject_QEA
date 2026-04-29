package org.example.model;

public class Listing {

    private String name;
    private String phone;
    private double rating;
    private int votes;

    public Listing(String name, String phone, double rating, int votes) {
        this.name = name;
        this.phone = phone;
        this.rating = rating;
        this.votes = votes;
    }

    public String getName()    { return name; }
    public String getPhone()   { return phone; }
    public double getRating()  { return rating; }
    public int    getVotes()   { return votes; }

    @Override
    public String toString() {
        return String.format("%-40s | %-15s | Rating: %.1f | Votes: %d",
                             name, phone, rating, votes);
    }
}
