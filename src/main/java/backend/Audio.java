package backend;

import java.util.ArrayList;

public class Audio extends Media {
    private int channels;

    public Audio(String name, Date releaseDate , Location location , int duration, String posterLink, Long Size, Boolean isPlaying, int sampleRate, int bitrate, String codec, ArrayList<Artist> artist, String genre,int channels,int rating,String playableLink) {
        super(name, releaseDate ,location ,duration, posterLink, Size, isPlaying, sampleRate, genre,artist, rating, bitrate, codec, playableLink);
        this.channels = channels;
    }

    public int getChannels() {
        return this.channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    @Override
    public String toString() {
        return "Audio [bitrate=" + ", channels=" + channels + "]";
    }


}
