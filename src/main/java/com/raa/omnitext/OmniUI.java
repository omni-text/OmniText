package com.raa.omnitext;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class OmniUI {
    // get the Image file from the icons folder inside resources
    public static Image assetImage(String filename){
        return new Image(OmniUI.class.getResource("/icons/" + filename).toString());
    }

    static Scene mainScene;
    static VBox mainLayout, mainArea, pasteListDiv, pastePage;
    static HBox topBar, navButtons, buttonContents;
    static ScrollPane scrollArea;
    static Label title, logotext, aa;
    static Button homeButton, aboutButton, contactButton, themeChangeButton, profileButton, createPasteButton;
    static Button saveButton, cancelButton, deleteButton, copyTextButton, linkButton;
    static ImageView logo, linkLogo, themeIcon, profileIcon;
    static TextField titleField;
    static TextArea contentArea;
    static Clipboard userClip = Clipboard.getSystemClipboard();
    static ClipboardContent clipboardContent = new ClipboardContent();


    // indicates if a paste is being edited or is a new one
    static boolean editMode = false;
    static int editIndex = -1;

    // build the Home Screen GUI
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

        createPasteButton = new Button("Create New");
        createPasteButton.setId("createPasteButton");
        createPasteButton.setOnAction(e -> openPaste());

        mainArea.getChildren().addAll(title, scrollArea, createPasteButton);

//        paste page setup
        pastePage = drawPastePage();

        aa = new Label();
        ImageView bottomDesignImage = new ImageView(assetImage("1920_page_end.png"));
        aa.setId("bottomDesignImage");
        bottomDesignImage.setPreserveRatio(false);
        bottomDesignImage.setFitHeight(48);
        bottomDesignImage.fitWidthProperty().bind(mainArea.widthProperty());
        aa.setGraphic(bottomDesignImage);

        mainLayout.getChildren().addAll(topBar, mainArea, aa);
        mainScene = new Scene(mainLayout, 700, 600);
        OmniColors.setTheme();

        return mainScene;
    }

    // build the top bar containing logo, etc
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

        homeButton = new Button("Your Pastes");
        homeButton.setOnAction(e -> openHomePage());
        aboutButton = new Button("About OmniText");
        aboutButton.setOnAction(e -> openAboutUsWindow());
        contactButton = new Button("CONTACT");
        navButtons.getChildren().addAll(homeButton, aboutButton);

        themeChangeButton = new Button();
        themeChangeButton.setId("themeChangeButton");
        themeIcon = new ImageView(assetImage("128_switch_darkmode.png"));
        themeIcon.setPreserveRatio(true);
        themeIcon.setFitHeight(24);
        themeChangeButton.setGraphic(themeIcon);
        themeChangeButton.setOnAction(e -> OmniColors.switchTheme());

        profileButton = new Button();
        profileButton.setId("profileButton");
        profileIcon = new ImageView(assetImage("128_profile.png"));
        profileIcon.setPreserveRatio(true);
        profileIcon.setFitHeight(40);
        profileButton.setGraphic(profileIcon);

        topbar.getChildren().addAll(logo, logotext, spacer1, navButtons, themeChangeButton, spacer2);
        return topbar;
    }

    // refresh the list of pastes to match the current data
    private static void refreshPastes(){
        pasteListDiv.getChildren().clear();
        displayPastes();
    }

    // build the gui of the list of pastes
    private static void displayPastes(){
        try {
            OmniEngine.getPastesFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> list = OmniEngine.getPasteList();
        pasteListDiv.setAlignment(Pos.CENTER);

        if(list.isEmpty()) title.setText("No Pastes Yet");
        else title.setText("Your Pastes");

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

            buttonContents.getChildren().addAll(title, spacer, deleteButton);
            paste.setGraphic(buttonContents);
            final int k = i;
            title.setOnMouseClicked(e -> {
                openPaste(k);
                editMode = true;
                editIndex = k;
            });
            spacer.setOnMouseClicked(e -> {
                openPaste(k);
                editMode = true;
                editIndex = k;
            });
            deleteButton.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete confirmation");
                alert.setHeaderText("Delete Paste titled \""+OmniEngine.getPasteTitle(k)+"\"?");
                String content = OmniEngine.getPasteContent(k);
                alert.setContentText("Are you sure you want to delete this paste?\n\n"+content.substring(0, Math.min(content.length(), 96))+"...");

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

    // build the paste edit page
    private static VBox drawPastePage(){
        pastePage = new VBox(4);
        pastePage.setId("pastePage");
        VBox.setVgrow(pastePage, Priority.ALWAYS);

        HBox titleDiv = new HBox(8);
        titleDiv.setAlignment(Pos.CENTER_LEFT);
        Label titleLabel = new Label("Paste Title: ");
        titleLabel.setId("pasteTitleLabel");
        titleField = new TextField();
        titleField.setId("pasteTitleField");
        titleDiv.getChildren().addAll(titleLabel, titleField);

        Label contentLabel = new Label("Edit your paste by using the textbox below:");
        contentLabel.setId("contentLabel");
        contentArea = new TextArea();
//        contentArea.setStyle("-fx-focus-color: red; -fx-faint-focus-color: transparent;");
        contentArea.setId("contentArea");
        VBox.setVgrow(contentArea, Priority.ALWAYS);

        HBox actionButtons = new HBox(8);
        actionButtons.setId("actionButtons");
        saveButton = new Button("Save");
        saveButton.setId("pasteSaveButton");
        cancelButton = new Button("Cancel");
        cancelButton.setId("linkButton"); // Id of an unused button
        deleteButton = new Button("Delete");
        deleteButton.setId("pasteDeleteButton");
        copyTextButton = new Button("Copy Text");
        copyTextButton.setId("copyTextButton");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label linkLabel = new Label("Get Link: ");
        linkButton = new Button();
        linkButton.setId("linkButton");

        linkLogo = new ImageView(assetImage("128_link.png"));
        linkLogo.setPreserveRatio(true);
        linkLogo.setFitHeight(28);
        linkButton.setGraphic(linkLogo);

        actionButtons.getChildren().addAll(saveButton, cancelButton, deleteButton, copyTextButton, spacer);

        pastePage.getChildren().addAll(titleDiv, contentLabel, contentArea, actionButtons);

        return pastePage;
    }

    // open a specific paste page
    private static void openPaste(String title, String content){
        titleField.setText(title);
        contentArea.setText(content);

        saveButton.setOnAction(e -> {
            String titleText = titleField.getText().trim();
            String contentText = contentArea.getText().trim();

            if(titleText.isEmpty()){
                Alert err = new Alert(Alert.AlertType.WARNING);
                err.setTitle("Warning");
                err.setHeaderText("Invalid Paste Title");
                err.setContentText("Paste Title must not be empty");
                err.showAndWait();
                return;
            }
            if(editMode){
                OmniEngine.editPaste(editIndex, titleText, contentText);
                editMode = false; editIndex = -1;
            }
            else OmniEngine.addPaste(titleText, contentText);
            openHomePage();
        });
        cancelButton.setOnAction(e -> {
            editMode = false; editIndex = -1;
            openHomePage();
        });
        deleteButton.setDisable(false);
        deleteButton.setOnAction(e -> {
            if(editMode){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete confirmation");
                alert.setHeaderText("Delete Paste titled \""+title+"\"?");
                alert.setContentText("Are you sure you want to delete this paste?\n\n"+content.substring(0, Math.min(content.length(), 96))+"...");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    OmniEngine.deletePaste(editIndex);
                    alert.close();
                    openHomePage();
                    editIndex = -1; editMode = false;
                }
                else {
                    alert.close();
                }
            }
        });
        copyTextButton.setOnAction(e -> {
            clipboardContent.putString(contentArea.getText());
            userClip.setContent(clipboardContent);
            copyTextButton.setText("Copied!");
            Timeline tl = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                copyTextButton.setText("Copy Text");
            }));
            tl.play();
        });

        mainLayout.getChildren().clear();
        mainLayout.getChildren().addAll(topBar, pastePage);
    }
    private static void openPaste(int index){
        String title = OmniEngine.getPasteTitle(index);
        String content = OmniEngine.getPasteContent(index);
        openPaste(title, content);
    }
    private static void openPaste(){
        int i = OmniEngine.getPasteList().size();
        openPaste("Untitled Paste "+(i+1), "");
        deleteButton.setDisable(true);
    }

    public static void openAboutUsWindow(){
        Alert aboutUs = new Alert(Alert.AlertType.INFORMATION);
        aboutUs.setTitle("About OmniText");
        aboutUs.setHeaderText("About OmniText");
        aboutUs.setContentText(
                "OmniText is a text storage application that " +
                "lets you store plain text in a single place in " +
                "the form of 'pastes', basically acting as a permanent clipboard." +
                "\n\nOmniText has been made using the GUI Toolkit JavaFX " +
                "and has been hand crafted to near perfection by us."
        );
        aboutUs.showAndWait();
    }

    // open the home page when any other page open
    private static void openHomePage(){
        mainLayout.getChildren().clear();
        mainLayout.getChildren().addAll(topBar, mainArea, aa);
        editMode = false; editIndex = -1;
        refreshPastes();
    }
}
