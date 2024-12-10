package src;

import java.util.ArrayList;
import models.general.items.diningplanlookups;  // Assuming you have this class for dining plan lookups

/**
 * Class that represents a database or container for storing dining plan lookups.
 * This class holds a list of dining plan lookup entries (key-value pairs),
 * where the key is a unique dining plan ID, and the value is the associated dining plan details.
 */
public class DB_DININGPLAN {

    /** A list of dining plan lookup entries */
    public ArrayList<diningplanlookups> diningPlans;
    

    /**
     * Sets the list of dining plan lookup entries.
     *
     * @param diningPlans the list of dining plan lookups to set
     */
    public void setDiningPlans(ArrayList<diningplanlookups> diningPlans) {
        this.diningPlans = diningPlans;  // Set the list of dining plan lookups
    }
}
