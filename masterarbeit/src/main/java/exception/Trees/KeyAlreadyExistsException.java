package exception.Trees;

public class KeyAlreadyExistsException extends RuntimeException {

    public KeyAlreadyExistsException(String key) {
        super("key '" + key + "' already exists.");
    }

}
