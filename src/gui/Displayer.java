
package gui;

import io.BATFileVisitor;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
//NetBean生成
public class Displayer extends javax.swing.JFrame {
    private Path path;
    private String version = "V1.1";

    public Displayer(String paths) {
        try {
            path=Paths.get(paths);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setResizable(false);
            initComponents();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void initComponents(){

        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        javax.swing.JButton jButton2 = new javax.swing.JButton();
        //javax.swing.JProgressBar jProgressBar1 = new javax.swing.JProgressBar();
        javax.swing.JTextField jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Unturned ID生成器 "+version);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(new ImageIcon(getClass().getResource("/assets/icon.gif")).getImage());
        jTextField1.setEditable(false);
        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Microsoft YaHei UI", Font.PLAIN, 13)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 12)); // NOI18N
        jButton1.setText("开始生成");
        jButton1.addActionListener(evt -> {
            jTextField1.setText("处理中....");
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
            Thread process = new Thread(() -> {
                new BATFileVisitor().visit(path, jTextArea1);
                jTextField1.setText(path.toString());
                jTextArea1.setEditable(true);
                jButton1.setEnabled(true);
                jButton2.setEnabled(true);
            });
            process.start();
        });

        jButton2.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 12)); // NOI18N
        jButton2.setText("选择游戏目录");
        jButton2.addActionListener(evt -> {
            JFileChooser chooser=new JFileChooser(path.toFile());
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("请选择游戏根目录");
            chooser.showDialog(jButton2,"选择");
            if(chooser.getSelectedFile()!=null) {
                path = chooser.getSelectedFile().toPath();
                jTextField1.setText(path.toString());
            }
        });


        jTextField1.setFont(new java.awt.Font("微软雅黑", Font.PLAIN, 12)); // NOI18N
        jTextField1.setText(path.toString());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jTextField1)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton2)))))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(145, 145, 145)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(155, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }
}
