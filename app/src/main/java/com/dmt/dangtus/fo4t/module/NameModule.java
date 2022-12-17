package com.dmt.dangtus.fo4t.module;

public class NameModule {
    public static String nameHandling(String name) {
        name = name.trim().replaceAll(" +", " ");
        String[] nameArray = name.split(" ");

        String newName = "";
        for (String item: nameArray) {
            newName += item.substring(0, 1).toUpperCase() + item.substring(1).toLowerCase() + " ";
        }

        return newName.trim();
    }
}
