package client;

import datastructure.Datastructure;
import datastructure.Profile;
import datastructure.Schema;

import java.util.*;

public class Generator {

    public Database database;

    Random r;

    Datastructure ds;

    List<String> name = new ArrayList<>();
    List<String> adress = new ArrayList<>();
    List<String> city = new ArrayList<>();
    List<String> age = new ArrayList<>();

    public Generator(Database database) {
        this.database = database;
        //initialize random number generator with generate always the same random numbers
        r = new Random(Long.MAX_VALUE);

        //set values
        name.addAll(Arrays.asList("Alice", "Ben", "Constantin", "Doris", "Elton", "Felix", "Gisela", "Hans", "Isabelle",
                "Jan"));
        adress.addAll(Arrays.asList("Hauptstrasse", "Mendelstrasse", "Schlossallee"));
        city.addAll(Arrays.asList("Aachen", "Berlin", "Dresden", "München", "Münster"));
        for (int i = 18; i < 100; i++) {
            age.add(String.valueOf(i));
        }
    }

    public Set<Profile> createProfiles(int number) {
        Set<Profile> profiles = new HashSet<>();
        int random;
        int nameSize = database.firstNameMale.size();
        int adressSize = adress.size();
        int citySize = city.size();
        int ageSize = age.size();
        for (int i = 0; i < number; i++) {
            //create data for profileData
            Map<String, String> profileData = new HashMap<>();
            profileData.put("Name", database.firstNameMale.get(Math.abs(r.nextInt() % nameSize)));
            profileData.put("Adresse", adress.get(Math.abs(r.nextInt() % adressSize)));
            profileData.put("Stadt", city.get(Math.abs(r.nextInt() % citySize)));
            profileData.put("Alter", age.get(Math.abs(r.nextInt() % ageSize)));

            //create profile and add it to profiles
            Profile p = new Profile(UUID.randomUUID(), profileData);
            profiles.add(p);

        }
        return profiles;
    }

}
