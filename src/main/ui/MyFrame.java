package ui;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {


    DragAndDrop drag = new DragAndDrop();

    public MyFrame() {
        this.getContentPane().setBackground(new Color(222, 184, 135));
        this.setLayout(new BorderLayout());
        this.add(drag);
        this.setSize(800,800);
        this.setVisible(true);
        this.setTitle("Love Letters Virtual");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon frameLogo = new ImageIcon("src/img_1.png");
        this.setIconImage(frameLogo.getImage());

    }
}
