package exception;

public class ProfileDataNullPointerException extends NullPointerException {

    public ProfileDataNullPointerException() {
        super("Profile key cannot be null");
    }

}
