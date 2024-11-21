package models.general.people;

public class genericPerson {
    private String name;
    private String phoneNumber;

    public genericPerson() {
    }

    public genericPerson(String name, String phoneNum) {
        this.name = name;
        this.phoneNumber = phoneNum;
    }

    public void setName(String _name) { this.name = _name;}
    public String getName() { return this.name; }
    public void setPhoneNumber(String _pn) { this.phoneNumber = _pn;}
    public String getPhoneNumber() { return this.phoneNumber; }
}