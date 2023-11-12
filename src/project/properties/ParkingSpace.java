package project.properties;

import project.Person;
import project.exeptions.*;
import project.vehicles.Vehicle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ParkingSpace extends StoragableProperty<Thingable> {

    Vehicle vehicle;

    static List<ParkingSpace> allParkingSpaces = new ArrayList<>();

    public ParkingSpace(Blok blok, int numer, double a, double b, double h) {
        super(blok,numer, a, b, h);
        this.vehicle = null;
        if(!allParkingSpaces.contains(this))
            allParkingSpaces.add(this);
    }

    public ParkingSpace(Blok blok, int numer, double volume) {
        super(blok, numer, volume);
        this.vehicle = null;
        if(!allParkingSpaces.contains(this))
            allParkingSpaces.add(this);
    }

    public String getPropertyType() {
        return "Miejsce Garazowe";
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public void rent(Person person, LocalDate endRentingDate) throws TooManyRentedPropertiesException, ProblematicTenantException, AlreadyRentedException, DontHaveRentedFlatException {
        if(person.getRentedFlats().isEmpty()) {
            throw new DontHaveRentedFlatException("Najpierw musisz wynajac jakies mieszkanie");
        } else {
            super.rent(person, endRentingDate);
        }
    }

    @Override
    public void storeItem(Thingable item) throws TooManyThingsException {

        boolean isEnoughSpace = this.volume - this.storage.getVolume() > item.getVolume();
        if(isEnoughSpace) {
            if((item instanceof Vehicle) && this.vehicle == null) {
                this.vehicle = (Vehicle) item;
                this.vehicle.setAsStorage(true);
            }
            if(storage.contains(item)) System.out.println("W miejscu parkingowym skladujesz juz " + item.getName());
            else {
                this.storage.addItem(item);
                System.out.println("Skladujesz " + item.getName());
            }
        } else {
            throw new TooManyThingsException("Remove some old items to insert a new item.");
        }

    }

    @Override
    public void retrieveItem(Thingable item) {
        if(item instanceof Vehicle) {
            if(storage.contains(item)) {
                System.out.println("Wyjezdzasz: " + item.getName());
                storage.removeItem(item);
            } else {
                System.out.println("Nie ma takiego pojazdu w magazynie");
            }
        } else {
            if(storage.contains(item)) {
                System.out.println("Wyjmujesz " + item.getName());
                storage.removeItem(item);
            } else {
                System.out.println("Nie ma takiego przedmiotu.");
            }
        }
    }

    @Override
    public void releaseProperty() {
        super.releaseProperty();
        this.vehicle = null;
    }
}
