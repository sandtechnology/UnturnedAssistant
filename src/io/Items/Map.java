package io.Items;

import java.util.HashMap;
import java.util.List;

import static Language.LanguageManager.getLocalizedMessage;

public class Map extends LocalizableItem {
    public Map(String type, List<String> keys, HashMap<String, String> dataMap) {
        super(type, keys, dataMap);
    }

    @Override
    public String toString() {
        return infoMap.getOrDefault("Path", getLocalizedMessage("result.namenoexist")) + "\n" + getLocalizedMessage("result.mapname") + infoMap.getOrDefault("Name", getLocalizedMessage("result.namenoexist")) + "\n" + infoMap.getOrDefault("Description", getLocalizedMessage("result.namenoexist"));
    }
}
