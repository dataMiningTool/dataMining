package model.dao;

import java.awt.Polygon;
import java.util.ArrayList;
import model.datastore.DataRow;
import model.io.ARFFFileWriter;

public class SampleConfig {
    private final SampleGenerator generator;
    
    private int sampleType;
    private boolean isAuto = true;
    private String savedDirectoryPath;

    public SampleConfig() {
        this.generator = new SampleGenerator();
    }

    public void setSampleType(int sampleType) {
        this.sampleType = sampleType;
    }

    public void setDirectoryPath(String directoryPath) {
        this.generator.setDirectoryPath(directoryPath);
    }

    public void setSavedDirectoryPath(String savedDirectoryPath) {
        this.savedDirectoryPath = savedDirectoryPath;
    }

    public void setPolygon(Polygon polygon) {
        this.generator.setPlgObj(new PixelValuesGetter(polygon));
    }

    public void setPhotos(ArrayList<String> photos) {
        this.generator.setPhotos(photos);
    }

    public void setIsAuto(boolean isAuto) {
        this.isAuto = isAuto;
    }
    
    public void createSampleDataFile(){
        ArrayList<DataRow> dataRows;
        if(!isAuto){
            dataRows = generator.generateByPolygon(sampleType);
        }else{
            dataRows = generator.generateByAuto();
        }
           
        ARFFFileWriter.writeToFile(dataRows, this.savedDirectoryPath);
    }
    
}
