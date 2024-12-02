package models.academics;

import java.util.ArrayList;

import models.general.people.professor;
import models.general.people.professorLookup;
import src.DatabaseSupport;

public class ProfessorController {
    DatabaseSupport db;

    public ProfessorController(DatabaseSupport _db) {
        this.db = _db;
    }

    public professorLookup getProfessor(String pid) {
        return this.db.getProfessor(pid);
    };

    public professor updateProfessor(String pid, professor p) {
        this.db.updateProfessor(pid, p);
        return p;
    }

    public ArrayList<professorLookup> getAllProfessors() {
        return this.db.getProfessors();
    }
}
