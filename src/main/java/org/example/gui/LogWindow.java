package org.example.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.example.log.LogChangeListener;
import org.example.log.LogEntry;
import org.example.log.LogWindowSource;
import org.example.log.Logger;
import org.example.save.Savable;

public class LogWindow extends JInternalFrame implements LogChangeListener, Savable
{
    private LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindow()
    {
        super("Протокол работы", true, true, true, true);
        setSize(300, 600);
        setLocation(50, 50);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                m_logSource.unregisterListener(LogWindow.this);
                dispose();
            }
        });
        m_logSource = Logger.getDefaultLogSource();
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);
        Logger.debug("Протокол работает");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        updateLogContent();
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }
    
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public String getPrefix() {
        return "log";
    }
}
