package org.example.model;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * класс упроавления моделью робота
 */
public class ModelController {
    private final RobotModel model;

    public ModelController(RobotModel model) {
        this.model = model;
        Timer timer = new Timer("events generator", true);
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                model.onModelUpdateEvent();
            }
        }, 0, 10);
    }

    /**
     * Метод для установки новых координат роботу
     * @param point
     */
    public void setCoordinates (Point point) {
        model.setTargetPosition(point);
    }
}
