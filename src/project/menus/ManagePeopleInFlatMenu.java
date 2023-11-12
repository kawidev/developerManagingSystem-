package project.menus;

import project.Person;
import project.exeptions.*;
import project.properties.Flat;
import project.exeptions.TooManyThingsException;
import project.exeptions.TooManyPropertiesException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ManagePeopleInFlatMenu {

    public static void displayMenu(Flat chosenFlat)
            throws TooManyRentedPropertiesException, ProblematicTenantException, AlreadyExistsException,
            AlreadyRentedException, TooManyThingsException, IOException, DontHaveRentedFlatException, TooManyPropertiesException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Wybierz jedna z opcji zarzadzania:  \n");

        System.out.println("1. Dodaj osobe jako lokatora mieszkania");
        System.out.println("2. Usun osobe z listy lokatorow");

        int choice = scanner.nextInt(); scanner.nextLine();

        switch (choice) {
            case 1: {
                List<Person> notAccomodatedPeople = Person.getPeople().stream()
                        .filter(person -> !person.isAccomodated())
                        .filter(person -> !person.equals(chosenFlat.getTenant())).toList();

                if(!notAccomodatedPeople.isEmpty()){
                    while (true) {
                        System.out.println("Wybierz osobe ktora chcesz zameldowac w wybranym mieszkaniu: \n");

                        int i = 1;
                        for (Person person : notAccomodatedPeople) {
                            System.out.println(i++ + ". " + person.getFirstName() + " " + person.getLastName());
                        }
                        int chosenPersonIndex = scanner.nextInt();
                        scanner.nextLine();

                        if (chosenPersonIndex > 0 && chosenPersonIndex <= notAccomodatedPeople.size()) {
                            Person selectedPerson = notAccomodatedPeople.get(chosenPersonIndex - 1);
                            chosenFlat.addPerson(selectedPerson);
                            PersonMenu.displayMenu(chosenFlat.getTenant());
                            break;
                        } else System.out.println("Niepoprawny wybor");
                    }
                } else {
                    System.out.println("Nie ma osob ktore aktualnie moglbys dodac do mieszkania");
                    PersonMenu.displayMenu(chosenFlat.getTenant());
                    break;
                }
            }
            case 2: {
                List<Person> peopleInThatFlatExceptTenant = chosenFlat.getPeople()
                        .stream()
                        .filter(person -> !person.equals(chosenFlat.getTenant()))
                        .toList();

                if(!peopleInThatFlatExceptTenant.isEmpty()){
                    while (true) {
                        System.out.println("Wybierz osobe ktora chcesz wyrzucic: \n");
                        int i = 1;
                        for (Person person : peopleInThatFlatExceptTenant) {
                            System.out.println(i++ + ". " + person.getFirstName() + " " + person.getLastName());
                        }
                        int chosenPersonIndex = scanner.nextInt();
                        scanner.nextLine();
                        if (chosenPersonIndex > 0 && chosenPersonIndex <= peopleInThatFlatExceptTenant.size()) {
                            Person selectedPerson = peopleInThatFlatExceptTenant.get(chosenPersonIndex - 1);

                            chosenFlat.removePerson(selectedPerson);
                            PersonMenu.displayMenu(chosenFlat.getTenant());
                            break;
                        } else System.out.println("Niepoprawny wybor");
                    }
                } else {
                    System.out.println("W mieszkaniu nie ma osob ktore moglbys wyrzucic");
                    PersonMenu.displayMenu(chosenFlat.getTenant());
                    break;
                }

            }
        }

    }
}
