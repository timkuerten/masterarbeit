package client;

import datastructure.DataStructure;
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
        try {
            //create data structure
            DataStructure ds = new DSHashMap(schema, thirdPartyIDs);
        } catch (Exception e) {
            System.out.print("Fehler beim Erstellen der Datenstruktur");
        }

    }

}
