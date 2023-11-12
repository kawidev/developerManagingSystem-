package project.menus;

import project.Person;
import project.exeptions.*;
import project.properties.Flat;
import project.properties.Property;
import project.threads.TimeSimulator;
import project.exeptions.TooManyThingsException;
import project.exeptions.TooManyPropertiesException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class RentFlatMenu {

    public static void displayMenu(Person person) throws ProblematicTenantException, AlreadyRentedException, AlreadyExistsException
            , TooManyRentedPropertiesException, TooManyThingsException, IOException, DontHaveRentedFlatException, TooManyPropertiesException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Oto lista dostepnych mieszkan do wynajecia:\n");


            List<Flat> availableToRentFlats = Property.getAllProperties().stream()
                    .filter(property -> property instanceof Flat)
                    .map(property -> (Flat) property)
                    .filter(flat -> flat.getTenant() == null)
                    .toList();



        if (!(availableToRentFlats.isEmpty())) {
            while (true) {
                int i = 1;
                for (Flat flat : availableToRentFlats) {
                    System.out.println(i++ + ". " + flat.getPropertyData());
                }
                int chosenFlatIndex = scanner.nextInt();
                scanner.nextLine();

                if (chosenFlatIndex > 0 && chosenFlatIndex <= availableToRentFlats.size()) {
                    Flat chosenFlat = availableToRentFlats.get(chosenFlatIndex - 1);

                    LocalDate endDateObj;
                    while(true) {
                        System.out.print("Wybierz date do kiedy chcesz wynajac to mieszkanie w formacie YYYY-MM-DD: ");
                        String endDate = scanner.nextLine();

                        endDateObj = LocalDate.parse(endDate);

                        LocalDate currentSimulatedDate = TimeSimulator.getCurrentTime();

                        // teraz sprawdzmy czy wprowadzona przez uzytkownika data jest conajmniej 30 dni pozniejsza
                        long daysDifference = ChronoUnit.DAYS.between(currentSimulatedDate, endDateObj);

                        if (daysDifference >= 30) {
                            chosenFlat.rent(person, endDateObj);
                            PersonMenu.displayMenu(person);
                        } else {
                            System.out.println("Wprowadzona data musi być co najmniej 30 dni późniejsza niż aktualna data.");
                        }
                    }
                } else{
                    System.out.println("Niepoprawny wybor");
                }

            }
        } else {
            System.out.println("Nie ma zadnych mieszkan do wynajecia");
            PersonMenu.displayMenu(person);
        }
    }
}
