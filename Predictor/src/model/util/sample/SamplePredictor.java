package model.util.sample;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import model.helper.StringHelper;
import model.helper.XMLParser;
import model.neuralnetwork.NeuralNetworkPredictor;
import model.util.Predictor;
import weka.core.Instances;

public class SamplePredictor implements Callable{
    private final int offset;
    private final int positiveResultThreshold;
    private final SampleGenerator sampleGenerator;
    private final Predictor neuralNetworkPredictor;
    private File directory;

    public SamplePredictor() {
        this.offset = XMLParser.getSlides();
        this.positiveResultThreshold = XMLParser.getPositiveResultThreshold();
        this.sampleGenerator = new SampleGenerator();
        this.neuralNetworkPredictor = new NeuralNetworkPredictor();       
    }
    
    public SamplePredictor(File directory) {
        this.offset = XMLParser.getSlides();
        this.positiveResultThreshold = XMLParser.getPositiveResultThreshold();
        this.sampleGenerator = new SampleGenerator();
        this.neuralNetworkPredictor = new NeuralNetworkPredictor(); 
        this.directory = directory;
    }
    
    public boolean isSick(File directory){
        ArrayList<String> photos = getPhotos(directory);
        int lastIndex = photos.size() - 1;
        
        ArrayList<Boolean> predictedResult = new ArrayList<>();
        int startIndex = photos.size() / 2 - 1;
                
        for(int i = startIndex; i <= lastIndex - this.offset; i++){
            ArrayList<String> subPhotos = new ArrayList<>(photos.subList(i, i + this.offset));
            
            this.sampleGenerator.setPhotos(subPhotos);
            Instances instances = this.sampleGenerator.generateSample();
            predictedResult.add(this.neuralNetworkPredictor.isSick(instances));
        }
        
        return predict(predictedResult);
    }
    
    private boolean predict(ArrayList<Boolean> predictedResult){
        int positiveResult = 0;
        
        for(boolean result : predictedResult){
            if(result){
                positiveResult++;
            }
        }
        return positiveResult >= positiveResultThreshold;
    }
    
    private ArrayList<String> getPhotos(File directory){
        if(directory != null){
            String directoryPath = StringHelper.getDirectoryPath(directory);
            File[] filesInDirectory = directory.listFiles();
            ArrayList<String> photos = new ArrayList<>();
            
            for(File file : filesInDirectory){
                photos.add(StringHelper.getAbsolutePath(directoryPath, file.getName()));
            }
            
            return photos;
        }
        
        return null;
    }

    @Override
    public Object call() throws Exception {
        if (isSick(directory))
            return "Folder " + directory.getName() + ": xuat huyet";
        else
            return "Folder " + directory.getName() + ": binh thuong";
         
    }
}
