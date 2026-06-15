package exception;

public class InvalidAccountNumberException extends DomainException{
    public InvalidAccountNumberException(String message) {
        super(message);
    }
}
