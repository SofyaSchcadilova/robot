package org.example.gui;

import org.example.model.RobotModel;
import org.example.save.Savable;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class CoordinateWindow extends JInternalFrame implements Savable, PropertyChangeListener {

    private final TextArea text;

    public CoordinateWindow(RobotModel model){
        super("Координаты робота", true, true, true, true);
        setLocation(500, 500);
        setSize(400, 400);
        text = new TextArea("");
        text.setSize(150, 150);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(text, BorderLayout.CENTER);
        getContentPane().add(panel);

        model.setPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof RobotModel model) {
            text.setText("X: " + model.getRobotPositionX() + "\n" +
                    "Y: " + model.getRobotPositionY() + "\n" +
                    "Direction: " + model.getRobotDirection());
        }
    }

    @Override
    public String getPrefix() {
        return "coordinate";
    }
}
