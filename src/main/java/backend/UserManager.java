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

public class UserManager {

    private ArrayList<User> users = new ArrayList<>();

    public UserManager(){
        loadUsers();
    }

    private void loadUsers(){
        JSONParser parser = new JSONParser();
        try {
            FileReader user = new FileReader("./users.json");
            JSONArray parsedUser = (JSONArray) parser.parse(user);

            for(int i=0;i<parsedUser.size();i++){
                JSONObject userJSON = (JSONObject) parsedUser.get(i);

                User userObj = getUserFromJSON(userJSON);

                users.add(userObj);

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUserFromJSON(JSONObject user){
        String name = (String) user.get("name");
        String password = (String) user.get("password");
        ArrayList<String> interests = getInterests((JSONArray) user.get("interests"));
        ArrayList<Media> watchList = getWatchList((JSONArray) user.get("watchlist"));
        return new User(name,password,interests,watchList);
    }

    private ArrayList<String> getInterests(JSONArray interests){
        ArrayList<String> interestsArr = new ArrayList<>();
        for(int i=0;i<interests.size();i++){
            String interest = (String) interests.get(i);
            interestsArr.add(interest);
        }
        return interestsArr;
    }

    private ArrayList<Media> getWatchList(JSONArray watchlist){
        ArrayList<Media> watchlistArr = new ArrayList<>();
        for(int i=0;i<watchlist.size();i++){
            Media mediaitem = getMedia((JSONObject) watchlist.get(i));
            watchlistArr.add(mediaitem);
        }
        return watchlistArr;
    }


    private Media getMedia(JSONObject media){
       String type = (String) media.get("type");
       if(type=="video"){
        Media mediaObj = MediaManager.getMovieFromJSON(media);
        return mediaObj;
       }
       else{
           Media mediaObj = MediaManager.getSongFromJSON(media);
           return mediaObj;
       }

    }


    public User Register(String username,String password) throws Exception {
        for(User user : users){
            if(user.getName().equalsIgnoreCase(username)){
                throw new Exception("User Already Exists");
            }
        }
        User user = new User(username,password);
        addUser(user);
        return user;
    }

    public User Login(String username,String Password) throws Exception {
        User tempUser = null;
        for(User user : users){
            if(user.getName().equalsIgnoreCase(username) && user.getPassword().equalsIgnoreCase(Password)){
                tempUser = user;
                break;
            }
        }
        if(tempUser == null){
            throw new Exception("Login Or Password Invalid");
        }
        return tempUser;
    }
    private void addUser(User user){
        users.add(user);
        addUserToDB(user);
    }

    private void addUserToDB(User user){
        JSONParser jsonParser = new JSONParser();

        try {
            Object obj = jsonParser.parse(new FileReader("./users.json"));
            JSONArray jsonArray = (JSONArray)obj;

            JSONObject userObj = new JSONObject();
            userObj.put("name", user.getName());
            userObj.put("password",user.getPassword());
            userObj.put("interests",new JSONArray());
            userObj.put("watchlist",new JSONArray());

            jsonArray.add(userObj);

            FileWriter file = new FileWriter("./users.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (ParseException | IOException e) {
            System.out.println(e);
        }
}
    

 



}
