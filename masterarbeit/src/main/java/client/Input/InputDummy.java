package client.Input;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InputDummy implements Input {

    private Integer i = 0;

    public Map<String, String> getNewProfileData(Set<String> schema) {
        Map<String, String> map = new HashMap<>();
        Integer j = 0;
        for (String string : schema) {
            map.put(string, j.toString());
            j++;
        }

        return map;
    }

    public Set<String> getNewSchema() {
        Set<String> set = new HashSet<>();
        set.add(i.toString());
        i++;
        return set;
    }

    public Set<String> getNewThirdPartyId(Set<String> schema) {
        return schema;
    }

}
