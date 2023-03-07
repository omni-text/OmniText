package com.raa.omnitext;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class OmniUI {
    public static Image assetImage(String filename){
        return new Image(OmniUI.class.getResource("/icons/" + filename).toString());
    }

    static VBox mainLayout, mainArea, pasteListDiv;
    static HBox topBar, navButtons, buttonContents;
    static ScrollPane scrollArea;
    static Label title, logotext;
    static Button homeButton, aboutButton, contactButton, themeChangeButton, profileButton;

    public static Scene drawMainScreen(){
        mainLayout = new VBox();

        topBar = new HBox(8);
        topBar.setId("topBar");
        topBar = drawTopBar(topBar);

        mainArea = new VBox();
        mainArea.setId("mainArea");
        VBox.setVgrow(mainArea, Priority.ALWAYS);

        title = new Label("Your Pastes");

        scrollArea = new ScrollPane();
        scrollArea.setMaxWidth(530);
        scrollArea.setPadding(new Insets(4, 4, 4, 4));

        pasteListDiv = new VBox(12);
        pasteListDiv = displayPastes(pasteListDiv);
        scrollArea.setContent(pasteListDiv);

        mainArea.getChildren().addAll(title, scrollArea);

        mainLayout.getChildren().addAll(topBar, mainArea);
        Scene mainScene = new Scene(mainLayout);
        mainScene.getStylesheets().addAll("app.css");
        return mainScene;
    }

    private static HBox drawTopBar(HBox topbar){
        HBox.setHgrow(topbar, Priority.ALWAYS);

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        ImageView logo = new ImageView();
        logo.setImage(assetImage("512_logo.png"));
        logo.setPreserveRatio(true);
        logo.setFitHeight(48);

        logotext = new Label("OmniText");
        logotext.setId("logotext");

        navButtons = new HBox(12);
        navButtons.setId("navButtons");

        homeButton = new Button("HOME");
        aboutButton = new Button("ABOUT");
        contactButton = new Button("CONTACT");
        navButtons.getChildren().addAll(homeButton, aboutButton, contactButton);

        themeChangeButton = new Button();
        themeChangeButton.setId("themeChangeButton");
        ImageView themeIcon = new ImageView(assetImage("128_switch_darkmode.png"));
        themeIcon.setPreserveRatio(true);
        themeIcon.setFitHeight(24);
        themeChangeButton.setGraphic(themeIcon);

        profileButton = new Button();
        profileButton.setId("profileButton");
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

        for(String item : list){
            Button paste = new Button();
            paste.setId("paste");
            paste.setPrefWidth(500);
            paste.setMaxWidth(500);
            paste.setPrefHeight(64);
            paste.setMaxHeight(64);
            paste.setAlignment(Pos.CENTER);

            buttonContents = new HBox(4);
            buttonContents.setAlignment(Pos.CENTER);

            Label title = new Label(item);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Button copyLinkButton = new Button();
            copyLinkButton.setId("copyLinkButton");
            copyLinkButton.setPrefHeight(48);
            copyLinkButton.setPrefWidth(48);
            ImageView copyLinkIcon = new ImageView(assetImage("128_link.png"));
            copyLinkIcon.setPreserveRatio(true);
            copyLinkIcon.setFitHeight(36);
            copyLinkButton.setGraphic(copyLinkIcon);

            Button deleteButton = new Button();
            deleteButton.setId("deleteButton");
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
