package exception;

import java.util.Set;

public class schemaException extends RuntimeException {

    public schemaException() {
        super();
    }
    public schemaException(Set<String> schema, Set<String> thirdPartyIDs) {
        super("thirdPartyIDs " + thirdPartyIDs + " are not in schema " + schema);
    }

}
