package models.academics;

import java.util.HashMap;

import models.general.people.professor;
import src.DatabaseSupport;

public class ProfessorController {
    DatabaseSupport db;

    public ProfessorController(DatabaseSupport _db) {
        this.db = _db;
    }

    public professor getProfessor(String pid) {
        return this.db.getProfessors().get(pid);
    };

    public HashMap<String, professor> getAllProfessors() {
        return this.db.getProfessors();
    }
}
