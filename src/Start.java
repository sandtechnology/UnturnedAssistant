import gui.Displayer;

class Start {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new Displayer(args.length == 1 ? args[0] : ".").setVisible(true));
    }
}
