package project.vehicles;

import project.interfaces.Driveable;

public class CityCar extends Vehicle implements Driveable {

    private boolean isEcoModeEnabled;
    public CityCar(String vehicleName, double vehicleVolume, double engineVolume, String vehicleType, String engineType) {
        super(vehicleName, vehicleVolume, engineVolume, vehicleType, engineType);

        this.isEcoModeEnabled = true;
    }

    @Override
    public void drive(String address) {
        System.out.println("Jedziesz autem miejskim do: " + address);
    }

    public void enableEcoMode() {
        if (!isEcoModeEnabled) {
            isEcoModeEnabled = true;
            System.out.println("Wlaczyles tryb eko - ilosc spalanego paliwa bedzie mniejsza");
        } else System.out.println("Tryb eko jest juz wlaczony");
    }

    public void disableEcoMode() {
        if (isEcoModeEnabled) {
            isEcoModeEnabled = false;
            System.out.println("Wylaczyles tryb eko - ilosc spalanego paliwa bedzie wieksza");
        } else System.out.println("Tryb eko jest juz wylaczony");
    }
}
