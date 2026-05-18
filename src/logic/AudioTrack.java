package logic;

public class AudioTrack implements Playable {
    private String instrumentName;
    private int midiNote;
    private boolean[] beats = new boolean[16];

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

    public boolean[] getBeats() {
        return beats;
    }

    public void setBeat(int index, boolean active) {
        if (index >= 0 && index < 16) {
            beats[index] = active;
        }
    }

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
