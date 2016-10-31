package model.datastore;

import java.util.ArrayList;

public class DataRow {
    private ArrayList<Integer> pixelValues;
    private int sampleType;

    public DataRow() {
        pixelValues = new ArrayList<>();
    }

    public void setPixelValues(ArrayList<Integer> pixelValues) {
        this.pixelValues = pixelValues;
    }

    public ArrayList<Integer> getPixelValues() {
        return pixelValues;
    }

    public void setSampleType(int sampleType) {
        this.sampleType = sampleType;
    }

    public int getSampleType() {
        return sampleType;
    }
    
}
