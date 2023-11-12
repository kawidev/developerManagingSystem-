package project.menus;

import project.Person;
import project.exeptions.*;
import project.properties.Flat;
import project.exeptions.TooManyThingsException;
import project.exeptions.TooManyPropertiesException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ManageFlatMenu {

    public static void displayMenu(Person person)
            throws TooManyRentedPropertiesException, ProblematicTenantException, AlreadyExistsException, AlreadyRentedException,
            TooManyThingsException, IOException, DontHaveRentedFlatException, TooManyPropertiesException {

        List<Flat> rentedFlats = person.getRentedFlats();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Wybierz mieszkanie ktorym chcesz zarzadzac");

        while(true) {
            int i = 1;
            for(Flat flat : rentedFlats) {
                System.out.println(i++ + ". " + flat.getPropertyData());
            }
            int chosenFlatIndex = scanner.nextInt(); scanner.nextLine();

            if(chosenFlatIndex > 0 && chosenFlatIndex <= rentedFlats.size()) {
                Flat chosenFlat = rentedFlats.get(chosenFlatIndex - 1);
                // pokaz odpowiednie menu
                ManagePeopleInFlatMenu.displayMenu(chosenFlat);
                break;
            } else System.out.println("Niepoprawny wybor");

        }

    }
}
