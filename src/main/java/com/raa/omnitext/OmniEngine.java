package com.raa.omnitext;

import java.util.ArrayList;

public class OmniEngine {
    private static ArrayList<String> pasteList = new ArrayList<>();
    private static ArrayList<String> pasteContentList = new ArrayList<>(){};

    public static ArrayList<String> getPasteList(){
        return pasteList;
    }
    public static ArrayList<String> getPasteContentList() {
        return pasteContentList;
    }

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
}
