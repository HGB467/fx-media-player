package backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MediaManager {

    private ArrayList<Video> storedMovies = new ArrayList<>();
    private ArrayList<Audio> storedSongs = new ArrayList<>();

    public MediaManager(){
       loadMovies();
       loadSongs();
    }

    private void loadMovies(){
        JSONParser parser = new JSONParser();
        try {
            FileReader movies = new FileReader("./movies.json");
            JSONArray parsedMovies = (JSONArray) parser.parse(movies);

            for(int i=0;i<parsedMovies.size();i++){
             JSONObject movie = (JSONObject) parsedMovies.get(i);

             Video video = getMovieFromJSON(movie);

             storedMovies.add(video);

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

   private void loadSongs(){
        JSONParser parser = new JSONParser();
        try {
            FileReader songs = new FileReader("./songs.json");
            JSONArray parsedSong = (JSONArray) parser.parse(songs);

            for(int i=0;i<parsedSong.size();i++){
                JSONObject song = (JSONObject) parsedSong.get(i);

                Audio audio = getSongFromJSON(song);

                storedSongs.add(audio);

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Video getMovieFromJSON(JSONObject movie){
        String name = (String) movie.get("title");
        String description = (String) movie.get("description");
        Long duration = (Long) movie.get("duration");
        Date releaseDate = getReleaseDate((JSONObject) movie.get("Date"));
        String posterLink = (String) movie.get("poster_link");
        Long size = (Long) movie.get("size");
        Long sampleRate = (Long) movie.get("sampleRate");
        String genre = (String) movie.get("genre");
        ArrayList<Artist> artists = getArtists((JSONArray) movie.get("artists"));
        Long frameRate = (Long) movie.get("frameRate");
        Long bitrate = (Long) movie.get("bitrate");
        String codec = (String) movie.get("codec");
        AspectRatio aspectRatio = getAspectRatio((JSONObject) movie.get("aspectRatio"));
        Resolution resolution = getResolution((JSONObject) movie.get("resolution"));
        Long rating = (Long) movie.get("rating");
        String playableLink = (String) movie.get("playable_link");
        Boolean isRemote = (Boolean) movie.get("isRemote");
        Location location;
        if(isRemote){
            location = Location.ONLINE;
        }
        else{
            location = Location.LOCAL;
        }

        return new Video(name,releaseDate,location,description,Math.toIntExact(duration),posterLink,size,false,Math.toIntExact(sampleRate),aspectRatio,Math.toIntExact(frameRate),Math.toIntExact(bitrate),codec,resolution,artists,genre,Math.toIntExact(rating),playableLink);
    }

    public static Audio getSongFromJSON(JSONObject song){
        String name = (String) song.get("title");
        Long duration = (Long) song.get("duration");
        Date releaseDate = getReleaseDate((JSONObject) song.get("Date"));
        String posterLink = (String) song.get("poster_link");
        Long size = (Long) song.get("size");
        Long sampleRate = (Long) song.get("sampleRate");
        String genre = (String) song.get("genre");
        ArrayList<Artist> artists = getArtists((JSONArray) song.get("artists"));
        Long bitrate = (Long) song.get("bitrate");
        String codec = (String) song.get("codec");
        Long channels = (Long) song.get("channels");
        Long rating = (Long) song.get("rating");
        String playableLink = (String) song.get("playable_link");
        Boolean isRemote = (Boolean) song.get("isRemote");
        Location location;
        if(isRemote){
            location = Location.ONLINE;
        }
        else{
            location = Location.LOCAL;
        }

        return new Audio(name,releaseDate,location,Math.toIntExact(duration),posterLink,size,false,Math.toIntExact(sampleRate),Math.toIntExact(bitrate),codec,artists,genre,Math.toIntExact(channels),Math.toIntExact(rating),playableLink);
    }


// To Convert Minutes In Hours And Minutes Format, Will Be Used On Frontend
//    public static String getDuration(Long Min){
//        Long hours   = Min / 60;
//        Long minutes = Min % 60;
//
//        return String.format("%d hrs %d mins",hours,minutes);
//    }

    public static Date getReleaseDate(JSONObject date){
        Long day = (Long) date.get("day");
        Long month = (Long) date.get("month");
        Long year = (Long) date.get("year");
        return new Date(Math.toIntExact(day),Math.toIntExact(month),Math.toIntExact(year));
    }

    public static JSONObject getReleaseDate(Date date){
        int day =  date.getDay();
        int month = date.getMonth();
        int year = date.getYear();

        JSONObject jsondate = new JSONObject();
        jsondate.put("day",day);
        jsondate.put("month",month);
        jsondate.put("year",year);

        return jsondate;
    }

    public static AspectRatio getAspectRatio(JSONObject aspectRatio){
        Long width = (Long) aspectRatio.get("width");
        Long height = (Long) aspectRatio.get("height");

        return new AspectRatio(Math.toIntExact(width),Math.toIntExact(height));
    }

    public static JSONObject getAspectRatio(AspectRatio aspectRatio){
        int width = aspectRatio.getWidth();
        int height = aspectRatio.getHeight();

        JSONObject jsonaspect = new JSONObject();
        jsonaspect.put("width",width);
        jsonaspect.put("height",height);

        return jsonaspect;
    }

    public static Resolution getResolution(JSONObject resolution){
        Long width = (Long) resolution.get("width");
        Long height = (Long) resolution.get("height");

        return new Resolution(Math.toIntExact(width),Math.toIntExact(height));
    }

    public static JSONObject getResolution(Resolution resolution){
        int width = resolution.getWidth();
        int height = resolution.getHeight();

        JSONObject jsonresolution = new JSONObject();
        jsonresolution.put("width",width);
       jsonresolution.put("height",height);

        return jsonresolution;
    }

    public static ArrayList<Artist> getArtists(JSONArray artists){
        ArrayList<Artist> artistsArr = new ArrayList<>();
       for(int i=0;i<artists.size();i++){
           Artist artist = getArtist((JSONObject) artists.get(i));
           artistsArr.add(artist);
       }
       return artistsArr;
    }

    public static ArrayList<Artist> getArtistsJSON(ArrayList<Artist> artists){
        JSONArray artistsArr = new JSONArray();
        for(int i=0;i<artists.size();i++){
            JSONObject artist = getArtistJSON(artists.get(i));
            artistsArr.add(artist);
        }
        return artistsArr;
    }

    public static Artist getArtist(JSONObject artist){
        String name = (String) artist.get("name");
        Long rating = (Long) artist.get("rating");
        return new Artist(name,Math.toIntExact(rating));
    }

    public static JSONObject getArtistJSON(Artist artist){
        String name =  artist.getName();
        int rating =   artist.getRating();

        JSONObject jsonartist = new JSONObject();
        jsonartist.put("name",name);
        jsonartist.put("rating",rating);

        return jsonartist;
    }

    public ArrayList<Video> getAllMovies(){
        return this.storedMovies;
    }

    public ArrayList<Audio> getAllSongs(){
        return this.storedSongs;
    }


    public ArrayList<Video> filterMoviesByGenre(String genre){
        ArrayList<Video> filteredMovies = new ArrayList<>();
        for(Video movie: storedMovies){
            if(movie.getGenre().equalsIgnoreCase(genre)){
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }

    public ArrayList<Audio> filterSongsByGenre(String genre){
        ArrayList<Audio> filteredSongs = new ArrayList<>();
        for(Audio audio: storedSongs){
            if(audio.getGenre().equalsIgnoreCase(genre)){
                filteredSongs.add(audio);
            }
        }
        return filteredSongs;
    }

    public ArrayList<Video> searchMovies(String searchTerm){
        ArrayList<Video> searchedMovies = new ArrayList<>();
        for(Video movie: searchedMovies){
            if(movie.getName().contains(searchTerm) || movie.getArtist().contains(searchTerm) || movie.getDescription().contains(searchTerm)){
                searchedMovies.add(movie);
            }
        }
        return searchedMovies;
    }

    public ArrayList<Audio> searchSongs(String searchTerm){
        ArrayList<Audio> searchedSongs = new ArrayList<>();
        for(Audio audio: searchedSongs){
            if(audio.getName().contains(searchTerm) || audio.getArtist().contains(searchTerm)){
                searchedSongs.add(audio);
            }
        }
        return searchedSongs;
    }

 public ArrayList<Video> getMoviesByArtistName(String artistName){
     ArrayList<Video> filteredMovies = new ArrayList<>();
     for(Video movie: storedMovies){
        for(Artist artist: movie.getArtist()){
            if(artist.getName().equalsIgnoreCase(artistName)){
                filteredMovies.add(movie);
            }
        }
     }
     return filteredMovies;
 }

    public ArrayList<Audio> getSongsByArtistName(String artistName){
        ArrayList<Audio> filteredSongs = new ArrayList<>();
        for(Audio audio: storedSongs){
            for(Artist artist: audio.getArtist()){
                if(artist.getName().equalsIgnoreCase(artistName)){
                    filteredSongs.add(audio);
                }
            }
        }
        return filteredSongs;
    }

    public ArrayList<Video> getMoviesByInterests(ArrayList<String> interests){
        ArrayList<Video> filteredMovies = new ArrayList<>();
        for(Video movie: storedMovies){
            for(String interest: interests){
                if(interest.equalsIgnoreCase(movie.getGenre())){
                    filteredMovies.add(movie);
                    break;
                }
            }
        }
        return filteredMovies;
    }

    public ArrayList<Audio> getSongsByInterests(ArrayList<String> interests){
        ArrayList<Audio> filteredSongs = new ArrayList<>();
        for(Audio audio: storedSongs){
            for(String interest: interests){
                if(interest.equalsIgnoreCase(audio.getGenre())){
                    filteredSongs.add(audio);
                    break;
                }
            }
        }
        return filteredSongs;
    }
    public void addMovie(Video video){
        storedMovies.add(video);
        addMovieToDB(video);
 }

 public void addSong(Audio audio){
        storedSongs.add(audio);
        addSongToDB(audio);
    }

 private void addMovieToDB(Video video){
     JSONParser jsonParser = new JSONParser();

     try {
         Object obj = jsonParser.parse(new FileReader("./movies.json"));
         JSONArray jsonArray = (JSONArray)obj;

         JSONObject movie = movieToJSON(video);

         jsonArray.add(movie);

         FileWriter file = new FileWriter("./movies.json");
         file.write(jsonArray.toJSONString());
         file.flush();
         file.close();

     } catch (ParseException | IOException e) {
         System.out.println(e);
     }
 }

 public static JSONObject movieToJSON(Video video){

        JSONObject movie = new JSONObject();

     movie.put("title", video.getName());
     movie.put("description",video.getDescription());
     movie.put("genre",video.getGenre());
     movie.put("duration",video.getDuration());
     if(video.getLocation() == Location.LOCAL){
         movie.put("isRemote",false);

     }
     else{
         movie.put("isRemote",true);

     }
     movie.put("playable_link",video.getPlayableLink());
     movie.put("size",video.getSize());
     movie.put("sampleRate",video.getSampleRate());
     movie.put("codec",video.getCodec());
     movie.put("bitrate",video.getBitrate());
     movie.put("frameRate",video.getFrameRate());
     movie.put("poster_link",video.getPosterLink());
     movie.put("Date",getReleaseDate(video.getReleaseDate()));
     movie.put("resolution",getResolution(video.getResolution()));
     movie.put("aspectRatio",getAspectRatio(video.getAspectRatio()));
     movie.put("artists",getArtistsJSON(video.getArtist()));
     movie.put("rating",video.getRating());

     return  movie;
 }


    private void addSongToDB(Audio audio){
        JSONParser jsonParser = new JSONParser();

        try {
            Object obj = jsonParser.parse(new FileReader("./songs.json"));
            JSONArray jsonArray = (JSONArray)obj;

            JSONObject song = songToJSON(audio);

            jsonArray.add(song);

            FileWriter file = new FileWriter("./songs.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (ParseException | IOException e) {
            System.out.println(e);
        }
    }

    public static JSONObject songToJSON(Audio audio){
        JSONObject song = new JSONObject();

        song.put("title", audio.getName());
        song.put("genre",audio.getGenre());
        song.put("duration",audio.getDuration());
        if(audio.getLocation()==Location.LOCAL){
            song.put("isRemote",false);
        }
        else{
            song.put("isRemote",true);
        }
        song.put("playable_link",audio.getPlayableLink());
        song.put("size",audio.getSize());
        song.put("sampleRate",audio.getSampleRate());
        song.put("codec",audio.getCodec());
        song.put("bitrate",audio.getBitrate());
        song.put("poster_link",audio.getPosterLink());
        song.put("Date",getReleaseDate(audio.getReleaseDate()));
        song.put("artists",getArtistsJSON(audio.getArtist()));
        song.put("channels",audio.getChannels());
        song.put("rating",audio.getRating());

        return song;
    }

    public void removeMovie(Video video){
        storedMovies.remove(video);
        removeMovieFromDB(video.getName());
    }

    public void removeSong(Audio audio){
        storedSongs.remove(audio);
        removeSongFromDB(audio.getName());
    }

    private void removeMovieFromDB(String title){
        JSONParser jsonParser = new JSONParser();

        try {
            Object obj = jsonParser.parse(new FileReader("./movies.json"));
            JSONArray jsonArray = (JSONArray)obj;

            for(int i=0;i<jsonArray.size();i++){
                JSONObject tempobj = (JSONObject) jsonArray.get(i);
                if(title.equalsIgnoreCase((String) tempobj.get("title"))){
                    jsonArray.remove(i);
                    break;
                }
            }


            FileWriter file = new FileWriter("./movies.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (ParseException | IOException e) {
            System.out.println(e);
        }
    }

    private void removeSongFromDB(String title){
        JSONParser jsonParser = new JSONParser();

        try {
            Object obj = jsonParser.parse(new FileReader("./songs.json"));
            JSONArray jsonArray = (JSONArray)obj;

            for(int i=0;i<jsonArray.size();i++){
                JSONObject tempobj = (JSONObject) jsonArray.get(i);
                if(title.equalsIgnoreCase((String) tempobj.get("title"))){
                    jsonArray.remove(i);
                    break;
                }
            }


            FileWriter file = new FileWriter("./songs.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (ParseException | IOException e) {
            System.out.println(e);
        }
    }

 //TODO: add storted by release date, add sorted by rating


}
