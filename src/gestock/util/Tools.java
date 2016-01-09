/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.util;

import java.io.*;

/**
 * @author Roger
 */
public class Tools {

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }

    /**
     * Checks whether the {@code} String given is a number or not.
     *
     * @param str The {@code String} which has to be checked if it's a number.
     * @return true if {@code String} str given is a number.
     */
    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Writes the content given onto the specified file.
     *
     * @param file    Where to write the content.
     * @param content What to write on the file.
     */
    public static void saver(String file, String content) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "utf-8"))) {
            writer.write(content);
            writer.write(System.lineSeparator());
        } catch (Exception ignored) {
        }
    }

    /**
     * Checks wether a directory exists or not and creates it if it does not exist.
     *
     * @param path The directory that is going to be created.
     */
    public static void createPath(String path) {
        File f;
        boolean bool = false;
        try {
            f = new File(path);
            if (!f.exists() || (f.exists() && !f.isDirectory())) {
                // create directory (path) if it does not exist
                bool = f.mkdirs();
            }
            // print status
            System.out.println("Directory(" + path + ") created? " + bool);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
