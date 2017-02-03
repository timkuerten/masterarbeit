package client;

import datastructure.Profile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Generator {

    Set<String> name = new HashSet<>();
    Set<String> adress = new HashSet<>();
    Set<String> city = new HashSet<>();
    Set<String> age = new HashSet<>();

    public void createNames() {
        name.clear();
        name.addAll(Arrays.asList("Alice", "Ben", "Constantin", "Doris", "Elton", "Felix", "Gisela", "Hans", "Isabelle",
                "Jan"));
    }

    public Profile createProfile() {
        Profile p = new Profile(UUID.randomUUID());
        return null;
    }

}
