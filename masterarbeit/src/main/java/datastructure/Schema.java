package datastructure;

import java.util.*;

public class Schema {

    private Set<String> schema = new HashSet<>();
    private Set<String> thirdPartyIDs = new HashSet<>();

    /**
     * constructor to create schema and third-party-IDs
     *
     * @param schema        schema of profiles
     * @param thirdPartyIDs third-party-IDs in schema
     * @throws Exception third-party-IDs must be contained in schema
     */
    public Schema(Set<String> schema, Set<String> thirdPartyIDs) throws Exception {
        if (update(schema, thirdPartyIDs) == false) {
            throw new Exception("thirdPartyIDs " + thirdPartyIDs + " are not in schema " + schema);
        }
    }

    /**
     * Returns a unmodifiableSet which contain current schema.
     *
     * @return current unmodifiable schema
     */
    public Set<String> getSchema() {
        return Collections.unmodifiableSet(this.schema);
    }

    /**
     * Returns a unmodifiableSet which contain current third-party-IDs.
     *
     * @return current unmodifiable third-party-IDs
     */
    public Set<String> getThirdPartyIDs() {
        return Collections.unmodifiableSet(this.thirdPartyIDs);
    }

    /**
     * Sets new schema and third-party-IDs. It checks if third-party-IDs contained in schema.
     *
     * @param schema        schema of profiles
     * @param thirdPartyIDs third-party-IDs in schema
     * @return if third-party-IDs are contained in schema
     */
    protected boolean update(Set<String> schema, Set<String> thirdPartyIDs) {
        if (schema.containsAll(thirdPartyIDs)) {
            this.schema.clear();
            this.thirdPartyIDs.clear();
            this.schema.addAll(schema);
            this.thirdPartyIDs.addAll(thirdPartyIDs);
            return true;
        } else {
            return false;
        }
    }

}
