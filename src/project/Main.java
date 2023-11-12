package project;

import project.exeptions.*;
import project.items.Helmet;
import project.items.Vacuum;
import project.menus.MainAppMenu;
import project.properties.*;
import project.threads.TimeSimulator;
import project.exeptions.TooManyThingsException;
import project.exeptions.TooManyPropertiesException;
import project.vehicles.*;

import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args)
            throws TooManyRentedPropertiesException, ProblematicTenantException, AlreadyRentedException,
            AlreadyExistsException, TooManyThingsException, IOException, DontHaveRentedFlatException, TooManyPropertiesException {

        TimeSimulator timeSimulator = new TimeSimulator();
        timeSimulator.start();

        TimeSimulator.RentalExpirationChecker expirationChecker = new TimeSimulator.RentalExpirationChecker();
        expirationChecker.start();


        Developer developer = new Developer("Developer Gdanski");
        Estate oliwaEstate = developer.addEstate("Oliwa");
        Estate wrzeszczEstate = developer.addEstate("Wrzeszcz");
        Estate glownyEstate = developer.addEstate("Śródmieście");

        Blok blokLiczmanskiego = oliwaEstate.addBlock("Liczmanskiego", "25D", 5);
        Blok blokMatejki = wrzeszczEstate.addBlock("Matejki", "2", 21);
        Blok blokKartuska = wrzeszczEstate.addBlock("Kartuska", "31A", 9);

        Flat flat1 = blokLiczmanskiego.createNewProperty(25, 65.0, Flat.class);
        Flat flat2 = blokMatejki.createNewProperty(13, 25.0, Flat.class);
        Flat flat3 = blokKartuska.createNewProperty(1, 31.0, Flat.class);
        Flat flat4 = blokKartuska.createNewProperty(2, 55.0, Flat.class);
        Flat flat5 = blokLiczmanskiego.createNewProperty(5, 65.0, Flat.class);
        Flat flat6 = blokMatejki.createNewProperty(21, 25.0, Flat.class);

        ParkingSpace parkingSpace1 = blokLiczmanskiego.createNewProperty(12, 55.0, ParkingSpace.class);
        ParkingSpace parkingSpace2 = blokMatejki.createNewProperty(14, 55.0, ParkingSpace.class);
        ParkingSpace parkingSpace3= blokMatejki.createNewProperty(13, 55.0, ParkingSpace.class);
        ParkingSpace parkingSpace4 = blokKartuska.createNewProperty(15, 55.0, ParkingSpace.class);
        ParkingSpace parkingSpace5 = blokKartuska.createNewProperty(17, 55.0, ParkingSpace.class);
        ParkingSpace parkingSpace6 = blokKartuska.createNewProperty(18, 55.0, ParkingSpace.class);




        Person person0 = new Person("Miłosz", "Król", "99122322853", "1999-12-23");
        Person person1 = new Person("Leszek", "Dudek", "26112606659", "1926-11-26");
        Person person2 = new Person("Bohdan", "Sawicki", "99022491772", "1999-02-24");
        Person person3 = new Person("Maciej", "Nowogradzki", "3432423423", "1996-03-22");
        Person person4 = new Person("Dawid", "Gajewski", "3432423423", "1996-03-22");
        Person person5 = new Person("Henryka", "Pawlak", "03310295946", "2003-11-02");
        Person person6 = new Person("Bogumił", "Sobolewski", "99052885897", "1999-05-28");
        Person person7 = new Person("Marta", "Nowakowska", "99032059306", "1999-03-20");
        Person person8 = new Person("Arleta", "Woźniak", "02282402761", "2002-08-24");
        Person person9 = new Person("Nela", "Musiał", "99121033581", "1999-12-10");
        Person person10 = new Person("Wioletta", "Urbaniak", "03282664762", "2003-08-26");
        Person person11 = new Person("Adolf", "Suchostrzycki", "00310936459", "2000-11-09");
        Person person12 = new Person("Klara", "Domagała", "03322478649", "2003-12-24");
        Person person13 = new Person("Przemysław", "Orzechowski", "00322510755", "2000-12-25");
        Person person14 = new Person("Gabriela", "Pawlik", "03311728225", "2003-11-17");

        flat1.rent(person1, TimeSimulator.getCurrentTime().plusMonths(1));
        flat2.rent(person2, TimeSimulator.getCurrentTime().plusMonths(2));
        flat3.rent(person3, TimeSimulator.getCurrentTime().plusMonths(3));
        flat4.rent(person4, TimeSimulator.getCurrentTime().plusMonths(4));
        flat5.rent(person5, TimeSimulator.getCurrentTime().plusMonths(5));
        flat6.rent(person6, TimeSimulator.getCurrentTime().plusMonths(6));

        // miejsca garazowe mozna wynajac za posrednictwem rozgrywki :D
        parkingSpace3.rent(person1, LocalDate.parse("2025-05-13"));

        // kilka pojazdow i przypisanie im wlascicieli
        Motor motor = new Motor("Motorynka", 5.0, 1.0, "Motor", "benzyna");
        motor.setOwner(person1);
        AmphibiousVehicle av = new AmphibiousVehicle("Amfibia", 10.0, 5.0, "Amfibia", "paliwo do łodzi");
        av.setOwner(person2);
        CityCar cc = new CityCar("FORD FIESTA", 8.0, 1.0, "Samochod Miejski", "hybryda");
        cc.setOwner(person3);
        Boat boat = new Boat("Lódź marzeń", 30.0, 7.0, "lodz morska", "paliwo rakietowe");
        boat.setOwner(person4);
        OffRoadCar orc = new OffRoadCar("Auto terenowe", 15.0, 5.0, "Monster truck", "DIESEL");
        orc.setOwner(person5);

        // kilka przedmiotow
        Helmet helmet1 = new Helmet("helm do jazdy motorem", 1.0, person1);
        Helmet helmet2 = new Helmet("helm wojskowy", 1.0, person2);
        Helmet helmet3 = new Helmet("helm do jazdy motorem", 1.0, person3);
        Helmet helmet4 = new Helmet("helm do jazdy motorem", 1.0, person4);
        Helmet helmet5 = new Helmet("helm do jazdy motorem", 1.0, person5);
        Vacuum vc1 = new Vacuum("Odkurzacz", 1.0, person1);
        Vacuum vc2 = new Vacuum("Odkurzacz", 1.0, person2);
        Vacuum vc3 = new Vacuum("Odkurzacz", 1.0, person3);
        Vacuum vc4 = new Vacuum("Odkurzacz", 1.0, person4);
        Vacuum vc5 = new Vacuum("Odkurzacz", 1.0, person5);
        Vacuum vc6 = new Vacuum("Odkurzacz", 1.0, person6);



        MainAppMenu.displayMenu();


    }

}

