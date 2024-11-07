package models.general.people;

public class genericPerson {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String SSN;

    public void setFirstName(String _firstName) { this.firstName = _firstName;}
    public String getFirstName() { return this.firstName; }
    public void setLastName(String _lastName) {this.lastName = _lastName;}
    public String getLastName() { return this.lastName; }
}