package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SettingsWindow extends JDialog {
    public SettingsWindow(Frame owner) {
        super(owner, "Settings", true);
        setTitle("Project Settings");
        setSize(600, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            //    MainWindow.subWindowClosed();
            }
        });

        try {
            loadSettings();
            loadBottomBar();
        } catch (Exception e) {
            System.err.println("Error loading settings UI: " + e.getMessage());
        }

        this.setVisible(true);



    }

    private void loadBottomBar() {
        JPanel panel = new JPanel();
        JButton saveButton = new JButton("Save changes");
        JButton delButton = new JButton("Discard changes");

        saveButton.setBackground(Color.LIGHT_GRAY);
        delButton.setBackground(Color.LIGHT_GRAY);

        delButton.addActionListener(e -> {
            this.dispose();
        //    MainWindow.subWindowClosed();
        });

        panel.add(delButton, BorderLayout.CENTER);
        panel.add(saveButton, BorderLayout.CENTER);

        this.add(panel, BorderLayout.SOUTH);
    }

    private void loadSettings() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextField bpmField = new JTextField();
        JLabel bpmText = new JLabel("Set project tempo (in BPM)");




        panel.add(bpmText, BorderLayout.NORTH);
        panel.add(bpmField, BorderLayout.CENTER);




        this.add(panel, BorderLayout.NORTH);

    }
}
