package com.raa.omnitext;

import java.io.File;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        String home = System.getProperty("user.home");
        System.out.println(home);

        File omni = new File(home + "\\OmniText");

//        if(omni.isDirectory()) {
//            System.out.println(omni.mkdir());
//        }
//        else {
//            System.out.println("not a directory");
//            omni.renameTo(new File(home + "\\OmniText2"));
//        }
//
//        System.out.println(omni.mkdir());

        String hello = "Hello ";
        System.out.println(Arrays.toString(hello.split(" ", 2)));

    }
}
