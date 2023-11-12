package project.menus;

import project.Person;
import project.exeptions.*;
import project.properties.Flat;
import project.threads.TimeSimulator;
import project.exeptions.TooManyPropertiesException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class RenewRentingFlatMenu {

    public static void displayMenu(Person person)
            throws TooManyRentedPropertiesException, ProblematicTenantException, DontHaveRentedFlatException,
            TooManyThingsException, AlreadyExistsException, AlreadyRentedException, IOException, TooManyPropertiesException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Wybierz mieszkanie w ktorym chcesz odnowic lub przedluzyc najem:\n");

        List<Flat> rentedFlats = person.getRentedFlats();

        while(true) {
            int i = 1;
            for(Flat flat : rentedFlats) {
                System.out.println(i++ + ". " + flat.getPropertyData());
            }
            int chosenFlatIndex = scanner.nextInt(); scanner.nextLine();

            if(chosenFlatIndex > 0 && chosenFlatIndex <= rentedFlats.size()) {
                Flat chosenFlatToRenewRenting = rentedFlats.get(chosenFlatIndex - 1);

                LocalDate newRentingDateObj;
                while(true) {
                    System.out.print("Wprowadz date przedluzenia najmu w formacie YYYY-MM-DD: ");
                    String newRentingDate = scanner.next(); scanner.nextLine();
                    newRentingDateObj = LocalDate.parse(newRentingDate);

                    LocalDate currentSimulatedDate = TimeSimulator.getCurrentTime();

                    long daysDifference = ChronoUnit.DAYS.between(currentSimulatedDate, newRentingDateObj);

                    if (daysDifference >= 30) {
                        chosenFlatToRenewRenting.renewRenting(newRentingDateObj);
                        PersonMenu.displayMenu(person);
                        break;
                    } else {
                        System.out.println("Wprowadzona data musi być co najmniej 30 dni późniejsza niż aktualna data.");
                    }
                }
                break;
            } else System.out.println("Niepoprawny wybor");
        }
    }
}
