package client.Input;

import java.util.Map;
import java.util.Set;

public class InputNull implements Input {

    public Map<String, String> getNewValuesToSchema(Set<String> schema) {
        return null;
    }

    public Set<String> getNewSchema() {
        return null;
    }

    public Set<String> getNewThirdPartyId(Set<String> schema) {
        return null;
    }

}
