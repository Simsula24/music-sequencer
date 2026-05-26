package ui;

import logic.AudioTrack;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {

    private List<JCheckBox> checkboxList = new ArrayList<>();
    private List<AudioTrack> tracks = new ArrayList<>();

    private Synthesizer synthesizer;
    private MidiChannel[] midiChannels;

    private boolean isPlaying = false;
    private int bpm = 120;
    private Thread playThread;


    public MainWindow() {
        setTitle("JL Studio");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try{
            loadInstrumentList();
            loadToolbar();
            loadStatusbar();
            loadGrid();
            initMidi();
        } catch (Exception e) {
            System.err.println("Error loading UI: " + e.getMessage());
        }

        this.setVisible(true);



    }

    private void initMidi() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            midiChannels = synthesizer.getChannels();
        } catch (Exception e) {
            System.err.println("Failed to initialize MIDI: " + e.getMessage());
        }
    }

    private void loadInstrumentList() {
        tracks.add(new AudioTrack("Kick Drum", 36));
        tracks.add(new AudioTrack("Snare Drum", 38));
        tracks.add(new AudioTrack("Hi-Hat", 42));
        tracks.add(new AudioTrack("Clap", 39));
    }

    private void loadGrid() {
        // A 4x16 grid (4 tracks, 16 beats)
        JPanel gridPanel = new JPanel(new GridLayout(4, 17, 5, 5));


        for (int trackIndex = 0; trackIndex < 4; trackIndex++) {
            AudioTrack currentTrack = tracks.get(trackIndex);
            JLabel trackLabel = new JLabel(currentTrack.getInstrumentName());
            trackLabel.setFont(new Font("Arial", Font.BOLD, 14));
            gridPanel.add(trackLabel);


            for (int beatIndex = 0; beatIndex < 16; beatIndex++) {
                final int t = trackIndex;
                final int b = beatIndex;
                JCheckBox cb = new JCheckBox();
                cb.setIcon(new ImageIcon("res/box32.png"));
                cb.setSelectedIcon(new ImageIcon("res/box-filled32.png"));
                cb.setSelected(false);

                // When user clicks the checkbox, update the beat in the AudioTrack model
                cb.addActionListener(e -> {
                    currentTrack.setBeat(b, cb.isSelected());
                });

                checkboxList.add(cb);
                gridPanel.add(cb);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
        System.out.println(checkboxList);
    }

    private void playMidiNote(int noteNumber) {
        if (midiChannels != null && midiChannels.length > 9) {
            midiChannels[9].noteOn(noteNumber, 100);
        }
    }

    private void loadStatusbar() {
        JLabel statusLabel = new JLabel(" Ready...");
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

        playBtn.addActionListener(e -> startSequencer());
        stopBtn.addActionListener(e -> stopSequencer());

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

    public void stopSequencer() {
        isPlaying = false;
        if (playThread != null) {
            playThread.interrupt();
            playThread = null;
        }
        // Ensure all UI elements reset to their default unhighlighted states
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < 16; i++) {
                removeHighlightColumn(i);
            }
        });
    }

    public void startSequencer() {
        if (isPlaying) return;
        isPlaying = true;
        playThread = new Thread(() -> {
            int currentBeat = 0;
            while (isPlaying) {
                final int beatToHighlight = currentBeat;

                for (int trackIndex = 0; trackIndex < 4; trackIndex++) {
                    AudioTrack track = tracks.get(trackIndex);
                    if (track.isBeatActive(beatToHighlight)) {
                        playMidiNote(track.getMidiNote());
                    }
                }


                SwingUtilities.invokeLater(() -> {
                    highlightColumn(beatToHighlight);
                });


                try {

                    int sleepTime = 60000 / (bpm * 4);
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    break;
                }


                SwingUtilities.invokeLater(() -> {
                    removeHighlightColumn(beatToHighlight);
                });


                currentBeat = (currentBeat + 1) % 16;
            }
        });

        playThread.start();
    }

    private void removeHighlightColumn(int beatIndex) {
        for (int trackIndex = 0; trackIndex < 4; trackIndex++) {
            int cbIndex = trackIndex * 16 + beatIndex;
            JCheckBox cb = checkboxList.get(cbIndex);
            cb.setBackground(null);
            cb.setOpaque(false);
        }
    }

    private void highlightColumn(int beatIndex) {
        for (int trackIndex = 0; trackIndex < 4; trackIndex++) {
            int cbIndex = trackIndex * 16 + beatIndex;
            JCheckBox cb = checkboxList.get(cbIndex);
            cb.setBackground(new Color(173, 216, 230));
            cb.setOpaque(true);
        }
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int newBpm) {
        this.bpm = newBpm;
    }

    public String getGridState() {
        StringBuilder sb = new StringBuilder();
        for (JCheckBox cb : checkboxList) {
            sb.append(cb.isSelected() ? "1" : "0");
        }
        return sb.toString();
    }

    public void setGridState(String state) {
        if (state == null || state.length() != 64) return;
        for (int i = 0; i < 64; i++) {
            boolean checked = state.charAt(i) == '1';
            checkboxList.get(i).setSelected(checked);

            int trackIndex = i / 16;
            int beatIndex = i % 16;
            tracks.get(trackIndex).setBeat(beatIndex, checked);
        }
    }


}
