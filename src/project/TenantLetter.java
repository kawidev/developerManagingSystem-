package project;

import project.interfaces.Messageable;
import project.properties.Property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TenantLetter implements Messageable {

    private static List<TenantLetter> allTenantLetters = new ArrayList<>();

    Property rentedProperty;

    boolean isSent;
    public TenantLetter(Property rentedProperty) {
        this.rentedProperty = rentedProperty;
        this.isSent = false;
        System.out.println("Tenant letter zostal wyslany do najemcy: " + rentedProperty.getTenant().getFirstName()
        + " " + rentedProperty.getTenant().getLastName() + " za nieoplacenie pomieszczenia: \n" + rentedProperty.getPropertyData());
        if(!allTenantLetters.contains(this))
            allTenantLetters.add(this);
    }

    public static List<TenantLetter> getAllTenantLetters() {
        if(allTenantLetters.isEmpty())
            return Collections.emptyList();
        return allTenantLetters;
    }

    public static void removeTenantLetterFromList(TenantLetter tenantLetter) {
        allTenantLetters.remove(tenantLetter);
    }

    @Override
    public void send() {
        if(rentedProperty.getTenant() != null) {
            Person tenant = rentedProperty.getTenant();
            tenant.addTenantLetter(this);
            this.isSent = true;
            System.out.println("Wyslalo Tenant letter do: " +tenant.getFirstName() + " "+ tenant.getLastName());
        }

    }
    public boolean isSent() {
        return isSent;
    }

    public Property getProperty() {
        return rentedProperty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenantLetter that = (TenantLetter) o;
        return Objects.equals(rentedProperty, that.rentedProperty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentedProperty);
    }
}
