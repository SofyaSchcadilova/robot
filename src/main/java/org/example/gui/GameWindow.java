package org.example.gui;

import org.example.gameModel.GameController;
import org.example.gameModel.GameVisualizer;
import org.example.gameModel.GameModel;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    public GameWindow(GameVisualizer gameVisualizer)
    {
        super("Игровое поле", true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameVisualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
