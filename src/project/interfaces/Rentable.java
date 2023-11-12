package project.interfaces;

import project.Person;
import project.exeptions.AlreadyRentedException;
import project.exeptions.DontHaveRentedFlatException;
import project.exeptions.ProblematicTenantException;
import project.exeptions.TooManyRentedPropertiesException;

import java.time.LocalDate;

public interface Rentable {

    void rent(Person tenant, LocalDate endRentingDate) throws DontHaveRentedFlatException, AlreadyRentedException, TooManyRentedPropertiesException, ProblematicTenantException;
    void renewRenting(LocalDate newEndingDate);

    Person getTenant();

    LocalDate getRentingStartDate();
    LocalDate getRentingEndDate();


}
