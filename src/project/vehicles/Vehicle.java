package project.vehicles;

import project.properties.Thingable;

public abstract class Vehicle extends Thingable {

    double engineVolume;
    String vehicleType;
    String engineType;

    public Vehicle(String vehicleName, double volume, double engineVolume, String vehicleType, String engineType) {
        super(vehicleName, volume);

        this.engineVolume = engineVolume;
        this.vehicleType = vehicleType;
        this.engineType = engineType;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getEngineType() {
        return engineType;
    }
}
