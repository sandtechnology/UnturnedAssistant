package io.Items;

import java.util.HashMap;

public class Vehicle extends LocalizableItem {
    public Vehicle(HashMap<String, String> map){
        map.keySet().retainAll(EnumItem.Vehicle.getAttrs());
        infoMap = map;
    }
}
