package model.util.neuralcluster;

import java.awt.Point;
import java.util.ArrayList;
import model.classifier.DBscanClassifier;
import model.datastore.CoordinateSample;
import model.detecter.NoiseDetecter;
import model.helper.XMLParser;
import weka.core.Instance;
import weka.core.Instances;

public class NCSampleNoiseRemover {
    private final ArrayList<Point> abnormalCoordinates;
    private final float clusterThreshold;
    private final NoiseDetecter detecter;

    public NCSampleNoiseRemover(ArrayList<Point> abnormalCoordinates) {
        this.abnormalCoordinates = abnormalCoordinates;
        this.clusterThreshold = XMLParser.getClusterThreshold();
        this.detecter = new NoiseDetecter();
        
    }
    
    public ArrayList<Point> removeNoise(){
        CoordinateSample coordinateSample = new CoordinateSample(this.abnormalCoordinates);
        Instances coordinateInstances = coordinateSample.getInstances();
        
        DBscanClassifier classifier = new DBscanClassifier(coordinateInstances);
        Instances predictedInstances = classifier.classify();
        double numOfGroup = predictedInstances.numDistinctValues(predictedInstances.numAttributes() - 1);
         
        for(double i = 0; i < numOfGroup; i++){
            ArrayList<Point> groupOfPoint = getPointsByGroup(predictedInstances, i);
            this.detecter.setGroupOfPoints(groupOfPoint);
            double meanDistance = this.detecter.getMeanDistance();
            
            //System.out.println(meanDistance);
            
            if(meanDistance >= this.clusterThreshold){
                predictedInstances = removeNoiseGroup(predictedInstances, i);
                this.abnormalCoordinates.removeAll(groupOfPoint);
            }
        }
        
        //System.out.println("/n");
        
        return this.abnormalCoordinates;
    }
    
    private ArrayList<Point> getPointsByGroup(Instances predictedInstances, double group){
        ArrayList<Point> groupOfPoint = new ArrayList<>();
        int index = 0;
     
        for(Instance instance : predictedInstances){
            if(instance.classValue() == group){
                groupOfPoint.add(this.abnormalCoordinates.get(index));
            }
            
            index++;
        }
        
        return groupOfPoint;
    }
    
    private Instances removeNoiseGroup(Instances predictedInstances, double group){
        ArrayList<Instance> removedInstances = new ArrayList<>();
        
        for(Instance instance : predictedInstances){
            if(instance.classValue() == group){
                removedInstances.add(instance);
            }
        }
        
        predictedInstances.removeAll(removedInstances);
        
        return predictedInstances;
    }
}
