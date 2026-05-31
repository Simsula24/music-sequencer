package ui;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.FileWriter;

/**
 * Dialog window for saving the project to a file.
 */
public class SaveWindow extends JDialog {
    private MainWindow mainWindow;
    private JTextField fileField;

    public SaveWindow(MainWindow owner) {
        super(owner, "Save Project", true);
        this.mainWindow = owner;

        setTitle("Save Project");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        try {
            loadSaveUI();
        } catch (Exception e) {
            System.err.println("Error loading Save UI: " + e.getMessage());
        }

        this.setVisible(true);
    }

    /**
     * Loads the UI for the save dialog window
     */
    private void loadSaveUI() {
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel label = new JLabel("Enter project filename (beat.txt):");
        label.setFont(new Font("Arial", Font.BOLD, 12));

        fileField = new JTextField("beat.txt");
        fileField.setFont(new Font("Arial", Font.PLAIN, 14));

        centerPanel.add(label);
        centerPanel.add(fileField);

        JPanel bottomPanel = new JPanel();
        JButton saveButton = new JButton("Save File");
        JButton cancelButton = new JButton("Cancel");

        saveButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setBackground(Color.LIGHT_GRAY);

        cancelButton.addActionListener(e -> this.dispose());

        saveButton.addActionListener(e -> {
            String filename = fileField.getText().trim();
            if (filename.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Filename cannot be empty.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

                writer.println(mainWindow.getBpm());

                // Write current grid state string
                writer.println(mainWindow.getGridState());

                JOptionPane.showMessageDialog(this,
                        "Project successfully saved to " + filename + "!",
                        "Saved",
                        JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error saving project: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);

        this.add(centerPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }
}