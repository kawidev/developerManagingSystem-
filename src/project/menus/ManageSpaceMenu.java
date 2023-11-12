package project.menus;

import project.Person;
import project.exeptions.*;
import project.interfaces.Driveable;
import project.interfaces.Swimmable;
import project.properties.ParkingSpace;
import project.properties.Thingable;
import project.exeptions.TooManyThingsException;
import project.exeptions.TooManyPropertiesException;
import project.vehicles.Boat;
import project.vehicles.Vehicle;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ManageSpaceMenu {

    public static void displayMenu(ParkingSpace parkingSpace)
            throws TooManyRentedPropertiesException, ProblematicTenantException, AlreadyRentedException,
            TooManyThingsException, IOException, DontHaveRentedFlatException, AlreadyExistsException, TooManyPropertiesException {

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Wybierz co chcesz zrobic: ");

            System.out.println("1. Zaparkuj pojazd lub umiesc lodz");
            if(!(parkingSpace.getVehicle() instanceof Boat))
                System.out.println("2. Wyjedz pojazdem");
            else System.out.println("2. Wyjmij lodz do rzeki");
            System.out.println("3. Wloz przedmiot");
            System.out.println("4. Wyjmij przedmiot");
            System.out.println("5. Wroc do poprzedniego menu");

            System.out.print("Wybor: ");

            int choice = scanner.nextInt(); scanner.nextLine();

            switch (choice) {
                case 1: {
                    Person parkingSpaceTenant = parkingSpace.getTenant();

                    List<Vehicle> ownedVehicles = Thingable.getAllExistedThingableEntities().stream()
                                    .filter(thing -> thing instanceof Vehicle)
                                            .map(thing -> (Vehicle) thing)
                                                    .filter(vehicle -> vehicle.getOwner() == parkingSpaceTenant)
                                                            .toList();

                    List<Vehicle> ownedButNotStoragedVehicles = ownedVehicles.stream().filter(vehicle -> !vehicle.isInStorage()).toList();

                    if(ownedButNotStoragedVehicles.isEmpty()) {
                        System.out.println("Nie masz zadnego pojazdu ktory moglbys zaparkowac");
                        break;
                    } else {
                        System.out.println("Wybierz pojazd lub lodz ktora chcesz umiescic w garazu: ");

                        int i = 1;
                        for(Vehicle vehicle : ownedButNotStoragedVehicles) {
                            System.out.println(i++ + ". " + vehicle.getName());
                        }
                        int chosenVehicleIndex = scanner.nextInt(); scanner.nextLine();

                        if(chosenVehicleIndex > 0 && chosenVehicleIndex <= ownedButNotStoragedVehicles.size()) {
                            Vehicle chosenVehicle = ownedButNotStoragedVehicles.get(chosenVehicleIndex - 1);

                            parkingSpace.storeItem(chosenVehicle);
                            break;
                        } else System.out.println("Nieprawidlowy wybor");
                    }
                }
                case 2: {
                    if (parkingSpace.getVehicle() != null) {
                        Vehicle storagedVehicle = parkingSpace.getVehicle();

                        if (storagedVehicle instanceof Driveable) {
                            System.out.print("Dokad chcesz jechac?: ");
                            String destination = scanner.next();
                            scanner.nextLine();

                            ((Driveable) storagedVehicle).drive(destination);
                            storagedVehicle.setAsStorage(false);
                        } else if (storagedVehicle instanceof Swimmable) {
                            System.out.print("Dokad chcesz plynac?: ");
                            String destination = scanner.next();
                            scanner.nextLine();
                            ((Swimmable) storagedVehicle).swim(destination);
                            storagedVehicle.setAsStorage(false);
                        }
                    }
                    break;
                }
                case 3: {
                        List<Thingable> notStoragedItemsElseThanVehicleOwnedByParkingSpaceTenant =
                                Thingable.getAllExistedThingableEntities().stream()
                                        .filter(item -> !item.isInStorage() && !(item instanceof Vehicle) && item.getOwner() == parkingSpace.getTenant())
                                        .toList();

                        while(true) {


                            if(notStoragedItemsElseThanVehicleOwnedByParkingSpaceTenant.isEmpty()) {
                                System.out.println("Nie posiadasz zadnych przedmiotow");
                                break;
                            } else {
                                System.out.println("Wybierz przedmiot ktory chcesz umiescic w schowku: \n");
                                int i = 1;
                                for(Thingable item : notStoragedItemsElseThanVehicleOwnedByParkingSpaceTenant) {
                                    System.out.println(i++ + ". " + item.getName());
                                }
                                int chosenItemIndex = scanner.nextInt(); scanner.nextLine();

                                if(chosenItemIndex > 0 && chosenItemIndex <= notStoragedItemsElseThanVehicleOwnedByParkingSpaceTenant.size()) {
                                    Thingable chosenItem = notStoragedItemsElseThanVehicleOwnedByParkingSpaceTenant.get(chosenItemIndex - 1);
                                    parkingSpace.storeItem(chosenItem);
                                    break;
                                } else System.out.println("Nieprawidlowy numer. Wybierz jeszcze raz");
                            }
                        }
                        break;
                }
                case 4: {
                    List<Thingable> storagedItems = parkingSpace.getStorage().getItems().stream()
                            .filter(item -> !(item instanceof Vehicle)).toList();

                    if (storagedItems.isEmpty()) {
                        System.out.println("Nie masz zadnych przedmiotow w schowku.");
                        break;
                    } else {
                        while (true) {
                            System.out.println("Wybierz przedmiot ktory chcesz wyjac ze schowka: \n");

                            int i = 1;
                            for (Thingable item : storagedItems) {
                                System.out.println(i++ + ". " + item.getName());
                            }
                            int chosenItemIndex = scanner.nextInt();
                            scanner.nextLine();

                            if (chosenItemIndex > 0 && chosenItemIndex <= storagedItems.size()) {
                                Thingable selectedItem = storagedItems.get(chosenItemIndex - 1);
                                parkingSpace.retrieveItem(selectedItem);
                                break;
                            } else System.out.println("Niepoprawny wybor. Wprowadz jeszcze raz nr");
                        }
                    }
                }
                case 5:
                    PersonMenu.displayMenu(parkingSpace.getTenant()); break;
            }
        }

    }
}
