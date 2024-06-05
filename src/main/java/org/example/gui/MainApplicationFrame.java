package org.example.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import org.example.log.Logger;
import org.example.model.GameVisualizer;
import org.example.model.RobotModel;
import org.example.save.StateConroller;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() throws PropertyVetoException {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        setLocation(50, 50);
        setExtendedState(MAXIMIZED_BOTH);
        RobotModel model = new RobotModel();

        addWindow(new LogWindow());
        addWindow(new GameWindow(new GameVisualizer(model)));
        addWindow(new CoordinateWindow(model));

        List<Container> frames = new ArrayList<>(Arrays.asList(desktopPane.getAllFrames()));
        frames.add(this);
        StateConroller.recoverState(frames);
        setContentPane(desktopPane);

        setJMenuBar(createJMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent event){
                Object[] options = {"Да", "Нет!"};
                int n = JOptionPane.showOptionDialog(event.getWindow(), "Закрыть окно?",
                                "Подтверждение", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, options, options[0]);
                if (n == 0) {
                    List<Container> frames = new ArrayList<>(Arrays.asList(desktopPane.getAllFrames()));
                    StateConroller.saveState(frames);
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
            }
        });
    }

    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    public JMenuBar createJMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu DocMenu = this.createJMenu("Document", KeyEvent.VK_D, "Document");
        //DocMenu.add(createDocMenuItem("New", KeyEvent.VK_N, "new"));
        //DocMenu.add(createDocMenuItem("Quit", KeyEvent.VK_Q, "quit"));
        DocMenu.add(createDocMenuItem("Выход", KeyEvent.VK_X, "выход", WindowEvent.WINDOW_CLOSING));

        JMenu lookAndFeelMenu = this.createJMenu("Режим отображения", KeyEvent.VK_V,
                "Управление режимом отображения приложения");
        lookAndFeelMenu.add(createLookAndFeelItem("Системная схема", KeyEvent.VK_S,
                UIManager.getSystemLookAndFeelClassName()));
        lookAndFeelMenu.add(createLookAndFeelItem("Универсальная схема", KeyEvent.VK_S,
                UIManager.getCrossPlatformLookAndFeelClassName()));

        JMenu testMenu = this.createJMenu("Тесты", KeyEvent.VK_T, "Тестовые команды");
        testMenu.add(createTestItem("Сообщение в лог", KeyEvent.VK_T, "Тестовые команды"));
        testMenu.add(deleteTestItem("Удаление сообщения", KeyEvent.VK_T));

        menuBar.add(DocMenu);
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);

        return menuBar;
    }

    /**
     * создает объект JMenu
     * @param menuName
     * @param mnemonic
     * @param accessibleDescription
     * @return JMenu menu
     */
    public JMenu createJMenu(String menuName, int mnemonic, String accessibleDescription){
        JMenu menu = new JMenu(menuName);
        menu.setMnemonic(mnemonic);
        menu.getAccessibleContext().setAccessibleDescription(accessibleDescription);
        return menu;
    }

    /**
     * создает элемент меню Document
     * @param label
     * @param mnemonic
     * @param actionCommand
     * @return JMenuItem menuItem
     */
    private JMenuItem createDocMenuItem(String label, int mnemonic, String actionCommand, int windowEvent){
        JMenuItem menuItem = new JMenuItem(label);
        menuItem.setMnemonic(mnemonic);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(mnemonic, ActionEvent.ALT_MASK));
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener((event) -> {
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                    new WindowEvent(this, windowEvent));
        });
        return menuItem;
    }

    /**
     * создает элемент меню "Режим отображения"
     * @param label
     * @param mnemonic
     * @param className
     * @return JMenuItem menuItem
     */
    private JMenuItem createLookAndFeelItem(String label, int mnemonic, String className){
        JMenuItem menuItem = new JMenuItem(label, mnemonic);
        menuItem.addActionListener((event) -> {
            setLookAndFeel(className);
            this.invalidate();
        });
        return menuItem;
    }

    /**
     * создает элемент меню "Тестовые команды"
     * @param label
     * @param mnemonic
     * @param message
     * @return JMenuItem menuIte
     */
    private JMenuItem createTestItem(String label, int mnemonic, String message){
        JMenuItem menuItem = new JMenuItem(label, mnemonic);
        menuItem.addActionListener((event) -> {
            Logger.debug(message);
        });
        return menuItem;
    }

    private JMenuItem deleteTestItem(String label, int mnemonic){
        JMenuItem menuItem = new JMenuItem(label, mnemonic);
        menuItem.addActionListener((event) -> {
            Logger.deleteMessage();
        });
        return menuItem;
    }

    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
