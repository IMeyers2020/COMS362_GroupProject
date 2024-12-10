package models.athletics;


public class events {
    private String Sport;
    private int Capacity;
    private double Price;
    private String team1;
    private String team2;
    private String date;
    private String time;
    private String location;
    private String sportsId;

    // Constructor
    public events(String s, int Capacity, double Price, String team1, String team2, String date, String time, String location, String sportsId) {
        this.Sport = s;
        this.Capacity = Capacity;
        this.Price = Price;
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.time = time;
        this.location = location;
        this.sportsId = sportsId;
    }

    // Getters and Setters
    public String getSport() {
        return Sport;
    }

    public void setSport(String Sport) {
        this.Sport = Sport;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int Capacity) {
        this.Capacity = Capacity;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getsportsId() {
        return sportsId;
    }

    public void setsportsId(String sportsId) {
        this.sportsId = sportsId;
    }
}
