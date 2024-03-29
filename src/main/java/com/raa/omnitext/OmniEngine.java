package com.raa.omnitext;

import javafx.scene.control.Alert;

import java.io.*;
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
    private static String userOS = System.getProperty("os.name").toLowerCase();

    private static File omniDirectory = new File(homeDirectory + (userOS.contains("mac") ? "/OmniText" : "\\OmniText"));
    private static File pasteFile = new File(omniDirectory + (userOS.contains("mac") ? "/pastes.txt" : "\\pastes.txt"));
    
    private static int tempNum = 1;

    public static void addPaste(String title, String content) {
        if(!pasteList.contains(title)) {
            pasteList.add(title);
            pasteContentList.add(content);

            try {
                writePastesToFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void editPaste(int index, String title, String content) {
        deletePaste(index);
        pasteList.add(index, title);

        pasteContentList.add(index, content);

        try {
            writePastesToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deletePaste(int index) {
        pasteList.remove(index);
        pasteContentList.remove(index);

        try {
            writePastesToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                while(
                        !omniDirectory.renameTo(
                                new File(homeDirectory + "\\OmniText (" + tempNum + ")")
                        )
                ) tempNum+=Math.random();
                checkOmniDirectory();
            }
        }
    }

    private static void checkPasteFile() throws IOException {
        checkOmniDirectory();
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

    public static void getPastesFromFile() throws IOException {
        checkPasteFile();

        ArrayList<String> pasteT = new ArrayList<>();
        ArrayList<String> pasteC = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(pasteFile))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] components = line.split("\t\t", 2);
                if(components.length == 2) {
                    pasteT.add(components[0]);

                    components[1] = components[1].replace("&&OMNI&N&", "\n").replace("&&OMNI&T&", "\t");
                    pasteC.add(components[1]);
                }
            }
        }

        pasteList = pasteT;
        pasteContentList = pasteC;
        writePastesToFile();
    }

    public static void writePastesToFile() throws IOException {
        checkPasteFile();

        try (BufferedWriter pasteFileWriter = new BufferedWriter(new FileWriter(pasteFile))){
            for(int i=0; i<pasteList.size(); i++){
                if(i <= pasteContentList.size()-1) {
                    String content = pasteContentList.get(i).replace("\n", "&&OMNI&N&").replace("\t", "&&OMNI&T&");
                    String out = pasteList.get(i) + "\t\t" + content;
                    pasteFileWriter.write(out + "\n");
                }
            }
        }
    }
}
