package client.Input;

import java.util.*;

import static client.Constants.*;

public class Generator implements Input {

    private Random random;
    private String CHARS;

    public Generator() {
        random = new Random(RANDOM_START_VALUE);
        setSaltChars();
    }

    private void setSaltChars() {
        if (LOWERCASE) {
            CHARS = "abcdefghijklmnopqrstuvwxyz";
        }
        if (UPPERCASE) {
            CHARS += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        if (NUMBERS) {
            CHARS += "1234567890";
        }
    }

    public Map<String, String> getNewProfileData(Set<String> schema) {
        Map<String, String> returnValue = new HashMap<>();
        for (String string : schema) {
            if (random.nextFloat() <= PROBABILITY_OF_SCHEMA_USAGE) {
                returnValue.put(string, createRandomString());
            }
        }
        return returnValue;
    }

    public Set<String> getNewSchema() {
        Set<String> returnValue = new HashSet<>();
        for (int i = 0; i < COUNT_NEW_SCHEMA; i++) {
            returnValue.add(createRandomString());
        }
        return returnValue;
    }

    public Set<String> getNewThirdPartyId(Set<String> schema) {
        Set<String> returnValue = new HashSet<>();
        for (String string : schema) {
            if (random.nextFloat() <= PROBABILITYOF_THIRD_PARTY_ID_USAGE) {
                returnValue.add(string);
            }
        }
        return returnValue;
    }

    private String createRandomString() {
        int stringLength = (int) (random.nextFloat() * (STRING_MAX_LENGTH - STRING_MIN_LENGTH + 1)) + STRING_MIN_LENGTH;
        return createRandomString(stringLength);
    }

    private String createRandomString(int stringLength) {
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < stringLength) {
            int index = (int) (random.nextFloat() * CHARS.length());
            stringBuilder.append(CHARS.charAt(index));
        }
        return stringBuilder.toString();
    }

}
