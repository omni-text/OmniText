package com.raa.omnitext;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class OmniUI {
    public static Image assetImage(String filename){
        return new Image(OmniUI.class.getResource("/icons/" + filename).toString());
    }
    public static Scene drawMainScreen(){
        VBox mainLayout = new VBox();

        HBox topBar = new HBox();
        topBar = drawTopBar(topBar);

        mainLayout.getChildren().addAll(topBar);
        Scene mainScene = new Scene(mainLayout);
        return mainScene;
    }

    private static HBox drawTopBar(HBox topbar){
        topbar.setPrefHeight(54);
        HBox.setHgrow(topbar, Priority.ALWAYS);
        topbar.setPadding(new Insets(8, 8, 8, 8));

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        ImageView logo = new ImageView();
        logo.setImage(assetImage("512_logo.png"));
        logo.setFitHeight(48);
        logo.setPreserveRatio(true);

        ImageView logotext = new ImageView();
        logotext.setImage(assetImage("512_logotext.png"));
        logotext.setFitHeight(24);
        logotext.setPreserveRatio(true);

        HBox navButtons = new HBox();
        Button homeButton = new Button("HOME");
        Button aboutButton = new Button("ABOUT");
        Button contactButton = new Button("CONTACT");
        navButtons.getChildren().addAll(homeButton, aboutButton, contactButton);

        Button themeChangeButton = new Button();
        ImageView themeIcon = new ImageView(assetImage("128_switch_darkmode.png"));
        themeIcon.setPreserveRatio(true);
        themeIcon.setFitHeight(24);
        themeChangeButton.setGraphic(themeIcon);

        Button profileButton = new Button();
        ImageView profileIcon = new ImageView(assetImage("128_profile.png"));
        profileIcon.setPreserveRatio(true);
        profileIcon.setFitHeight(40);
        profileButton.setGraphic(profileIcon);

        topbar.getChildren().addAll(logo, logotext, spacer1, navButtons, spacer2, themeChangeButton, profileButton);
        return topbar;
    }

    public static Scene drawPasteScreen(){
        Scene pasteScreen = new Scene(new VBox());
        return pasteScreen;
    }
}
