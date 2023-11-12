package project.vehicles;

import project.interfaces.Driveable;
import project.properties.Storage;
import project.properties.Thingable;

public class Motor extends Vehicle implements Driveable {

    private Storage<Thingable> motorStorage;
    public Motor(String vehicleName, double vehicleVolume, double engineVolume, String vehicleType, String engineType) {
        super(vehicleName, vehicleVolume, engineVolume, vehicleType, engineType);
        this.motorStorage = new Storage<>();
    }

    public void putItemToStorage(Thingable item) {
        if(!motorStorage.contains(item)) {
            motorStorage.addItem(item);
            System.out.println("Wyjales ze schowka " + item.getName());
        } else System.out.println("Nie mozesz umiescic w schowku przedmiotu ktory juz w nim jest");
    }

    public void removeFromStorage(Thingable item) {
        if(motorStorage.contains(item))
            motorStorage.removeItem(item);
        else System.out.println("Nie ma takiego przedmiotu w schowku na kask");
    }

    @Override
    public void drive(String address) {
        System.out.println("Jedziesz motorem do: " + address);
    }
}
