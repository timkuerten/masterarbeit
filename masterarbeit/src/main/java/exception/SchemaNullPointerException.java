package exception;

public class SchemaNullPointerException extends NullPointerException {

    public SchemaNullPointerException() {
        super("Schema and third-party-ID cannot be null");
    }

}
