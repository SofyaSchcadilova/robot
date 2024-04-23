package org.example.gui;

import org.example.gameModel.GameModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CoordinateWindow extends JInternalFrame implements PropertyChangeListener {
    private final TextArea textArea = new TextArea();
    public CoordinateWindow()
    {
        super("Координаты", true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textArea);
        getContentPane().add(panel);
        pack();
    }

    /**
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GameModel gameModel = (GameModel) evt.getSource();
        String coordinateString = "Robot x = " + gameModel.get_robotPositionX() + "\n" +
                                  "Robot y = " + gameModel.get_robotPositionY() + "\n" +
                                  "Robot direction = " + gameModel.get_robotDirection() + "\n" +
                                  "Target x = " + gameModel.get_targetPositionX() + "\n" +
                                  "Target y = " + gameModel.get_targetPositionY() + "\n" +
                                  "Angle = " + gameModel.get_Angle();


        textArea.setText(coordinateString);
    }
}
