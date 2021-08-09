package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
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
    JButton buttonPrevious;
    JButton buttonNext;
    File clickSound = new File("data/mouseClick.wav");

    //REQUIRES: Discards not Empty
    //EFFECTS: Initializes DragAndDrop
    public DragAndDrop(Card[] discard, int num) {
        discards = discard;
        maxArrayNum = num;
        num1 = num;

        if (num >= 0) {
            imageSetter(cardToImage(discards[num1]));
        } else {
            ImageIcon im = new ImageIcon("data/Images/EmptyDiscard.png");
            imageSetter(im);
        }

        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);

        setButtons();

        if ((num1 - 1) < 0) {
            buttonPrevious.setEnabled(false);
        }

        if ((num1 + 1) > maxArrayNum) {
            buttonNext.setEnabled(false);
        }
    }

    //MODIFIES: this
    //EFFECTS: Creates buttons for next and previous cards
    public void setButtons() {
        buttonPrevious = new JButton();
        buttonPrevious.setPreferredSize(new Dimension(200, 40));
        buttonPrevious.addActionListener(this);
        buttonPrevious.setText("Previous Discard");

        buttonNext = new JButton();
        buttonNext.setPreferredSize(new Dimension(200, 40));
        buttonNext.addActionListener(this);
        buttonNext.setText("Next Discard");

        this.add(buttonPrevious);
        this.add(buttonNext);
    }

    //EFFECTS: Paints over background where Card is dragged
    public void paintComponent(Graphics g) {
     // Table image from
     //https://st3.depositphotos.com/12985656/15711/i/600/depositphotos_157114568-stock-photo-top-view-of-old-shabby.jpg
        ImageIcon bg = new ImageIcon("data/Images/Table.png");
        Image image1 = bg.getImage();
        Image image2 = image1.getScaledInstance(1600, 800, Image.SCALE_SMOOTH);
        bg = new ImageIcon(image2);
        super.paintComponent(g);
        g.drawImage(bg.getImage(), 0, 0, null);
        currentImage.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());
    }

    //REQUIRES: Discard is not empty
    //MODIFIES: this
    //EFFECTS: Changes Card to previous or next Discard
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonPrevious) {
            playFile(clickSound);
            num1 = num1 - 1;
            imageSetter(cardToImage(discards[num1]));


        } else if (e.getSource() == buttonNext) {
            num1 = num1 + 1;
            imageSetter(cardToImage(discards[num1]));
            playFile(clickSound);
        }
        if ((num1 - 1) < 0) {
            buttonPrevious.setEnabled(false);
        } else {
            buttonPrevious.setEnabled(true);
        }
        if ((num1 + 1) > maxArrayNum) {
            buttonNext.setEnabled(false);
        } else {
            buttonNext.setEnabled(true);
        }
    }

    //EFFECTS: Plays inputted file
    public void playFile(File file) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(clickSound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception a) {
            //
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
            image = new ImageIcon("data/Images/Guard.png");
        } else if (card.returnCardName().equals("Royal Subject")) {
            image = new ImageIcon("data/Images/RoyalSubject.png");
        } else if (card.returnCardName().equals("Gossip")) {
            image = new ImageIcon("data/Images/Gossip.png");
        } else if (card.returnCardName().equals("Companion")) {
            image = new ImageIcon("data/Images/Companion.png");
        } else if (card.returnCardName().equals("Hero")) {
            image = new ImageIcon("data/Images/Hero.png");
        } else if (card.returnCardName().equals("Wizard")) {
            image = new ImageIcon("data/Images/Wizard.png");
        } else if (card.returnCardName().equals("Lady")) {
            image = new ImageIcon("data/Images/Lady.png");
        } else {
            image = new ImageIcon("data/Images/Princess.png");
        }
        return image;
    }

}
