package io.Items;

import java.util.Arrays;
import java.util.List;

import static Language.LanguageManager.getI18nText;

//关于各种类型的定义
//Type是分类
//Useable为能使用的类型
//Width和Height是背包空间大小
public enum ItemLibrary {
    Ordinal("顺序", getI18nText("unturned.objects"), getI18nText("unturned.item"), getI18nText("unturned.objects"), getI18nText("unturned.map"), getI18nText("unturned.vehicle")),
    Animals(getI18nText("unturned.animals"), "ID", "Name"),
    Item(getI18nText("unturned.item"), "ID", "Name", "Description", "Width", "Height", "Food", "Water", "Virus"),
    Objects(getI18nText("unturned.objects"), "ID", "Name"),
    Map(getI18nText("unturned.map"), "Description", "Loading_Server", "Loading_Editor"),
    Vehicle(getI18nText("unturned.vehicle"), "ID", "Name");

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

