package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

import model.*;


// Allows the dragging, dropping and looking through of discarded cards
public class DragAndDrop extends JPanel implements ActionListener {
    private ImageIcon currentImage;
    private int width;
    private int height;
    private Card[] discards;
    private int maxArrayNum = 0;
    private int num1;
    Point imageCorner;
    Point prevPt;
    JButton buttonPrevious;
    JButton buttonNext;
    JButton buttonTop;
    File clickSound = new File("data/mouseClick.wav");
    File cardSlide = new File("data/CardSlide.wav");
    File pickCard = new File("data/cardPick.wav");
    Clip clip;


    //EFFECTS: Initializes DragAndDrop
    public DragAndDrop(Card[] discard, int num) {
        discards = discard;
        maxArrayNum = num;
        num1 = num;

        this.setBounds(160, 160, 400, 600);
        this.setOpaque(false);
        this.setLayout(null);

        if (num >= 0) {
            imageSetter(cardToImage(discards[num1]));
        } else {
            currentImage = null;
            repaint();
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
        buttonPrevious.setBounds(100, 40, 200, 20);
        buttonPrevious.addActionListener(this);
        buttonPrevious.setText("Previous Discard");

        buttonNext = new JButton();
        buttonNext.setBounds(100, 70, 200, 20);
        buttonNext.addActionListener(this);
        buttonNext.setText("Next Discard");

        buttonTop = new JButton();
        buttonTop.setBounds(100, 100, 200, 20);
        buttonTop.addActionListener(this);
        buttonTop.setText("Return to Top");

        this.add(buttonPrevious);
        this.add(buttonNext);
        this.add(buttonTop);
        buttonConditions();
    }

    //REQUIRES: Buttons already set
    //MODIFIES: this
    //EFFECTS: Enables or disabled buttons based on conditions
    public void buttonConditions() {
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
        if (num1 == maxArrayNum) {
            buttonTop.setEnabled(false);
        } else {
            buttonTop.setEnabled(true);
        }
    }

    //MODIFIES: this
    //EFFECTS: Sets and paints over background where Card is dragged
    public void paintComponent(Graphics g) {
     // Table image from
     //https://st3.depositphotos.com/12985656/15711/i/600/depositphotos_157114568-stock-photo-top-view-of-old-shabby.jpg
       //** ImageIcon bg = new ImageIcon("data/Images/Table.png");
        //Image image1 = bg.getImage();
        //Image image2 = image1.getScaledInstance(1600, 800, Image.SCALE_SMOOTH);
        // bg = new ImageIcon(image2);
        super.paintComponent(g);
       // g.drawImage(bg.getImage(), 0, 0, null);
        g.setFont(g.getFont().deriveFont(18.0F));
        g.setColor(Color.black);
        g.drawString("Discard", 30, 35);
        g.drawString("Pile", 30, 60);

        g.drawString("Empty Discard", 140, 350);
        g.drawRect(100,200, 200, 300);
        if (!(currentImage == null)) {
            currentImage.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());
        }


    }

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
        } else if (e.getSource() == buttonTop) {
            num1 = maxArrayNum;
            imageSetter(cardToImage(discards[num1]));
            playFile(clickSound);
        }
        buttonConditions();
    }

    //EFFECTS: Plays inputted file
    public void playFile(File file) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception a) {
            //
        }
    }

    //MODIFIES: this
    //EFFECTS: Sets image attributes to Card height and length
    public void imageSetter(ImageIcon i) {
        currentImage = i;
        Image image1 = currentImage.getImage();
        Image image2 = image1.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        currentImage = new ImageIcon(image2);
        imageCorner = new Point(100, 200);
        width = currentImage.getIconWidth();
        height = currentImage.getIconHeight();
        repaint();
    }


    //MODIFIES: this
    //EFFECTS: Checks for mouse click
    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            prevPt = e.getPoint();
            playFile(pickCard);
        }

        public void mouseReleased(MouseEvent e) {
            if (!(clip == null) && (clip.isOpen())) {
                clip.close();
            }
        }
    }


    //MODIFIES: this
    //EFFECTS: Checks for mouse movement and drags card
    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            if (!(imageCorner == null)) {
                Point currentPt = e.getPoint();

                imageCorner.translate(currentPt.x - prevPt.x, currentPt.y - prevPt.y);
                prevPt = currentPt;
                repaint();
                if ((clip == null) || !(clip.isOpen()) || (!clip.isActive())) {
                    try {
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(cardSlide);
                        clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        clip.start();
                    } catch (Exception a) {
                        //
                    }
                }
            }
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
