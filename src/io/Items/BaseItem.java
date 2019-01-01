package io.Items;

import java.util.HashMap;
import java.util.HashSet;

import static io.Items.EnumItem.ID;
import static io.Items.EnumItem.NAME;

public abstract class BaseItem extends LocalizableItem {
    final HashSet<String> set = new HashSet<>();

    BaseItem(HashMap<String, String> map){
        set.add(ID.toString());
        set.add(NAME.toString());
        map.keySet().retainAll(set);
        infoMap=map;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof BaseItem) && ((BaseItem) obj).infoMap.equals(infoMap);
    }

    @Override
    public int hashCode() {
        return 31 * infoMap.hashCode();
    }
}
