package io.Items;

import java.util.HashMap;
import java.util.List;

import static Language.LanguageManager.getI18nText;

public class Map extends LocalizableItem {
    public Map(String type, List<String> keys, HashMap<String, String> dataMap, HashMap<String, String> langMap) {
        super(type, keys, dataMap, langMap);
    }

    @Override
    public String toString() {
        return infoMap.getOrDefault("Path", getI18nText("result.namenoexist")) + "\n" + getI18nText("result.mapname") + infoMap.getOrDefault("Name", getI18nText("result.namenoexist")) + "\n" + infoMap.getOrDefault("Description", getI18nText("result.namenoexist"));
    }
}
