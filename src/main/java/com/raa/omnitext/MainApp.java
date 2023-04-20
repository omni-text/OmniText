package com.raa.omnitext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    Scene mainScreen;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("OmniText");
        stage.getIcons().add(OmniUI.assetImage("512_logo.png"));
        stage.setMinHeight(600);
        stage.setMinWidth(700);

        mainScreen = OmniUI.drawMainScreen();
        stage.setScene(mainScreen);

        stage.show();
    }

    public static void close(){
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }
}