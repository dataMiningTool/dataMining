package model.util.neuralcluster;

import constant.Constant;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import model.classifier.DBscanClassifier;
import model.datastore.CoordinateSample;
import weka.core.Instance;
import weka.core.Instances;

public class NCSampleTransformer {
    private Instances dataSet;
    private ArrayList<Point> coordinates;
    private final ArrayList<Point> normalCoordinates;
    private final ArrayList<Point> abnormalCoordinates;
    private NCSampleNoiseRemover noiseRemover;

    public NCSampleTransformer() {
        normalCoordinates = new ArrayList<>();
        abnormalCoordinates = new ArrayList<>();
    }
    
    public void categorizeData(){
        int index = 0;
        
        for(Instance instance : this.dataSet){
            String classLabel = instance.stringValue(instance.attribute(instance.numAttributes() - 1));
            
            if(classLabel.equalsIgnoreCase(Constant.NORMAL_CLASS)){
                normalCoordinates.add(this.coordinates.get(index));
            }else{
                abnormalCoordinates.add(this.coordinates.get(index));
                noiseRemover = new NCSampleNoiseRemover(abnormalCoordinates);
            }
            
            index++;
        }
    }
    
    public int getNumOfNormalPoints(){
        return this.normalCoordinates.size();
    }
    
    public int getNumOfAbnormalPoints(){
        ArrayList<Point> realAbnormalPoints = noiseRemover.removeNoise();
        return realAbnormalPoints.size();
    }

    public void setDataSet(Instances dataSet) {
        this.dataSet = dataSet;
    }

    public void setCoordinates(ArrayList<Point> coordinates) {
        this.coordinates = coordinates;
    }
}
