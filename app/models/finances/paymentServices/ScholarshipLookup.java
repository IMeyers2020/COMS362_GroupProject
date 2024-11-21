package models.finances.paymentServices;

public class ScholarshipLookup {
    public String key;
    public Scholarship value;

    public ScholarshipLookup(String key, Scholarship val) {
        this.key = key;
        this.value = val;
    }
}
