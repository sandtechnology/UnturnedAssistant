package gui;

import javax.swing.*;
import java.io.OutputStream;

class JTextAreaWithInputStream extends OutputStream {
    private final JTextArea jTextArea;

    JTextAreaWithInputStream(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }

    private void append(String str) {
        jTextArea.append(str);
    }

    @Override
    public void write(int b) {
        switch (b) {
            case '\n':
                append("\n");
            case '\r':
                return;
            default:
                append(String.valueOf((char) b));
        }
    }
}
