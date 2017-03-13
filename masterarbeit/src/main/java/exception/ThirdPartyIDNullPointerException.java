package exception;

public class ThirdPartyIDNullPointerException extends NullPointerException {

    public ThirdPartyIDNullPointerException() {
        super("Third-party-ID cannot be null");
    }

}
