package models.general.people;

public class professor extends genericPerson {
    private String pid;
    private String name;
    private String address;
    private String AOS;

    public professor(String pid, String name, String address, String aos) {
        this.pid = pid;
        this.name = name;
        this.address = address;
        this.AOS = aos;
    }

    public String getPID() { return this.pid; }
    public void setPID(String pid) { this.pid = pid }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return this.address; }
    public void setAddress(String address) { this.address = address; }
    public String getAOS() { return this.AOS; }
    public void setAOS(String AOS) { this.AOS = AOS; }
}
