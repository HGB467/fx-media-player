package com.example.demo;

import backend.UserManager;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import backend.User;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        UserManager userManager = new UserManager();

        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(25,187,212), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(25);

        Text text = new Text("Welcome To Streamify");
        text.setFont(new Font("Arial",100));
        text.setFill(Color.WHITE);


        Text text1 = new Text("Your Own Home Theater");
        text1.setFont(new Font("Arial",40));
        text1.setFill(Color.WHITE);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);

        Button button = new Button("Login");
        button.setFont(new Font("Arial",20));
        button.setBackground(new Background(new BackgroundFill(Color.rgb(86,162,203), CornerRadii.EMPTY, Insets.EMPTY)));
        button.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-cursor: hand;");
        button.setOnAction(e -> {
            displayLoginScene(stage,userManager);
        });


        Button button1 = new Button("Register");
        button1.setFont(new Font("Arial",20));
        button1.setBackground(new Background(new BackgroundFill(Color.rgb(86,162,203), CornerRadii.EMPTY, Insets.EMPTY)));
        button1.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-cursor: hand;");
        button1.setOnAction(e -> {
            displayRegisterScene(stage,userManager);
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

    private void displayLoginScene(Stage stage,UserManager userManager){
        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(25,187,212), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(70);

        Text text = new Text("Login");
        text.setFont(new Font("Arial",80));
        text.setFill(Color.WHITE);

        VBox loginContainer = new VBox();
        loginContainer.setAlignment(Pos.CENTER);
        loginContainer.setSpacing(25);

        VBox usernameContainer = new VBox();
        usernameContainer.setAlignment(Pos.CENTER);
        usernameContainer.setSpacing(10);

        Label usernameLabel = new Label("Username");
        usernameLabel.setAlignment(Pos.CENTER);
        usernameLabel.setFont(new Font("Arial",20));
        usernameLabel.setStyle("-fx-text-fill: white");

        TextField username = new TextField();
        username.setAlignment(Pos.CENTER);
        username.setMaxWidth(300);
        username.setFont(new Font("Arial",20));

        usernameContainer.getChildren().addAll(usernameLabel,username);

        VBox passwordContainer = new VBox();
        passwordContainer.setAlignment(Pos.CENTER);
        passwordContainer.setSpacing(6);

        Label passwordLabel = new Label("Password");
        passwordLabel.setAlignment(Pos.CENTER);
        passwordLabel.setFont(new Font("Arial",20));
        passwordLabel.setStyle("-fx-text-fill: white");

        PasswordField password = new PasswordField();
        password.setAlignment(Pos.CENTER);
        password.setMaxWidth(300);
        password.setFont(new Font("Arial",20));

        Button register = new Button("Don't Have An Account? Register");
        register.setFont(new Font("Arial",15));
        register.setStyle("-fx-text-fill: white; -fx-border-color: transparent;-fx-cursor: hand; -fx-background-color: transparent");
        register.setUnderline(true);
        register.setOnAction(e->{
            displayRegisterScene(stage,userManager);
        });


        passwordContainer.getChildren().addAll(passwordLabel,password,register);

        Label error = new Label();
        error.setFont(new Font("Arial",15));
        error.setBackground(new Background(new BackgroundFill(Color.rgb(86,162,203), CornerRadii.EMPTY, Insets.EMPTY)));
        error.setStyle("-fx-text-fill: white; -fx-border-color: white;");
        error.setPadding(new Insets(7));
        error.setVisible(false);

        Button button1 = new Button("Login");
        button1.setFont(new Font("Arial",20));
        button1.setBackground(new Background(new BackgroundFill(Color.rgb(86,162,203), CornerRadii.EMPTY, Insets.EMPTY)));
        button1.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-cursor: hand;");
        button1.setOnAction(e-> {
            handleLogin(stage,username.getText(),password.getText(),error,userManager);
        });


        loginContainer.getChildren().addAll(usernameContainer,passwordContainer,error,button1);

        vbox.getChildren().addAll(text,loginContainer);

        Scene scene = new Scene(vbox,1280,720);
        stage.setScene(scene);
        stage.setTitle("Streamify - Login");

        stage.show();
    }

    private void handleLogin(Stage stage,String username,String password,Label error,UserManager userManager){
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
            userManager.Login(username,password);
            error.setText("Success: Registered Successfully! Please Login");
            error.setVisible(true);
            displayMainScene(stage);
        }
        catch(Exception exception){
            error.setText("Error:" + exception.getMessage());
            error.setVisible(true);
        }
    }

    private void displayRegisterScene(Stage stage,UserManager userManager){
        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(25,187,212), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(70);

        Text text = new Text("Register");
        text.setFont(new Font("Arial",80));
        text.setFill(Color.WHITE);

        VBox registerContainer = new VBox();
        registerContainer.setAlignment(Pos.CENTER);
        registerContainer.setSpacing(25);

        VBox usernameContainer = new VBox();
        usernameContainer.setAlignment(Pos.CENTER);
        usernameContainer.setSpacing(10);

        Label usernameLabel = new Label("Username");
        usernameLabel.setAlignment(Pos.CENTER);
        usernameLabel.setFont(new Font("Arial",20));
        usernameLabel.setStyle("-fx-text-fill: white");

        TextField username = new TextField();
        username.setAlignment(Pos.CENTER);
        username.setMaxWidth(300);
        username.setFont(new Font("Arial",20));

        usernameContainer.getChildren().addAll(usernameLabel,username);

        VBox passwordContainer = new VBox();
        passwordContainer.setAlignment(Pos.CENTER);
        passwordContainer.setSpacing(6);

        Label passwordLabel = new Label("Password");
        passwordLabel.setAlignment(Pos.CENTER);
        passwordLabel.setFont(new Font("Arial",20));
        passwordLabel.setStyle("-fx-text-fill: white");

        PasswordField password = new PasswordField();
        password.setAlignment(Pos.CENTER);
        password.setMaxWidth(300);
        password.setFont(new Font("Arial",20));

        Button register = new Button("Already Have An Account? Login");
        register.setFont(new Font("Arial",15));
        register.setStyle("-fx-text-fill: white; -fx-border-color: transparent; -fx-cursor: hand; -fx-background-color: transparent");
        register.setUnderline(true);
        register.setOnAction(e->{
            displayLoginScene(stage,userManager);
        });


        passwordContainer.getChildren().addAll(passwordLabel,password,register);

        Label error = new Label();
        error.setFont(new Font("Arial",15));
        error.setBackground(new Background(new BackgroundFill(Color.rgb(86,162,203), CornerRadii.EMPTY, Insets.EMPTY)));
        error.setStyle("-fx-text-fill: white; -fx-border-color: white;");
        error.setPadding(new Insets(7));
        error.setVisible(false);

        Button button1 = new Button("Register");
        button1.setFont(new Font("Arial",20));
        button1.setBackground(new Background(new BackgroundFill(Color.rgb(86,162,203), CornerRadii.EMPTY, Insets.EMPTY)));
        button1.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-cursor: hand;");
        button1.setOnAction(e-> {
            handleRegister(stage,username.getText(),password.getText(),error,userManager);
        });

        registerContainer.getChildren().addAll(usernameContainer,passwordContainer,error,button1);

        vbox.getChildren().addAll(text,registerContainer);




        Scene scene = new Scene(vbox,1280,720);
        stage.setScene(scene);
        stage.setTitle("Streamify - Register");

        stage.show();
    }

    private void handleRegister(Stage stage, String username,String password,Label error,UserManager userManager){
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
            userManager.Register(username,password);
            error.setText("Success: Registered Successfully! Please Login");
            error.setVisible(true);
            displayLoginScene(stage,userManager);
        }
        catch(Exception exception){
            error.setText("Error:" + exception.getMessage());
            error.setVisible(true);
        }

    }

    private void displayMainScene(Stage stage){
        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(25,187,212), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        Button button = new Button("Choose Media File");
        button.setOnAction(e-> {
            try {
                chooseFile(stage);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        });

        vbox.getChildren().add(button);

        Scene scene = new Scene(vbox,1280,720);
        stage.setScene(scene);
        stage.setTitle("Streamify - Home");

        stage.show();
    }

    private void chooseFile(Stage stage) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Media File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println(selectedFile.getAbsolutePath());
            displayPlayer(selectedFile,stage);
        }

    }

    private void displayPlayer(File file,Stage stage) throws MalformedURLException {
        VBox vbox = new VBox();

        Media media = new Media("https://wmediasoup.watchblock.net/video/Runner.mp4");

        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();

        System.out.println(mediaPlayer.getOnError());

        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(720);
        mediaView.setFitWidth(1280);

        vbox.getChildren().add(mediaView);

        Scene scene = new Scene(vbox,1280,720);
        stage.setScene(scene);
        stage.setTitle("Streamify - "+ file.getName());

        stage.show();
    }


}