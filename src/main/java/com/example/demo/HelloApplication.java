package com.example.demo;

import backend.*;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.*;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.Flow;

import org.controlsfx.control.CheckComboBox;


public class HelloApplication extends Application {

    User loggedInUser;


    FlowPane localItemsContainer;

    FlowPane localAudioItemsContainer;

    VBox localFilePicker;

    VBox localAudioFilePicker;

    UserManager userManager;

    MediaManager mediaManager;

    @Override
    public void start(Stage stage) throws IOException {

        userManager = new UserManager();
        mediaManager = new MediaManager();

        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(25,187,212), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(25);

        Text text = new Text("Welcome To Streamify");
        text.setFont(Font.loadFont("file:montserrat.ttf",100));
        text.setFill(Color.WHITE);


        Text text1 = new Text("Your Own Home Theater");
        text1.setFont(Font.loadFont("file:montserrat.ttf",40));
        text1.setFill(Color.WHITE);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);

        Button button = new Button("Login");
        button.setFont(Font.loadFont("file:montserrat.ttf",20));
        button.setBackground(new Background(new BackgroundFill(Color.rgb(86,162,203), CornerRadii.EMPTY, Insets.EMPTY)));
        button.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-cursor: hand;");
        button.setOnAction(e -> {
            displayLoginScene(stage,userManager,mediaManager);
        });


        Button button1 = new Button("Register");
        button1.setFont(Font.loadFont("file:montserrat.ttf",20));
        button1.setBackground(new Background(new BackgroundFill(Color.rgb(86,162,203), CornerRadii.EMPTY, Insets.EMPTY)));
        button1.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-cursor: hand;");
        button1.setOnAction(e -> {
            displayRegisterScene(stage,userManager,mediaManager);
        });



        hbox.getChildren().addAll(button,button1);

        vbox.getChildren().addAll(text,text1,hbox);

