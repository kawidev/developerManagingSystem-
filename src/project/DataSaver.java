package project;

import project.properties.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DataSaver {

    public static void saveInformations() {
        List<Person> allPeople = Person.getPeople();
        List<Property> allProperties = Property.getAllProperties();


        // Sortowanie
        allProperties.sort(Comparator.comparing(Property::getVolume).reversed());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("database.json"))) {
            writer.write("[\n");


            for (Iterator<Property> it = allProperties.iterator(); it.hasNext();) {
                Property property = it.next();
                if (property instanceof Flat) {
                    writeFlatJSON(writer, (Flat) property);
                } else if (property instanceof ParkingSpace) {
                    writeParkingSpaceJSON(writer, (ParkingSpace) property);
                }
                // Dodanie przecinka tylko jeśli to nie jest ostatni element
                if (it.hasNext()) {
                    writer.write(",\n");
                }
            }
            writer.write("\n]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFlatJSON(BufferedWriter writer, Flat flat) throws IOException {
        Person tenant = flat.getTenant();
        writer.write("  {\n");
        writer.write("    \"property_type\": \"Mieszkanie\",\n");
        writer.write("    \"property_data\": \"" + flat.getPropertyData() + "\",\n");
        writer.write("    \"size\": \"" + flat.getVolume() + "\",\n");

        String tenantInfo = (tenant != null) ? tenant.getFirstName() + " " + tenant.getLastName() : "Unrented";
        writer.write("    \"tenant\": \"" + tenantInfo + "\",\n");

        writer.write("    \"locators\": [\n");
        List<Person> locators = flat.getPeople();
        if (locators != null && !locators.isEmpty()) {
            for (Person locator : locators) {
                writer.write("      {\n");
                writer.write("        \"name\": \"" + locator.getFirstName() + " " + locator.getLastName() + "\",\n");
                writer.write("        \"PESEL\": \"" + locator.getPesel() + "\",\n");
                writer.write("        \"birthDate\": \"" + locator.getBirthDate() + "\"\n");
                writer.write("      },\n");
            }
            writer.write("    ]\n");
        } else {
            writer.write("      \"Brak lokatorów\"\n");
            writer.write("    ]\n");
        }
        writer.write("  }\n");
    }

    private static void writeParkingSpaceJSON(BufferedWriter writer, ParkingSpace parkingSpace) throws IOException {
        Person tenant = parkingSpace.getTenant();
        writer.write("  {\n");
        writer.write("    \"property_type\": \"Miejsce Garażowe\",\n");
        writer.write("    \"property_data\": \"" + parkingSpace.getPropertyData() + "\",\n");
        writer.write("    \"size\": \"" + parkingSpace.getVolume() + "\",\n");

        String tenantInfo = (tenant != null) ? tenant.getFirstName() + " " + tenant.getLastName() : "Brak wynajmu";
        writer.write("    \"tenant\": \"" + tenantInfo + "\",\n");
        writer.write("    \"things\": [\n");

        List<Thingable> things = parkingSpace.getStorage().getItems();
        if (things != null && !things.isEmpty()) {
            for (Iterator<Thingable> it = things.iterator(); it.hasNext(); ) {
                Thingable thing = it.next();
                writer.write("      {\n");
                writer.write("        \"name\": \"" + thing.getName() + "\",\n");
                writer.write("        \"size\": \"" + thing.getVolume() + "\"\n");
                writer.write(it.hasNext() ? "      },\n" : "      }\n");
            }
            writer.write("    ]\n");
        } else {
            writer.write("      \"Brak przedmiotów\"\n");
            writer.write("    ]\n");
        }
        writer.write("  }\n");
    }
}
