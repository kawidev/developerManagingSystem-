package project.menus;
import project.Person;
import project.exeptions.*;
import project.properties.ParkingSpace;
import project.threads.TimeSimulator;
import project.exeptions.TooManyThingsException;
import project.exeptions.TooManyPropertiesException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class RenewRentingParkingSpaceMenu {

    public static void displayMenu(Person person)
            throws TooManyRentedPropertiesException, ProblematicTenantException, AlreadyExistsException,
            AlreadyRentedException, TooManyThingsException, IOException, DontHaveRentedFlatException, TooManyPropertiesException {

        Scanner scanner = new Scanner(System.in);

        List<ParkingSpace> rentedParkingSpaces = person.getRentedParkingSpaces();


        if(!rentedParkingSpaces.isEmpty()){
            while (true) {
                System.out.println("Wybierz miejsce parkingowe ktorego najem chcesz odnowic lub przedluzyc: \n");
                int i = 1;
                for (ParkingSpace parkingSpace : rentedParkingSpaces) {
                    System.out.println(i++ + ". " + parkingSpace.getPropertyData());
                }
                int chosenParkingSpaceIndex = scanner.nextInt();
                scanner.nextLine();

                if (chosenParkingSpaceIndex > 0 && chosenParkingSpaceIndex <= rentedParkingSpaces.size()) {
                    ParkingSpace chosenParkingSpace = rentedParkingSpaces.get(chosenParkingSpaceIndex - 1);

                    LocalDate newRentingDateObj;
                    while (true) {
                        System.out.print("Wprowadz date przedluzenia najmu w formacie YYYY-MM-DD: ");
                        String newRentingDate = scanner.next();
                        scanner.nextLine();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        newRentingDateObj = LocalDate.parse(newRentingDate, formatter);

                        LocalDate currentSimulatedDate = TimeSimulator.getCurrentTime();

                        long daysDifference = ChronoUnit.DAYS.between(currentSimulatedDate, newRentingDateObj);

                        if (daysDifference >= 30) {
                            chosenParkingSpace.renewRenting(newRentingDateObj);
                            System.out.println("Przedluzyles najem miejsca parkingowego do: " + chosenParkingSpace.getRentingEndDate());
                            PersonMenu.displayMenu(person);
                        } else {
                            System.out.println("Wprowadzona data musi być co najmniej 30 dni późniejsza niż aktualna data.");
                        }
                    }
                } else System.out.println("Nieprawidlowy wybor, wybierz jeszcze raz.");
            }
        } else {
            System.out.println("Nie masz wynajetych zadnych miejsc parkingowych");
            PersonMenu.displayMenu(person);
        }



    }
}
