package org.example.save;

import org.example.log.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * класс, отвечающий за работу с файлом
 */
public class StateFile {
    private final String path = System.getProperty("user.home") + "/windowsStates.txt";

    /**
     * метод, отвечающий за запись в файл
     * @param states
     */
    public void writeState(List<SubMap> states){
        File file = new File(path);
        try(FileWriter writer = new FileWriter(file))
        {
            for (SubMap state : states){
                for (String key : state.keySet()) {
                    String value = state.get(key);
                    writer.append(key).append(" ").append(value).append('\n');
                }
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
     * метод, отвечающий за чтение из файла
     * @return
     */
    public Map<String, SubMap> readState() {
        File file = new File(path);
        Map<String, SubMap> states = new HashMap<>();
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] value = line.split(" ");
                String[] parameters = value[0].split("\\.");

                if (!states.containsKey(parameters[0])){
                    states.put(parameters[0], new SubMap(parameters[0], new HashMap<String, String>()));
                }
                states.get(parameters[0]).put(parameters[1], value[1]);
            }
        } catch (Exception ex) {
            System.out.println("Файл не найден: " + file.getName());
        }
        return states;
    }
}
