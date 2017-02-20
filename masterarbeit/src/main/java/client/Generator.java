package client;

import datastructure.Profile;

import java.util.*;

public class Generator {

    public Database database;

    Random random;

    public Generator(Database database, long startValue) {
        this.database = database;
        //initialize random number generator which always generates same random numbers if startValue is same
        random = new Random(startValue);
    }


    public Profile generateNewProfile() {
        Map<String, String> profileData = new HashMap<>();
        //gender    0: female; 1: male
        int gender = Math.abs(random.nextInt() % 2);
        if (gender == 1) {
            //name
            profileData.put("Name",
                    database.firstNameMale.get(Math.abs(random.nextInt() % database.firstNameMale.size())) + " "
                            + database.lastName.get(Math.abs(random.nextInt() % database.lastName.size())));
            //gender
            profileData.put("Geschlecht", "männlich");
        } else {
            //name
            profileData.put("Name",
                    database.firstNameFemale.get(Math.abs(random.nextInt() % database.firstNameFemale.size())) + " "
                            + database.lastName.get(Math.abs(random.nextInt() % database.lastName.size())));
            //gender
            profileData.put("Geschlecht", "weiblich");
        }
        //age (0, ..., 100)
        profileData.put("Alter", Integer.toString(Math.abs(random.nextInt() % 101)));
        //city
        profileData.put("Stadt", database.city.get(Math.abs(random.nextInt() % database.city.size())));
        //street
        profileData.put("Straße", database.street.get(Math.abs(random.nextInt() % database.street.size())));
        //Hausnummer (1, ..., 1000)
        profileData.put("Hausnummer", Integer.toString(Math.abs(1 + random.nextInt() % 1000)));

        Profile p = new Profile(UUID.randomUUID(), profileData);
        return p;
    }

}
