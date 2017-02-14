package client;

import datastructure.Profile;

import java.util.*;

public class Generator {

    public Database database;

    Random random;

    public Generator(Database database) {
        this.database = database;
        //initialize random number generator with generate always the same random numbers
        random = new Random(Long.MAX_VALUE);

    }

    public Set<Profile> generateNewProfiles(int number) {
        Set<Profile> profiles = new HashSet<>();
        for (int i = 0; i < number; i++) {
            Map<String, String> profileData = new HashMap<>();
            //gender    0: female; 1: male
            int gender = Math.abs(random.nextInt() % 2);
            if (gender == 1) {
                profileData.put("Name",
                        database.firstNameMale.get(Math.abs(random.nextInt() % database.firstNameMale.size())) + " "
                                + database.lastName.get(Math.abs(random.nextInt() % database.lastName.size())));
                profileData.put("Geschlecht", "männlich");
            } else {
                profileData.put("Name",
                        database.firstNameFemale.get(Math.abs(random.nextInt() % database.firstNameFemale.size())) + " "
                                + database.lastName.get(Math.abs(random.nextInt() % database.lastName.size())));
                profileData.put("Geschlecht", "weiblich");
            }
            //Alter (0, ..., 100)
            profileData.put("Alter", Integer.toString(Math.abs(1 + random.nextInt() % 100)));
            //Stadt
            profileData.put("Stadt", database.city.get(Math.abs(random.nextInt() % database.city.size())));
            //Straße
            profileData.put("Straße", database.street.get(Math.abs(random.nextInt() % database.street.size())));
            //Hausnummer (1, ..., 1000)
            profileData.put("Hausnummer", Integer.toString(Math.abs(1 + random.nextInt() % 1000)));

            Profile p = new Profile(UUID.randomUUID(), profileData);
            profiles.add(p);

        }
        return profiles;
    }

    public Profile generateNewProfile() {
        Map<String, String> profileData = new HashMap<>();
        //gender    0: female; 1: male
        int gender = Math.abs(random.nextInt() % 2);
        if (gender == 1) {
            profileData.put("Name",
                    database.firstNameMale.get(Math.abs(random.nextInt() % database.firstNameMale.size())) + " "
                            + database.lastName.get(Math.abs(random.nextInt() % database.lastName.size())));
            profileData.put("Geschlecht", "männlich");
        } else {
            profileData.put("Name",
                    database.firstNameFemale.get(Math.abs(random.nextInt() % database.firstNameFemale.size())) + " "
                            + database.lastName.get(Math.abs(random.nextInt() % database.lastName.size())));
            profileData.put("Geschlecht", "weiblich");
        }
        //Alter (0, ..., 100)
        profileData.put("Alter", Integer.toString(Math.abs(1 + random.nextInt() % 100)));
        //Stadt
        profileData.put("Stadt", database.city.get(Math.abs(random.nextInt() % database.city.size())));
        //Straße
        profileData.put("Straße", database.street.get(Math.abs(random.nextInt() % database.street.size())));
        //Hausnummer (1, ..., 1000)
        profileData.put("Hausnummer", Integer.toString(Math.abs(1 + random.nextInt() % 1000)));

        Profile p = new Profile(UUID.randomUUID(), profileData);
        return p;
    }

}
