package exception;

public class InvalidBranchException extends DomainException{
    public InvalidBranchException(String message) {
        super(message);
    }
}
