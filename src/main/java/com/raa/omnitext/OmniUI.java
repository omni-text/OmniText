package com.raa.omnitext;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

public class OmniUI {
    public static Image assetImage(String filename){
        return new Image(OmniUI.class.getResource("/icons/" + filename).toString());
    }
    public static Scene drawMainScreen(){
        VBox mainLayout = new VBox();

        HBox topBar = new HBox(8);
        topBar = drawTopBar(topBar);

        VBox mainArea = new VBox();
        mainArea.setPadding(new Insets(20, 0, 20, 0));
        mainArea.setStyle("-fx-background-color: red;");
        mainArea.setPrefHeight(500);
        mainArea.setAlignment(Pos.CENTER);
        VBox.setVgrow(mainArea, Priority.ALWAYS);

        Label title = new Label("Your Pastes");

        ScrollPane scrollArea = new ScrollPane();
        scrollArea.setStyle("-fx-background-color: green;");
        scrollArea.setMaxWidth(550);
        scrollArea.setPadding(new Insets(4, 4, 4, 4));

        VBox pasteListDiv = new VBox(12);
        pasteListDiv = displayPastes(pasteListDiv);
        scrollArea.setContent(pasteListDiv);

        mainArea.getChildren().addAll(title, scrollArea);

        mainLayout.getChildren().addAll(topBar, mainArea);
        Scene mainScene = new Scene(mainLayout);
        return mainScene;
    }

    private static HBox drawTopBar(HBox topbar){
        topbar.setPrefHeight(54);
        HBox.setHgrow(topbar, Priority.ALWAYS);
        topbar.setPadding(new Insets(8, 8, 8, 8));
        topbar.setAlignment(Pos.CENTER);

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        ImageView logo = new ImageView();
        logo.setImage(assetImage("512_logo.png"));
        logo.setPreserveRatio(true);
        logo.setFitHeight(48);

        Label logotext = new Label("OmniText");

        HBox navButtons = new HBox(12);
        navButtons.setAlignment(Pos.CENTER);
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

    private static VBox displayPastes(VBox div){
        String[] list = OmniEngine.getPasteList();
        div.setAlignment(Pos.CENTER);
        div.setStyle("-fx-background-color: yellow;");

        for(String item : list){
            Button paste = new Button();
            paste.setPrefWidth(500);
            paste.setMaxWidth(500);
            paste.setPrefHeight(64);
            paste.setMaxHeight(64);
            paste.setAlignment(Pos.CENTER);

            HBox buttonContents = new HBox(4);
            buttonContents.setAlignment(Pos.CENTER);

            Label title = new Label(item);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Button copyLinkButton = new Button();
            copyLinkButton.setPrefHeight(48);
            copyLinkButton.setPrefWidth(48);
            ImageView copyLinkIcon = new ImageView(assetImage("128_link.png"));
            copyLinkIcon.setPreserveRatio(true);
            copyLinkIcon.setFitHeight(36);
            copyLinkButton.setGraphic(copyLinkIcon);

            Button deleteButton = new Button();
            deleteButton.setPrefHeight(48);
            deleteButton.setPrefWidth(48);
            ImageView deletePasteIcon = new ImageView(assetImage("128_delete_paste.png"));
            deletePasteIcon.setPreserveRatio(true);
            deletePasteIcon.setFitHeight(36);
            deleteButton.setGraphic(deletePasteIcon);

            buttonContents.getChildren().addAll(title, spacer, copyLinkButton, deleteButton);
            paste.setGraphic(buttonContents);

            div.getChildren().add(paste);
        }

        return div;
    }
    public static Scene drawPasteScreen(){
        Scene pasteScreen = new Scene(new VBox());
        return pasteScreen;
    }
}
