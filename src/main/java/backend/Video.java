package backend;

import java.util.ArrayList;

public class Video extends Media implements Comparable{

    private AspectRatio AspectRatio;
    private int frameRate;
    private Resolution resolution;

    private String description;

    public Video(String name, Date releaseDate ,Location location ,String description, int duration, String posterLink, Long Size, Boolean isPlaying, int sampleRate, AspectRatio AspectRatio, int frameRate, int bitrate, String codec, Resolution resolution, ArrayList<Artist> artist,String genre,int rating, String playableLink) {
        super(name, releaseDate ,location ,duration, posterLink, Size, isPlaying, sampleRate, genre,artist, rating,bitrate,codec,playableLink);
        this.AspectRatio = AspectRatio;
        this.frameRate = frameRate;
        this.resolution = resolution;
        this.description = description;
    }


    public AspectRatio getAspectRatio() {
        return this.AspectRatio;
    }

    public void setAspectRatio(AspectRatio AspectRatio) {
        this.AspectRatio = AspectRatio;
    }

    public int getFrameRate() {
        return this.frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public Resolution getResolution() {
        return this.resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }


    @Override
    public String toString() {
        return "Video [AspectRatio=" + AspectRatio + ", frameRate=" + frameRate + " Rating= "+ super.getRating()+
                 ", resolution=" + resolution + "]";
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int compareTo(Object o){
        int compareRating = ((Video)o).getRating();
        return compareRating-this.getRating();
    }

}
