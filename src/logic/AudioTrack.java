package logic;


public class AudioTrack implements Playable {
    private String instrumentName;
    private int midiNote;
    private boolean[] beats = new boolean[16];

    /**
     * Constructor for AudioTrack.
     * @param name Name of the instrument.
     * @param midiNote MIDI note value representing this instrument.
     */
    public AudioTrack(String name, int midiNote) {
        this.instrumentName = name;
        this.midiNote = midiNote;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public int getMidiNote() {
        return midiNote;
    }

    /**
     * Gets the array representing the 16 beats.
     * @return Boolean array of beat states.
     */
    public boolean[] getBeats() {
        return beats;
    }

    /**
     * Sets the state of a beat at a specific index.
     * @param index Index (0 to 15).
     * @param active True to activate the beat, false to disable.
     */
    public void setBeat(int index, boolean active) {
        if (index >= 0 && index < 16) {
            beats[index] = active;
        }
    }

    /**
     * Checks if a beat is active at a specific index.
     * @param index Index (0 to 15).
     * @return True if active, false if disabled.
     */
    public boolean isBeatActive(int index) {
        if (index >= 0 && index < 16) {
            return beats[index];
        }
        return false;
    }

    @Override
    public void play() {
        System.out.println("Playing track: " + instrumentName);
    }

    @Override
    public void stop() {
        System.out.println("Stopping track: " + instrumentName);
    }
}
