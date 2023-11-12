package project.properties;

import project.exeptions.AlreadyExistsException;
import project.exeptions.TooManyPropertiesException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Blok {

    private final int MAX_FLAT_NUMBER;

    private static List<Blok> allBlocks = new ArrayList<>();

    private final Estate estate;
    private final String streetName;
    private final String blockNumber;
    private List<Property> properties;

    public Blok(Estate estate, String nazwaUlicy, String numerBloku, int liczbaMieszkan) throws AlreadyExistsException {

        List<Blok> blocksInEstate = estate.getBlocks();
        for(Blok block : blocksInEstate) {
            if(block.getStreetName().equals(nazwaUlicy) && block.getBuildingNumber().equals(numerBloku)) {
                throw new AlreadyExistsException("Ten blok juz istnieje na tym osiedlu.");
            }
        }
        this.streetName = nazwaUlicy;
        this.blockNumber = numerBloku;
        this.MAX_FLAT_NUMBER = liczbaMieszkan;
        this.estate = estate;
        this.properties = new ArrayList<>();
        estate.addBlockToEstate(this);
        if(!allBlocks.contains(this))
            allBlocks.add(this);
    }


    public String getStreetName() {
        return streetName;
    }

    public String getBuildingNumber() {
        return blockNumber;
    }
    public String getAddress() {
        return streetName + " " + blockNumber;
    }

    public Estate getBlockEstate() {
        return estate;
    }

    public int getMaxApartmentsNumber() {
        return MAX_FLAT_NUMBER;
    }

    public boolean isApartmentNumberAvailable(int number) {
        return getApartments().stream().noneMatch(flat -> flat.getPropertyNumber() == number);
    }


    public Flat getFlatByNumber(int number) {
        List<Flat> flats = this.getApartments();
        for (Flat flat : flats) {
            if (flat.getPropertyNumber() == number) {
                return flat;
            }
        }
        System.out.println("Nie ma mieszkania o tym numerze");
        return null;
    }

    public List<Property> getProperties() {
        return properties;
    }
    public List<Flat> getApartments() {
        return properties.stream()
                .filter(p -> p instanceof Flat)
                .map(p -> (Flat) p)
                .toList();
    }
    public List<ParkingSpace> getParkingSpaces() {
        return properties.stream()
                .filter(p -> p instanceof ParkingSpace)
                .map(p -> (ParkingSpace) p)
                .toList();
    }

    public <T extends Property> T createNewProperty(int number, double a, double b, double h, Class<T> type)
            throws AlreadyExistsException, TooManyPropertiesException {

        List<Flat> apartments = this.getApartments();
        List<ParkingSpace> parkingSpaces = this.getParkingSpaces();

        if (Flat.class.isAssignableFrom(type)) {

            if (apartments.size() >= this.getMaxApartmentsNumber()) {
                throw new TooManyPropertiesException("Przekroczono maksymalną liczbę mieszkań w bloku.");
            }
            if (!isApartmentNumberAvailable(number)) {
                throw new AlreadyExistsException("Mieszkanie o numerze " + number + " już istnieje.");
            }

        } else if (ParkingSpace.class.isAssignableFrom(type)) {

            if (parkingSpaces.size() >= this.getMaxApartmentsNumber()) {
                throw new TooManyPropertiesException("Liczba miejsc parkingowych nie może przekraczać maksymalnej liczby mieszkań.");
            }
            if (parkingSpaces.stream().anyMatch(p -> p.getPropertyNumber() == number)) {
                throw new AlreadyExistsException("Miejsce parkingowe o numerze " + number + " już istnieje.");
            }
        }

        try {
            // Uzyskujemy konstruktor klasy
            Constructor<T> constructor = type.getDeclaredConstructor(Blok.class, int.class, double.class);

            // Tworzymy instancję, przekazując odpowiednie argumenty
            T newProperty = constructor.newInstance(this, number, a*b*h);

            // Dodajemy nowe pomieszczenie do listy ogólnej
            this.addPropertyToBlock(newProperty);

            return newProperty;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T extends Property> T createNewProperty(int number, double volume, Class<T> type)
            throws AlreadyExistsException, TooManyPropertiesException {

        List<Flat> apartments = this.getApartments();
        List<ParkingSpace> parkingSpaces = this.getParkingSpaces();

        if (Flat.class.isAssignableFrom(type)) {

            if (apartments.size() >= this.getMaxApartmentsNumber()) {
                throw new TooManyPropertiesException("Przekroczono maksymalną liczbę mieszkań w bloku.");
            }
            if (apartments.stream().anyMatch(m -> m.getPropertyNumber() == number)) {
                throw new AlreadyExistsException("Mieszkanie o numerze " + number + " już istnieje.");
            }

        } else if (ParkingSpace.class.isAssignableFrom(type)) {

            if (parkingSpaces.size() >= this.getMaxApartmentsNumber()) {
                throw new TooManyPropertiesException("Liczba miejsc parkingowych nie może przekraczać maksymalnej liczby mieszkań.");
            }
            if (parkingSpaces.stream().anyMatch(p -> p.getPropertyNumber() == number)) {
                throw new AlreadyExistsException("Miejsce parkingowe o numerze " + number + " już istnieje.");
            }
        }

        try {

            Constructor<T> constructor = type.getDeclaredConstructor(Blok.class, int.class, double.class);

            T newProperty = constructor.newInstance(this, number, volume);

            this.addPropertyToBlock(newProperty);

            return newProperty;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            // Obsługa błędów
            e.printStackTrace();
            return null;
        }
    }
    public void addPropertyToBlock(Property property) {
        if(!properties.contains(property))
            properties.add(property);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blok blok = (Blok) o;
        return Objects.equals(streetName, blok.streetName) &&
                Objects.equals(blockNumber, blok.blockNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, blockNumber);
    }
}
