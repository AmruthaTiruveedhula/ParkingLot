package exceptions;

public class NoVacantSpotsException extends RuntimeException {
    public NoVacantSpotsException(String message) {
        super(message);
    }
}