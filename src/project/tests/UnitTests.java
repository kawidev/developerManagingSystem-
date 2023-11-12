package project.tests;

import org.junit.Before;
import org.junit.Test;
import project.Person;
import project.exeptions.*;
import project.items.Helmet;
import project.properties.*;
import project.exeptions.DontHaveRentedFlatException;
import project.exeptions.AlreadyExistsException;
import project.exeptions.TooManyThingsException;
import project.exeptions.TooManyPropertiesException;
import project.vehicles.Motor;


import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class UnitTests {

    Estate testEstate;
    Blok testBlok;

    ParkingSpace parkingSpace;


    Person personTenant;

    @Before
    public void setUp() throws AlreadyExistsException {
        testEstate = new Estate("Osiedle testowe");
        testBlok = new Blok(testEstate, "Ulica testowa", "21D", 25);
        parkingSpace = new ParkingSpace(testBlok, 22, 25.0);
        personTenant = new Person("Tenant", "Tenant", "12345678", "2010-05-13");
    }

    @Test
    public void shouldNotRentParkingSpaceWithoutRentedFlat() {
        // osoba ktora nie wynajmuje mieszkania nie powinna miec mozliwosci wynajecia miejsca garazowego

        Person personWithoutRentedFlat = new Person("Jan", "Łasica", "12345678910", "2004-03-02");

        assertThrows(DontHaveRentedFlatException.class, () ->
                parkingSpace.rent(personWithoutRentedFlat, LocalDate.now().plusMonths(6)));

    }
    @Test
    public void shouldNotAddItemIfFreeSpaceIsLowerThanItemVolume()
            throws ProblematicTenantException, TooManyRentedPropertiesException, AlreadyRentedException,
            DontHaveRentedFlatException, AlreadyExistsException {

        Flat flat;
        if(testBlok.isApartmentNumberAvailable(22)) {
            flat = new Flat(testBlok, 22, 80.0);
        } else {
            flat = testBlok.getFlatByNumber(22);
        }

        flat.rent(personTenant, LocalDate.parse("2025-05-13"));
        parkingSpace.rent(personTenant, LocalDate.parse("2025-05-13"));
        Motor motor = new Motor("YAMAHA", 25.0, 1.0, "Motor", "Benzyna");
        motor.setOwner(personTenant);

        assertThrows(TooManyThingsException.class, () ->
                parkingSpace.storeItem(motor));
    }


    @Test
    public void shouldNotCreatePropertyOfTheSameTypeWithTheSameNumber()
            throws AlreadyExistsException, TooManyPropertiesException {

        Flat flat = testBlok.createNewProperty(22, 67.0, Flat.class);
        ParkingSpace parkingSpace1 = testBlok.createNewProperty(22, 67.0, ParkingSpace.class);

            assertThrows(AlreadyExistsException.class, () ->
                    testBlok.createNewProperty(22, 67.0, Flat.class));
            assertThrows(AlreadyExistsException.class, () ->
                    testBlok.createNewProperty(22, 67.0, ParkingSpace.class));


    }

    @Test
    public void shouldThrowTooManyRentedExeceptionIfTryToRentOver5Property()
            throws AlreadyExistsException, ProblematicTenantException, AlreadyRentedException, DontHaveRentedFlatException,
            TooManyRentedPropertiesException, TooManyPropertiesException {

        Flat flat1 = testBlok.createNewProperty(1, 50.0, Flat.class);
        Flat flat2 = testBlok.createNewProperty(2, 50.0, Flat.class);
        Flat flat3 = testBlok.createNewProperty(3, 50.0, Flat.class);
        ParkingSpace parkingSpace1 = testBlok.createNewProperty(3, 50.0, ParkingSpace.class);
        ParkingSpace parkingSpace2 = testBlok.createNewProperty(4, 50.0, ParkingSpace.class);
        ParkingSpace parkingSpace3 = testBlok.createNewProperty(5, 50.0, ParkingSpace.class);
        Person bigTenant = new Person("Rentier", "Rentier", "23412423452", "1992-05-02");

        flat1.rent(bigTenant, LocalDate.parse("2025-05-13"));
        flat2.rent(bigTenant, LocalDate.parse("2025-05-13"));
        flat3.rent(bigTenant, LocalDate.parse("2025-05-13"));
        parkingSpace1.rent(bigTenant, LocalDate.parse("2025-05-13"));
        parkingSpace2.rent(bigTenant, LocalDate.parse("2025-05-13"));

        assertThrows(TooManyRentedPropertiesException.class, () ->
                parkingSpace3.rent(bigTenant, LocalDate.parse("2025-05-13")));
    }

    @Test
    public void allPropertiesIdsShouldBeUnique()
            throws AlreadyExistsException, TooManyPropertiesException {

        Flat flat1 = testBlok.createNewProperty(1, 50.0, Flat.class);
        Flat flat2 = testBlok.createNewProperty(2, 50.0, Flat.class);
        Flat flat3 = testBlok.createNewProperty(3, 50.0, Flat.class);
        ParkingSpace parkingSpace1 = testBlok.createNewProperty(3, 50.0, ParkingSpace.class);
        ParkingSpace parkingSpace2 = testBlok.createNewProperty(4, 50.0, ParkingSpace.class);
        ParkingSpace parkingSpace3 = testBlok.createNewProperty(5, 50.0, ParkingSpace.class);

        List<Property> propertyList = Property.getAllProperties();

        long uniqueIdCounter = propertyList.stream().map(Property::getId).distinct().count();
        assertEquals(propertyList.size(), uniqueIdCounter);
    }
    @Test
    public void tenantShouldManageFlat()
            throws TooManyRentedPropertiesException, AlreadyExistsException, ProblematicTenantException,
            DontHaveRentedFlatException, AlreadyRentedException, TooManyPropertiesException {

        Flat flat1 = testBlok.createNewProperty(1, 50.0, Flat.class);
        Person tenant = new Person("Rentier", "Rentier", "23412423452", "1992-05-02");
        Person locator = new Person("jan", "lasica", "23412423452", "1992-05-02");

        flat1.rent(tenant, LocalDate.parse("2025-05-13"));

        if(flat1.getTenant().equals(tenant)) {
            flat1.addPerson(locator);
            assertTrue(flat1.getPeople().contains(locator));

            flat1.removePerson(locator);
            assertFalse(flat1.getPeople().contains(locator));
        }
    }
    @Test
    public void shouldNotRentAnyPropertyWithBackDate()
            throws TooManyRentedPropertiesException, AlreadyExistsException, ProblematicTenantException,
            DontHaveRentedFlatException, AlreadyRentedException, TooManyPropertiesException {

        Person tenant = new Person("Rentier", "Rentier", "23412423452", "1992-05-02");

        Flat flat1 = testBlok.createNewProperty(1, 50.0, Flat.class);
        ParkingSpace parkingSpace1 = testBlok.createNewProperty(3, 50.0, ParkingSpace.class);

        LocalDate currentTime = LocalDate.now();
        LocalDate beforeDate = currentTime.minusDays(5);

        flat1.rent(tenant, beforeDate);
        assertNotEquals(tenant, flat1.getTenant());


        flat1.rent(tenant, currentTime.plusMonths(1));
        parkingSpace1.rent(tenant, beforeDate);
        assertNotEquals(tenant, parkingSpace1.getTenant());
    }
    @Test
    public void shouldRemoveAllItemsAndPeopleAfterRealisingProperty()
            throws AlreadyExistsException, TooManyPropertiesException, ProblematicTenantException,
            DontHaveRentedFlatException, TooManyRentedPropertiesException, AlreadyRentedException, TooManyThingsException {

        Person tenant = new Person("Rentier", "Rentier", "23412423452", "1992-05-02");

        Flat flat1 = testBlok.createNewProperty(1, 50.0, Flat.class);
        ParkingSpace parkingSpace1 = testBlok.createNewProperty(3, 50.0, ParkingSpace.class);
        Person person1 = new Person("Klara", "Domagała", "03322478649", "2003-12-24");
        Person person2 = new Person("Przemysław", "Orzechowski", "00322510755", "2000-12-25");
        Person person3 = new Person("Gabriela", "Pawlik", "03311728225", "2003-11-17");

        Helmet helmet = new Helmet("helm wojskowy", 2.0, tenant);

        flat1.rent(tenant, LocalDate.now().plusMonths(3));
        parkingSpace1.rent(tenant, LocalDate.now().plusMonths(3));

        flat1.addPerson(person1);
        flat1.addPerson(person2);
        flat1.addPerson(person3);

        parkingSpace1.storeItem(helmet);

        flat1.releaseProperty();
        assertTrue(flat1.getPeople().isEmpty());
        assertNull(flat1.getTenant());

        parkingSpace1.releaseProperty();
        assertTrue(parkingSpace1.getStorage().getItems().isEmpty());
        assertNull(parkingSpace1.getTenant());

    }
}
