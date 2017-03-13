package exception;

public class UuidNullPointerException extends NullPointerException {

    public UuidNullPointerException() {
        super("UUID cannot be null");
    }

}
