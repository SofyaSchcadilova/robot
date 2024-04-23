package org.example.gameModel;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Управляет моделью
 */
public class GameController {
    private final GameModel gameModel;

    public GameController(GameModel gameModel){
        this.gameModel = gameModel;
        Timer timer = initTimer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                gameModel.onModelUpdateEvent();
            }
        }, 0, 10);
    }

    /**
     * инициализация таймера
     * @return Timer
     */
    private static Timer initTimer(){
        return new Timer("events generator", true);
    }

    /**
     * установка новых координат
     * @param point - точка, куда кликнули
     */
    public void setCoordinates(Point point){
        gameModel.setTargetPosition(point);
    }
}
