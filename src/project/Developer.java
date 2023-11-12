package project;

import project.properties.Blok;
import project.properties.Estate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Developer {


    private static List<Developer> allDevelopers = new ArrayList<>();
    private final String developerName;
    private List<Estate> developerEstates;
    private List<Blok> developerBlocks;

    public Developer(String developerName) {
        this.developerName = developerName;
        this.developerEstates = new ArrayList<>();
        this.developerBlocks = new ArrayList<>();
        if(!allDevelopers.contains(this))
            allDevelopers.add(this);
    }

    public static List<Developer> getAllDevelopers() {
        if(allDevelopers.isEmpty()) return Collections.emptyList();
        return allDevelopers;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public List<Estate> getDeveloperEstates() {
        return developerEstates;
    }

    public List<Blok> getDeveloperBlocks() {

        developerBlocks.clear();
        for (Estate estate : developerEstates) {
            developerBlocks.addAll(estate.getBlocks());
        }
        return developerBlocks;
    }


    public Estate addEstate(String estateName) {
        Estate newEstate = new Estate(estateName);
        this.developerEstates.add(newEstate);
        return newEstate;
    }




}
