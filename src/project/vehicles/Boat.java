package project.vehicles;

import project.interfaces.Swimmable;
public class Boat extends Vehicle implements Swimmable {

    private boolean isAnchorThrown;

    public Boat(String vehicleName, double vehicleVolume, double engineVolume, String vehicleType, String engineType) {
        super(vehicleName, vehicleVolume, engineVolume, vehicleType, engineType);
        this.isAnchorThrown = false;
    }

    public void throwAnchor() {
        if(!isAnchorThrown) {
            System.out.println("Rzuciles kotwice");
            this.isAnchorThrown = true;
        } else System.out.println("Kotwica jest juz w wodzie");

    }
    public void takeOutAnchorFromWater() {
        if(isAnchorThrown) {
            System.out.println("Wyjales kotwice");
            this.isAnchorThrown = false;
        } else System.out.println("Nie masz zarzuconej kotwicy");

    }

    @Override
    public void swim(String destination) {
        if(!isAnchorThrown)
            System.out.println("Plyniesz lodzia do: " + destination);
        else System.out.println("Najpierw wyjmij kotwice z wody");
    }
}
