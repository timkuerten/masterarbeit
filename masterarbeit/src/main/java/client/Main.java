package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import datastructure.DataStructure;
import datastructure.DSHashMap;

import java.io.File;
import java.io.IOException;
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
