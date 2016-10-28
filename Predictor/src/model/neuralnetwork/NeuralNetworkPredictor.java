package model.neuralnetwork;

import java.io.File;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.helper.XMLParser;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class NeuralNetworkPredictor {
    private MultilayerPerceptron neuronNetwork;
    private static final String MODEL_PATH = "/resources/neuron network/mlp.model";

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
        float rate = nominalCounts[1] / nominalCounts[0];
                
        float threshold = XMLParser.getRateThreshold();
        
        return rate > threshold;
    }
}
