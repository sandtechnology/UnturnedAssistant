package io.Items;

import java.util.HashMap;

public class Map extends LocalizableItem {
    public Map(HashMap<String,String> map){
        map.keySet().retainAll(EnumItem.Map.getAttrs());
        infoMap=map;
    }
}
