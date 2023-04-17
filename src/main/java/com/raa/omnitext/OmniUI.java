package com.raa.omnitext;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Optional;

public class OmniUI {
    public static Image assetImage(String filename){
        return new Image(OmniUI.class.getResource("/icons/" + filename).toString());
    }

    static Scene mainScene;
    static VBox mainLayout, mainArea, pasteListDiv, pastePage;
    static HBox topBar, navButtons, buttonContents;
    static ScrollPane scrollArea;
    static Label title, logotext;
    static Button homeButton, aboutButton, contactButton, themeChangeButton, profileButton, createPasteButton;
    static Button saveButton, deleteButton, copyTextButton, linkButton;
    static ImageView logo, linkLogo;
    static TextField titleField;
    static TextArea contentArea;

    static boolean editMode = false;
    static int editIndex = -1;

    public static Scene drawMainScreen(){
        mainLayout = new VBox();
        mainLayout.setAlignment(Pos.CENTER);

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
        displayPastes();
        scrollArea.setContent(pasteListDiv);

        mainArea.getChildren().addAll(title, scrollArea);

        createPasteButton = new Button("Create New");
        createPasteButton.setId("createPasteButton");
        createPasteButton.setOnAction(e -> openPaste());

//        paste page setup
        pastePage = drawPastePage();

        mainLayout.getChildren().addAll(topBar, mainArea, createPasteButton);
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
        displayPastes();
        System.out.println("refreshed");
    }

    private static void displayPastes(){
        ArrayList<String> list = OmniEngine.getPasteList();
        ArrayList<String> contentList = OmniEngine.getPasteContentList();
        pasteListDiv.setAlignment(Pos.CENTER);

        for(int i=0; i<list.size(); i++){
            Button paste = new Button();
            paste.setId("paste");

            buttonContents = new HBox(4);
            buttonContents.setAlignment(Pos.CENTER);

            Label title = new Label(list.get(i));
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
            final int k = i;
            paste.setOnAction(e -> {
                openPaste(k);
                editMode = true;
                editIndex = k;
            });
            deleteButton.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete confirmation");
                alert.setHeaderText("Delete Paste?");
                alert.setContentText("Are you sure you want to delete this paste?\n"+OmniEngine.getPasteTitle(k)+"\n"+OmniEngine.getPasteContent(k));

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    OmniEngine.deletePaste(k);
                    alert.close();
                }
                else {
                    alert.close();
                }
                openHomePage();
            });

            pasteListDiv.getChildren().add(paste);
        }
    }

    private static VBox drawPastePage(){
        pastePage = new VBox(4);
        pastePage.setId("pastePage");
        VBox.setVgrow(pastePage, Priority.ALWAYS);

        HBox titleDiv = new HBox(8);
        Label titleLabel = new Label("Paste Title: ");
        titleField = new TextField();
        titleDiv.getChildren().addAll(titleLabel, titleField);

        Label contentLabel = new Label("Edit your paste by using the textbox below:");
        contentArea = new TextArea();

        HBox actionButtons = new HBox(8);
        saveButton = new Button("Save");
        deleteButton = new Button("Delete");
        copyTextButton = new Button("Copy Text");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label linkLabel = new Label("Get Link: ");
        linkButton = new Button();

        linkLogo = new ImageView(assetImage("128_link.png"));
        linkLogo.setPreserveRatio(true);
        linkLogo.setFitHeight(28);
        linkButton.setGraphic(linkLogo);

        actionButtons.getChildren().addAll(saveButton, deleteButton, copyTextButton, spacer, linkLabel, linkButton);

        pastePage.getChildren().addAll(titleDiv, contentLabel, contentArea, actionButtons);

        return pastePage;
    }

    public static void openPaste(String title, String content){
        titleField.setText(title);
        contentArea.setText(content);

        saveButton.setOnAction(e -> {
            if(editMode){
                OmniEngine.editPaste(editIndex, titleField.getText(), contentArea.getText());
                editMode = false; editIndex = -1;
            }
            else OmniEngine.addPaste(titleField.getText(), contentArea.getText());
            openHomePage();
        });
        deleteButton.setDisable(false);
        deleteButton.setOnAction(e -> {
            if(editMode){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete confirmation");
                alert.setHeaderText("Delete Paste " + editIndex + "?");
                alert.setContentText("Are you sure you want to delete this paste?\n"+title+"\n"+content);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    OmniEngine.deletePaste(editIndex);
                    alert.close();
                }
                else {
                    alert.close();
                }
                openHomePage();
                editMode = false;
                editIndex = -1;
            }
        });

        mainLayout.getChildren().clear();
        mainLayout.getChildren().addAll(topBar, pastePage);
    }
    public static void openPaste(int index){
        String title = OmniEngine.getPasteTitle(index);
        String content = OmniEngine.getPasteContent(index);
        openPaste(title, content);
    }
    public static void openPaste(){
        int i = OmniEngine.getPasteList().size();
        openPaste("UntitledPaste"+(i+1), "");
        deleteButton.setDisable(true);
    }

    public static void openHomePage(){
        mainLayout.getChildren().clear();
        mainLayout.getChildren().addAll(topBar, mainArea, createPasteButton);
        editMode = false; editIndex = -1;
        refreshPastes();
    }
}
