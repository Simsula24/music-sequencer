package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SettingsWindow extends JDialog {
    private MainWindow mainWindow;
    private JTextField bpmField;

    public SettingsWindow(MainWindow owner) {
        super(owner, "Settings", true);
        this.mainWindow = owner;
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

        saveButton.addActionListener(e -> {
            try {
                String input = bpmField.getText().trim();
                int newBpm = Integer.parseInt(input);

                if (newBpm < 30 || newBpm > 300) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a tempo between 30 and 300 BPM.",
                            "Tempo Out of Range",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    mainWindow.setBpm(newBpm);
                    this.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid whole number for the tempo.",
                        "Invalid Tempo Format",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(delButton, BorderLayout.CENTER);
        panel.add(saveButton, BorderLayout.CENTER);

        this.add(panel, BorderLayout.SOUTH);
    }

    private void loadSettings() {
        JPanel panel = new JPanel(new BorderLayout());
        bpmField = new JTextField(String.valueOf(mainWindow.getBpm()));
        JLabel bpmText = new JLabel("Set project tempo (in BPM)");




        panel.add(bpmText, BorderLayout.NORTH);
        panel.add(bpmField, BorderLayout.CENTER);




        this.add(panel, BorderLayout.NORTH);

    }
}
