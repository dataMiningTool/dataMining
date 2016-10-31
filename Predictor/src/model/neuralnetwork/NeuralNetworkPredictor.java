package model.neuralnetwork;

import java.io.File;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.helper.XMLParser;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class NeuralNetworkPredictor {
    private MultilayerPerceptron neuronNetwork;
    private static final String MODEL_PATH = "\\resources\\neuronnetwork\\mlp.model";

    public NeuralNetworkPredictor() {
        loadModel();
    }
    
    private void loadModel(){
        File temp = Paths.get(".").toAbsolutePath().normalize().toFile();
        try {
            this.neuronNetwork = (MultilayerPerceptron) SerializationHelper.read(temp + MODEL_PATH);
        } catch (Exception ex) {
            Logger.getLogger(NeuralNetworkPredictor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isSick(Instances instances){
        Instances predicteddata = new Instances(instances);
        
        for (int i = 0; i < instances.numInstances(); i++) {
            try {
                double clsLabel = this.neuronNetwork.classifyInstance(instances.instance(i));
                predicteddata.instance(i).setClassValue(clsLabel);
            } catch (Exception ex) {
                Logger.getLogger(NeuralNetworkPredictor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        int[] nominalCounts = predicteddata.attributeStats(predicteddata.numAttributes() - 1).nominalCounts;
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.HALF_UP);
        
        double rate = Double.parseDouble(df.format((double)nominalCounts[1] / (double)nominalCounts[0])); 
        double threshold = Double.parseDouble(df.format(XMLParser.getRateThreshold()));
        System.out.println(rate);
        System.out.println(threshold);
        
        
        return rate > threshold;
    }
}
