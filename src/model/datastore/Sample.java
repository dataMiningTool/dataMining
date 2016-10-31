package model.datastore;

import java.util.ArrayList;

public class Sample {
    private ArrayList<ArrayList<Integer>> sampleData;
    private int sampleType;

    public Sample() {
        this.sampleData = new ArrayList<>();
    }

    public int getSampleType() {
        return sampleType;
    }

    public void setSampleType(int sampleType) {
        this.sampleType = sampleType;
    }

    public ArrayList<ArrayList<Integer>> getSampleData() {
        return sampleData;
    }

    public void setSampleData(ArrayList<ArrayList<Integer>> sampleData) {
        this.sampleData = sampleData;
    }
    
    public ArrayList<DataRow> dataReformat(){
        ArrayList<DataRow> dataRows = new ArrayList<>();
        
        ArrayList<Integer> onePhotoPixelValues = this.sampleData.get(0);
        int numOfRow = onePhotoPixelValues.size();
        int numOfColumn = this.sampleData.size();
        
        for(int i = 0; i < numOfRow; i++){
            ArrayList<Integer> oneRowPixels = new ArrayList<>();
            
            for(int j = 0; j < numOfColumn; j++){
                oneRowPixels.add(this.sampleData.get(j).get(i));
            }
            
            DataRow row = new DataRow();
            
            row.setSampleType(this.sampleType);
            row.setPixelValues(oneRowPixels);
            
            dataRows.add(row);
        }
        
        return dataRows;
    }
}
