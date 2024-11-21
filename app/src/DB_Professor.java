package src;

import java.util.ArrayList;

import models.general.people.professorLookup;

public class DB_Professor {
    public ArrayList<professorLookup> professors;

    public DB_Professor() {
    }

    public void setProfessors(ArrayList<professorLookup> _professors) {
        this.professors = _professors;
    }
}
