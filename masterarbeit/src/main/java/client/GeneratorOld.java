package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GeneratorOld {

    private Database database;

    private Random random;

    public GeneratorOld(long startValue) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ClassLoader classLoader = getClass().getClassLoader();
        // TODO: Warning:(20, 71) Method invocation 'getFile' may produce 'java.lang.NullPointerException'
        File file = new File(classLoader.getResource("database.yaml").getFile());
        try {
            database = mapper.readValue(file, Database.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // initialize random number generator which always generates same random numbers if startValue is same
        random = new Random(startValue);
    }

    public Map<String, String> generateNewProfileData() {
        Map<String, String> profileData = new HashMap<>();
        //gender    0: female; 1: male
        int gender = Math.abs(random.nextInt() % 2);
        if (gender == 1) {
            // name
            profileData.put("Name",
                    database.firstNameMale.get(Math.abs(random.nextInt() % database.firstNameMale.size())) + " "
                            + database.lastName.get(Math.abs(random.nextInt() % database.lastName.size())));
            // gender
            profileData.put("Geschlecht", "männlich");
        } else {
            // name
            profileData.put("Name",
                    database.firstNameFemale.get(Math.abs(random.nextInt() % database.firstNameFemale.size())) + " "
                            + database.lastName.get(Math.abs(random.nextInt() % database.lastName.size())));
            // gender
            profileData.put("Geschlecht", "weiblich");
        }
        // age (0, ..., 100)
        profileData.put("Alter", Integer.toString(Math.abs(random.nextInt() % 101)));
        // city
        profileData.put("Stadt", database.city.get(Math.abs(random.nextInt() % database.city.size())));
        // street
        profileData.put("Straße", database.street.get(Math.abs(random.nextInt() % database.street.size())));
        // house number (1, ..., 1000)
        profileData.put("Hausnummer", Integer.toString(Math.abs(1 + random.nextInt() % 1000)));

        return profileData;
    }

}
