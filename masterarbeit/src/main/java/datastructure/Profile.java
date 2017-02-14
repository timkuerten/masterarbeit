package datastructure;

import java.util.*;

public class Profile {

    final UUID uuid;
    Map<String, String> profileData;

    /**
     *
     * @return current profile data
     */
    public Map<String, String> getProfileData() {
        return Collections.unmodifiableMap(profileData);
    }

    /**
     * constructor to create profile with uuid
     *
     * @param uuid universually unique identifier
     */
    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.profileData = new HashMap<>();
    }

    /**
     * constructor to create profile with uuid and other data
     *
     * @param uuid universually unique identifier
     * @param profileData contain other data of profile
     */
    public Profile(UUID uuid, Map<String, String> profileData) {

        this.uuid = uuid;
        this.profileData = profileData;

    }

    /**
     *
     * @param schema
     */
    public void update(Schema schema) {
        this.profileData.entrySet()
                .stream()
                .filter(map -> schema.getSchema().contains(map.getKey()));
    }

    public boolean correspondToSchema(Set<String> schema) {
        return (schema.containsAll(profileData.keySet()));
    }

    /**
     * Overrides toString()
     *
     * @return this profile as String
     */
    @Override
    public String toString() {
        return "<Profile " + this.uuid + ", profileData: " + profileData + ">";
    }

    /**
     * Overrides equals()
     *
     * @param other the object to compare with
     * @return are they equal
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Profile)) {
            return false;
        }
        final Profile that = (Profile) other;
        return this.uuid == that.uuid && Objects.equals(this.profileData, that.profileData);
    }

    /**
     * Overrides hashCode()
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.uuid, this.profileData);
    }

}
