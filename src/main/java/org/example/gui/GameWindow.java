package org.example.gui;

import org.example.model.GameVisualizer;
import org.example.save.Savable;

import java.awt.*;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements Savable
{
    public GameWindow(GameVisualizer gameVisualizer)
    {
        super("Игровое поле", true, true, true, true);
        setSize(300, 300);
        setLocation(250, 10);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameVisualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
    }

    @Override
    public String getPrefix() {
        return "model";
    }
}
