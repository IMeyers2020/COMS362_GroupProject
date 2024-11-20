package models.general.items;

import models.dorms.DormInfo;

public class dormLookup {
    public String key;
    public DormInfo value;

    public dormLookup(String key, DormInfo val) {
        this.key = key;
        this.value = val;
    }
}
