package project.properties;

import project.Person;
import project.interfaces.Measureable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Thingable implements Measureable {

    protected static List<Thingable> allThingableObjects = new ArrayList<>();
    protected Person owner;
    protected String name;
    double volume;

    boolean isInStorage;

    public Thingable(String name, double volume, Person owner) {
        this.name = name;
        this.volume = volume;
        this.owner = owner;
        this.isInStorage = false;
        if(!allThingableObjects.contains(this))
            allThingableObjects.add(this);
    }
    public Thingable(String name, double volume) {
        this.name = name;
        this.volume = volume;
        this.owner = null;
        this.isInStorage = false;
        if(!allThingableObjects.contains(this))
            allThingableObjects.add(this);
    }

    public static List<Thingable> getAllExistedThingableEntities() {
        if(allThingableObjects.isEmpty()) return Collections.emptyList();
        return allThingableObjects;
    }

    public boolean isInStorage() {
        return isInStorage;
    }
    public void setAsStorage(boolean state) {
        this.isInStorage = state;
    }

    public Person getOwner() {
        return owner;
    }
    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    @Override
    public double getVolume() {
        return  this.volume;
    }
}
