package io.Items;


import java.util.HashMap;

public class Item extends LocalizableItem {

    public Item(HashMap<String,String> map){
        map.keySet().retainAll(EnumItem.Item.getAttrs());
        infoMap=map;
    }
}
