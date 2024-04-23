package org.example.gameModel;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameModel {
    private volatile double robotPositionX = 100;
    private volatile double robotPositionY = 100;
    private volatile double robotDirection;

    private volatile int targetPositionX = 150;
    private volatile int targetPositionY = 100;

    private static double angle;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

    private static PropertyChangeSupport propertyChangeSupport;

    public GameModel(){
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * подписывает слушателя на изменения
     * @param propertyChangeListener
     */
    public void addListener(PropertyChangeListener propertyChangeListener){
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    protected void setTargetPosition(Point p)
    {
        propertyChangeSupport.firePropertyChange("targetPositionX", targetPositionX, p.x);
        propertyChangeSupport.firePropertyChange("targetPositionY", targetPositionY, p.y);

        targetPositionX = p.x;
        targetPositionY = p.y;
    }

    private static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        double newAngle = asNormalizedRadians(Math.atan2(diffY, diffX));
        if (propertyChangeSupport != null){
            propertyChangeSupport.firePropertyChange("angle", angle, newAngle);
        }

        return newAngle;
    }

    protected void onModelUpdateEvent()
    {
        double distance = distance(targetPositionX, targetPositionY,
                robotPositionX, robotPositionY);
        if (distance < 0.5)
        {
            return;
        }
        double angle = angleTo(robotPositionX, robotPositionY, targetPositionX, targetPositionY);
        double angularVelocity;
        if (asNormalizedRadians(angle - robotDirection) < Math.PI)
        {
            angularVelocity = maxAngularVelocity;
        } else {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(maxVelocity, angularVelocity, 10);
    }

    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = robotPositionX + velocity / angularVelocity *
                (Math.sin(robotDirection  + angularVelocity * duration) -
                        Math.sin(robotDirection));
        if (!Double.isFinite(newX))
        {
            newX = robotPositionX + velocity * duration * Math.cos(robotDirection);
        }
        double newY = robotPositionY - velocity / angularVelocity *
                (Math.cos(robotDirection  + angularVelocity * duration) -
                        Math.cos(robotDirection));
        if (!Double.isFinite(newY))
        {
            newY = robotPositionY + velocity * duration * Math.sin(robotDirection);
        }

        double newDirection = asNormalizedRadians(robotDirection + angularVelocity * duration);

        if (propertyChangeSupport != null){
            propertyChangeSupport.firePropertyChange("robotPositionX", robotPositionX, newX);
            propertyChangeSupport.firePropertyChange("robotPositionY", robotPositionY, newY);
            propertyChangeSupport.firePropertyChange("robotDirection", robotDirection, newDirection);
        }

        robotPositionX = newX;
        robotPositionY = newY;
        robotDirection = newDirection;
    }

    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }

    public double get_robotPositionX() {
        return robotPositionX;
    }

    public double get_robotPositionY() {
        return robotPositionY;
    }

    public double get_robotDirection() {
        return robotDirection;
    }

    public int get_targetPositionX() {
        return targetPositionX;
    }

    public int get_targetPositionY() {
        return targetPositionY;
    }

    public double get_Angle(){
        return angle;
    }
}
