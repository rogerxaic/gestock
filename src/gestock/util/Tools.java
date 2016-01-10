/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Roger
 */
public class Tools {

    public static boolean isWindows() {
        return (Constants.OS.contains("win"));
    }

    public static boolean isMac() {
        return (Constants.OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (Constants.OS.contains("nix") || Constants.OS.contains("nux") || Constants.OS.contains("aix"));
    }

    public static boolean isSolaris() {
        return (Constants.OS.contains("sunos"));
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

    /**
     * Re-maps a number from one range to another.
     *
     * @param x      variable to re-map
     * @param inMin  in-range minimum
     * @param inMax  in-range maximum
     * @param outMin out-range minimum
     * @param outMax out-range maximum
     * @return re-mapped number
     */
    public static long map(long x, long inMin, long inMax, long outMin, long outMax) {
        return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

    /**
     * Replaces new lines and tabs for \n and \t respectively
     *
     * @param toEscape String that needs to be escaped
     * @return escaped String
     */
    public static String escape(String toEscape) {
        return toEscape.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n").replaceAll("(\\t)+", "\\\\t");
    }

    /**
     * Creates a ZIP file with the files of the specified path.
     *
     * @param input   The directory from which the files will be zipped.
     * @param zipFile The zip file that will be created with the files from input.
     */
    public static void zip(String input, String zipFile) {
        try {
            BufferedInputStream origin;
            FileOutputStream dest = new
                    FileOutputStream(zipFile);
            ZipOutputStream out = new ZipOutputStream(new
                    BufferedOutputStream(dest));
            //out.setMethod(ZipOutputStream.DEFLATED);
            byte data[] = new byte[Constants.BUFFER];
            // get a list of files from current directory
            File f = new File(input);
            String files[] = f.list();
            String fs = Constants.FS;

            for (String file : files) {
                System.out.println("Adding: " + file);
                FileInputStream fi = new
                        FileInputStream(input + fs + file);
                origin = new
                        BufferedInputStream(fi, Constants.BUFFER);
                ZipEntry entry = new ZipEntry(file);
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0,
                        Constants.BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
