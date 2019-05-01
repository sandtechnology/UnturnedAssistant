package io.Items;

import java.util.Map;
import java.util.*;

import static Language.LanguageManager.getI18nText;

public class LocalizableItem implements Comparable<LocalizableItem>, Comparator<LocalizableItem> {

    final HashMap<String, String> infoMap;
    private final String type;
    private final int id;
    public String getLang(String key) {
        return infoMap.getOrDefault(key, "");
    }

    public LocalizableItem(String type, List<String> keys, HashMap<String, String> dataMap) {
        this.type = type;
        dataMap.keySet().retainAll(keys);
        id = Integer.parseInt(dataMap.getOrDefault("ID", "0"));
        infoMap = dataMap;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getLang() {
        return Collections.unmodifiableMap(infoMap);
    }

    public boolean setLang(String key, String value) {
        return infoMap.replace(key, value) != null;
    }

    @Override
    public int compare(LocalizableItem o1, LocalizableItem o2) {
        return o1.type.equals(o2.type) ? Integer.compare(o1.id, o2.id) : Integer.compare(ItemLibrary.Ordinal.getAttrs().indexOf(o1.type), ItemLibrary.Ordinal.getAttrs().indexOf(o2.type));
    }

    @Override
    public int compareTo(LocalizableItem o) {
        return compare(this, o);
    }

    @Override
    public String toString() {
        return id + "   " + infoMap.getOrDefault("Name", getI18nText("result.namenoexist"));
    }

    public boolean equals(Object obj) {
        return (obj instanceof LocalizableItem) && type.equals(((LocalizableItem) obj).type) && id == ((LocalizableItem) obj).id;
    }

    @Override
    public int hashCode() {
        return 31 * (type.hashCode() + id);
    }
}
