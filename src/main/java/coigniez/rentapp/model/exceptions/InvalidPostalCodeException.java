package coigniez.rentapp.model.exceptions;

public class InvalidPostalCodeException extends Exception {
    public InvalidPostalCodeException(String message) {
        super(message);
    }
}
