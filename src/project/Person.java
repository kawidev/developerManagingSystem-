package project;

import project.properties.AccomodableProperty;
import project.properties.Flat;
import project.properties.ParkingSpace;
import project.properties.Property;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Person {


    private final String firstName;
    private final String lastName;
    private AccomodableProperty address;
    private final String pesel;
    private final LocalDate birthDate;

    private boolean isAccomodated;

    private List<Property> rentedProperties;
    private List<Flat> rentedFlats;
    private List<ParkingSpace> rentedParkingSpaces;

    private List<TenantLetter> tenantLetters;

    private static List<Person> people = new ArrayList<>();

    public Person(String firstName, String lastName, String pesel, String birthDateStr) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = null;
        this.birthDate = LocalDate.parse(birthDateStr);
        this.pesel = pesel;
        this.rentedProperties = new ArrayList<>();
        this.rentedFlats = new ArrayList<>();
        this.rentedParkingSpaces = new ArrayList<>();
        this.tenantLetters = new ArrayList<>();
        this.isAccomodated = false;
        if(!people.contains(this))
            people.add(this);
    }

    public static List<Person> getPeople() {
        return people;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<TenantLetter> getTenantLetters() {
        return tenantLetters;
    }

    public void addPropertyAsRented(Property property) {
        this.rentedProperties.add(property);
    }

    public void removePropertyFromRentedList(Property property) {
        if(rentedProperties.contains(property)) {
            rentedProperties.remove(property);
        } else {
            System.out.println("Nie ma takiej nieruchomosci na liscie wynajmowanych przez Ciebie");
        }
    }

    public List<Flat> getRentedFlats() {
        return rentedProperties.stream()
                .filter(Flat.class::isInstance)
                .map(Flat.class::cast)
                .collect(Collectors.toList());
    }

    public List<ParkingSpace> getRentedParkingSpaces() {
        return rentedProperties.stream()
                .filter(ParkingSpace.class::isInstance)
                .map(ParkingSpace.class::cast)
                .collect(Collectors.toList());
    }
    public List<Property> getRentedProperties() {
        return rentedProperties;
    }

    public String getRentedPropertiesList() {
        StringBuilder sb = new StringBuilder();
        for(Property property: rentedProperties) {
            sb.append(property.getPropertyData()).append("\n");
        }
        return sb.toString();
    }

    public boolean isAccomodated() {
        return isAccomodated;
    }
    public void setAddress(AccomodableProperty property) {
        this.isAccomodated = true;
        this.address = property;
    }
    public void deleteAddress() {
        this.isAccomodated = false;
        this.address = null;
    }

    public void addTenantLetter(TenantLetter tenantLetter) {
        this.tenantLetters.add(tenantLetter);
    }

    public void removeTenantLetter(TenantLetter tenantLetter) {
        tenantLetters.remove(tenantLetter);
    }

    public void printTenantLetters() {
        if (tenantLetters.isEmpty()) {
            System.out.println("Nie masz żadnych listów Tenant letters.");
        } else {
            for (TenantLetter tenantLetter : tenantLetters) {
                System.out.println(tenantLetter.getProperty().getPropertyData());
            }
        }
    }





}
