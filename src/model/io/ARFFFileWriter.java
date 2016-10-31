package model.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.datastore.DataRow;

public class ARFFFileWriter {
    private static final String COMMA_DELIMITER = ",";
    private static final String END_FILE = "%";
    private static final String NEW_LINE_SEPARATOR = System.lineSeparator();
    
    private static final String RELATION_NAME = "DataMiningDataset";
    private static final String ATTRIBUTE_TYPE = "NUMERIC";
    private static final String FILE_NAME = "dataset.arff";
    
    private static final String NORMAL_CLASS = "Normal";
    private static final String ABNORMAL_CLASS = "Abnormal";
    private static final String UNPREDICTED_CLASS = "?";
    
    private static void writeHeader(FileWriter fileWriter, int numOfSlides){
        try {
            fileWriter.append("@RELATION " + RELATION_NAME);
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append(NEW_LINE_SEPARATOR);
            for(int i = 1; i <= numOfSlides; i++ ){
                fileWriter.append("@ATTRIBUTE slide" + String.valueOf(i) + " " + ATTRIBUTE_TYPE);
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            fileWriter.append("@ATTRIBUTE class {" + NORMAL_CLASS + ", " + ABNORMAL_CLASS + "}");
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append("@Data");
            fileWriter.append(NEW_LINE_SEPARATOR);
        } catch (IOException ex) {
            Logger.getLogger(ARFFFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void removeLastCharacter(String savedDirectoryPath){
        try{
             RandomAccessFile raf = new RandomAccessFile(savedDirectoryPath + FILE_NAME, "rw");
             long length = raf.length();
             //supposing that last line is of 8 
             raf.setLength(length - 2);
             raf.close();
             }catch(Exception ex){
             ex.printStackTrace();
         }
    }
    
    public static void writeToFile(ArrayList<DataRow> rows, String savedDirectoryPath){
        FileWriter fileWriter;
        boolean isNew = false;
        try {
            File savedFile = new File(savedDirectoryPath + FILE_NAME);
            if(!savedFile.isFile()) {
                savedFile.createNewFile();
                isNew = true;
            }
            
            fileWriter = new FileWriter(savedDirectoryPath + FILE_NAME, true);
            
            if(isNew) {
                writeHeader(fileWriter, rows.get(0).getPixelValues().size());
            }else{
                removeLastCharacter(savedDirectoryPath);
            }
            
            for(DataRow row : rows){
                ArrayList<Integer> oneRowPixels = row.getPixelValues();
                
                for(int i = 0; i < oneRowPixels.size(); i++){
                    fileWriter.append(String.valueOf(oneRowPixels.get(i)));
                    fileWriter.append(COMMA_DELIMITER);
                }
                switch (row.getSampleType()) {
                    case 0:
                        fileWriter.append(NORMAL_CLASS);
                        break;
                    case 1:
                        fileWriter.append(ABNORMAL_CLASS);
                        break;
                    default:
                        fileWriter.append(UNPREDICTED_CLASS);
                        break;
                }
                
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            
            fileWriter.append(END_FILE);
            
            fileWriter.flush();
            fileWriter.close();
            
            rows.clear();
        } catch (IOException ex) {
            Logger.getLogger(ARFFFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
