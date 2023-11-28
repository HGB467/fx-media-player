package backend;

import java.util.ArrayList;

public class Media {

    private String name;
    private Date ReleaseDate;
    private int duration;
    private String posterLink;
    private Long Size;
    private Boolean isPlaying;
    private int sampleRate;
    private ArrayList<Artist> artist;
    private String genre;

    private Location location;
    private int rating;

    private int bitrate;

    private String codec;

    private String playableLink;

    public Media(String name, Date releaseDate, Location location, int duration, String posterLink, Long Size, Boolean isPlaying, int sampleRate, String genre, ArrayList<Artist> artist, int rating, int bitrate, String codec, String playableLink) {
        this.name = name;
        this.ReleaseDate = releaseDate;
        this.duration = duration;
        this.posterLink = posterLink;
        this.Size = Size;
        this.isPlaying = isPlaying;
        this.sampleRate = sampleRate;
        this.genre = genre;
        this.artist = artist;
        this.location = location;
        this.rating = rating;
        this.bitrate = bitrate;
        this.codec = codec;
        this.playableLink = playableLink;
    }
   


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Long getSize() {
        return this.Size;
    }

    public void setSize(Long Size) {
        this.Size = Size;
    }

    public Boolean isIsPlaying() {
        return this.isPlaying;
    }

    public Boolean getIsPlaying() {
        return this.isPlaying;
    }

    public void setIsPlaying(Boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    
    public ArrayList<Artist> getArtist() {
        return artist;
    }


    public void setArtist(ArrayList<Artist> artist) {
        this.artist = artist;
    }


    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        ReleaseDate = releaseDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public String getPlayableLink() {
        return playableLink;
    }

    public void setPlayableLink(String playableLink) {
        this.playableLink = playableLink;
    }
}
