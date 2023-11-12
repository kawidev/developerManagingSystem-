package project.menus;

import project.DataSaver;
import project.Developer;
import project.Person;
import project.exeptions.*;
import project.exeptions.TooManyThingsException;
import project.exeptions.TooManyPropertiesException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MainAppMenu {


    public static void displayMenu()
            throws AlreadyExistsException, TooManyRentedPropertiesException, ProblematicTenantException, AlreadyRentedException, TooManyThingsException, IOException, DontHaveRentedFlatException, TooManyPropertiesException {

        System.out.println("Witaj w aplikacji do zarzadzania osiedlem");
        System.out.println("------------------------------------------");
        System.out.println("Jestes deweloperem czy osoba ktora chce wynajac lub wynajmuje mieszkanie?");
        System.out.println();
        System.out.println("Deweloper - 1");
        System.out.println("Dolacz jako nowy Deweloper - 2");
        System.out.println("Istniejaca Osoba - 3");
        System.out.println("Dolacz do aplikacji jako nowy uzytkownik - 4");
        System.out.println("Zapisz informacje o stanie programu w postaci czytelnego formatu JSON - 5");

        Scanner scanner = new Scanner(System.in);

        int choice = scanner.nextInt();

        switch(choice) {
            case 1: {
                List<Developer> developerList = Developer.getAllDevelopers();
                if(developerList.isEmpty()) {
                    System.out.println("Dolacz do aplikacji jako developer.\n" +
                            "Wprowadz swoja nazwe: ");
                    String developerName = scanner.next(); scanner.nextLine();
                    Developer newDeveloper = new Developer(developerName);
                    DeveloperMenu.displayMenu(newDeveloper);
                } else {

                    while(true) {
                        System.out.println("Wybierz siebie z listy developerów: ");

                        for(Developer developer : developerList) {
                            int i = 1;
                            System.out.println(i++ + ". " + developer.getDeveloperName());
                        }
                        int chosenDeveloperIndex = scanner.nextInt(); scanner.nextLine();
                        if(chosenDeveloperIndex > 0 && chosenDeveloperIndex <= developerList.size()) {
                            Developer chosenDeveloper = developerList.get(chosenDeveloperIndex - 1);
                            DeveloperMenu.displayMenu(chosenDeveloper);
                            break;
                        }
                    }
                }
            }
            case 2: {
                List<Developer> developerList = Developer.getAllDevelopers();


                while(true) {
                    System.out.print("Dolacz do aplikacji jako developer.\n" +
                            "Wprowadz swoja nazwe: ");
                    String developerName = scanner.next(); scanner.nextLine();

                    // sprawdzenie czy nazwa nie jest zajeta

                    boolean isNameTaken = developerList.stream()
                            .anyMatch(developer -> developer.getDeveloperName().equals(developerName));

                    if (!isNameTaken) {
                        Developer newDeveloper = new Developer(developerName);
                        DeveloperMenu.displayMenu(newDeveloper);
                        break;
                    } else System.out.println("Nazwa jest juz zajeta");
                }

            }
            case 3: {
                List<Person> peopleList = Person.getPeople();

                if(peopleList.isEmpty()) {
                    System.out.println("Dolacz do aplikacji jako nowy uzytkownik");
                    System.out.println();

                    System.out.print("Wprowadz swoje imie: ");
                    String firstName = scanner.next(); scanner.nextLine();
                    System.out.print("Wprowadz swoje nazwisko: ");
                    String lastName = scanner.next(); scanner.nextLine();
                    System.out.print("Wprowadz swoj PESEL: ");
                    String pesel = scanner.next(); scanner.nextLine();
                    System.out.print("Wprowadz swoja date urodzenia w formacie YYYY-MM-DD: ");
                    String birthDateStr = scanner.next(); scanner.nextLine();

                    Person newPerson = new Person(firstName, lastName, pesel, birthDateStr);

                    PersonMenu.displayMenu(newPerson);
                    break;
                } else {
                    while(true) {
                        System.out.println("Wybierz siebie z listy");
                        System.out.println();

                        int i = 1;
                        for(Person person : peopleList) {
                            System.out.println(i++ + ". " + person.getFirstName() + " " + person.getLastName());
                        }
                        int chosenPersonIndex = scanner.nextInt(); scanner.nextLine();

                        if(chosenPersonIndex > 0 && chosenPersonIndex <= peopleList.size()) {
                            Person chosenPerson = peopleList.get(chosenPersonIndex - 1);
                            PersonMenu.displayMenu(chosenPerson);
                            break;
                        } else System.out.println("Nieprawidlowy numer osoby z listy.");

                    }
                }

            }
            case 4: {
                System.out.println("Dolacz do aplikacji jako nowy uzytkownik");
                System.out.println();

                System.out.print("Wprowadz swoje imie: ");
                String firstName = scanner.next(); scanner.nextLine();
                System.out.print("Wprowadz swoje nazwisko: ");
                String lastName = scanner.next(); scanner.nextLine();
                System.out.print("Wprowadz swoj PESEL: ");
                String pesel = scanner.next(); scanner.nextLine();
                System.out.print("Wprowadz swoja date urodzenia w formacie YYYY-MM-DD: ");
                String birthDateStr = scanner.next(); scanner.nextLine();

                Person newPerson = new Person(firstName, lastName, pesel, birthDateStr);

                PersonMenu.displayMenu(newPerson);
                break;
            }
            case 5: {
                DataSaver.saveInformations();
                MainAppMenu.displayMenu();
                break;
            }
            default:
                System.out.println("Wybierz poprawna opcje");
        }
    }
}
