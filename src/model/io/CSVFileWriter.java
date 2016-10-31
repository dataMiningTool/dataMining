package model.io;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVFileWriter {
    private static final String FILE_NAME = "coordinate.csv";
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = System.lineSeparator();
    
    public static void writeToFile(ArrayList<Point> coordinateValues){
        String savedDirectoryPath = "E:\\";
        FileWriter fileWriter;
        
        try {
            File savedFile = new File(savedDirectoryPath + FILE_NAME);
            if(!savedFile.isFile()) savedFile.createNewFile();
            
            fileWriter = new FileWriter(savedDirectoryPath + FILE_NAME);
            for(Point pointCoordinate : coordinateValues){
                fileWriter.append(String.valueOf(pointCoordinate.x));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(pointCoordinate.y));
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            
            fileWriter.flush();
            fileWriter.close();
            
            coordinateValues.clear();
        } catch (IOException ex) {
            Logger.getLogger(CSVFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
