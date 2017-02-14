package client;

import datastructure.Datastructure;
import datastructure.DSHashMap;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {

        //create schema
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("Vorname", "Nachname", "Geschlecht", "Stadt", "Straße", "Hausnummer", "Alter"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Alter");

        //create ds
        Datastructure ds = new DSHashMap();
        try {
            ds.init(schema, thirdPartyIDs);
        } catch (Exception e) {
            System.out.print("Fehler beim Erstellen der Datenstruktur");
        }

        //Generator generator = new Generator(ds);
        //generator.createProfiles(100);
    }

}
