package io.Items;

import java.util.HashMap;
import java.util.HashSet;

public class Map extends LocalizableItem {
    HashSet<String> set=new HashSet<>();
    public Map(HashMap<String,String> map){
        set.add(EnumItem.DESCRIPTION.toString());
        set.add("Loading_Server");
        set.add("Loading_Editor");
        map.keySet().retainAll(set);
        infoMap=map;
    }

    @Override
    public int hashCode() {
        return infoMap.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Map&&((Map) obj).infoMap.equals(infoMap);
    }

    @Override
    public String toString() {
        return infoMap.toString();
    }
}
