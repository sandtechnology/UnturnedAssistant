package io.item;

import java.util.Arrays;
import java.util.List;

import static Language.LanguageManager.getI18nText;

//类型数据
public enum ItemLibrary {
    Ordinal("Sort", getI18nText("unturned.animals"), getI18nText("unturned.item"), getI18nText("unturned.resource"), getI18nText("unturned.objects"), getI18nText("unturned.vehicle"), getI18nText("unturned.resource"), getI18nText("unturned.npc"), getI18nText("unturned.dialog"), getI18nText("unturned.quest")),
    AnimalType(getI18nText("unturned.animals"), "Animal"),
    ItemType(getI18nText("unturned.item"), "Water", "Grower", "Supply", "Grip", "Detonator", "Shirt", "Fisher", "Vehicle_Repair_Tool", "Vest", "Sentry", "Structure", "Food", "Tool", "Optic", "Gun", "Tactical", "Oil_Pump", "Arrest_End", "Charge", "Mask", "Pants", "Barrel", "Trap", "Arrest_Start", "Tank", "Compass", "Tire", "Backpack", "Barricade", "Storage", "Filter", "Farm", "Cloud", "Fuel", "Beacon", "Glasses", "Melee", "Throwable", "Hat", "Medical", "Refill", "Library", "Map", "Magazine", "Generator", "Sight"),
    ObjectType(getI18nText("unturned.objects"), "Decal", "Small", "Medium", "Large"),
    ResourceType(getI18nText("unturned.resource"), "Resource"),
    VehicleType(getI18nText("unturned.vehicle"), "Vehicle"),
    NPCType(getI18nText("unturned.npc"), "NPC"),
    DialogType(getI18nText("unturned.dialog"), "Dialogue"),
    QuestType(getI18nText("unturned.quest"), "Quest");

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

