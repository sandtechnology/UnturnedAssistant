package io.Items;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class LocalizableItem implements Comparable<LocalizableItem>, Comparator<LocalizableItem> {
    HashMap<String, String> infoMap;
    public String getLang(String key) {
        return infoMap.getOrDefault(key, "");
    }

    public Map<String, String> getLang() {
        return Collections.unmodifiableMap(infoMap);
    }

    public boolean setLang(String key, String value) {
        return infoMap.replace(key, value) != null;
    }
    @Override
    public int compare(LocalizableItem o1, LocalizableItem o2) {
        return Integer.compare(o1.getID(), o2.getID());
    }

    @Override
    public int compareTo(LocalizableItem o) {
        return Integer.compare(getID(), o.getID());
    }

    private int getID() {
        return Integer.parseInt(infoMap.getOrDefault("ID", "0"));
    }
    @Override
    public String toString() {
        return infoMap.getOrDefault("ID", "-1") + "   " + infoMap.getOrDefault("Name", "名称未定义");
    }

    public boolean equals(Object obj) {
        return (obj instanceof LocalizableItem) && ((LocalizableItem) obj).infoMap.equals(infoMap);
    }

    @Override
    public int hashCode() {
        return 31 * infoMap.hashCode();
    }
}
