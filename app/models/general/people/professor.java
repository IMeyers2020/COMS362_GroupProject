package models.general.people;

import java.util.ArrayList;

public class professor extends genericPerson {
    private String pid;
    private String name;
    private String address;
    private ArrayList<genericPerson> recommendations;
    private String AOS;

    public professor(String pid, String name, String address, String aos) {
        this.pid = pid;
        this.name = name;
        this.address = address;
        this.AOS = aos;
        this.recommendations = new ArrayList<>();
    }

    public professor(String pid, String name, String address, String aos, ArrayList<genericPerson> recommendations) {
        this.pid = pid;
        this.name = name;
        this.address = address;
        this.AOS = aos;
        this.recommendations = recommendations;
    }

    public professor() {
        this.recommendations = new ArrayList<>();
    }

    public String getPID() { return this.pid; }
    public void setPID(String pid) { this.pid = pid; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return this.address; }
    public void setAddress(String address) { this.address = address; }
    public String getAOS() { return this.AOS; }
    public void setAOS(String AOS) { this.AOS = AOS; }
    public ArrayList<genericPerson> getRecommendations() { return this.recommendations; }
    public void setRecommendations(ArrayList<genericPerson> _rec) { this.recommendations = _rec; }
    public void addRecommendation(genericPerson _rec) { 
        if(this.recommendations != null) {
            this.recommendations.add(_rec);
        }
    }
}
