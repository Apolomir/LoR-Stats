package com.rybalko.ua.lorstats.view;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private JPanel panel1;
    private JTextField textWins;
    private JTextField textLoses;

    View() {
        setSize(400, 120);
        setMinimumSize(new Dimension(400, 100));
        setMaximumSize(new Dimension(400, 120));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        textWins.setFocusable(false);
        textLoses.setFocusable(false);
        setContentPane(panel1);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    JTextField getTextWins() {
        return textWins;
    }

    JTextField getTextLoses() {
        return textLoses;
    }
}
