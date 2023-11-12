package project.exeptions;

public class DontHaveRentedFlatException extends Exception {
    public DontHaveRentedFlatException(String message) {
        super(message);
    }
}
