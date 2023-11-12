package project.threads;

import project.TenantLetter;
import project.properties.Property;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TimeSimulator extends Thread {


    private static final Object lock = new Object();
    private static LocalDate currentDate = LocalDate.now();

    public static LocalDate getCurrentTime() {
        synchronized (lock) {
            return currentDate;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lock) {
                currentDate = currentDate.plusDays(1);
                //System.out.println("Aktualna data: " + currentDate);
            }
        }
    }
    public static class RentalExpirationChecker extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }

                synchronized (TimeSimulator.lock) {
                    List<Property> allRentedProperties = Property.getAllProperties().stream()
                            .filter(property -> property.getTenant() != null).toList();

                    for (Property property : allRentedProperties) {
                        LocalDate rentingEndDate = property.getRentingEndDate();
                        LocalDate currentDate = TimeSimulator.getCurrentTime();

                        if (rentingEndDate.isBefore(currentDate) || rentingEndDate.isEqual(currentDate)) {
                            long daysDifference = ChronoUnit.DAYS.between(rentingEndDate, currentDate);

                            if (!property.isReleaseNotificationSent()) {
                                TenantLetter newTenantLetter = new TenantLetter(property);
                                newTenantLetter.send();
                                property.setReleaseNotificationSent(newTenantLetter.isSent());
                            }

                            if (property.getTenant() != null && daysDifference >= 30) {
                                property.releaseProperty();
                                System.out.println("Nieruchomosc " + property.getPropertyData() + " zostala zwolniona na mocy postanowienia komorniczego");
                            }
                        }
                    }
                }
            }
        }
    }

}
