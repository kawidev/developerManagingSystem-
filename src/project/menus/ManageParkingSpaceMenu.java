package project.menus;

import project.Person;
import project.exeptions.*;
import project.properties.ParkingSpace;
import project.exeptions.TooManyThingsException;
import project.exeptions.TooManyPropertiesException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ManageParkingSpaceMenu {

    public static void displayMenu(Person person)
            throws TooManyRentedPropertiesException, ProblematicTenantException, AlreadyExistsException,
            AlreadyRentedException, TooManyThingsException, IOException, DontHaveRentedFlatException, TooManyPropertiesException {

        Scanner scanner = new Scanner(System.in);

        List<ParkingSpace> rentedParkingSpaces = person.getRentedParkingSpaces();

        while(true) {
            System.out.println("Wybierz miejsce parkingowe ktorym chcesz zarzadzac: \n");

            int i = 1;
            for(ParkingSpace parkingSpace : rentedParkingSpaces) {
                System.out.println(i++ + ". " + parkingSpace.getPropertyData());
            }
            int chosenParkingSpaceIndex = scanner.nextInt(); scanner.nextLine();

            if(chosenParkingSpaceIndex > 0 && chosenParkingSpaceIndex <= rentedParkingSpaces.size()) {
                ParkingSpace chosenParkingSpace = rentedParkingSpaces.get(chosenParkingSpaceIndex - 1);

                ManageSpaceMenu.displayMenu(chosenParkingSpace);

                break;
            } else System.out.println("Niepoprawny wybor. Sprobuj jeszcze raz");
        }

    }
}
