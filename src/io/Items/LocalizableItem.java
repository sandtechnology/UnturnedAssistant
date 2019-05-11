package io.Items;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static Language.LanguageManager.getI18nText;

public class LocalizableItem implements Comparable<LocalizableItem>, Comparator<LocalizableItem> {

    protected final HashMap<String, String> infoMap;
    protected final HashMap<String, String> langMap;
    private final String type;
    private final int id;


    public LocalizableItem(String type, List<String> keys, HashMap<String, String> dataMap, HashMap<String, String> langMap) {
        this.type = type;
        dataMap.keySet().retainAll(keys);
        langMap.keySet().retainAll(keys);
        id = Integer.parseInt(dataMap.getOrDefault("ID", "0"));
        infoMap = dataMap;
        this.langMap = langMap;
    }

    public String getOriginal(String key) {
        return infoMap.getOrDefault(key, "");
    }

    public String getLang(String key) {
        return langMap.getOrDefault(key, "");
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean setLang(String key, String value) {
        return langMap.replace(key, value) != null;
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
        return id
                + "   "
                + infoMap.getOrDefault("Name", getI18nText("result.namenoexist"))
                + " 【"
                + langMap.getOrDefault("Name", getI18nText("result.namenoexist"))
                + "】";
    }

    public boolean equals(Object obj) {
        return (obj instanceof LocalizableItem) && type.equals(((LocalizableItem) obj).type) && id == ((LocalizableItem) obj).id;
    }

    @Override
    public int hashCode() {
        return 31 * (type.hashCode() + id);
    }
}
