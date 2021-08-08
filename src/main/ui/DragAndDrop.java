package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DragAndDrop extends JPanel implements ActionListener {
    private ImageIcon image;
    private int width;
    private int height;
    Point imageCorner;
    Point prevPt;
    JButton buttonNext;
    JButton buttonBefore;

    public DragAndDrop() {
        image = new ImageIcon("src/Hero.png");
        Image image1 = image.getImage();
        Image image2 = image1.getScaledInstance(300, 420, Image.SCALE_SMOOTH);
        image = new ImageIcon(image2);

        width = image.getIconWidth();
        height = image.getIconHeight();

        imageCorner = new Point(220, 180);
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);

        buttonNext = new JButton();
        buttonNext.setPreferredSize(new Dimension(200, 40));
        buttonNext.addActionListener(this);
        buttonNext.setText("Next");
        buttonNext.setEnabled(true);

        buttonBefore = new JButton();
        buttonBefore.setPreferredSize(new Dimension(200, 40));
        buttonBefore.addActionListener(this);
        buttonBefore.setText("Before");
        buttonBefore.setEnabled(true);

        this.add(buttonBefore, BorderLayout.WEST);
        this.add(buttonNext, BorderLayout.EAST);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        image.paintIcon(this, g, (int)imageCorner.getX(), (int)imageCorner.getY());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if ( !(num == (returnCardsInDiscard - 1)))
        // ButtonNext.setEnabled(false);
        if (e.getSource() == buttonNext) {
            image = new ImageIcon("src/Princess.png");
            Image image1 = image.getImage();
            Image image2 = image1.getScaledInstance(300, 420, Image.SCALE_SMOOTH);
            image = new ImageIcon(image2);
            repaint();
        } else if (e.getSource() == buttonBefore) {
            image = new ImageIcon("src/Hero.png");
            Image image1 = image.getImage();
            Image image2 = image1.getScaledInstance(300, 420, Image.SCALE_SMOOTH);
            image = new ImageIcon(image2);
            repaint();
        }
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
            repaint();
        }

    }

}
