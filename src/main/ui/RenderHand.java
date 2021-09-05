package ui;

import model.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

public class RenderHand extends JPanel {
    private Player player;

    private ImageIcon cardOne = null;
    private ImageIcon cardTwo = null;
    private ImageIcon cardThree = null;
    private int width;
    private int height;
    private int handSize;
    Point imageCorner1 = new Point(120,20);
    Point imageCorner2 = new Point(220,20);
    Point imageCorner3 = new Point(320,20);

    public RenderHand(Player player) {
        this.player = player;
        handSize = player.returnHandSize();
        this.setBounds(400, 800, 800, 500);
        super.setOpaque(false);
        this.setLayout(null);

        if (player.returnHandSize() == 2) {
            imageSetter(1, cardToImage(player.returnPlayerHand()[0]));
            imageSetter(2, cardToImage(player.returnPlayerHand()[1]));
            repaint();
        } else if (player.returnHandSize() == 1) {
            //cardThree = cardToImage(player.returnPlayerHand()[player.returnSlotWithCard()]);
            imageSetter(3, cardToImage(player.returnPlayerHand()[player.returnSlotWithCard()]));
            repaint();
        } else {
            //
        }

    }

    //MODIFIES: this
    //EFFECTS: Sets and paints over background where Card is dragged
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        String a = player.returnPlayerName() + "'s hand";
        g.setFont(g.getFont().deriveFont(18.0F));
        g.setColor(Color.black);
        drawHand(g);
        g.drawRect(63,45,a.length() * 10,60);
        g.drawString(a, 75, 80);
        if (!(player.returnHandSize() == handSize)) {
            repaint();
        }
    }

    //MODIFIES: this
    //EFFECTS: Helper for paintComponent
    public void drawHand(Graphics g) {

        if (!(cardOne == null)) {
            cardOne.paintIcon(this, g, (int) imageCorner1.getX(), (int) imageCorner1.getY());
        }
        if (!(cardTwo == null)) {
            cardTwo.paintIcon(this, g, (int) imageCorner2.getX(), (int) imageCorner2.getY());
        }
        if (!(cardThree == null)) {
            cardThree.paintIcon(this, g, (int) imageCorner3.getX(), (int) imageCorner3.getY());
        }
    }


    //MODIFIES: this
    //EFFECTS: Sets image attributes to Card height and length
    public void imageSetter(int a, ImageIcon i) {
        Image image1 = i.getImage();
        Image image2 = image1.getScaledInstance(215, 300, Image.SCALE_SMOOTH);
        if (a == 1) {
            cardOne = new ImageIcon(image2);
            imageCorner1 = new Point(200, 20);
        } else if (a == 2) {
            cardTwo = new ImageIcon(image2);
            imageCorner2 = new Point(350, 20);
        } else {
            cardThree = new ImageIcon(image2);
            imageCorner3 = new Point(275, 20);
        }
        width = i.getIconWidth();
        height = i.getIconHeight();
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