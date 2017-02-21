package exception;

import java.util.Set;

public class SchemaException extends RuntimeException {

    public SchemaException() {
        super();
    }

    public SchemaException(Set<String> schema, Set<String> thirdPartyIDs) {
        super("thirdPartyIDs " + thirdPartyIDs + " are not in schema " + schema);
    }

}