package org.example.save;


import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class StateConroller {
    private static StateFile stateFile = new StateFile();

    /**
     * получение состояния окна
     * @param window
     * @return подсловарь состояния
     */
    private static SubMap getState(Container window){
        SubMap subMap = new SubMap(((Savable)window).getPrefix(), new HashMap<>());
        subMap.put("Height", String.valueOf(window.getHeight()));
        subMap.put("Width", String.valueOf(window.getWidth()));
        subMap.put("X", String.valueOf(window.getX()));
        subMap.put("Y", String.valueOf(window.getY()));
        if (window instanceof JInternalFrame jInternalFrame) {
            subMap.put("isIcon", String.valueOf(jInternalFrame.isIcon()));
        }

        return subMap;
    }

    /**
     * метод устанавливает состояние окна по подсловарю
     * @param window
     * @param subMap
     * @throws PropertyVetoException
     */
    private static void setState(Container window, SubMap subMap, String prefix) throws PropertyVetoException {
        window.setSize(Integer.parseInt(subMap.get(prefix + ".Height")), Integer.parseInt(subMap.get(prefix + ".Width")));
        window.setLocation(Integer.parseInt(subMap.get(prefix + ".X")), Integer.parseInt(subMap.get(prefix + ".Y")));
        if (window instanceof JInternalFrame jInternalFrame) {
            jInternalFrame.setIcon(Boolean.parseBoolean(subMap.get(prefix + ".isIcon")));
        }
    }

    /**
     * сохраняет состояния окон
     * @param windows
     */
    public static void saveState(List<Container> windows){
        List<SubMap> state = new ArrayList<>();
        for (Container window : windows){
            if (window instanceof Savable){
                state.add(getState(window));
            }
        }
        stateFile.writeState(state);
    }

    /**
     * устанавливает состояние окон
     * @param windows
     * @throws PropertyVetoException
     */
    public static void recoverState (List<Container> windows) {
        try {
            Map<String, SubMap> states = stateFile.readState();
            for (Container window : windows){
                if (window instanceof Savable savable) {
                    setState(window, states.get(savable.getPrefix()), savable.getPrefix());
                }
            }
        } catch (Exception e){
            System.out.println("Не удалось установить состояние окна");
        }
    }
}
