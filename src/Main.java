import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JPaint panel = new JPaint();
        JFrame frame = new JFrame("JPaint");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}