package io.item;

import java.util.Comparator;
import java.util.HashMap;

import static Language.LanguageManager.getI18nText;

public class LocalizableItem implements Comparable<LocalizableItem>, Comparator<LocalizableItem> {

    private final HashMap<String, HashMap<String, String>> langMap;
    private final HashMap<String, String> infoMap;
    private final String type;
    private final int id;


    public LocalizableItem(String type, HashMap<String, HashMap<String, String>> dataMap) {
        this.type = type;
        infoMap = dataMap.remove("info");
        id = Integer.valueOf(infoMap.get("ID"));
        langMap = dataMap;
    }

    public String getOriginal(String key) {
        return infoMap.getOrDefault(key, "");
    }

    public String getLang(String language, String key) {
        return langMap.getOrDefault(language, null).getOrDefault(key, "");
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean setLang(String language, String key, String value) {
        return langMap.getOrDefault(language, null).replace(key, value) != null;
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
        StringBuilder result = new StringBuilder(id + "   ");
        //英语优先
        result.append(langMap.get("English").getOrDefault("Name", getI18nText("result.null")));
        //再加其他
        langMap.forEach((x, y) -> {
            if (!"English".equals(x)) {
                result.append("|").append(y.getOrDefault("Name", getI18nText("result.null")));
            }
        });
        return result.toString();
    }

    public boolean equals(Object obj) {
        return (obj instanceof LocalizableItem) && type.equals(((LocalizableItem) obj).type) && id == ((LocalizableItem) obj).id;
    }

    @Override
    public int hashCode() {
        return 31 * (type.hashCode() + id);
    }
}
