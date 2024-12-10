package models.general.items;

import models.Diningplan.Dplan;

/**
 * Represents a lookup entry for a dining plan.
 * Stores a key-value pair where the key is a unique identifier for a dining plan, and the value
 * is the associated `Dplan` object that contains detailed information about the dining plan.
 */
public class diningplanlookups {

    /** The key used to identify the dining plan (e.g., dining plan ID) */
    public String key;
    
    /** The value associated with the dining plan, which is a `Dplan` object containing dining plan details */
    public Dplan value;

    /**
     * Constructor for creating a dining plan lookup object.
     * Initializes the key-value pair for the dining plan lookup.
     *
     * @param key the unique identifier for the dining plan (e.g., dining plan ID)
     * @param val the `Dplan` object containing details about the dining plan
     */
    public diningplanlookups(String key, Dplan val) {
        this.key = key;    // Sets the dining plan key (e.g., dining plan ID)
        this.value = val;  // Sets the Dplan object associated with the dining plan
    }

    public diningplanlookups() {
    }

    /**
     * Retrieves the key (ID) for the dining plan.
     * 
     * @return the unique identifier for the dining plan
     */
    public String getKey() {
        return key;  // Return the key for the dining plan
    }

    /**
     * Retrieves the value (Dplan) associated with the key.
     * 
     * @return the Dplan object containing the details of the dining plan
     */
    public Dplan getValue() {
        return value;  // Return the Dplan object for the dining plan
    }

    /**
     * Sets the key (ID) for the dining plan.
     * 
     * @param key the unique identifier for the dining plan
     */
    public void setKey(String key) {
        this.key = key;  // Set the key for the dining plan
    }

    /**
     * Sets the value (Dplan) for the dining plan lookup.
     * 
     * @param value the Dplan object containing the dining plan details
     */
    public void setValue(Dplan value) {
        this.value = value;  // Set the Dplan object for the dining plan
    }
}
