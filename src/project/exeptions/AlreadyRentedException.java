package project.exeptions;

public class AlreadyRentedException extends Exception {
    public AlreadyRentedException(String message) {
        super(message);
    }
}
