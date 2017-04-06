package datastructure;

import java.util.*;
import java.util.stream.Collectors;

public class Profile {

    //universal unique identifier
    final UUID uuid;

    Map<String, String> profileData;

    /**
     * Constructor to create profile with uuid.
     *
     * @param uuid universually unique identifier
     */
    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.profileData = new HashMap<>();
    }

    /**
     * Constructor to create profile with uuid and other key
     *
     * @param uuid        universually unique identifier
     * @param profileData contain other key of profile
     */
    public Profile(UUID uuid, Map<String, String> profileData) {
        this.uuid = uuid;
        this.profileData = profileData;
    }

    /**
     * Returns the uuid.
     *
     * @return uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Returns a unmodifiableSet which contain current profileData.
     *
     * @return current unmodifiable profileData
     */
    public Map<String, String> getProfileData() {
        //use unmodifiableSet to prevent manipulation
        return Collections.unmodifiableMap(profileData);
    }

    /**
     * Deletes entries of profileData which don't correlate to schema.
     *
     * @param schema new schema of profileData
     */
    protected void update(Schema schema) {
        profileData = profileData.entrySet()
                .stream()
                .filter(map -> schema.getSchema().contains(map.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Checks if profile key corresponds to given schema.
     *
     * @param schema given schema
     * @return if profile key corresponds to given schema
     */
    protected boolean correspondToSchema(Set<String> schema) {
        return (schema.containsAll(profileData.keySet()));
    }

    /**
     * Overrides toString()
     *
     * @return this profile as String
     */
    @Override
    public String toString() {
        return "<Profile " + uuid + ", profileData: " + profileData + ">";
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
