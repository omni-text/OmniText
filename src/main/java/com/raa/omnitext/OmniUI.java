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

    static Scene mainScene;
    static VBox mainLayout, mainArea, pasteListDiv, pastePage;
    static HBox topBar, navButtons, buttonContents;
    static ScrollPane scrollArea;
    static Label title, logotext, hello;
    static Button homeButton, aboutButton, contactButton, themeChangeButton, profileButton;
    static ImageView logo;

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
        scrollArea.setId("scrollArea");

        pasteListDiv = new VBox(12);
        pasteListDiv = displayPastes(pasteListDiv);
        scrollArea.setContent(pasteListDiv);

        mainArea.getChildren().addAll(title, scrollArea);

        //paste page setup
        pastePage = drawPastePage();

        mainLayout.getChildren().addAll(topBar, mainArea);
        mainScene = new Scene(mainLayout);
        OmniColors.setTheme();

        return mainScene;
    }

    private static HBox drawTopBar(HBox topbar){
        HBox.setHgrow(topbar, Priority.ALWAYS);

        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        logo = new ImageView();
        logo.setImage(assetImage("512_logo.png"));
        logo.setPreserveRatio(true);
        logo.setFitHeight(48);

        logotext = new Label("OmniText");
        logotext.setId("logotext");

        navButtons = new HBox(12);
        navButtons.setId("navButtons");

        homeButton = new Button("HOME");
        homeButton.setOnAction(e -> openHomePage());
        aboutButton = new Button("ABOUT");
        aboutButton.setOnAction(e -> openPaste());
        contactButton = new Button("CONTACT");
        navButtons.getChildren().addAll(homeButton, aboutButton, contactButton);

        themeChangeButton = new Button();
        themeChangeButton.setId("themeChangeButton");
        ImageView themeIcon = new ImageView(assetImage("128_switch_darkmode.png"));
        themeIcon.setPreserveRatio(true);
        themeIcon.setFitHeight(24);
        themeChangeButton.setGraphic(themeIcon);
        themeChangeButton.setOnAction(e -> OmniColors.switchTheme());

        profileButton = new Button();
        profileButton.setId("profileButton");
        ImageView profileIcon = new ImageView(assetImage("128_profile.png"));
        profileIcon.setPreserveRatio(true);
        profileIcon.setFitHeight(40);
        profileButton.setGraphic(profileIcon);

        topbar.getChildren().addAll(logo, logotext, spacer1, navButtons, spacer2, themeChangeButton, profileButton);
        return topbar;
    }

    private static void refreshPastes(){
        pasteListDiv.getChildren().clear();
        pasteListDiv = displayPastes(pasteListDiv);
        System.out.println("refreshed");
    }

    private static VBox displayPastes(VBox div){
        String[] list = OmniEngine.getPasteList();
        div.setAlignment(Pos.CENTER);

        for(String item : list){
            Button paste = new Button();
            paste.setId("paste");

            buttonContents = new HBox(4);
            buttonContents.setAlignment(Pos.CENTER);

            Label title = new Label(item);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Button copyLinkButton = new Button();
            copyLinkButton.setId("copyLinkButton");
            ImageView copyLinkIcon = new ImageView(assetImage("128_link.png"));
            copyLinkIcon.setPreserveRatio(true);
            copyLinkIcon.setFitHeight(28);
            copyLinkButton.setGraphic(copyLinkIcon);

            Button deleteButton = new Button();
            deleteButton.setId("deleteButton");
            ImageView deletePasteIcon = new ImageView(assetImage("128_delete_paste.png"));
            deletePasteIcon.setPreserveRatio(true);
            deletePasteIcon.setFitHeight(28);
            deleteButton.setGraphic(deletePasteIcon);

            buttonContents.getChildren().addAll(title, spacer, copyLinkButton, deleteButton);
            paste.setGraphic(buttonContents);

            div.getChildren().add(paste);
        }

        return div;
    }

    private static VBox drawPastePage(){
        pastePage = new VBox(4);
        pastePage.setId("pastePage");
        VBox.setVgrow(pastePage, Priority.ALWAYS);

        hello = new Label("HEHHHEHEHEHE");
        hello.setId("logotect");

        pastePage.getChildren().add(hello);

        return pastePage;
    }

    public static void openPaste(String title, String content){
        hello.setText(title + content);
        mainLayout.getChildren().clear();
        mainLayout.getChildren().addAll(topBar, pastePage);
    }
    public static void openPaste(){
        int i = OmniEngine.getPasteList().length;
        openPaste("Untitled "+(i+1), "");
    }

    public static void openHomePage(){
        mainLayout.getChildren().clear();
        mainLayout.getChildren().addAll(topBar, mainArea);
        refreshPastes();
    }
}
