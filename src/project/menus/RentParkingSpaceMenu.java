package project.menus;

import project.Person;
import project.exeptions.*;
import project.properties.ParkingSpace;
import project.properties.Property;
import project.exeptions.TooManyThingsException;
import project.exeptions.TooManyPropertiesException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class RentParkingSpaceMenu {

    public static void displayMenu(Person person)
            throws ProblematicTenantException, AlreadyRentedException, AlreadyExistsException,
            TooManyRentedPropertiesException, TooManyThingsException, IOException, DontHaveRentedFlatException, TooManyPropertiesException {

        Scanner scanner = new Scanner(System.in);

        List<ParkingSpace> availableParkingSpaces = Property.getAllProperties().stream()
                .filter(property -> property instanceof ParkingSpace)
                .map(property -> (ParkingSpace) property)
                .filter(parkingSpace -> parkingSpace.getTenant() == null)
                .toList();


        if(!availableParkingSpaces.isEmpty()){
            while (true) {
                System.out.println("Wybierz miejsce parkingowe ktore chcesz wynajac: \n");

                int i = 1;
                for (ParkingSpace parkingSpace : availableParkingSpaces) {
                    System.out.println(i++ + ". " + parkingSpace.getPropertyData());
                }
                int chosenParkingSpaceIndex = scanner.nextInt();
                scanner.nextLine();

                if (chosenParkingSpaceIndex > 0 && chosenParkingSpaceIndex <= availableParkingSpaces.size()) {
                    ParkingSpace chosenParkingSpace = availableParkingSpaces.get(chosenParkingSpaceIndex - 1);

                    System.out.print("Wybierz date do kiedy chcesz wynajac miejsce parkingowe w formacie YYYY-MM-DD: ");
                    String endRentingDateStr = scanner.next();
                    scanner.nextLine();

                    LocalDate endRentingDateObj = LocalDate.parse(endRentingDateStr);

                    chosenParkingSpace.rent(person, endRentingDateObj);

                    PersonMenu.displayMenu(person);
                } else System.out.println("Niewlasciwy wybor. Sprobuj jeszcze raz.");
            }
        } else {
            System.out.println("Nie ma aktualnie miejsc parkingowych ktore moglbys wynajac");
            PersonMenu.displayMenu(person);
        }
    }
}
