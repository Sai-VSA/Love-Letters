package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import model.*;

// Allows the dragging, dropping and looking through of discarded cards
public class DragAndDrop extends JPanel implements ActionListener {
    private ImageIcon currentImage;
    private int width;
    private int height;
    private Card[] discards;
    private int maxArrayNum;
    private int num1;
    Point imageCorner;
    Point prevPt;
    JButton buttonNext;
    JButton buttonBefore;

    //REQUIRES: Discards not Empty
    //EFFECTS: Initializes DragAndDrop
    public DragAndDrop(Card[] discard, int num) {
        this.setBackground(Color.BLACK);
        discards = discard;
        maxArrayNum = num;

        if (num >= 0) {
            imageSetter(cardToImage(discards[0]));
        } else {
            ImageIcon im = new ImageIcon("src/EmptyDiscard.png");
            imageSetter(im);
        }

        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);

        setButtons();

        if ((num1 - 1) < 0) {
            buttonNext.setEnabled(false);
        }

        if ((num1 + 1) > maxArrayNum) {
            buttonBefore.setEnabled(false);
        }
    }

    //MODIFIES: this
    //EFFECTS: Creates buttons for next and previous cards
    public void setButtons() {
        buttonNext = new JButton();
        buttonNext.setPreferredSize(new Dimension(200, 40));
        buttonNext.addActionListener(this);
        buttonNext.setText("Next Discard");

        buttonBefore = new JButton();
        buttonBefore.setPreferredSize(new Dimension(200, 40));
        buttonBefore.addActionListener(this);
        buttonBefore.setText("Previous Discard");

        this.add(buttonBefore);
        this.add(buttonNext);
    }

    //EFFECTS: Paints over background where Card is dragged
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        currentImage.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());

    }

    //REQUIRES: Discard is not empty
    //MODIFIES: this
    //EFFECTS: Changes Card to previous or next Discard
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonNext) {
            num1 = num1 - 1;
            imageSetter(cardToImage(discards[num1]));

        } else if (e.getSource() == buttonBefore) {
            num1 = num1 + 1;
            imageSetter(cardToImage(discards[num1]));

        }
        if ((num1 - 1) < 0) {
            buttonNext.setEnabled(false);
        } else {
            buttonNext.setEnabled(true);
        }
        if ((num1 + 1) > maxArrayNum) {
            buttonBefore.setEnabled(false);
        } else {
            buttonBefore.setEnabled(true);
        }
    }

    //EFFECTS: Sets image attributes to Card height and length
    public void imageSetter(ImageIcon i) {
        currentImage = i;
        Image image1 = currentImage.getImage();
        Image image2 = image1.getScaledInstance(300, 420, Image.SCALE_SMOOTH);
        currentImage = new ImageIcon(image2);
        imageCorner = new Point(220, 180);
        width = currentImage.getIconWidth();
        height = currentImage.getIconHeight();
        repaint();
    }


    //MODIFIES: this
    //EFFECTS: Checks for mouse click
    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            prevPt = e.getPoint();
        }

    }

    //MODIFIES: this
    //EFFECTS: Checks for mouse movement
    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            Point currentPt = e.getPoint();

            imageCorner.translate(currentPt.x - prevPt.x, currentPt.y - prevPt.y);
            prevPt = currentPt;
            repaint();
        }

    }

    // EFFECTS: Consumes a Card and returns image of Card
    public ImageIcon cardToImage(Card card) {
        ImageIcon image;
        if (card.returnCardName().equals("Guard")) {
            image = new ImageIcon("src/Guard.png");
        } else if (card.returnCardName().equals("Royal Subject")) {
            image = new ImageIcon("src/RoyalSubject.png");
        } else if (card.returnCardName().equals("Gossip")) {
            image = new ImageIcon("src/Gossip.png");
        } else if (card.returnCardName().equals("Companion")) {
            image = new ImageIcon("src/Companion.png");
        } else if (card.returnCardName().equals("Hero")) {
            image = new ImageIcon("src/Hero.png");
        } else if (card.returnCardName().equals("Wizard")) {
            image = new ImageIcon("src/Wizard.png");
        } else if (card.returnCardName().equals("Lady")) {
            image = new ImageIcon("src/Lady.png");
        } else {
            image = new ImageIcon("src/Princess.png");
        }
        return image;
    }

}
