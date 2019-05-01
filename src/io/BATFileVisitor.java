package io;

import io.Items.LocalizableItem;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static io.Items.ItemLibrary.*;

public class BATFileVisitor implements FileVisitor<Path> {
    private static BATFileVisitor visitor = new BATFileVisitor();
    private static final Logger logger = Logger.getLogger("[File Visit Test]");
    private final LinkedList<LocalizableItem> itemList = new LinkedList<>();
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    public static LinkedList<LocalizableItem> visit(Path path) throws IOException {
        if (visitor == null) {
            visitor = new BATFileVisitor();
        }
        visitor.itemList.clear();
            Files.walkFileTree(path, visitor);
        visitor.itemList.sort(LocalizableItem::compareTo);
        return visitor.itemList;
    }

    private void fillList(List<String> info, String type, List<String> keys) {
        HashMap<String, String> infoMap = new HashMap<>();
        info.forEach(x -> {
            //只分割一次
            String[] result = x.split(" ", 2);
            if (result.length == 2)
                infoMap.put(result[0], result[1]);
        });
        itemList.add(new LocalizableItem(type, keys, infoMap));
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
            String path = file.toString();
            info.add("Path" + " " + path);
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
                if (path.contains("Item")) {
                    fillList(info, Item.getName(), Item.getAttrs());
                }//物体
                else if (path.contains("Objects") || path.contains("Tree")) {
                    fillList(info, Objects.getName(), Objects.getAttrs());
                }//载具
                else if (path.contains("Vehicle")) {
                    fillList(info, Vehicle.getName(), Vehicle.getAttrs());
                }//动物
                else if (path.contains("Animals")) {
                    fillList(info, Animals.getName(), Animals.getAttrs());
                }
            }
 /*           //用于NPC文件的判断——任务、NPC、对话、商店
            Path npcInfoFile = Paths.get(file.getParent().toString(), "Asset.dat");
            if (npcInfoFile.toFile().exists()) {
                info.addAll(Files.readAllLines(npcInfoFile));
                info.addAll(Files.readAllLines(file));
                //角色
                if (path.contains("Character")) {

                }//任务
                else if (path.contains("Quest")) {

                }//对话
                else if (path.contains("Dialogue")) {

                }//商店
                else if (path.contains("Vendor")) {

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
