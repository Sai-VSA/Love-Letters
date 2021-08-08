package ui;

import model.*;
import ui.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.*;

public class DragAndDrop extends JPanel {
    private JFrame frame;
    private ImageIcon image;
    private int width;
    private int height;
    Point imageCorner;
    Point prevPt;

    public DragAndDrop() {
        makeFrame();
        image = new ImageIcon("src/Hero.png");
        width = image.getIconWidth();
        height = image.getIconHeight();

        imageCorner = new Point(0, 0);
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        frame.addMouseListener(clickListener);
        frame.addMouseMotionListener(dragListener);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        image.paintIcon(frame, g, (int)imageCorner.getX(), (int)imageCorner.getY());

    }

    public void makeFrame() {
        frame = new JFrame();
        frame.setTitle("Love Letters Virtual");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(true);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(222, 184, 135));

        ImageIcon frameLogo = new ImageIcon("src/img_1.png");
        frame.setIconImage(frameLogo.getImage());
    }


    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            prevPt = e.getPoint();
        }

    }

    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            Point currentPt = e.getPoint();

            imageCorner.translate(currentPt.x - prevPt.x,currentPt.y - prevPt.y);
            prevPt = currentPt;
            frame.repaint();
        }

    }

}
