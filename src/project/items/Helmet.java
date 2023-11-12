package project.items;

import project.Person;
import project.properties.Thingable;

public class Helmet extends Thingable {
    public Helmet(String name, double volume, Person owner) {
        super(name, volume, owner);
    }

    public Helmet(String name, double volume) {
        super(name, volume);
    }
}
