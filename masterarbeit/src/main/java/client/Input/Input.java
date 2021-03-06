package client.Input;

import java.util.Map;
import java.util.Set;

public interface Input {

    Map<String, String> getNewValuesToSchema(Set<String> schema);

    Set<String> getNewSchema();

    Set<String> getNewThirdPartyId(Set<String> schema);

}
