package client;

import datastructure.Profile;

import java.util.*;

public class Generator {

    Random r;

    Set<String> name = new HashSet<>();
    Set<String> adress = new HashSet<>();
    Set<String> city = new HashSet<>();
    Set<String> age = new HashSet<>();



    public Generator() {
        r = new Random(Long.MAX_VALUE);
        name.clear();
        name.addAll(Arrays.asList("Alice", "Ben", "Constantin", "Doris", "Elton", "Felix", "Gisela", "Hans", "Isabelle",
                "Jan"));
    }

    public Set<Profile> createProfiles(int number) {
        Set<Profile> profiles = new HashSet<>();
        Profile p = new Profile(UUID.randomUUID());
        int random;
        int size = name.size();
        for (int i = 0; i < number; i++) {
            random = r.nextInt() % size;
            //TODO
        }

        return profiles;
    }

}
