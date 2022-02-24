package board.api.exception;

public class InvalidSignUpRequestException extends IllegalArgumentException {

    public InvalidSignUpRequestException(String message) {
        super(message);
    }
}