        Scene scene = new Scene(vbox,1280,720);
        stage.setScene(scene);
        stage.setTitle("Streamify - Your Own Home Theater");



        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    private void displayLoginScene(Stage stage,UserManager userManager,MediaManager mediaManager){
        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(25,187,212), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(70);

        Text text = new Text("Login");
        text.setFont(Font.loadFont("file:montserrat.ttf",80));
        text.setFill(Color.WHITE);

        VBox loginContainer = new VBox();
        loginContainer.setAlignment(Pos.CENTER);
        loginContainer.setSpacing(25);

        VBox usernameContainer = new VBox();
        usernameContainer.setAlignment(Pos.CENTER);
        usernameContainer.setSpacing(10);

        Label usernameLabel = new Label("Username");
        usernameLabel.setAlignment(Pos.CENTER);
        usernameLabel.setFont(Font.loadFont("file:montserrat.ttf",20));
        usernameLabel.setStyle("-fx-text-fill: white");

        TextField username = new TextField();
        username.setAlignment(Pos.CENTER);
        username.setMaxWidth(300);
        username.setFont(Font.loadFont("file:montserrat.ttf",20));

        usernameContainer.getChildren().addAll(usernameLabel,username);

        VBox passwordContainer = new VBox();
        passwordContainer.setAlignment(Pos.CENTER);
        passwordContainer.setSpacing(6);

        Label passwordLabel = new Label("Password");
        passwordLabel.setAlignment(Pos.CENTER);
        passwordLabel.setFont(Font.loadFont("file:montserrat.ttf",20));
        passwordLabel.setStyle("-fx-text-fill: white");

        PasswordField password = new PasswordField();
        password.setAlignment(Pos.CENTER);
        password.setMaxWidth(300);
        password.setFont(new Font("Arial",20));

        Button register = new Button("Don't Have An Account? Register");
        register.setFont(Font.loadFont("file:montserrat.ttf",15));
        register.setStyle("-fx-text-fill: white; -fx-border-color: transparent;-fx-cursor: hand; -fx-background-color: transparent");
        register.setUnderline(true);
        register.setOnAction(e->{
            displayRegisterScene(stage,userManager,mediaManager);
        });


        passwordContainer.getChildren().addAll(passwordLabel,password,register);

        Label error = new Label();
        error.setFont(Font.loadFont("file:montserrat.ttf",15));
        error.setBackground(new Background(new BackgroundFill(Color.rgb(86,162,203), CornerRadii.EMPTY, Insets.EMPTY)));
        error.setStyle("-fx-text-fill: white; -fx-border-color: white;");
        error.setPadding(new Insets(7));
        error.setVisible(false);

        Button button1 = new Button("Login");
        button1.setFont(Font.loadFont("file:montserrat.ttf",20));
        button1.setBackground(new Background(new BackgroundFill(Color.rgb(86,162,203), CornerRadii.EMPTY, Insets.EMPTY)));
        button1.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-cursor: hand;");
        button1.setOnAction(e-> {
            handleLogin(stage,username.getText(),password.getText(),error,userManager,mediaManager);
        });


        loginContainer.getChildren().addAll(usernameContainer,passwordContainer,error,button1);

        vbox.getChildren().addAll(text,loginContainer);

        System.out.println(stage.getWidth());


        Scene scene = new Scene(vbox,stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Streamify - Login");



        stage.show();
    }

    private void handleLogin(Stage stage,String username,String password,Label error,UserManager userManager,MediaManager mediaManager){
        error.setVisible(false);
        if(username.length()==0){
            error.setText("Error: Please Input Username");
            error.setVisible(true);
            return;
        }
        if(password.length()==0){
            error.setText("Error: Please Input Password");
            error.setVisible(true);
            return;
        }

        try{
            loggedInUser = userManager.Login(username,password);
            error.setText("Success: Logged In Successfully! Please Wait...");
            error.setVisible(true);
            displayHomeScene(stage,mediaManager,userManager);
        }
        catch(Exception exception){
            error.setText("Error:" + exception.getMessage());
            error.setVisible(true);
        }
    }

    private void displayRegisterScene(Stage stage,UserManager userManager,MediaManager mediaManager){
        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(25,187,212), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(70);

        Text text = new Text("Register");
        text.setFont(Font.loadFont("file:montserrat.ttf",80));
        text.setFill(Color.WHITE);

        VBox registerContainer = new VBox();
        registerContainer.setAlignment(Pos.CENTER);
        registerContainer.setSpacing(25);

        VBox usernameContainer = new VBox();
        usernameContainer.setAlignment(Pos.CENTER);
        usernameContainer.setSpacing(10);

        Label usernameLabel = new Label("Username");
        usernameLabel.setAlignment(Pos.CENTER);
        usernameLabel.setFont(Font.loadFont("file:montserrat.ttf",20));
        usernameLabel.setStyle("-fx-text-fill: white");

        TextField username = new TextField();
        username.setAlignment(Pos.CENTER);
        username.setMaxWidth(300);
        username.setFont(Font.loadFont("file:montserrat.ttf",20));

        usernameContainer.getChildren().addAll(usernameLabel,username);

        VBox passwordContainer = new VBox();
        passwordContainer.setAlignment(Pos.CENTER);
        passwordContainer.setSpacing(6);


        Label passwordLabel = new Label("Password");
        passwordLabel.setAlignment(Pos.CENTER);
        passwordLabel.setFont(Font.loadFont("file:montserrat.ttf",20));
        passwordLabel.setStyle("-fx-text-fill: white");

        PasswordField password = new PasswordField();
        password.setAlignment(Pos.CENTER);
        password.setMaxWidth(300);
        password.setFont(new Font("Arial",20));


        passwordContainer.getChildren().addAll(passwordLabel,password);

        VBox interestsCont = new VBox();
        interestsCont.setAlignment(Pos.CENTER);
        interestsCont.setSpacing(12);

        Label interestsLabel = new Label("Interests");
        interestsLabel.setAlignment(Pos.CENTER);
        interestsLabel.setFont(Font.loadFont("file:montserrat.ttf",20));
        interestsLabel.setStyle("-fx-text-fill: white");

        CheckComboBox<String> movieInterestsBox = new CheckComboBox<>();
        movieInterestsBox.setTitle("Choose Interests");
        movieInterestsBox.setMinWidth(80);
        movieInterestsBox.setMaxWidth(300);
        movieInterestsBox.getItems().addAll("Action", "Thriller", "Crime","Sci-fi","Horror","Pop", "Rock", "Electronic","Hip-hop");

        interestsCont.getChildren().addAll(interestsLabel,movieInterestsBox);

        Button register = new Button("Already Have An Account? Login");
        register.setFont(Font.loadFont("file:montserrat.ttf",15));
        register.setStyle("-fx-text-fill: white; -fx-border-color: transparent; -fx-cursor: hand; -fx-background-color: transparent");
        register.setUnderline(true);
        register.setOnAction(e->{
            displayLoginScene(stage,userManager,mediaManager);
        });

        Label error = new Label();
        error.setFont(Font.loadFont("file:montserrat.ttf",15));
        error.setBackground(new Background(new BackgroundFill(Color.rgb(86,162,203), CornerRadii.EMPTY, Insets.EMPTY)));
        error.setStyle("-fx-text-fill: white; -fx-border-color: white;");
        error.setPadding(new Insets(7));
        error.setVisible(false);

        Button button1 = new Button("Register");
        button1.setFont(Font.loadFont("file:montserrat.ttf",20));
        button1.setBackground(new Background(new BackgroundFill(Color.rgb(86,162,203), CornerRadii.EMPTY, Insets.EMPTY)));
        button1.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-cursor: hand;");
        button1.setOnAction(e-> {
            handleRegister(stage,username.getText(),password.getText(),movieInterestsBox.getCheckModel().getCheckedItems(),error,userManager,mediaManager);
        });

        registerContainer.getChildren().addAll(usernameContainer,passwordContainer,interestsCont,register,error,button1);

        vbox.getChildren().addAll(text,registerContainer);




        Scene scene = new Scene(vbox,stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Streamify - Register");

        stage.show();
    }

    private void handleRegister(Stage stage, String username, String password, ObservableList<String> interests, Label error, UserManager userManager, MediaManager mediaManager){
        error.setVisible(false);
        if(username.length()==0){
            error.setText("Error: Please Input Username");
            error.setVisible(true);
            return;
        }
        if(password.length()==0){
            error.setText("Error: Please Input Password");
            error.setVisible(true);
            return;
        }
        if(interests.size()==0){
            error.setText("Error: Please Choose At Least One Interest");
            error.setVisible(true);
            return;
        }

        try{
            userManager.Register(username,password,interests);
            error.setText("Success: Registered Successfully! Please Login");
            error.setVisible(true);
            displayLoginScene(stage,userManager,mediaManager);
        }
        catch(Exception exception){
            error.setText("Error:" + exception.getMessage());
            error.setVisible(true);
        }

    }

    private <T extends backend.Media> ContextMenu createContextMenu(Boolean removeOption, T MediaItem,Stage stage,VBox ImageContainer,FlowPane flow,MediaManager mediaManager){

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setStyle("-fx-text-fill: rgb(255,98,145);");

        Boolean containsItem = checkWatchListContains(loggedInUser.getWatchlist(),MediaItem);

        MenuItem watchlist = new MenuItem(containsItem ? "Remove From Watchlist" :"Add To Watchlist");
        watchlist.setOnAction(e->{
            if(checkWatchListContains(loggedInUser.getWatchlist(),MediaItem)){
                loggedInUser.removeFromWatchlist(MediaItem);
                watchlist.setText("Add To Watchlist");
                if(stage.getTitle().contains("Watchlist")){
                    flow.getChildren().remove(ImageContainer);
                }
            }
            else{
                loggedInUser.addToWatchlist(MediaItem);
                watchlist.setText("Remove From Watchlist");
            }
        });

        contextMenu.getItems().add(watchlist);

        if(removeOption && !stage.getTitle().contains("Watchlist")){
            MenuItem removeItem = new MenuItem("Remove Item");
            removeItem.setOnAction(e->{
                if(MediaItem instanceof Video){
                    mediaManager.removeMovie((Video )MediaItem);
                }
                else{
                    mediaManager.removeSong((Audio) MediaItem);
                }
                flow.getChildren().remove(ImageContainer);
            });
            contextMenu.getItems().add(removeItem);
        }


        return contextMenu;
    }

    public static Boolean checkWatchListContains(ArrayList<backend.Media> list,backend.Media item){
        for(backend.Media m: list){
            if(m.getName().equalsIgnoreCase(item.getName())){
                return true;
            }
        }
        return false;
    }


    private BorderPane createNavbar(Stage stage,MediaManager mediaManager,UserManager userManager){
       BorderPane navCont = new BorderPane();
       navCont.setPadding(new Insets(8));

       Text heading = new Text("Streamify");
       heading.setFill(Color.WHITE);
       heading.setFont(Font.loadFont("file:montserrat.ttf",35));

       HBox rightCont = new HBox();
       rightCont.setSpacing(5);

       Button home = new Button("Home");
       home.setFont(Font.loadFont("file:montserrat.ttf",21));
       home.setStyle("-fx-text-fill: rgb(255,98,145); -fx-border-color: white; -fx-cursor: hand; -fx-background-color:transparent; -fx-border-color:transparent");
       home.setOnAction(e->{
           displayHomeScene(stage,mediaManager,userManager);
       });

        Button movies = new Button("Movies");
        movies.setFont(Font.loadFont("file:montserrat.ttf",21));
        movies.setStyle("-fx-text-fill: rgb(255,98,145); -fx-border-color: white; -fx-cursor: hand; -fx-background-color:transparent; -fx-border-color:transparent");
        movies.setOnAction(e->{
            displayCategoryScene(stage,mediaManager,userManager);
        });

        Button songs = new Button("Songs");
        songs.setFont(Font.loadFont("file:montserrat.ttf",21));
        songs.setStyle("-fx-text-fill: rgb(255,98,145); -fx-border-color: white; -fx-cursor: hand; -fx-background-color:transparent; -fx-border-color:transparent");
        songs.setOnAction(e->{
            displaySongsCategoryScene(stage,mediaManager,userManager);
        });


        Button watchlist = new Button("Watchlist");
        watchlist.setFont(Font.loadFont("file:montserrat.ttf",21));
        watchlist.setStyle("-fx-text-fill: rgb(255,98,145); -fx-border-color: white; -fx-cursor: hand; -fx-background-color:transparent; -fx-border-color:transparent");
        watchlist.setOnAction(e->{
            displayWatchlistScene(stage,mediaManager,userManager);
        });


        Button logOut = new Button("Log Out");
        logOut.setFont(Font.loadFont("file:montserrat.ttf",21));
        logOut.setStyle("-fx-text-fill: rgb(255,98,145); -fx-border-color: white; -fx-cursor: hand; -fx-background-color:transparent; -fx-border-color:transparent");
        logOut.setOnAction(e->{
            displayLoginScene(stage,userManager,mediaManager);
        });

        rightCont.getChildren().addAll(home,movies,songs,watchlist,logOut);

        navCont.setLeft(heading);
        navCont.setRight(rightCont);

        return navCont;
    }

    private void displayHomeScene(Stage stage,MediaManager mediaManager,UserManager userManager){
        ScrollPane scrollPane = new ScrollPane();

        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(36,23,45), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(2));
        vbox.setMinHeight(720);

        scrollPane.setPrefViewportHeight(720);
        scrollPane.setPrefViewportWidth(1080);
        scrollPane.setContent(vbox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);

        BorderPane navbar = createNavbar(stage,mediaManager,userManager);

        HBox headingCont = new HBox();
        headingCont.setAlignment(Pos.CENTER);
        headingCont.setPadding(new Insets(1));


        Text heading = new Text("Welcome Back, "+loggedInUser.getName());
        heading.setFont(Font.loadFont("file:montserrat.ttf",45));
        heading.setFill(Color.WHITE);
        heading.setTextAlignment(TextAlignment.CENTER);

        headingCont.getChildren().addAll(heading);


        vbox.getChildren().addAll(navbar,headingCont);

        VBox mainCont = new VBox();
        mainCont.setSpacing(10);
        mainCont.setAlignment(Pos.TOP_CENTER);

        vbox.getChildren().add(mainCont);


        Scene scene = new Scene(scrollPane,stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Streamify - Home");

        stage.show();

        System.out.println("loc");
        displayLocalTab(mediaManager,stage,mainCont);
        displayLocalSongsTab(mediaManager,stage,mainCont);
        displayTab(mediaManager,stage,mainCont,"Interests");
        displayTab(mediaManager,stage,mainCont,"Rating");
        displaySongsTab(mediaManager,stage,mainCont,"Interests");
        displaySongsTab(mediaManager,stage,mainCont,"Rating");
    }

    private void displayWatchlistScene(Stage stage,MediaManager mediaManager,UserManager userManager){
        ScrollPane scrollPane = new ScrollPane();

        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(36,23,45), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(2));
        vbox.setMinHeight(stage.getHeight());

        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                vbox.setMinHeight(stage.getHeight());
            }
        });

        scrollPane.setPrefViewportHeight(720);
        scrollPane.setPrefViewportWidth(1080);
        scrollPane.setContent(vbox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);

        BorderPane navbar = createNavbar(stage,mediaManager,userManager);

        HBox headingCont = new HBox();
        headingCont.setAlignment(Pos.CENTER);
        headingCont.setPadding(new Insets(1));


        Text heading = new Text(loggedInUser.getName()+"'s Watchlist");
        heading.setFont(Font.loadFont("file:montserrat.ttf",45));
        heading.setFill(Color.WHITE);
        heading.setTextAlignment(TextAlignment.CENTER);

        headingCont.getChildren().add(heading);

        vbox.getChildren().addAll(navbar,headingCont);

        VBox mainCont = new VBox();
        mainCont.setSpacing(10);
        mainCont.setAlignment(Pos.TOP_CENTER);

        vbox.getChildren().add(mainCont);


        Scene scene = new Scene(scrollPane,stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Streamify - Watchlist");

        stage.show();


        displayWatchlistTab(stage,mainCont,mediaManager);

    }



    private void displayCategoryScene(Stage stage,MediaManager mediaManager,UserManager userManager){
        ScrollPane scrollPane = new ScrollPane();

        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(36,23,45), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(2));
        vbox.setMinHeight(720);

        scrollPane.setPrefViewportHeight(720);
        scrollPane.setPrefViewportWidth(1080);
        scrollPane.setContent(vbox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);

        BorderPane navbar = createNavbar(stage,mediaManager,userManager);

        HBox headingCont = new HBox();
        headingCont.setAlignment(Pos.CENTER);
        headingCont.setPadding(new Insets(1));


        Text heading = new Text("Movies - Genres");
        heading.setFont(Font.loadFont("file:montserrat.ttf",45));
        heading.setFill(Color.WHITE);
        heading.setTextAlignment(TextAlignment.CENTER);

        headingCont.getChildren().add(heading);

        HBox topbar = new HBox();
        topbar.setAlignment(Pos.CENTER);
        topbar.setSpacing(10);

        TextField textField = new TextField();
        textField.setPromptText("Search Movies");
        textField.setAlignment(Pos.TOP_CENTER);
        textField.setMaxWidth(1000);
        textField.setMinWidth(700);
        textField.setFont(Font.loadFont("file:montserrat.ttf",20));
        textField.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ;");

        Image buttonImage = new Image("file:searchIcon.png",true);

        ImageView buttonImageView = new ImageView(buttonImage);
        buttonImageView.setPreserveRatio(true);
        buttonImageView.setCache(true);
        buttonImageView.setFitWidth(30);
        buttonImageView.setFitHeight(30);
        buttonImageView.setStyle("-fx-cursor: hand;");



        topbar.getChildren().addAll(textField,buttonImageView);


        vbox.getChildren().addAll(navbar,headingCont,topbar);

        VBox mainCont = new VBox();
        mainCont.setSpacing(10);
        mainCont.setAlignment(Pos.TOP_CENTER);

        vbox.getChildren().add(mainCont);


        Scene scene = new Scene(scrollPane,stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Streamify - Movie Genres");

        stage.show();


        VBox action = displayTab(mediaManager,stage,mainCont,"Action");
        VBox horror = displayTab(mediaManager,stage,mainCont,"Horror");
        VBox scifi = displayTab(mediaManager,stage,mainCont,"Sci-fi");
        VBox thriller = displayTab(mediaManager,stage,mainCont,"Thriller");
        VBox crime = displayTab(mediaManager,stage,mainCont,"Crime");



        textField.textProperty().addListener((observable, oldValue, newValue) -> {
           if(newValue==""){
               mainCont.getChildren().clear();

               mainCont.getChildren().addAll(action,horror,scifi,thriller,crime);

           }
        });

        buttonImageView.setOnMouseClicked(e->{
            mainCont.getChildren().clear();

            displaySearchTab(mediaManager,stage,mainCont,textField.getText());
        });

        textField.setOnAction(e->{
            mainCont.getChildren().clear();

            displaySearchTab(mediaManager,stage,mainCont,textField.getText());

        });


    }

    private void displaySongsCategoryScene(Stage stage,MediaManager mediaManager,UserManager userManager){
        ScrollPane scrollPane = new ScrollPane();

        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(36,23,45), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(2));
        vbox.setMinHeight(720);

        scrollPane.setPrefViewportHeight(720);
        scrollPane.setPrefViewportWidth(1080);
        scrollPane.setContent(vbox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);

        BorderPane navbar = createNavbar(stage,mediaManager,userManager);



        HBox headingCont = new HBox();
        headingCont.setAlignment(Pos.CENTER);
        headingCont.setPadding(new Insets(1));


        Text heading = new Text("Songs - Genres");
        heading.setFont(Font.loadFont("file:montserrat.ttf",45));
        heading.setFill(Color.WHITE);
        heading.setTextAlignment(TextAlignment.CENTER);

        headingCont.getChildren().add(heading);

        HBox topbar = new HBox();
        topbar.setAlignment(Pos.CENTER);
        topbar.setSpacing(10);

        TextField textField = new TextField();
        textField.setPromptText("Search Songs");
        textField.setAlignment(Pos.TOP_CENTER);
        textField.setMaxWidth(1000);
        textField.setMinWidth(700);
        textField.setFont(Font.loadFont("file:montserrat.ttf",20));
        textField.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ;");

        Image buttonImage = new Image("file:searchIcon.png",true);

        ImageView buttonImageView = new ImageView(buttonImage);
        buttonImageView.setPreserveRatio(true);
        buttonImageView.setCache(true);
        buttonImageView.setFitWidth(30);
        buttonImageView.setFitHeight(30);
        buttonImageView.setStyle("-fx-cursor: hand;");



        topbar.getChildren().addAll(textField,buttonImageView);


        vbox.getChildren().addAll(navbar,headingCont,topbar);

        VBox mainCont = new VBox();
        mainCont.setSpacing(10);
        mainCont.setAlignment(Pos.TOP_CENTER);

        vbox.getChildren().add(mainCont);


        Scene scene = new Scene(scrollPane,stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Streamify - Song Genres");

        stage.show();


        VBox pop = displaySongsTab(mediaManager,stage,mainCont,"Pop");
        VBox rock = displaySongsTab(mediaManager,stage,mainCont,"Rock");
        VBox hiphop = displaySongsTab(mediaManager,stage,mainCont,"Hip-hop");
        VBox electronic = displaySongsTab(mediaManager,stage,mainCont,"Electronic");


        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue==""){
                mainCont.getChildren().clear();

                mainCont.getChildren().addAll(pop,rock,hiphop,electronic);

            }
        });

        buttonImageView.setOnMouseClicked(e->{
            mainCont.getChildren().clear();

            displaySongsSearchTab(mediaManager,stage,mainCont,textField.getText());
        });

        textField.setOnAction(e->{
            mainCont.getChildren().clear();

            displaySongsSearchTab(mediaManager,stage,mainCont,textField.getText());

        });


    }

    private void displayLocalTab(MediaManager mediaManager,Stage stage,VBox vbox){
        VBox vbox1 = new VBox();
        vbox1.setAlignment(Pos.TOP_LEFT);
        vbox1.setSpacing(5);

        Label label = new Label("Local Movies");
        label.setFont(Font.loadFont("file:montserrat.ttf",30));
        label.setStyle("-fx-text-fill: white");
        label.setAlignment(Pos.TOP_LEFT);
        label.setPadding(new Insets(10));

        vbox1.getChildren().add(label);

        ArrayList<Video> filteredMovies = mediaManager.getLocalMovies();
        System.out.println(filteredMovies+"f");

        displayMovieItems(filteredMovies,vbox1,mediaManager,stage,true);
        System.out.println("after displaying movie");

        vbox.getChildren().addAll(vbox1);
    }

    private void displayLocalSongsTab(MediaManager mediaManager,Stage stage,VBox vbox){
        VBox vbox1 = new VBox();
        vbox1.setAlignment(Pos.TOP_LEFT);
        vbox1.setSpacing(5);

        Label label = new Label("Local Songs");
        label.setFont(Font.loadFont("file:montserrat.ttf",30));
        label.setStyle("-fx-text-fill: white");
        label.setAlignment(Pos.TOP_LEFT);
        label.setPadding(new Insets(10));

        vbox1.getChildren().add(label);

        ArrayList<Audio> filteredSongs = mediaManager.getLocalSongs();

        displaySongItems(filteredSongs,vbox1,mediaManager,stage,true);

        vbox.getChildren().addAll(vbox1);
    }


    private void displaySearchTab(MediaManager mediaManager,Stage stage,VBox vbox,String searchItem){

        VBox vbox1 = new VBox();
        vbox1.setAlignment(Pos.TOP_LEFT);
        vbox1.setSpacing(5);
        vbox1.setMinHeight(stage.getHeight());

        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                vbox.setMinHeight(stage.getHeight());
            }
        });

        Label label = new Label(String.format("Search Results For %s:",searchItem));
        label.setFont(Font.loadFont("file:montserrat.ttf",30));
        label.setStyle("-fx-text-fill: white");
        label.setAlignment(Pos.TOP_LEFT);
        label.setPadding(new Insets(10));

        vbox1.getChildren().add(label);

        ArrayList<Video> filteredMovies = mediaManager.searchMovies(searchItem);

        displayMovieItems(filteredMovies,vbox1,mediaManager,stage,false);

        vbox.getChildren().addAll(vbox1);
    }

    private void displaySongsSearchTab(MediaManager mediaManager,Stage stage,VBox vbox,String searchItem){

        VBox vbox1 = new VBox();
        vbox1.setAlignment(Pos.TOP_LEFT);
        vbox1.setSpacing(5);
        vbox1.setMinHeight(stage.getHeight());

        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                vbox.setMinHeight(stage.getHeight());
            }
        });


        Label label = new Label(String.format("Search Results For %s:",searchItem));
        label.setFont(Font.loadFont("file:montserrat.ttf",30));
        label.setStyle("-fx-text-fill: white");
        label.setAlignment(Pos.TOP_LEFT);
        label.setPadding(new Insets(10));

        vbox1.getChildren().add(label);

        ArrayList<Audio> filteredSongs = mediaManager.searchSongs(searchItem);
        System.out.println(filteredSongs);

        displaySongItems(filteredSongs,vbox1,mediaManager,stage,false);

        vbox.getChildren().addAll(vbox1);
    }

    private VBox displaySongsTab(MediaManager mediaManager,Stage stage,VBox vbox,String genreType){
        VBox vbox1 = new VBox();
        vbox1.setAlignment(Pos.TOP_LEFT);
        vbox1.setSpacing(5);

        Label label = new Label(genreType=="Rating"? "Highly Rated Songs" : genreType== "Interests" ? "Songs Recommanded For You": genreType );
        label.setFont(Font.loadFont("file:montserrat.ttf",30));
        label.setStyle("-fx-text-fill: white");
        label.setAlignment(Pos.TOP_LEFT);
        label.setPadding(new Insets(10));

        vbox1.getChildren().add(label);

        ArrayList<Audio> filteredSongs;


        switch(genreType) {
            case "Rating":
                filteredSongs = mediaManager.sortSongsByRating();
                break;
            case "Interests":
                filteredSongs = mediaManager.getSongsByInterests(loggedInUser.getInterests());
                break;
            default:
                filteredSongs = mediaManager.filterSongsByGenre(genreType);
        }

        if(filteredSongs.size()==0){
            return vbox1;
        }


        displaySongItems(filteredSongs,vbox1,mediaManager,stage,false);


        vbox.getChildren().addAll(vbox1);

        return vbox1;

    }

    private VBox displayWatchlistTab(Stage stage,VBox vbox,MediaManager mediaManager){
        VBox vbox1 = new VBox();
        vbox1.setAlignment(Pos.TOP_LEFT);
        vbox1.setSpacing(5);

        ArrayList<backend.Media> filteredMedia = loggedInUser.getWatchlist();

       displayMediaItems(filteredMedia,vbox1,stage,mediaManager);


        vbox.getChildren().add(vbox1);

        return vbox1;
    }

    private void displayMediaItems(ArrayList<backend.Media> filteredMedia,VBox vbox,Stage stage,MediaManager mediaManager){


        FlowPane flow = new FlowPane();
        flow.setPrefWrapLength(1280);
        flow.setVgap(8);
        flow.setHgap(4);


        for(backend.Media media:filteredMedia){
            if(media instanceof Video){
                displayMovieItem(flow,(Video) media,stage,media.getLocation()==Location.LOCAL ? true : false,mediaManager);
            }
            else{
                displaySongItem(flow,(Audio) media,stage,media.getLocation()==Location.LOCAL ? true : false,mediaManager);
            }
        }

        vbox.getChildren().add(flow);


    }

    private VBox displayTab(MediaManager mediaManager,Stage stage,VBox vbox,String genreType){
        VBox vbox1 = new VBox();
        vbox1.setAlignment(Pos.TOP_LEFT);
        vbox1.setSpacing(5);

            Label label = new Label(genreType=="Rating"? "Highly Rated Movies" : genreType== "Interests" ? "Recommanded For You": genreType );
            label.setFont(Font.loadFont("file:montserrat.ttf",30));
            label.setStyle("-fx-text-fill: white");
            label.setAlignment(Pos.TOP_LEFT);
            label.setPadding(new Insets(10));


        ArrayList<Video> filteredMovies;

        switch(genreType) {
            case "Rating":
                filteredMovies = mediaManager.sortMoviesByRating();
                break;
            case "Interests":
                filteredMovies = mediaManager.getMoviesByInterests(loggedInUser.getInterests());
                break;
            default:
                filteredMovies = mediaManager.filterMoviesByGenre(genreType);
        }

        if(filteredMovies.size()==0){
            return vbox1;
        }

        vbox1.getChildren().add(label);


        displayMovieItems(filteredMovies,vbox1,mediaManager,stage,false);


        vbox.getChildren().addAll(vbox1);

        return vbox1;

    }

    private VBox createFilePicker(MediaManager mediaManager,Stage stage){
        VBox ImageContainer = new VBox();
        ImageContainer.setAlignment(Pos.CENTER);
        ImageContainer.setSpacing(5);
        ImageContainer.setPadding(new Insets(5));
        ImageContainer.styleProperty().bind(Bindings.when(ImageContainer.hoverProperty())
                .then("-fx-padding: 10;"
                        + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;"
                        + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;"
                        + "-fx-background-insets: 5;"
                        + "-fx-border-color: rgb(255,255,255);"
                        + "-fx-background-color: rgb(217,86,125);"
                        + "-fx-border-style: segments(10, 15, 15, 15)  line-cap round;"
                        + "-fx-background-radius:5;"
                        + "-fx-cursor: hand;")
                .otherwise("-fx-padding: 10;"
                        + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;"
                        + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;"
                        + "-fx-border-color: rgb(255,98,145);"
                        + "-fx-border-style: segments(10, 15, 15, 15)  line-cap round;"
                        + "-fx-cursor: hand;"));
        ImageContainer.setMinWidth(270);
        ImageContainer.setMinHeight(400);

        Text plusIcon = new Text("+");
        plusIcon.setFill(Color.WHITE);
        plusIcon.setFont(Font.loadFont("file:montserrat.ttf",70));

        Text addText = new Text("Add Media");
        addText.setFill(Color.WHITE);
        addText.setFont(Font.loadFont("file:montserrat.ttf",25));

        ImageContainer.setOnMouseClicked(e->{
            try {
                File file = chooseFile(stage,false);
                if(file==null) return;

                Media media = new Media(file.toURI().toString());

                MediaPlayer mediaPlayer = new MediaPlayer(media);

                LocalDate currentDate = LocalDate.now();

                mediaPlayer.setOnReady(new Runnable() {
                    @Override
                    public void run() {
                        int duration = (int) media.getDuration().toMinutes();
                        Video video = new Video(file.getName(),new Date(currentDate.getDayOfMonth(),currentDate.getMonthValue(),currentDate.getYear()),Location.LOCAL,"",duration,"",file.length(),false,90000,new AspectRatio(16,9),30,3500000,"h264",new Resolution(media.getWidth(),media.getHeight()),new ArrayList<Artist>(),"",0,file.getAbsolutePath());
                        mediaManager.addMovie(video);
                        localItemsContainer.getChildren().remove(localFilePicker);
                        displayMovieItem(localItemsContainer,video,stage,true,mediaManager);
                        VBox fileChooser = createFilePicker(mediaManager,stage);
                        localItemsContainer.getChildren().add(fileChooser);
                        localFilePicker = fileChooser;
                    }
                });


            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        });

        ImageContainer.getChildren().addAll(plusIcon,addText);

        return ImageContainer;

    }

    private VBox createAudioFilePicker(MediaManager mediaManager,Stage stage){
        VBox ImageContainer = new VBox();
        ImageContainer.setAlignment(Pos.CENTER);
        ImageContainer.setSpacing(5);
        ImageContainer.setPadding(new Insets(5));
        ImageContainer.styleProperty().bind(Bindings.when(ImageContainer.hoverProperty())
                .then("-fx-padding: 10;"
                        + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;"
                        + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;"
                        + "-fx-background-insets: 5;"
                        + "-fx-border-color: rgb(255,255,255);"
                        + "-fx-background-color: rgb(217,86,125);"
                        + "-fx-border-style: segments(10, 15, 15, 15)  line-cap round;"
                        + "-fx-background-radius:5;"
                        + "-fx-cursor: hand;")
                .otherwise("-fx-padding: 10;"
                        + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;"
                        + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;"
                        + "-fx-border-color: rgb(255,98,145);"
                        + "-fx-border-style: segments(10, 15, 15, 15)  line-cap round;"
                        + "-fx-cursor: hand;"));
        ImageContainer.setMinWidth(270);
        ImageContainer.setMinHeight(400);

        Text plusIcon = new Text("+");
        plusIcon.setFill(Color.WHITE);
        plusIcon.setFont(Font.loadFont("file:montserrat.ttf",70));

        Text addText = new Text("Add Media");
        addText.setFill(Color.WHITE);
        addText.setFont(Font.loadFont("file:montserrat.ttf",25));

        ImageContainer.setOnMouseClicked(e->{
            try {
                File file = chooseFile(stage,true);
                if(file==null) return;

                Media media = new Media(file.toURI().toString());

                MediaPlayer mediaPlayer = new MediaPlayer(media);

                LocalDate currentDate = LocalDate.now();

                mediaPlayer.setOnReady(new Runnable() {
                    @Override
                    public void run() {
                        int duration = (int) media.getDuration().toMinutes();
                        Audio audio = new Audio(file.getName(),new Date(currentDate.getDayOfMonth(),currentDate.getMonthValue(),currentDate.getYear()),Location.LOCAL,duration,"",file.length(),false,90000,30,"h264",new ArrayList<Artist>(),"",0,0,file.getAbsolutePath());
                        mediaManager.addSong(audio);
                        localAudioItemsContainer.getChildren().remove(localAudioFilePicker);
                        displaySongItem(localAudioItemsContainer,audio,stage,true,mediaManager);
                        VBox fileChooser = createAudioFilePicker(mediaManager,stage);
                        localAudioItemsContainer.getChildren().add(fileChooser);
                        localAudioFilePicker = fileChooser;
                    }
                });


            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        });

        ImageContainer.getChildren().addAll(plusIcon,addText);

        return ImageContainer;

    }

    private void displayMovieItems(ArrayList<Video> filteredMovies,VBox vbox,MediaManager mediaManager,Stage stage,Boolean filePicker){


        FlowPane flow = new FlowPane();
        flow.setPrefWrapLength(1280);
        flow.setVgap(8);
        flow.setHgap(4);


        System.out.println(filteredMovies.size()+"displaying");
        for(Video movie:filteredMovies){
            displayMovieItem(flow,movie,stage,filePicker,mediaManager);
        }

        System.out.println("displayed");

        if(filePicker){
            VBox fileChooser = createFilePicker(mediaManager,stage);
            flow.getChildren().add(fileChooser);
            localItemsContainer = flow;
            localFilePicker = fileChooser;
        }
        vbox.getChildren().add(flow);


    }

    private void displaySongItems(ArrayList<Audio> filteredMovies,VBox vbox,MediaManager mediaManager,Stage stage,Boolean filePicker){
        FlowPane flow = new FlowPane();
        flow.setPrefWrapLength(1280);
        flow.setVgap(8);
        flow.setHgap(4);


        for(Audio song:filteredMovies){
            displaySongItem(flow,song,stage,filePicker,mediaManager);
        }

        if(filePicker){
            VBox fileChooser = createAudioFilePicker(mediaManager,stage);
            flow.getChildren().add(fileChooser);
            localAudioItemsContainer = flow;
            localAudioFilePicker = fileChooser;
        }
        vbox.getChildren().add(flow);


    }

    private void displaySongItem(FlowPane flow,Audio song,Stage stage,Boolean filePicker,MediaManager mediaManager){
        VBox ImageContainer = new VBox();
        ImageContainer.setAlignment(Pos.CENTER);
        ImageContainer.setSpacing(10);
        ImageContainer.setPadding(new Insets(5));
        ImageContainer.styleProperty().bind(Bindings.when(ImageContainer.hoverProperty())
                .then("-fx-padding: 10;"
                        + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;"
                        + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;"
                        + "-fx-background-insets: 5;"
                        + "-fx-border-color: rgb(255,255,255);"
                        + "-fx-background-color: rgb(217,86,125);"
                        + "-fx-background-radius:5;"
                        + "-fx-cursor: hand;")
                .otherwise("-fx-padding: 10;"
                        + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;"
                        + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;"
                        + "-fx-border-color: rgb(255,98,145);"
                        + "-fx-cursor: hand;"));

        ContextMenu contextMenu = createContextMenu(filePicker,song,stage,ImageContainer,flow,mediaManager);
        ImageContainer.setOnContextMenuRequested(e -> {
            contextMenu.show(ImageContainer.getScene().getWindow(), e.getScreenX(), e.getScreenY());
        });

        ImageContainer.setMaxHeight(400);
        ImageContainer.setMaxWidth(320);


        if(song.getPosterLink()==""){
            ImageContainer.setMinWidth(320);
            ImageContainer.setMaxWidth(320);
            ImageContainer.setMinHeight(400);
            ImageContainer.setMaxHeight(400);

            Image image = new Image("file:musicPlaceholder.png");



            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(210);
            imageView.setFitWidth(210);
            imageView.setCache(true);
            imageView.setPreserveRatio(true);

            imageView.setOnMouseClicked(e->{
                try {
                    displayPlayer(new File(song.getPlayableLink()),stage,song);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            ImageContainer.getChildren().add(imageView);

        }
        else{
            Image image = new Image("file:"+song.getPosterLink());

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(320);
            imageView.setFitHeight(400);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            imageView.setOnMouseClicked(e->{
                try {
                    displayPlayer(new File(song.getPlayableLink()),stage,song);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            ImageContainer.getChildren().add(imageView);
        }



        Label name = new Label(song.getName());
        name.setFont(Font.loadFont("file:montserrat.ttf",17));
        name.setAlignment(Pos.TOP_LEFT);
        name.setStyle("-fx-text-fill: white");
        name.setWrapText(true);

        BorderPane bottomCont = new BorderPane();


        HBox tags = new HBox();
        tags.setAlignment(Pos.TOP_LEFT);
        tags.setSpacing(10);

        Label releaseYear = new Label(String.format("%d",song.getReleaseDate().getYear()));
        releaseYear.setPadding(new Insets(4));
        releaseYear.setStyle("-fx-text-fill: white");

        Label duration = new Label(String.format("%d Mins",song.getDuration()));
        duration.setPadding(new Insets(4));
        duration.setStyle("-fx-text-fill: white");

        Label quality = new Label(song.getCodec());
        quality.setPadding(new Insets(4));
        quality.setStyle("-fx-text-fill: white");


        tags.getChildren().addAll(releaseYear,duration,quality);


        if(!filePicker){
            HBox ratingCont = new HBox();
            ratingCont.setAlignment(Pos.CENTER);
            ratingCont.setSpacing(1);

            Label rating = new Label(String.format("%d",song.getRating()));
            rating.setPadding(new Insets(4));
            rating.setStyle("-fx-text-fill: white");

            Image stars = new Image("file:starsIcon.png");

            ImageView starsView = new ImageView(stars);
            starsView.setCache(true);
            starsView.setFitWidth(12);
            starsView.setFitHeight(12);
            starsView.setPreserveRatio(true);

            ratingCont.getChildren().addAll(rating,starsView);
            bottomCont.setRight(ratingCont);

        }
        else{
            HBox sizeCont = new HBox();
            sizeCont.setAlignment(Pos.CENTER);
            sizeCont.setSpacing(1);

            Label size = new Label(formatBytes(song.getSize(),2));
            size.setPadding(new Insets(4));
            size.setStyle("-fx-text-fill: white");


            sizeCont.getChildren().add(size);
            bottomCont.setRight(sizeCont);
        }

        bottomCont.setLeft(tags);


        ImageContainer.getChildren().addAll(name,bottomCont);
        flow.getChildren().add(ImageContainer);
    }




    private void displayMovieItem(FlowPane flow,Video movie,Stage stage,Boolean filePicker,MediaManager mediaManager){
        System.out.println("in displaying movie");
        VBox ImageContainer = new VBox();
        ImageContainer.setAlignment(Pos.CENTER);
        ImageContainer.setSpacing(10);
        ImageContainer.setPadding(new Insets(5));
        ImageContainer.styleProperty().bind(Bindings.when(ImageContainer.hoverProperty())
                .then("-fx-padding: 10;"
                        + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;"
                        + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;"
                        + "-fx-background-insets: 5;"
                        + "-fx-border-color: rgb(255,255,255);"
                        + "-fx-background-color: rgb(217,86,125);"
                        + "-fx-background-radius:5;"
                        + "-fx-cursor: hand;")
                .otherwise("-fx-padding: 10;"
                        + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;"
                        + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;"
                        + "-fx-border-color: rgb(255,98,145);"
                        + "-fx-cursor: hand;"));

        System.out.println("ctx menu");

        ContextMenu contextMenu = createContextMenu(filePicker,movie,stage,ImageContainer,flow,mediaManager);
        System.out.println(contextMenu+"ctx");
        ImageContainer.setOnContextMenuRequested(e -> {
            contextMenu.show(ImageContainer.getScene().getWindow(), e.getScreenX(), e.getScreenY());
        });

        System.out.println("after ctx menu");

        ImageContainer.setMaxHeight(400);
        ImageContainer.setMaxWidth(320);

        System.out.println("after image cont");


        if(movie.getPosterLink()==""){
            ImageContainer.setMinWidth(320);
            ImageContainer.setMaxWidth(320);
            ImageContainer.setMinHeight(400);
            ImageContainer.setMaxHeight(400);

            Image image = new Image("file:videoPlaceholder.png");



            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(210);
            imageView.setFitWidth(210);
            imageView.setCache(true);
            imageView.setPreserveRatio(true);

            imageView.setOnMouseClicked(e->{
                try {
                    displayPlayer(new File(movie.getPlayableLink()),stage,movie);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            ImageContainer.getChildren().add(imageView);

        }
        else{
            Image image = new Image("file:"+movie.getPosterLink());

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(320);
            imageView.setFitHeight(400);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            imageView.setOnMouseClicked(e->{
                try {
                    displayPlayer(movie.getPlayableLink(),stage,movie.getName(),movie);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            ImageContainer.getChildren().add(imageView);
        }


        System.out.println("after poster");


        Label name = new Label(movie.getName());
        name.setFont(Font.loadFont("file:montserrat.ttf",17));
        name.setAlignment(Pos.TOP_LEFT);
        name.setStyle("-fx-text-fill: white");
        name.setWrapText(true);

        System.out.println("after name");

        BorderPane bottomCont = new BorderPane();


        HBox tags = new HBox();
        tags.setAlignment(Pos.TOP_LEFT);
        tags.setSpacing(10);

        Label releaseYear = new Label(String.format("%d",movie.getReleaseDate().getYear()));
        releaseYear.setPadding(new Insets(4));
        releaseYear.setStyle("-fx-text-fill: white");

        Label duration = new Label(convertDuration(movie.getDuration()));
        duration.setPadding(new Insets(4));
        duration.setStyle("-fx-text-fill: white");

        Label quality = new Label(String.format("%dp",movie.getResolution().getHeight()));
        quality.setPadding(new Insets(4));
        quality.setStyle("-fx-text-fill: white");


        tags.getChildren().addAll(releaseYear,duration,quality);


            if(!filePicker){
                HBox ratingCont = new HBox();
                ratingCont.setAlignment(Pos.CENTER);
                ratingCont.setSpacing(1);

                Label rating = new Label(String.format("%d",movie.getRating()));
                rating.setPadding(new Insets(4));
                rating.setStyle("-fx-text-fill: white");

                Image stars = new Image("file:starsIcon.png");

                ImageView starsView = new ImageView(stars);
                starsView.setCache(true);
                starsView.setFitWidth(12);
                starsView.setFitHeight(12);
                starsView.setPreserveRatio(true);

                ratingCont.getChildren().addAll(rating,starsView);
                bottomCont.setRight(ratingCont);

            }
            else{
                HBox sizeCont = new HBox();
                sizeCont.setAlignment(Pos.CENTER);
                sizeCont.setSpacing(1);

                Label size = new Label(formatBytes(movie.getSize(),2));
                size.setPadding(new Insets(4));
                size.setStyle("-fx-text-fill: white");


                sizeCont.getChildren().add(size);
                bottomCont.setRight(sizeCont);
            }

        bottomCont.setLeft(tags);


        ImageContainer.getChildren().addAll(name,bottomCont);
        flow.getChildren().add(ImageContainer);
    }

    public static String convertDuration(int Min){
        int hours   = Min / 60;
        int minutes = Min % 60;

        return String.format("%d hrs %d mins",hours,minutes);
    }


    public static String formatBytes(long bytes, int decimals) {
        if (bytes == 0) {
            return "0 Bytes";
        }

        int k = 1024;
        int dm = decimals < 0 ? 0 : decimals;
        String[] sizes = {"Bytes", "KB", "MB", "GB", "TB", "PB"};
        int i = (int) (Math.floor(Math.log(bytes) / Math.log(k)));

        return String.format("%." + dm + "f %s", bytes / Math.pow(k, i), sizes[i]);
    }

    private File chooseFile(Stage stage,Boolean isAudio) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Media File");
        if(isAudio){
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
        }
        else{
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Video Files", "*.mp4"));
        }
        File selectedFile = fileChooser.showOpenDialog(stage);

        return selectedFile;
    }

    private <T extends backend.Media> void displayPlayer(File file,Stage stage,T medi) throws MalformedURLException {
        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(36,23,45), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);
        vbox.setPadding(new Insets(2));


        Media media = new Media(file.toURI().toURL().toString());

        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();

        if(medi instanceof Audio){
            HBox imageCont = new HBox();
            imageCont.setAlignment(Pos.CENTER);

            Image image;
            if(medi.getPosterLink()==""){
                image = new Image("file:bigMusic.png");

            } else{
                image = new Image("file:"+medi.getPosterLink());
            }


            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(1280);
            imageView.setFitHeight(720);
            imageView.setPreserveRatio(true);
            imageView.setCache(true);
            imageView.setStyle("-fx-border-radius:5");


            imageCont.getChildren().add(imageView);
            vbox.getChildren().add(imageCont);
        }
        else{
            MediaView mediaView = new MediaView(mediaPlayer);
            mediaView.setFitHeight(0.6*stage.getHeight());
            mediaView.setFitWidth(stage.getWidth());
            mediaView.setPreserveRatio(true);

            vbox.getChildren().add(mediaView);

            stage.widthProperty().addListener((obs, oldVal, newVal) -> {
                mediaView.setFitWidth(stage.getWidth());
            });

            stage.heightProperty().addListener((obs, oldVal, newVal) -> {
                mediaView.setFitHeight(stage.getHeight());

            });
        }

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                BorderPane controls = createControls(mediaPlayer,stage);

                BorderPane durationCont = new BorderPane();
                durationCont.setPadding(new Insets(5));

                Text changingDuration = new Text("00:00:00");
                changingDuration.setFill(Color.WHITE);
                durationCont.setLeft(changingDuration);

                Text totalDuration = new Text(convertTime((int) mediaPlayer.getMedia().getDuration().toSeconds()));
                totalDuration.setFill(Color.WHITE);
                durationCont.setRight(totalDuration);


                Slider timeSlider = new Slider();
                HBox.setHgrow(timeSlider,Priority.ALWAYS);
                timeSlider.setMinWidth(50);
                timeSlider.setMaxWidth(Double.MAX_VALUE);
                timeSlider.setMin(0);
                timeSlider.setMax(mediaPlayer.getMedia().getDuration().toSeconds());
                timeSlider.setValue(0);
                timeSlider.setStyle("-fx-cursor:hand");

                timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if(timeSlider.isPressed()){
                            double val = timeSlider.getValue();
                            mediaPlayer.seek(new Duration(val * 1000));
                        }
                    }
                });

                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                        Duration d = mediaPlayer.getCurrentTime();
                        timeSlider.setValue(d.toSeconds());
                        changingDuration.setText(convertTime((int) d.toSeconds()));
                    }
                });

                vbox.getChildren().addAll(timeSlider,durationCont,controls);
            }
        });

        Scene scene = new Scene(vbox,stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Streamify - "+ file.getName());

        stage.show();
    }

    private <T extends backend.Media> void displayPlayer(String url,Stage stage,String name,T medi) throws MalformedURLException {
        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(36,23,45), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);

        Image spinner = new Image("file:spinner.gif");

        ImageView spinnerView = new ImageView(spinner);
        spinnerView.setFitWidth(stage.getWidth());
        spinnerView.setFitHeight(0.8*stage.getHeight());
        spinnerView.setPreserveRatio(true);
        spinnerView.setCache(true);

        vbox.getChildren().add(spinnerView);


        Media media = new Media(url);


        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();

        MediaView mediaViewCopy = null;

        if(medi instanceof Audio){
            HBox imageCont = new HBox();
            imageCont.setAlignment(Pos.CENTER);

            Image image;
            if(medi.getPosterLink()==""){
                image = new Image("file:bigMusic.png");

            } else{
                image = new Image("file:"+medi.getPosterLink());
            }

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(1280);
            imageView.setFitHeight(720);
            imageView.setPreserveRatio(true);
            imageView.setCache(true);


            imageCont.getChildren().add(imageView);
            vbox.getChildren().add(imageCont);
        }
        else{
            MediaView mediaView = new MediaView(mediaPlayer);
            mediaView.setFitHeight(0.8*stage.getHeight());
            mediaView.setFitWidth(stage.getWidth());
            mediaView.setPreserveRatio(true);


            stage.widthProperty().addListener((obs, oldVal, newVal) -> {
                mediaView.setFitWidth(stage.getWidth());
            });

            stage.heightProperty().addListener((obs, oldVal, newVal) -> {
                mediaView.setFitHeight(stage.getHeight());

            });

            mediaViewCopy = mediaView;
        }


        MediaView finalMediaViewCopy = mediaViewCopy;
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().remove(spinnerView);
                if(finalMediaViewCopy!=null){
                    vbox.getChildren().add(finalMediaViewCopy);
                }

                BorderPane controls = createControls(mediaPlayer,stage);

                BorderPane durationCont = new BorderPane();
                durationCont.setPadding(new Insets(5));

                Text changingDuration = new Text("00:00:00");
                changingDuration.setFill(Color.WHITE);
                durationCont.setLeft(changingDuration);

                Text totalDuration = new Text(convertTime((int) mediaPlayer.getMedia().getDuration().toSeconds()));
                totalDuration.setFill(Color.WHITE);
                durationCont.setRight(totalDuration);


                Slider timeSlider = new Slider();
                HBox.setHgrow(timeSlider,Priority.ALWAYS);
                timeSlider.setMinWidth(50);
                timeSlider.setMaxWidth(Double.MAX_VALUE);
                System.out.println(mediaPlayer.getMedia().getDuration().toSeconds());
                timeSlider.setMin(0);
                timeSlider.setMax(mediaPlayer.getMedia().getDuration().toSeconds());
                timeSlider.setValue(0);
                timeSlider.setStyle("-fx-cursor:hand");

                timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if(timeSlider.isPressed()){
                            double val = timeSlider.getValue();
                            mediaPlayer.seek(new Duration(val * 1000));
                        }
                    }
                });

                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                        Duration d = mediaPlayer.getCurrentTime();
                        timeSlider.setValue(d.toSeconds());
                        changingDuration.setText(convertTime((int) d.toSeconds()));
                    }
                });

                vbox.getChildren().addAll(timeSlider,durationCont,controls);
            }
        });




        Scene scene = new Scene(vbox,stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Streamify - "+ name);

        stage.show();
    }

    public static String convertTime(int totalSeconds) {
        if (totalSeconds < 0) {
            throw new IllegalArgumentException("Total seconds must be non-negative.");
        }

        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private BorderPane createControls(MediaPlayer mediaPlayer,Stage stage){

        BorderPane controlsCont = new BorderPane();

        Image BackImage = new Image("file:arrow.png");


        ImageView BackImageView = new ImageView(BackImage);
        BackImageView.setPreserveRatio(true);
        BackImageView.setCache(true);
        BackImageView.setFitWidth(60);
        BackImageView.setFitHeight(60);
        BackImageView.setStyle("-fx-cursor:hand");


        BackImageView.setOnMouseClicked(e -> {
            mediaPlayer.stop();
            displayHomeScene(stage,mediaManager,userManager);
        });

        controlsCont.setLeft(BackImageView);

        HBox hbox = new HBox();
        hbox.setSpacing(25);
        hbox.setAlignment(Pos.CENTER);


        Image backwardImg = new Image("file:backward.png");

        ImageView backwardImageView = new ImageView(backwardImg);
        backwardImageView.setPreserveRatio(true);
        backwardImageView.setCache(true);
        backwardImageView.setFitWidth(60);
        backwardImageView.setFitHeight(60);
        backwardImageView.setStyle("-fx-cursor:hand");


        backwardImageView.setOnMouseClicked(e -> {
            Duration currentDuration = mediaPlayer.getCurrentTime();
            Duration seekDuration = currentDuration.subtract(Duration.seconds(10));
            mediaPlayer.seek(seekDuration);
        });


        Image image = new Image("file:pause.png");

        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setCache(true);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        imageView.setStyle("-fx-cursor:hand");

        imageView.setOnMouseClicked(e->{
            MediaPlayer.Status status = mediaPlayer.getStatus();

            if (status == MediaPlayer.Status.UNKNOWN  || status == MediaPlayer.Status.HALTED)
            {
                return;
            }

            if ( status == MediaPlayer.Status.PAUSED
                    || status == MediaPlayer.Status.READY
                    || status == MediaPlayer.Status.STOPPED)
            {
                mediaPlayer.play();
                Image img = new Image("file:pause.png");
                imageView.setImage(img);
            } else {
                mediaPlayer.pause();
                Image img = new Image("file:play.png");
                imageView.setImage(img);

            }
        });

        Image forwardImg = new Image("file:forward.png");

        ImageView forwardImageView = new ImageView(forwardImg);
        forwardImageView.setPreserveRatio(true);
        forwardImageView.setCache(true);
        forwardImageView.setFitWidth(60);
        forwardImageView.setFitHeight(60);
        forwardImageView.setStyle("-fx-cursor:hand");


        forwardImageView.setOnMouseClicked(e -> {
            Duration currentDuration = mediaPlayer.getCurrentTime();
            Duration seekDuration = currentDuration.add(Duration.seconds(10));
            mediaPlayer.seek(seekDuration);
        });



        hbox.getChildren().addAll(backwardImageView,imageView,forwardImageView);
        controlsCont.setCenter(hbox);


        return controlsCont;
    }


}