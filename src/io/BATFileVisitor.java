package io;

import com.sun.istack.internal.Nullable;
import io.item.ItemLibrary;
import io.item.LocalizableItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.item.ItemLibrary.Ordinal;

public class BATFileVisitor implements FileVisitor<Path> {
    private static final Logger logger = Logger.getLogger("[File Visit Test]");
    private final LinkedList<LocalizableItem> itemList = new LinkedList<>();

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    public static LinkedList<LocalizableItem> visit(@Nullable BATFileVisitor visitor, Path... paths) throws IOException {
        if (visitor == null) {
            visitor = new BATFileVisitor();
        }
        logger.setLevel(Level.INFO);
        visitor.itemList.clear();
        for (Path path : paths) {
            Files.walkFileTree(path, visitor);
        }
        visitor.itemList.sort(LocalizableItem::compareTo);
        return visitor.itemList;
    }


    private HashMap<String, HashMap<String, String>> getDataMap(HashMap<String, String> infoMap, Path path) throws IOException {
        HashMap<String, HashMap<String, String>> dataMap = new HashMap<>();
        //文件信息
        dataMap.put("info", infoMap);
        //寻找语言文件
        //资源文件名称
        Path infoFile = path.getName(path.getNameCount() - 2);
        File[] files = path.toFile().getParentFile().listFiles(((dir, name) ->
                name.endsWith(".dat")
                        //排除资源文件
                        && !name.endsWith(infoFile + ".dat")
                        //排除NPC资源文件
                        && !name.endsWith("Asset.dat")));
        if (files != null) {
            for (File file : files) {
                logger.info("Found Language File:" + file);
                dataMap.putIfAbsent(file.getName().replace(".dat", ""), toMap(Files.readAllLines(file.toPath())));
            }
        }
        return dataMap;
    }

    /**
     * 将以" "为分隔符的list转换为mapping
     *
     * @param list 要转换的列表
     * @return 转换完毕的mapping
     */
    private HashMap<String, String> toMap(List<String> list) {
        HashMap<String, String> infoMap = new HashMap<>();
        list.forEach(x -> {
            //只分割一次
            String[] result = x.split(" ", 2);
            if (result.length == 2)
                infoMap.put(result[0], result[1]);
        });
        return infoMap;
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
            // 添加路径信息
            info.add("Path" + " " + path);
            logger.info("Found English Language File:" + file);
            //语言文件位于的文件夹名称
            Path pathname = file.getName(file.getNameCount() - 2);
            //一般资源文件
            Path infoFile = Paths.get(file.getParent().toString(), pathname + ".dat");
            //NPC资源文件
            Path assetFile = Paths.get(file.getParent().toString(), "Asset.dat");
            //资源文件和English.dat同时存在，为游戏内可见的对象
            if (infoFile.toFile().exists()) {
                logger.info("Found Info File:" + infoFile);
                info.addAll(Files.readAllLines(infoFile));
            }
            else if (assetFile.toFile().exists()) {
                logger.info("Found NPC Info File:" + assetFile);
                info.addAll(Files.readAllLines(assetFile));
            }
            //size为1为只有路径信息
            //大于1说明上面的资源文件存在
            if (info.size() > 1) {
                HashMap<String, String> infoMap = toMap(info);
                for (ItemLibrary item : ItemLibrary.values()) {
                    //排除自然顺序枚举
                    if (!item.equals(Ordinal)
                            && item.getAttrs().contains(infoMap.get("Type"))) {
                        itemList.add(new LocalizableItem(item.getName(), getDataMap(infoMap, file)));
                    }
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
