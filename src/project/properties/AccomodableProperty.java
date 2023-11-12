package project.properties;

import project.Person;
import project.exeptions.AlreadyRentedException;
import project.exeptions.DontHaveRentedFlatException;
import project.exeptions.ProblematicTenantException;
import project.exeptions.TooManyRentedPropertiesException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AccomodableProperty extends Property {

    Set<Person> tenants;


    public AccomodableProperty(Blok blok, int number, double a, double b, double h) {
        super(blok, number, a, b, h);
        this.tenants = new HashSet<>();
    }

    public AccomodableProperty(Blok blok, int number, double volume) {
        super(blok, number, volume);
        this.tenants = new HashSet<>();
    }

    public void addPerson(Person person) {
        this.tenants.add(person);
        person.setAddress(this);
    }

    public void removePerson(Person person) {

        if(tenants.contains(person)) {
            this.tenants.remove(person);
            person.deleteAddress();
        } else {
            System.out.println("W nieruchomosci nie ma takiej osoby");
        }
    }

    public List<Person> getPeople() {
        List<Person> people = new ArrayList<>(this.tenants);
        return people;
    }

    @Override
    public void releaseProperty() {
        this.tenants.clear();
        this.tenant.removePropertyFromRentedList(this);
        this.tenant = null;
    }

    @Override
    public void rent(Person tenant, LocalDate endRentingDate) throws TooManyRentedPropertiesException, ProblematicTenantException, AlreadyRentedException, DontHaveRentedFlatException {
        super.rent(tenant, endRentingDate);
        tenant.setAddress(this);
    }
}
