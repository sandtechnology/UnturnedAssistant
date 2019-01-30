package io;

import io.Items.Animals;
import io.Items.Item;
import io.Items.Objects;
import io.Items.Vehicle;
import io.Items.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.Items.EnumItem.*;

public class BATFileVisitor implements FileVisitor<Path> {
    private static final HashMap<String, List<LocalizableItem>> itemMap = new LinkedHashMap<>();
    private static final Logger logger = Logger.getLogger("[File Visit Test]");
    private static final BATFileVisitor visitor = new BATFileVisitor();

    private BATFileVisitor() {
        logger.setLevel(Level.OFF);
        for (EnumItem item : EnumItem.values()) {
            if (!item.getName().equals("地图")) {
                itemMap.put(item.getName(), new ArrayList<>());
            }
        }
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    public static HashMap<String, List<LocalizableItem>> visit(Path path) {
        try {
            itemMap.values().forEach(List::clear);
            Files.walkFileTree(path, visitor);
            return itemMap;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private void fillList(List<String> info, String type, Class<? extends LocalizableItem> item) {
        HashMap<String, String> infoMap = new HashMap<>();
        info.forEach(x -> {
            //只分割一次
            String[] result=x.split(" ",2);
            if (result.length == 2)
                infoMap.put(result[0], result[1]);
        });
        try {
            //logger.info(item.getName()+"Constructors:");
            //Arrays.asList(item.getConstructors()).forEach(x->logger.info(x.getName()));
            itemMap.get(type).add(item.getConstructor(HashMap.class).newInstance(infoMap));
        }
        catch (Exception e) {
            e.getCause();
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.endsWith("English.dat")) {
            List<String> info = new ArrayList<>();
            info.add("Path" + " " + file.toString());
            logger.info("Found Language File:" + file);
            //语言文件位于的文件夹名称
            Path pathname = file.subpath(file.getNameCount() - 2, file.getNameCount() - 1);
            //用于一般描述的描述文件判断——物品、物体、载具等等
            Path infoFile = Paths.get(file.getParent().toString(), pathname + ".dat");
            if (infoFile.toFile().exists()) {
                logger.info("Found Info File:" + infoFile);
                info.addAll(Files.readAllLines(infoFile));
                info.addAll(Files.readAllLines(file));
                //物品
                if (file.toString().contains("Item")) {
                    fillList(info, Item.getName(), Item.class);
                }//物体
                else if (file.toString().contains("Objects") || file.toString().contains("Tree")) {
                    fillList(info, Objects.getName(), Objects.class);
                }//载具
                else if (file.toString().contains("Vehicle")) {
                    fillList(info, Vehicle.getName(), Vehicle.class);
                }//动物
                else if (file.toString().contains("Animals")) {
                    fillList(info, Animals.getName(), Animals.class);
                }
            }
 /*           //用于NPC文件的判断——任务、NPC、对话、商店
            Path npcInfoFile = Paths.get(file.getParent().toString(), "Asset.dat");
            if (npcInfoFile.toFile().exists()) {
                info.addAll(Files.readAllLines(npcInfoFile));
                info.addAll(Files.readAllLines(file));
                //角色
                if (file.toString().contains("Character")) {

                }//任务
                else if (file.toString().contains("Quest")) {

                }//对话
                else if (file.toString().contains("Dialogue")) {

                }//商店
                else if (file.toString().contains("Vendor")) {

                }
            }
            //此情况为地图描述（只有English.dat）
            if(!infoFile.toFile().exists()&&!npcInfoFile.toFile().exists()){
                info.addAll(Files.readAllLines(file));
                fillList(info, Map.getName(), Map.class);
            }*/
        }
        return FileVisitResult.CONTINUE;
    }
}
