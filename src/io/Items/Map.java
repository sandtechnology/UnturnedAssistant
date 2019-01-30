package io.Items;

import java.util.HashMap;

public class Map extends LocalizableItem {
    public Map(HashMap<String,String> map){
        map.keySet().retainAll(EnumItem.Map.getAttrs());
        infoMap=map;
    }

    @Override
    public String toString() {
        return infoMap.getOrDefault("Path", "未定义") + "\n地图名称：" + infoMap.getOrDefault("Name", "未定义") + "\n" + infoMap.getOrDefault("Description", "未定义");
    }
}
