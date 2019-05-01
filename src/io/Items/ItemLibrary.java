package io.Items;

import java.util.Arrays;
import java.util.List;

import static Language.LanguageManager.getLocalizedMessage;

//关于各种类型的定义
//Type是分类
//Useable为能使用的类型
//Width和Height是背包空间大小
public enum ItemLibrary {
    Ordinal("顺序", getLocalizedMessage("unturned.objects"), getLocalizedMessage("unturned.item"), getLocalizedMessage("unturned.objects"), getLocalizedMessage("unturned.map"), getLocalizedMessage("unturned.vehicle")),
    Animals(getLocalizedMessage("unturned.animals"), "ID", "Name"),
    Item(getLocalizedMessage("unturned.item"), "ID", "Name", "Description", "Width", "Height", "Food", "Water", "Virus"),
    Objects(getLocalizedMessage("unturned.objects"), "ID", "Name"),
    Map(getLocalizedMessage("unturned.map"), "Description", "Loading_Server", "Loading_Editor"),
    Vehicle(getLocalizedMessage("unturned.vehicle"), "ID", "Name");

    private final String name;
    private final String[] attrs;

    ItemLibrary(String name, String... attrs) {
        this.name = name;
        this.attrs = attrs;
    }

    public List<String> getAttrs() {
        return Arrays.asList(attrs);
    }

    public String getName() {
        return name;
    }
}

