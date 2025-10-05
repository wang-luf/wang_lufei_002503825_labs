/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Profiles;

import Business.Person.Person;
import java.util.ArrayList;

public class FacultyDirectory {
    
    private final ArrayList<FacultyProfile> facultyList;

    public FacultyDirectory() {
        facultyList = new ArrayList<>();
    }

    public FacultyProfile newFacultyProfile(Person p) {
        FacultyProfile fp = new FacultyProfile(p);
        facultyList.add(fp);
        return fp;
    }

    public ArrayList<FacultyProfile> getFacultyList() {
        return facultyList;
    }
}

