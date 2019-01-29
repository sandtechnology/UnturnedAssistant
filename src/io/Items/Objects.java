package io.Items;

import java.util.HashMap;

public class Objects extends LocalizableItem {
   public Objects(HashMap<String, String> map){
       map.keySet().retainAll(EnumItem.Objects.getAttrs());
       infoMap = map;
    }
}
