package project.vehicles;

import project.interfaces.Driveable;


public class OffRoadCar extends Vehicle implements Driveable {

    private boolean is4x4ModeOn;
    public OffRoadCar(String vehicleName, double vehicleVolume, double engineVolume, String vehicleType, String engineType) {
        super(vehicleName, vehicleVolume, engineVolume, vehicleType, engineType);
        this.is4x4ModeOn = true;
    }

    @Override
    public void drive(String address) {
        System.out.println("Jedziesz autem terenowym do: " + address);
    }

    public void switchOn4x4Mode() {
        if(!is4x4ModeOn) {
            is4x4ModeOn = true;
            System.out.println("Wlaczyles naped na 4 kola");
        } else System.out.println("Naped na 4 kola jest juz wlaczony");
    }

    public void switchOff4x4Mode() {
        if(is4x4ModeOn) {
            is4x4ModeOn = false;
            System.out.println("Wylaczyles naped na 4 kola");
        } else System.out.println("Naped na 4 kola jest juz wylaczony");
    }
}
