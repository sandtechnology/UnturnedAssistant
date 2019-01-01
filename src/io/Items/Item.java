package io.Items;

import java.util.HashMap;
import java.util.Objects;
import static io.Items.EnumItem.*;

public class Item extends BaseItem{

    public Item(HashMap<String,String> map){
        super(map);
        set.addAll(getAllItems());
        map.keySet().retainAll(set);
        infoMap=map;
    }
}
