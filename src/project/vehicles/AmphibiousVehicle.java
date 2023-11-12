package project.vehicles;

import project.interfaces.Driveable;
import project.interfaces.Swimmable;

public class AmphibiousVehicle extends Vehicle implements Driveable, Swimmable {

    enum Mode {
        SWIM_MODE, DRIVE_MODE
    }

    private Mode currentMode;
    public AmphibiousVehicle(String vehicleName, double vehicleVolume, double engineVolume, String vehicleType, String engineType) {
        super(vehicleName, vehicleVolume, engineVolume, vehicleType, engineType);
        this.currentMode = Mode.DRIVE_MODE;
    }

    public void switchMODE(Mode mode) {
        this.currentMode = mode;
    }

    @Override
    public void drive(String address) {
        if(currentMode == Mode.DRIVE_MODE) {
            System.out.println("Jedziesz amfibią do: " + address);
        } else {
            System.out.println("Najpierw zmien tryb na odpowiedni");
        }

    }

    @Override
    public void swim(String destination) {
        if(currentMode == Mode.SWIM_MODE) {
            System.out.println("Plyniesz amfibią do: " + destination);
        } else {
            System.out.println("Najpierw zmien tryb na odpowiedni");
        }

    }
}
