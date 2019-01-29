package io.Items;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//关于各种类型的定义
//Type是分类
//Useable为能使用的类型
//Width和Height是背包空间大小
public enum EnumItem {
    Animals("动物", Arrays.asList("ID", "Name")),
    Item("物品", Arrays.asList("ID", "Name", "Description", "Width", "Height", "Food", "Water", "Virus")),
    Objects("物体", Arrays.asList("ID", "Name")),
    Map("地图", Arrays.asList("Description", "Loading_Server", "Loading_Editor")),
    Vehicle("载具", Arrays.asList("ID", "Name"));

    private final String name;
    private final List<String> attrs;

    EnumItem(String name, List<String> attrs) {
        this.name = name;
        this.attrs = attrs;
    }

    public List<String> getAttrs() {
        return Collections.unmodifiableList(attrs);
    }

    public String getName() {
        return name;
    }
}
