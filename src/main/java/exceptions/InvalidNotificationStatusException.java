package exceptions;

public class InvalidNotificationStatusException extends Throwable {

    public InvalidNotificationStatusException(String message) {
        super(message);
    }
}
