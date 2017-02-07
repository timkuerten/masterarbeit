package datastructure;

import javafx.util.Pair;

import java.util.*;

public class Ds2 implements Datastructure {

    Schema schema;
    Map<UUID, Profile> profiles = new HashMap<>();
    Map<String, Map<String, Set<UUID>>> thirdPartiIDs = new HashMap<>();

    public void init(Set<String> schema, Set<String> thirdPartyIDs) throws Exception {
        this.schema = new Schema(schema, thirdPartyIDs);
    }

    public Profile get(UUID uuid) {
        return profiles.get(uuid);
    }

    public Set<Profile> get(String ThirdPartyID, String value) {
        if (this.schema.getThirdPartyIDs().contains(ThirdPartyID) == false) {
            return null;
        } else if (thirdPartiIDs.get(ThirdPartyID).get(value) != null) {
            Set<Profile> lProfiles = new HashSet<>();
            this.thirdPartiIDs.get(ThirdPartyID).get(value).forEach(uuid -> {
                lProfiles.add(profiles.get(uuid));
            });
            return lProfiles;
        } else {
            return new HashSet<>();
        }
    }

    public UUID insert(Profile p) {
        if (p.correspondToSchema(schema.getSchema())) {
            this.profiles.put(p.uuid, p);
            addProfileToThirtPartyIDs(p);
            return p.uuid;
        } else {
            return null;
        }
    }

    public UUID insert(Map<String, String> profileData){
        Profile p = new Profile(UUID.randomUUID());
        p.profileData = profileData;
        return insert(p);
    }

    public boolean update(UUID uuid, HashMap<String, String> profileData) {
        if (profiles.get(uuid) != null && this.schema.getSchema().containsAll(profileData.keySet())) {
            profiles.get(uuid).profileData.putAll(profileData);
            addProfileToThirtPartyIDs(profiles.get(uuid));
            return true;
        } else {
            return false;
        }
    }

    public Schema getSchema() {
        return this.schema;
    }

    private void setSchema(Schema schema) {
        this.schema = schema;
        this.schema.getThirdPartyIDs().forEach(t -> {
            Map<String, Set<UUID>> m = new HashMap<String, Set<UUID>>();
            thirdPartiIDs.clear();
            thirdPartiIDs.put(t, m);
        });
    }

    public boolean changeSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        if (this.schema.update(schema, thirdPartyIDs)) {
            profiles.values().forEach(profile ->
                    profile.update(this.schema)
            );
            setSchema(this.schema);
            profiles.forEach((u, p) -> {
                addProfileToThirtPartyIDs(p);
            });
            return true;
        } else {
            return false;
        }
    }

    public void addProfileToThirtPartyIDs(Profile p) {
        p.profileData.forEach((k, v) -> {
            Map<String, Set<UUID>> kList = this.thirdPartiIDs.get(k);
            if (kList == null) {
                Set<UUID> s = new HashSet<>();
                Map<String, Set<UUID>> m = new HashMap<String, Set<UUID>>();
                s.add(p.uuid);
                m.put(v, s);
                this.thirdPartiIDs.put(k, m);
            } else {
                Set<UUID> sList = kList.get(v);
                if (sList == null) {
                    Set<UUID> s = new HashSet<>();
                    s.add(p.uuid);
                    kList.put(v, s);
                } else {
                    kList.get(v).add(p.uuid);
                }
            }
        });
    }

}
