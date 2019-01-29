package io.Items;

import java.util.HashMap;

public class Animals extends LocalizableItem {
    public Animals(HashMap<String, String> map){
        map.keySet().retainAll(EnumItem.Animals.getAttrs());
        infoMap = map;
    }
}
