package io;

import io.Items.Map;
import io.Items.Objects;
import io.Items.*;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BATFileVisitor implements FileVisitor<Path> {
    private static final List<LocalizableItem> items = new ArrayList<>();
    private static final List<LocalizableItem> objects = new ArrayList<>();
    private static final List<LocalizableItem> animals = new ArrayList<>();
    private static final List<LocalizableItem> vehicles = new ArrayList<>();
    private static final List<LocalizableItem> maps = new ArrayList<>();
    private static final List<List<LocalizableItem>> lists=new ArrayList<>();
    private static final Logger logger = Logger.getLogger("[File Visit Test]");

    public BATFileVisitor() {
        lists.addAll(Arrays.asList(items,objects,animals,vehicles,maps));
        logger.setLevel(Level.OFF);
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    private void fillList(List<String> info,List<LocalizableItem> target, Class<? extends LocalizableItem> item) {
        HashMap<String, String> map = new LinkedHashMap<>();
        info.forEach(x -> {
            //只分割一次
            String[] result=x.split(" ",2);
            if (result.length == 2)
                map.put(result[0], result[1]);
        });
        try {
            //logger.info(item.toString()+"Constructors:");
            //Arrays.asList(item.getConstructors()).forEach(x->logger.info(x.toString()));
            target.add(item.getConstructor(HashMap.class).newInstance(map));
        }
        catch (Exception e) {
            e.getCause();
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.endsWith("English.dat")) {
            List<String> info = new ArrayList<>();
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
                    fillList(info,items, Item.class);
                }//物体
                else if (file.toString().contains("Objects") || file.toString().contains("Tree")) {
                    fillList(info,objects, Objects.class);
                }//载具
                else if (file.toString().contains("Vehicle")) {
                    fillList(info,vehicles, Vehicle.class);
                }//动物
                else if (file.toString().contains("Animals")) {
                    fillList(info,animals, Animals.class);
                }
            }
            //用于NPC文件的判断——任务、NPC、对话、商店
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
                fillList(info,maps,Map.class);
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    public void visit(Path path, JTextArea textArea) {
        try {
            lists.forEach(List::clear);
            Files.walkFileTree(path, new BATFileVisitor());
            lists.forEach(x->x.sort(LocalizableItem::compareTo));
            textArea.append("生成日期：" + new Date().toString() + "\n\n");
            textArea.append("===============动物===============\n\n");
            animals.forEach(x -> textArea.append(x.toString() + "\n"));
            textArea.append("===============物品===============\n\n");
            items.forEach(x -> textArea.append(x.toString() + "\n"));
            textArea.append("===============载具===============\n\n");
            vehicles.forEach(x -> textArea.append(x.toString() + "\n"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
