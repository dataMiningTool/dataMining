package model.helper;

import java.io.File;

public class StringHelper { 
    
    public static String getExtension(String fileName){
        return fileName.substring(fileName.indexOf('.', fileName.lastIndexOf('/')) + 1);
    }
    
    public static String getAbsolutePath(String directoryPath, String fileName){
        return directoryPath + "/" + fileName;
    }
    
    public static String getDirectoryPath(File directory){
        return directory.getAbsolutePath().replace("\\", "/");
    }
}
