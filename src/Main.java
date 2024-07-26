import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JPaint panel = new JPaint();

        /*
        JFrame frame = new JFrame("JPaint");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel);

         */
        panel.pack();
        panel.setVisible(true);
    }
}