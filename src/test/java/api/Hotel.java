package api;

public class Hotel {
    public String city;
    public String description;
    public String name;
    public int rating;

    public Hotel(String city, String description, String name, int rating) {
        this.city = city;
        this.description = description;
        this.name = name;
        this.rating = rating;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
