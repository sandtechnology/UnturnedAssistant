package io.Items;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;

import static io.Items.EnumItem.ID;
import static io.Items.EnumItem.NAME;

public abstract class LocalizableItem implements Comparable<LocalizableItem>, Comparator<LocalizableItem> {
    HashMap<String, String> infoMap;
    public String getLang(String key) {
        return infoMap.getOrDefault(key, "");
    }
    public HashMap<String, String> getLangInfo() {
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
        if (infoMap.get(EnumItem.ID.toString()) != null) {
            return Integer.parseInt(infoMap.get(ID.toString()));
        }
        else {
            return 0;
        }
    }
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String set : infoMap.keySet()) {
            if (set.equals(ID.toString()) || set.equals(NAME.toString())) {
                str.append(infoMap.getOrDefault(set,"无"));
                str.append(" ");
            }
        }
        return str.toString();
    }
}
