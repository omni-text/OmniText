package com.raa.omnitext;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OmniEngine {
    private static ArrayList<String> pasteList = new ArrayList<>(List.of(new String[]{"Paste1", "Paste2"}));
    private static ArrayList<String> pasteContentList = new ArrayList<>(List.of(new String[]{"content of paste 1 here", "content of paste 2 here"}));

    public static ArrayList<String> getPasteList(){
        return pasteList;
    }
    public static ArrayList<String> getPasteContentList() {
        return pasteContentList;
    }

    private static String homeDirectory = System.getProperty("user.home");
    private static File omniDirectory = new File(homeDirectory + "\\OmniText");
    private static File pasteFile = new File(omniDirectory + "\\pastes.txt");
    private static int tempNum = 1;

    public static void addPaste(String title, String content){
        if(!pasteList.contains(title)) {
            pasteList.add(title);
            pasteContentList.add(content);
        }
    }
    public static void editPaste(int index, String title, String content){
        deletePaste(index);
        pasteList.add(index, title);
        pasteContentList.add(index, content);
    }
    public static void deletePaste(int index){
        pasteList.remove(index);
        pasteContentList.remove(index);
    }

    public static String getPasteTitle(int index){
        if(pasteList.size()-1 >= index) return pasteList.get(index);
        return "";
    }
    public static String getPasteContent(int index){
        if(pasteContentList.size()-1 >= index) return pasteContentList.get(index);
        return "";
    }

    private static void checkOmniDirectory() {
        boolean result = omniDirectory.mkdir();

        if(!result){
            if(omniDirectory.isFile()) {
                while(!omniDirectory.renameTo(new File(homeDirectory + "\\OmniText (" + tempNum + ")"))) tempNum*=4;
                checkOmniDirectory();
            }
        }
    }

    private static void checkPasteFile() throws IOException {
        try{
            pasteFile.createNewFile();
        }
        catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error occurred");
            error.setHeaderText("OmniText faced an issue while reading its source files");
            error.setContentText(e.getMessage());
            error.showAndWait();
            MainApp.close();
            System.exit(0);
        }
    }

    private static void getPastesFromFile() throws IOException {
        checkPasteFile();

        ArrayList<String> pasteT = new ArrayList<>();
        ArrayList<String> pasteC = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(pasteFile))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] components = line.split("\\t\\t", 2);
                pasteT.add(components[0]);
                pasteC.add(components[1]);
            }
        }

        pasteList = pasteT;
        pasteContentList = pasteC;
    }
}
