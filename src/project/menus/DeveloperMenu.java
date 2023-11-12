package project.menus;

import project.Developer;
import project.exeptions.*;
import project.properties.*;
import project.exeptions.TooManyThingsException;
import project.exeptions.TooManyPropertiesException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class DeveloperMenu {

    public static void displayMenu(Developer developer) throws AlreadyExistsException, TooManyRentedPropertiesException, ProblematicTenantException, AlreadyRentedException, TooManyThingsException, IOException, DontHaveRentedFlatException, TooManyPropertiesException {
        Scanner scanner = new Scanner(System.in);

        int choice, apartmentNumber = 0, parkingSpaceNumber = 0;

        do {
            System.out.println("MENU DEWELOPERA:\n");
            System.out.println("1. Stworz osiedle");
            System.out.println("2. Zbuduj blok");
            System.out.println("3. Zbuduj Pomieszczenie");
            System.out.println("4. Lista nieruchomości");
            System.out.println("5. Wroc do glownego menu aplikacji");
            System.out.println("0. Wyjście");

            System.out.print("Wybierz opcję: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: {
                    System.out.println("Wprowadz nazwe osiedla, ktore chcesz stworzyc: ");
                    String name = scanner.nextLine();
                    developer.addEstate(name);
                    break;
                }

                case 2: {
                    List<Estate> estates = developer.getDeveloperEstates();

                    if (estates.isEmpty()) {
                        System.out.println("Brak dostępnych osiedli. Najpierw stwórz osiedle.");
                        break;
                    }

                    System.out.println("Wybierz osiedle na którym chcesz zbudować blok: ");
                    int i=1;
                    for(Estate estate : estates) {
                        System.out.println(i++ + ". " + estate.getEstateName());
                    }
                    System.out.print("Wybierz osiedle: ");
                    int estateChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (estateChoice < 1 || estateChoice > estates.size()) {
                        System.out.println("Nieprawidłowy wybór osiedla.");
                        break;
                    }
                    Estate selectedEstate = estates.get(estateChoice -1);

                    System.out.print("Wprowadz nazwe ulicy, na ktorej chcesz zbudowac blok: ");
                    String streetName = scanner.nextLine();
                    System.out.print("\nWprowadz numer bloku: ");
                    String blockNumber = scanner.nextLine();
                    System.out.print("\nWprowadz maksymalna ilosc mieszkan w bloku: ");
                    int maxPropertiesNumber = scanner.nextInt();
                    scanner.nextLine();

                    assert selectedEstate != null;
                    selectedEstate.addBlock(streetName, blockNumber, maxPropertiesNumber);
                    break;
                }
                case 3: {

                    List<Blok> blocks = developer.getDeveloperBlocks();

                    if(blocks.isEmpty()) {
                        System.out.println("Nie masz wybudowane zadnego bloku, wiec nie mozesz zbudowac pomieszczenia.");
                        break;
                    }

                    System.out.println("Wybierz blok w ktorym chcesz zbudowac nowe pomieszczenie: ");

                    int i=1;
                    for(Blok block : blocks) {
                        System.out.println(i + ". " + block.getAddress());
                        i++;
                    }
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    Blok selectedBlock = blocks.stream().skip(choice - 1).findFirst().orElse(null);

                    System.out.println("Wybierz rodzaj pomieszczenia ktore chcesz zbudowac:");
                    System.out.println("1. Mieszkanie");
                    System.out.println("2. Miejsce Garazowe");

                    int propertyTypeChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (propertyTypeChoice) {
                        case 1: {
                            boolean uniqueApartmentNumber = false;

                            while (!uniqueApartmentNumber) {
                                System.out.print("Wprowadz numer mieszkania: ");
                                apartmentNumber = scanner.nextInt();
                                scanner.nextLine();

                                List<Flat> selectedBlockAparments = selectedBlock.getApartments();
                                boolean apartmentNumberExists = false;

                                for (Flat apartment : selectedBlockAparments) {
                                    if (apartment.getPropertyNumber() == apartmentNumber) {
                                        apartmentNumberExists = true;
                                        System.out.println("Wybierz inny numer mieszkania, bo mieszkanie o tym numerze już istnieje w tym bloku");
                                        break;
                                    }
                                }

                                if (!apartmentNumberExists) {
                                    uniqueApartmentNumber = true;
                                }
                            }
                            break;
                        }
                        case 2: {
                            boolean uniqueParkingSpaceNumber = false;

                            while (!uniqueParkingSpaceNumber) {
                                System.out.print("Wprowadz numer miejsca parkingowego: ");
                                parkingSpaceNumber = scanner.nextInt();
                                scanner.nextLine();

                                assert selectedBlock != null;
                                List<ParkingSpace> selectedBlockParkingSpaces = selectedBlock.getParkingSpaces();
                                boolean parkingSpaceNumberExists = false;

                                for (ParkingSpace space : selectedBlockParkingSpaces) {
                                    if (space.getPropertyNumber() == parkingSpaceNumber) {
                                        parkingSpaceNumberExists = true;
                                        System.out.println("Wybierz inny numer, bo miejsce garazowe o tym numerze juz istnieje w tym bloku");
                                        break;
                                    }
                                }

                                if (!parkingSpaceNumberExists) {
                                    uniqueParkingSpaceNumber = true;
                                }
                            }
                            break;
                        }
                    }

                    System.out.println("Wprowadz powierzchnie pomieszczenia poprzez podanie: ");
                    System.out.println("1. Wymiarow");
                    System.out.println("2. Powierzchni");

                    choice = scanner.nextInt();
                    scanner.nextLine();

                    switch(choice) {
                        case 1: {
                            System.out.print("Wprowadz a (zmiennoprzecinkowe): ");
                            double a = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.print("\nWprowadz b (zmiennoprzecinkowe): ");
                            double b = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.print("\nWprowadz h (zmiennoprzecinkowe): ");
                            double h = scanner.nextDouble();
                            scanner.nextLine();


                            if(propertyTypeChoice == 1) {
                                selectedBlock.createNewProperty(apartmentNumber, a, b, h, Flat.class);
                            } else if(propertyTypeChoice == 2) {
                                selectedBlock.createNewProperty(parkingSpaceNumber, a, b, h, ParkingSpace.class);
                            }
                            break;
                        }

                        case 2: {
                            System.out.println("Wprowadz powierzchnie (zmiennoprzecinkowa): ");
                            double volume = scanner.nextDouble();
                            scanner.nextLine();

                            if(propertyTypeChoice == 1) {
                                selectedBlock.createNewProperty(apartmentNumber, volume, Flat.class);
                            } else if(propertyTypeChoice == 2) {
                                selectedBlock.createNewProperty(parkingSpaceNumber, volume, ParkingSpace.class);
                            }
                            break;
                        }
                    }

                    break;
                }
                case 4:
                    showOwnedProperties(developer);
                    break;
                case 5: MainAppMenu.displayMenu(); break;
                case 0:
                    System.out.println("Koniec programu.");
                    return;
                default:
                    System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        } while (choice != 0);
    }

    public static void showOwnedProperties(Developer developer) {
        List<Estate> estates = developer.getDeveloperEstates();

        if (estates.isEmpty()) {
            System.out.println("Brak dostępnych osiedli.");
            return;
        }

        for (Estate osiedle : estates) {
            System.out.println("OSIEDLE: " + osiedle.getEstateName());

            for (Blok blok : osiedle.getBlocks()) {
                System.out.println("BLOK: " + blok.getAddress());

                List<Flat> mieszkania = new ArrayList<>();
                List<ParkingSpace> miejscaGarazowe = new ArrayList<>();

                for (Property property : blok.getProperties()) {
                    if (property instanceof Flat) {
                        mieszkania.add((Flat) property);
                    } else if (property instanceof ParkingSpace) {
                        miejscaGarazowe.add((ParkingSpace) property);
                    }
                }

                System.out.print("Mieszkania: ");
                Iterator<Flat> iteratorMieszkania = mieszkania.iterator();
                if (!iteratorMieszkania.hasNext()) {
                    System.out.print("Brak mieszkań.");
                } else {
                    while (iteratorMieszkania.hasNext()) {
                        Flat mieszkanie = iteratorMieszkania.next();
                        System.out.print(mieszkanie.getPropertyNumber());
                        System.out.print(iteratorMieszkania.hasNext() ? ", " : "");
                    }
                }
                System.out.println();

                System.out.print("Miejsca garazowe: ");
                Iterator<ParkingSpace> iteratorMiejscaGarazowe = miejscaGarazowe.iterator();
                if (!iteratorMiejscaGarazowe.hasNext()) {
                    System.out.print("Brak miejsc garażowych.");
                } else {
                    while (iteratorMiejscaGarazowe.hasNext()) {
                        ParkingSpace miejsceGarazowe = iteratorMiejscaGarazowe.next();
                        System.out.print(miejsceGarazowe.getPropertyNumber());
                        System.out.print(iteratorMiejscaGarazowe.hasNext() ? ", " : "");
                    }
                }
                System.out.println("\n");
            }
        }
    }


}
