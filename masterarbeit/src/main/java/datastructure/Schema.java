package datastructure;

import java.util.*;

public class Schema {

    private Set<String> schema = new HashSet<>();
    private Set<String> thirdPartyIDs = new HashSet<>();

    public Schema(Set<String> schema, Set<String> thirdPartyIDs) throws Exception {
        if (schema.containsAll(thirdPartyIDs)) {
            this.schema = schema;
            this.thirdPartyIDs = thirdPartyIDs;
        } else {
            throw new Exception("thirdPartyIDs " + thirdPartyIDs + " are not in schema " + schema);
        }
    }

    public boolean checkProfile(Profile p) {
        return (this.schema.containsAll(p.profileData.keySet()));
    }

    public Set<String> getSchema() {
        return Collections.unmodifiableSet(this.schema);
    }

    public Set<String> getThirdPartyIDs() {
        return Collections.unmodifiableSet(this.thirdPartyIDs);
    }

    public boolean update(Set<String> schema, Set<String> thirdPartyIDs) {
        if (schema.containsAll(thirdPartyIDs)) {
            this.schema = schema;
            this.thirdPartyIDs = thirdPartyIDs;
            return true;
        } else {
            return false;
        }
    }

}
