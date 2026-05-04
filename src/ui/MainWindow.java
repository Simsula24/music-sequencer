package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {

    private List<JCheckBox> checkboxList = new ArrayList<>();



    public MainWindow() {
        setTitle("JL Studio");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try{
            loadToolbar();
            loadStatusbar();
            loadInstrumentList();
            loadGrid();
        } catch (Exception e) {
            System.err.println("Error loading UI: " + e.getMessage());
        }

        this.setVisible(true);



    }

    private void loadInstrumentList() {
    }

    private void loadGrid() {
        // A 4x16 grid (4 tracks, 16 beats)
        JPanel gridPanel = new JPanel(new GridLayout(5, 16));

        for (int i = 0; i < 64; i++) {
            JCheckBox cb = new JCheckBox();
            cb.setIcon(new ImageIcon("res/box32.png"));
            cb.setSelectedIcon(new ImageIcon("res/box-filled32.png"));
            cb.setSelected(false);
            checkboxList.add(cb);
            gridPanel.add(cb);
        }

        add(gridPanel, BorderLayout.CENTER);
        System.out.println(checkboxList);
    }

    private void loadStatusbar() {
        JLabel statusLabel = new JLabel(" Ready to create music...");
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void loadToolbar() {
        JPanel toolbar = new JPanel();

        JButton playBtn = new JButton("Play");
        JButton stopBtn = new JButton("Stop");
        JButton settingsBtn = new JButton("Settings");
        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");

        playBtn.setBackground(Color.LIGHT_GRAY);
        stopBtn.setBackground(Color.LIGHT_GRAY);
        settingsBtn.setBackground(Color.LIGHT_GRAY);
        saveBtn.setBackground(Color.LIGHT_GRAY);
        loadBtn.setBackground(Color.LIGHT_GRAY);

        saveBtn.setIcon(new ImageIcon("res/save16.png"));
        loadBtn.setIcon(new ImageIcon("res/load16.png"));
        settingsBtn.setIcon(new ImageIcon("res/gear16.png"));
        playBtn.setIcon(new ImageIcon("res/play16.png"));
        stopBtn.setIcon(new ImageIcon("res/stop16.png"));
        //saveBtn.setPreferredSize(new Dimension(50, 50));

        saveBtn.addActionListener(e -> {
            new SaveWindow(this);
        });

        loadBtn.addActionListener(e -> {
            new LoadWindow(this);
        });

        settingsBtn.addActionListener(e -> {
            new SettingsWindow(this);
        });

        toolbar.add(playBtn);
        toolbar.add(stopBtn);
        toolbar.add(settingsBtn);
        toolbar.add(saveBtn);
        toolbar.add(loadBtn);

        add(toolbar, BorderLayout.NORTH);
    }


}
