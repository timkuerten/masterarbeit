package client;

import datastructure.Datastructure;
import datastructure.Ds2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {

        //create schema
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Adresse", "Stadt", "Alter"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Stadt");

        //create ds
        Datastructure ds = new Ds2();
        try {
            ds.init(schema, thirdPartyIDs);
        } catch (Exception e) {
            System.out.print("Fehler beim Erstellen der Datenstruktur");
        }

        Generator generator = new Generator();
        generator.createProfiles(100);
    }

}
