package models.academics;

import java.util.HashMap;

import models.general.items.Major;
import src.DatabaseSupport;

public class MajorController {
    DatabaseSupport db;

    public MajorController(DatabaseSupport _db) {
        this.db = _db;
    }
    
    public HashMap<String, Major> getAllMajors() {
        return DatabaseSupport.getAllMajors();
    }
}
