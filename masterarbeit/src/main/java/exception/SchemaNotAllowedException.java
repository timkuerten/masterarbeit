package exception;

import java.util.Set;

public class SchemaNotAllowedException extends RuntimeException {

    public SchemaNotAllowedException() {
        super();
    }

    public SchemaNotAllowedException(Set<String> schema, Set<String> thirdPartyIDs) {
        super("thirdPartyIDs " + thirdPartyIDs + " are not in schema " + schema);
    }

}
