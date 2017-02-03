package datastructure;

import java.util.*;

public class Ds1 implements Datastructure {

    Schema schema;
    Map<UUID, Profile> profiles = new HashMap<>();

    public void init(Set<String> schema, Set<String> thirdPartyIDs) throws Exception {
        this.schema = new Schema(schema, thirdPartyIDs);
    }

    public Profile get(UUID uuid) {
        return profiles.get(uuid);
    }

    public Set<Profile> get(String ThirdPartyID, String value) {
        if (this.schema.getThirdPartyIDs().contains(ThirdPartyID) == false) {
            return null;
        }
        Set<Profile> lProfiles = new HashSet<>();
        this.profiles.forEach((k, v) -> {
            if (v.profileData.get(ThirdPartyID) == value) {
                lProfiles.add(v);
            }
        });
        return lProfiles;
    }

    public UUID insert(Profile p) {
        if (schema.checkProfile(p)) {
            profiles.put(p.uuid, p);
            return p.uuid;
        } else {
            return null;
        }
    }

    public void update(Profile p) {

    }

    public Schema getSchema() {
        return this.schema;
    }

    private void setSchema(Schema schema) {
        this.schema = schema;
    }

    public boolean changeSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        if (this.schema.update(schema, thirdPartyIDs)) {
            profiles.values().forEach(profile ->
                    profile.update(this.schema)

            );
            return true;
        } else {
            return false;
        }
    }

}
