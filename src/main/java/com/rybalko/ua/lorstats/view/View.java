package com.rybalko.ua.lorstats.view;

import com.rybalko.ua.lorstats.exel.DocumentOpener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class View extends JFrame {
    private JPanel panel1;
    private JTextField textWins;
    private JTextField textLoses;
    private JButton openStatsButton;

    View() {
        setSize(420, 120);
        setMinimumSize(new Dimension(420, 100));
        setMaximumSize(new Dimension(420, 120));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        openStatsButton.setBackground(Color.BLACK);
        openStatsButton.setFocusable(false);
        openStatsButton.addMouseListener(new OpenStatsEvent());
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        textWins.setFocusable(false);
        textWins.setBorder(new LineBorder(Color.BLACK, 0));
        textLoses.setFocusable(false);
        textLoses.setBorder(new LineBorder(Color.BLACK, 0));
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

    private static class OpenStatsEvent implements MouseListener {

        private DocumentOpener documentOpener;

        OpenStatsEvent() {
            this.documentOpener = new DocumentOpener();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                documentOpener.openStats();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
