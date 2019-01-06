package io.Items;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.Items.EnumItem.ID;
import static io.Items.EnumItem.NAME;

public abstract class LocalizableItem implements Comparable<LocalizableItem>, Comparator<LocalizableItem> {
    HashMap<String, String> infoMap;
    public String getLang(String key) {
        return infoMap.getOrDefault(key, "");
    }

    public Map<String, String> getLangInfo() {
        return infoMap;
    }

    public boolean setLang(String key, String value) {
        return infoMap.containsKey(key) && !Objects.requireNonNull(infoMap.replace(key, value)).equals(value);
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
        return Integer.parseInt(infoMap.getOrDefault(ID.toString(), "0"));
    }
    @Override
    public String toString() {
        return infoMap.getOrDefault(ID.toString(), "-1") + "   " + infoMap.getOrDefault(NAME.toString(), "名称未定义");
    }
}
