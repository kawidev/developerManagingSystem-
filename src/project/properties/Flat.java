package project.properties;

import project.exeptions.AlreadyExistsException;

public class Flat extends AccomodableProperty  {

    public Flat(Blok blok, int number, double a, double b, double h) throws AlreadyExistsException {
        super(blok, number, a, b, h);
    }

    public Flat(Blok blok, int number, double volume) throws AlreadyExistsException {
        super(blok, number, volume);
    }

    @Override
    String getPropertyType() {
        return "Mieszkanie";
    }
}
