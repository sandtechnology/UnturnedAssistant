
package gui;


import io.BATFileVisitor;
import io.item.ItemLibrary;
import io.item.LocalizableItem;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static Language.LanguageManager.getI18nText;
import static javax.swing.JFileChooser.DIRECTORIES_ONLY;

public class Displayer extends JFrame {
    private JFileChooser pathChooser;
    private JTextArea out;
    private JPanel selectPanel;
    private JPanel pathPanel;
    private JPanel startPanel;
    private JCheckBox workShopCheck;
    private JButton selectButton;
    private JButton startButton;
    private JTextField path;
    private ProgressMonitor pm;

    public Displayer(String path) {
        initComponents();
        setUpComponents();
        addComponents();
        pathChooser.setCurrentDirectory(new File(path));
        this.path.setText(path);
        pack();
    }

    //设置默认字体
    //https://stackoverflow.com/questions/51194267/set-default-font-of-swing-application-once-even-even-if-new-text-is-drawn
    private static void setUIFont() {
        FontUIResource f = new FontUIResource("Sans", Font.PLAIN, 12);
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, f);
        }
    }

    private void reset() {
        pm.setProgress(100);
        selectButton.setEnabled(true);
        startButton.setEnabled(true);
    }
    private void initComponents() {
        String version = "V3.9";
        //设置默认异常捕获
        Thread.setDefaultUncaughtExceptionHandler((t, e) ->
        {
            out.append(getI18nText("gui.error") + "\n" + t.toString() + "\n");
            e.printStackTrace(new PrintWriter(new OutputStreamWriter(new JTextAreaWithInputStream(out), StandardCharsets.UTF_8), true));
            reset();
        });
        //将UI设置在所有组件的前面
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setUIFont();
        }
        catch (Exception ignore) {
        }
        //创建所有组件
        pm = new ProgressMonitor(this, "", getI18nText("gui.processing"), 0, 100);
        pathChooser = new JFileChooser();
        out = new JTextArea();
        selectPanel = new JPanel();
        pathPanel = new JPanel();
        startPanel = new JPanel();
        workShopCheck = new JCheckBox(getI18nText("gui.checkbox.workshop"));
        selectButton = new JButton(getI18nText("gui.button.filechoose"));
        startButton = new JButton(getI18nText("gui.button.start"));
        path = new JTextField();
        setTitle(getI18nText("gui.title") + version);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    private void setUpComponents() {
        //设置选择框
        for (ItemLibrary item : ItemLibrary.values()) {
            if (!item.equals(ItemLibrary.Ordinal)) {
                selectPanel.add(new JCheckBox(item.getName(), true));
            }
        }
        //设置选择按钮
        selectButton.addActionListener((Action) -> {
            pathChooser.setFileSelectionMode(DIRECTORIES_ONLY);
            pathChooser.setDialogTitle(getI18nText("gui.button.filechoose.title"));
            pathChooser.setApproveButtonText(getI18nText("gui.button.filechoose.select"));
            pathChooser.showDialog(this, null);
            if (pathChooser.getSelectedFile().isDirectory()) {
                path.setText(pathChooser.getSelectedFile().getPath());
            }
        });
        //路径显示预排版
        path.setColumns(30);
        //输出区域预排版
        out.setRows(30);
        out.setEditable(false);
        //进度条预排版
        pm.setNote("awoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
        //设置开始按钮事件
        startButton.addActionListener((Action) ->
        {
            //使进度条显示
            pm.setProgress(0);
            //构建SwingWorker
            new SwingWorker<LinkedList<LocalizableItem>, String>() {
                long time;

                @Override
                protected LinkedList<LocalizableItem> doInBackground() throws IOException {
                    //清空输出
                    out.setText("");
                    //起始时间
                    time = System.nanoTime();
                    //禁用按钮
                    selectButton.setEnabled(false);
                    startButton.setEnabled(false);
                    //创建内部类以覆盖方法
                    class Visitor extends BATFileVisitor {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            if (pm.isCanceled()) {
                                reset();
                                return FileVisitResult.TERMINATE;
                            }
                            publish(file.toString());
                            super.visitFile(file, attrs);
                            return FileVisitResult.CONTINUE;
                        }
                    }
                    //游戏目录：...\steamapps\common\Unturned
                    Path gamePath = Paths.get(path.getText());
                    //判断创意工坊
                    if (workShopCheck.isSelected() && gamePath.toFile().exists()) {
                        //创意工坊物品目录：...\steamapps\workshop\content\304930
                        Path workshopPath = Paths.get(gamePath.toFile().getParentFile().getParentFile().getPath(), "workshop", "content", "304930");
                        return Visitor.visit(new Visitor(), gamePath, workshopPath);
                    }
                    else {
                        return Visitor.visit(new Visitor(), gamePath);
                    }
                }

                @Override
                protected void process(List<String> chunks) {
                    if (!pm.isCanceled()) {
                        for (String path : chunks) {
                            //防止超过100
                            pm.setProgress(chunks.size() > 100 ? 99 : chunks.size());
                            pm.setNote(path);
                        }
                    }
                    else {
                        reset();
                    }
                }

                @Override
                protected void done() {
                    List<LocalizableItem> itemList;
                    try {
                        itemList = get();
                    }
                    catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    if (itemList != null && !itemList.isEmpty()) {
                        //输出生成时间和日期
                        out.append(getI18nText("gui.result.cost") + (System.nanoTime() - time) * 10E-9 + " s\n");
                        out.append(getI18nText("gui.result.date") + LocalDate.now() + "\n");
                        //类型留存
                        String lastType = "";
                        for (LocalizableItem item : itemList) {
                            for (Component checkBox : selectPanel.getComponents()) {
                                //类型相符且已被选中
                                if (item.getType().equals(((JCheckBox) checkBox).getText()) && ((JCheckBox) checkBox).isSelected()) {
                                    if (!lastType.equals(item.getType())) {
                                        lastType = item.getType();
                                        out.append("\n===============" + lastType + "===============\n\n");
                                    }
                                    out.append(item.toString() + "\n");
                                }
                            }
                        }
                    }
                    else {
                        out.setText(getI18nText("result.null"));
                    }

                    reset();
                }
            }.execute();
        });


    }

    private void addComponents() {
        pathPanel.add(path);
        pathPanel.add(selectButton);
        startPanel.add(workShopCheck);
        startPanel.add(startButton);
        add(new JScrollPane(out));
        add(selectPanel);
        add(pathPanel);
        add(startPanel);
    }
}
