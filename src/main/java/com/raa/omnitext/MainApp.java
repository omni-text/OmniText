package com.raa.omnitext;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    Scene mainScreen, pasteScreen;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("OmniText");
        stage.getIcons().add(OmniUI.assetImage("512_logo.png"));
        stage.setMinHeight(500);
        stage.setMinWidth(700);

        mainScreen = OmniUI.drawMainScreen();
        pasteScreen = OmniUI.drawPasteScreen();

        stage.setScene(mainScreen);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}