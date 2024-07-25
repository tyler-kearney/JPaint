import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class JPaint extends JFrame {
    private JComboBox<Integer> cbxBrushSize;
    private JTextField txtColorChange;
    private JButton btnColorChange;
    private JButton btnEraser;
    private JButton btnSave;
    private JButton btnLoad;
    private JPanel drawingCanvas;

    protected int previousX, previousY;
    public Color currentColor = Color.BLACK;

    public JPaint() {
        cbxBrushSize = new JComboBox<>(new Integer[]{5, 10, 15, 20, 25});

        btnColorChange.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String hex = txtColorChange.getText();
                    currentColor = Color.decode(hex);
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid hex code" + ex);
                }
            }
        });
        btnEraser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Create a BufferedImage
                BufferedImage im = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = im.createGraphics();

                paint(g2d);
                g2d.dispose();

                try {
                    File file = new File("im.jpg");
                    ImageIO.write(im, "jpg", file);
                } catch(IOException ex) {
                    System.err.println( "Error saving file" + ex);
                }
            }
        });
        btnLoad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BufferedImage loadedImage;

                try {
                    loadedImage = ImageIO.read(new File("/"));
                    repaint();
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        drawingCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int brushSize = (int) cbxBrushSize.getSelectedItem();

                draw(x, y, currentColor, brushSize);
            }
        });
    }
    private void draw(int x, int y, Color selectedColor, int brushSize) {
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.setColor(selectedColor);
        g2d.setStroke(new BasicStroke(brushSize));
        g2d.drawLine(previousX, previousY, x, y);
        previousX = x;
        previousY = y;
    }
}
