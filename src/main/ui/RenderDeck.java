package ui;

import model.Deck;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

// Renders the GUI for deck which player can draw from
public class RenderDeck extends JPanel {
    private Deck deck;
    private Player player;
    private ImageIcon currentImage;
    private int width;
    private int height;
    private Scanner input;
    Point imageCorner;
    GameInterface gameInterface;

    public RenderDeck(Deck deck, Player player, GameInterface I) {
        this.deck = deck;
        this.player = player;
        this.gameInterface = I;
        this.setBounds(1200, 350, 201, 301);
        this.setOpaque(false);
        this.setLayout(null);
        super.setBackground(Color.black);
        if (deck.returnDeck().size() <= 0) {
            ///
        } else {
            ImageIcon bg = new ImageIcon("data/Images/CardBack.png");
            imageSetter(bg);
        }

        ClickListener clickListener = new ClickListener();
        this.addMouseListener(clickListener);
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
        g.drawString("Deck", 400, 200);

        g.drawString("Empty Deck", 50, 150);
        g.drawRect(0,0, 215, 300);
        if (!(currentImage == null)) {
            currentImage.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());
        }


    }

    //MODIFIES: this
    //EFFECTS: Sets image attributes to Card height and length
    public void imageSetter(ImageIcon i) {
        currentImage = i;
        Image image1 = currentImage.getImage();
        Image image2 = image1.getScaledInstance(215, 300, Image.SCALE_SMOOTH);
        currentImage = new ImageIcon(image2);
        imageCorner = new Point(0, 0);
        width = currentImage.getIconWidth();
        height = currentImage.getIconHeight();
        repaint();
    }


    //MODIFIES: this
    //EFFECTS: Checks for mouse click
    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            ///
        }

        public void mouseReleased(MouseEvent e) {
            if (player.returnDrewCard() == false) {
                gameInterface.drawAndReset();
                //
            } else {
                JOptionPane.showMessageDialog(null, player.returnPlayerName()
                        + " already drew card this turn.");
            }
        }
    }
}
