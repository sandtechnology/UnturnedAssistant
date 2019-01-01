package io.Items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//关于各种类型的定义
//Type是分类
//Useable为能使用的类型
//Width和Height是背包空间大小
public enum EnumItem {
    ID("ID"),
    NAME("Name"),
    DESCRIPTION("Description"),
    WIDTH("Width"),
    HEIGHT("Height");
    private final String name;

    EnumItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Collection<String> getAllItems() {
        List<String> list = new ArrayList<>();
        for (EnumItem item : values()) {
            list.add(item.toString());
        }
        return list;
    }
}
