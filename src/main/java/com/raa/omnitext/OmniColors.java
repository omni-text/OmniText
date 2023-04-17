package com.raa.omnitext;

public class OmniColors {
    private static boolean lightTheme = true;

    static boolean isLightTheme() {
        return lightTheme;
    }

    static void setTheme(){
        if(lightTheme) OmniUI.mainScene.getStylesheets().addAll("lightmode.css", "app.css");
        else OmniUI.mainScene.getStylesheets().addAll("darkmode.css", "app.css");
    }

    static void switchTheme(){
        if(lightTheme) {
            OmniUI.mainScene.getStylesheets().removeAll("lightmode.css", "app.css");
            OmniUI.logo.setImage(OmniUI.assetImage("512_logo_darkmode.png"));
            lightTheme = false;
        }
        else {
            OmniUI.mainScene.getStylesheets().removeAll("darkmode.css", "app.css");
            OmniUI.logo.setImage(OmniUI.assetImage("512_logo.png"));
            lightTheme = true;
        }
        setTheme();
    }
}
