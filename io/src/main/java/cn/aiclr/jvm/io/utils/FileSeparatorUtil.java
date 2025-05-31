package cn.aiclr.jvm.io.utils;

import java.io.File;

public class FileSeparatorUtil {

    public static String expandUserHome(String path) {
        if (path == null || path.isEmpty()) {
            return path;
        }
        if (File.separator.equals("/")) {
            path = path.replace("\\", File.separator);
        }
        if (File.separator.equals("\\")) {
            path = path.replace("/", File.separator);
        }
        if (path.startsWith("~")) {
            String userHome = System.getProperty("user.home");
            if (path.equals("~")) {
                return userHome;
            } else {
                return userHome + path.substring(1);
            }
        }
        return path;
    }

}
