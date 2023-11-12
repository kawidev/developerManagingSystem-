package project.items;

import project.Person;
import project.properties.Thingable;

import java.io.Serializable;

public class Vacuum extends Thingable {
    public Vacuum(String name, double volume, Person owner) {
        super(name, volume, owner);
    }

    public Vacuum(String name, double volume) {
        super(name, volume);
    }
}
