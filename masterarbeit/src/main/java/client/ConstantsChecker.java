package client;

import static client.Constants.*;

public class ConstantsChecker {

    public ConstantsChecker() {
        checkInputValues();
    }

    // input checks

    private void checkInputValues() {
        checkStringLegth();
        checkProbabilities();
    }

    private void checkStringLegth() {
        if (STRING_MAX_LENGTH < 1 || STRING_MIN_LENGTH < 1 || STRING_MAX_LENGTH < STRING_MIN_LENGTH) {
            throw new RuntimeException("string legth check failed");
        }
    }

    private void checkSchemaCount() {
        if (COUNT_NEW_SCHEMA < 1) {
            throw new RuntimeException("schema count check failed");
        }
    }

    private void checkProbabilities() {
        if (PROBABILITY_OF_SCHEMA_USAGE < 0.0 || PROBABILITY_OF_SCHEMA_USAGE > 1.0 || PROBABILITYOF_THIRD_PARTY_ID_USAGE < 0.0
                || PROBABILITYOF_THIRD_PARTY_ID_USAGE > 1.0) {
            throw new RuntimeException("probability check failed");
        }
    }

}
