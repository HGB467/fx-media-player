package backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class User {

    private String name;

    private String password;

    ArrayList<String> interests;

    ArrayList<Media> watchList;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.interests = new ArrayList<>();
        this.watchList = new ArrayList<>();
    }

    public User(String name, String password,ArrayList<String> interests,ArrayList<Media> watchList) {
        this.name = name;
        this.password = password;
        this.interests = interests;
        this.watchList = watchList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getInterests(){
        return  interests;
    }

    public void addInterest(String interest){
        interests.add(interest);
        addInterestToDB(interest);
    }

    private void addInterestToDB(String interest){
        JSONParser jsonParser = new JSONParser();

        try {
            Object obj = jsonParser.parse(new FileReader("./users.json"));
            JSONArray jsonArray = (JSONArray)obj;

            for(int i=0;i<jsonArray.size();i++){
                JSONObject JSONobj = (JSONObject) jsonArray.get(i);
                String name = (String) JSONobj.get("name");
                if(name.equalsIgnoreCase(this.name)){
                    JSONArray arr = (JSONArray) JSONobj.get("interests");
                    arr.add(interest);
                    break;
                }
            }


            FileWriter file = new FileWriter("./users.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (ParseException | IOException e) {
            System.out.println(e);
        }
    }
    

public ArrayList<Media> getWatchlist(){
    return watchList;
}

public void addToWatchlist(Media media){
   watchList.add(media);
   addMediaToDB(media);
}

private void addMediaToDB(Media media){
    JSONParser jsonParser = new JSONParser();

    try {
        Object obj = jsonParser.parse(new FileReader("./users.json"));
        JSONArray jsonArray = (JSONArray)obj;

        for(int i=0;i<jsonArray.size();i++){
            JSONObject JSONobj = (JSONObject) jsonArray.get(i);
            String name = (String) JSONobj.get("name");
            if(name.equalsIgnoreCase(this.name)){
                JSONArray arr = (JSONArray) JSONobj.get("watchlist");
                JSONObject mediaObj = null;
                if(media instanceof Video){
                  mediaObj = MediaManager.movieToJSON((Video) media);
                  mediaObj.put("type","video");
                }
                else{
                    mediaObj = MediaManager.songToJSON((Audio) media);
                    mediaObj.put("type","audio");
                }
                arr.add(mediaObj);
                break;
            }
        }


        FileWriter file = new FileWriter("./users.json");
        file.write(jsonArray.toJSONString());
        file.flush();
        file.close();

    } catch (ParseException | IOException e) {
        System.out.println(e);
    }
}


}
