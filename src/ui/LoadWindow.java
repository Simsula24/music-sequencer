package ui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class LoadWindow extends JDialog {
    private MainWindow mainWindow;
    private JTextField fileField;

    public LoadWindow(MainWindow owner) {
        super(owner, "Load Project", true);
        this.mainWindow = owner;

        setTitle("Load Project");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        try {
            loadLoadUI();
        } catch (Exception e) {
            System.err.println("Error loading Load UI: " + e.getMessage());
        }

        this.setVisible(true);
    }

    private void loadLoadUI() {
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel label = new JLabel("Enter project filename (beat.txt):");
        label.setFont(new Font("Arial", Font.BOLD, 12));

        fileField = new JTextField("beat.txt");
        fileField.setFont(new Font("Arial", Font.PLAIN, 14));

        centerPanel.add(label);
        centerPanel.add(fileField);

        JPanel bottomPanel = new JPanel();
        JButton loadButton = new JButton("Load File");
        JButton cancelButton = new JButton("Cancel");

        loadButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setBackground(Color.LIGHT_GRAY);

        cancelButton.addActionListener(e -> this.dispose());

        loadButton.addActionListener(e -> {
            String filename = fileField.getText().trim();
            if (filename.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Filename cannot be empty.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }


            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

                String bpmLine = reader.readLine();
                if (bpmLine == null) {
                    throw new Exception("File is empty.");
                }
                int loadedBpm = Integer.parseInt(bpmLine.trim());


                String gridLine = reader.readLine();
                if (gridLine == null || gridLine.length() != 64) {
                    throw new Exception("Invalid or incomplete grid data.");
                }


                mainWindow.stopSequencer();


                mainWindow.setBpm(loadedBpm);
                mainWindow.setGridState(gridLine);

                JOptionPane.showMessageDialog(this,
                        "Project successfully loaded from " + filename + "!",
                        "Loaded",
                        JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error loading project: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        bottomPanel.add(cancelButton);
        bottomPanel.add(loadButton);

        this.add(centerPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }
}
