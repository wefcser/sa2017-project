package scis.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateLinks {
    public static Map<String, String> createHref(String link) {
        return new HashMap<String, String>() {{
            put("href", link);
        }};
    }

    public static Map createLinks(ArrayList<String> names, ArrayList<String> links) {
        Map<String, Object> map = new HashMap<>();
        for (Integer i = 0; i < names.size(); i++) {
            map.put(names.get(i), createHref(links.get(i)));
        }
        return map;
    }
}