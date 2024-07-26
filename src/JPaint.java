import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class JPaint extends JFrame {
    private JComboBox<Integer> cbxBrushSize;
    private JTextField txtColorChange;
    private JButton btnColorChange;
    private JButton btnEraser;
    private JButton btnSave;
    private JButton btnLoad;
    private JPanel mainPanel;

    protected int previousX = -1;
    protected int previousY = -1;
    public Color currentColor = Color.BLACK;
    protected ArrayList<Point> points = new ArrayList<>();
    private DrawingCanvas drawingCanvas;

    public JPaint() {
        getContentPane().add(mainPanel);
        cbxBrushSize.addItem(5);
        cbxBrushSize.addItem(10);
        cbxBrushSize.addItem(15);
        cbxBrushSize.addItem(20);
        cbxBrushSize.addItem(25);
        System.out.println(mainPanel);
        drawingCanvas = new DrawingCanvas();
        drawingCanvas.setVisible(true);
        mainPanel.add(drawingCanvas, BorderLayout.CENTER);
        System.out.println(drawingCanvas);
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
                currentColor = Color.WHITE;
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
                    drawingCanvas.repaint();
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        drawingCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                points.add(e.getPoint());
                previousX = e.getX();
                previousY = e.getY();
                drawingCanvas.repaint();
            }
        });

        drawingCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                points.add(e.getPoint());
                previousX = e.getX();
                previousY = e.getY();
                drawingCanvas.repaint();
            }
        });
    }

    class DrawingCanvas extends JPanel {
        public DrawingCanvas() {
            super();
            setMinimumSize(new Dimension(600, 400));
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(currentColor);

            int brushSize = cbxBrushSize.getItemAt(cbxBrushSize.getSelectedIndex());
            g2d.setStroke(new BasicStroke(brushSize));

            for (int i = 1; i < points.size(); i++) {
                Point p1 = points.get(i - 1);
                Point p2 = points.get(i);
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }
}
