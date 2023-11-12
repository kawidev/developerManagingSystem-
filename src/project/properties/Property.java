package project.properties;

import project.Person;
import project.TenantLetter;
import project.exeptions.AlreadyRentedException;
import project.exeptions.DontHaveRentedFlatException;
import project.exeptions.ProblematicTenantException;
import project.exeptions.TooManyRentedPropertiesException;
import project.interfaces.Measureable;
import project.interfaces.Rentable;
import project.threads.TimeSimulator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Property implements Rentable, Measureable {

    static List<Property> allProperties = new ArrayList<>();
    private static int nextId = 1;
    protected int id;
    protected int number;
    protected double volume;
    Person tenant;
    LocalDate startRentingDate, endRentingDate;

    boolean isReleaseNotificationSent;

    protected final Blok blok;

    abstract String getPropertyType();
    public abstract void releaseProperty();

    public Property(Blok blok, int number, double a, double b, double h) {
        this.blok = blok;
        this.volume = a*b*h;
        this.number = number;
        this.id = nextId++;
        this.tenant = null;
        if(!allProperties.contains(this))
            allProperties.add(this);
        this.isReleaseNotificationSent = false;
    }

    public Property(Blok blok, int number, double volume) {
        this.blok = blok;
        this.number = number;
        this.volume = volume;
        this.id = nextId++;
        this.isReleaseNotificationSent = false;
        if(!allProperties.contains(this))
            allProperties.add(this);
    }

    public Property getPropertyById(int id) {
        for(Property property : allProperties)
            if(property.id == id) return property;
        return null;
    }

    @Override
    public void renewRenting(LocalDate newEndingDate) {
        this.endRentingDate = newEndingDate;

        if (this.tenant != null && this.tenant.getTenantLetters() != null) {
            TenantLetter relatedLetter = this.tenant.getTenantLetters().stream()
                    .filter(letter -> letter.getProperty().equals(this))
                    .findFirst()
                    .orElse(null);

            if (relatedLetter != null) {
                this.tenant.removeTenantLetter(relatedLetter);
                TenantLetter.removeTenantLetterFromList(relatedLetter);
                System.out.println("Usunięto tenant letter z listy");
            } else {
                System.out.println("Nie znaleziono powiązanego tenant letter");
            }
        } else {
            System.out.println("Nie znaleziono najemcy lub zadnych tenant letters");
        }
        System.out.println("Najem został odnowiony do: " + newEndingDate);
    }


    @Override
    public double getVolume() {
        return volume;
    }

    public Blok getBlock() {
        return blok;
    }



    public String getPropertyData() {
        Estate estate = this.getBlock().getBlockEstate();

        return estate.getEstateName() + ": " + this.getBlock().getAddress() + " | " + this.getPropertyType() + " nr " +
                this.getPropertyNumber();
    }

    public int getPropertyNumber() {
        return number;
    }

    public static List<Property> getAllProperties() {
        if(allProperties.isEmpty())
            return Collections.emptyList();
        return allProperties;
    }

    public boolean isReleaseNotificationSent() {
        return this.isReleaseNotificationSent;
    }
    public void setReleaseNotificationSent(boolean state) {
        this.isReleaseNotificationSent = state;
    }

    @Override
    public Person getTenant() {
        return this.tenant;
    }

    @Override
    public void rent(Person tenant, LocalDate endRentingDate) throws TooManyRentedPropertiesException, ProblematicTenantException, AlreadyRentedException, DontHaveRentedFlatException {
        if (tenant.getTenantLetters().size() >= 3) {
            throw new ProblematicTenantException("Osoba " + tenant.getFirstName() + " " + tenant.getLastName()
                    + " posiadała już najem pomieszczeń: " + tenant.getRentedPropertiesList());
        }

        if (tenant.getRentedProperties().size() >= 5) {
            throw new TooManyRentedPropertiesException("Przekroczono limit wynajmowanych nieruchomosci");
        }

        if (this.tenant == null) {
            if(!endRentingDate.isBefore(TimeSimulator.getCurrentTime())){
                this.tenant = tenant;
                this.startRentingDate = TimeSimulator.getCurrentTime();
                this.endRentingDate = endRentingDate;
                tenant.addPropertyAsRented(this);
            } else {
                System.out.println("Nie mozesz wynajac mieszkania z data wsteczna");
            }
        } else {
            throw new AlreadyRentedException("To " + this.getPropertyType() +" jest już wynajęte przez Osobe "
                    + this.tenant.getFirstName() + " " + this.tenant.getLastName());
        }
    }

    @Override
    public LocalDate getRentingStartDate() {
        return startRentingDate;
    }

    @Override
    public LocalDate getRentingEndDate() {
        return endRentingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(id, property.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public int getId() {
        return this.id;
    }
}
