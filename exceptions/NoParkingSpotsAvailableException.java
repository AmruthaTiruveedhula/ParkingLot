package exceptions;

public class NoParkingSpotsAvailableException extends RuntimeException {
    public NoParkingSpotsAvailableException(String message) {
        super(message);
    }
}
