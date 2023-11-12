package project.menus;

import project.Person;
import project.exeptions.*;
import project.threads.TimeSimulator;
import project.exeptions.TooManyThingsException;
import project.exeptions.TooManyPropertiesException;

import java.io.IOException;
import java.util.Scanner;

public class PersonMenu {

    public static void displayMenu(Person person) throws ProblematicTenantException, AlreadyRentedException,
            AlreadyExistsException, TooManyRentedPropertiesException, TooManyThingsException, IOException, DontHaveRentedFlatException, TooManyPropertiesException {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            int chosenOption;
            if (person.getRentedFlats().isEmpty()) {
                System.out.println("1. Wynajmij mieszkanie");
                System.out.println("2. Wroc do glownego menu aplikacji");
                chosenOption = scanner.nextInt();
                scanner.nextLine();

                if (chosenOption == 1) {
                    RentFlatMenu.displayMenu(person);
                    break;
                } else if(chosenOption == 2) {
                    MainAppMenu.displayMenu();
                } else System.out.println("Niepoprawny wybor. Wybierz jeszcze raz.");
            } else {
                System.out.println("MENU UZYTKOWNIKA:" + "(" + TimeSimulator.getCurrentTime() + ")");
                System.out.println("----------------------------");
                System.out.println("1. Wynajmij kolejne mieszkanie");
                System.out.println("2. Odnow najem mieszkania");
                System.out.println("3. Zarzadzaj wynajetym mieszkaniem");
                System.out.println("4. Wynajmij miejsce garażowe");
                if(!person.getRentedParkingSpaces().isEmpty()) {
                    System.out.println("5. Odnow lub przedluz najem miejsca garazowego");
                    System.out.println("6. Zarządzaj miejscem garażowym");
                }
                System.out.println("7. Sprawdz swoje Tenant letters");
                System.out.println("8. Wroc do glownego menu aplikacji");


                System.out.print("Wybierz opcję: ");

                chosenOption = scanner.nextInt();
                scanner.nextLine();

                switch (chosenOption) {
                    case 1:
                        RentFlatMenu.displayMenu(person);
                        break;
                    case 2:
                        RenewRentingFlatMenu.displayMenu(person);
                        break;
                    case 3:
                        ManageFlatMenu.displayMenu(person);
                        break;
                    case 4:
                        RentParkingSpaceMenu.displayMenu(person);
                        break;
                    case 5:
                        RenewRentingParkingSpaceMenu.displayMenu(person);
                        break;
                    case 6:
                        ManageParkingSpaceMenu.displayMenu(person);
                        break;
                    case 7:
                        person.printTenantLetters();
                        PersonMenu.displayMenu(person );break;
                    case 8:
                        MainAppMenu.displayMenu(); break;
                    default:
                        System.out.println("Niepoprawny wybor opcji");
                }
            }
        }
    }
}

