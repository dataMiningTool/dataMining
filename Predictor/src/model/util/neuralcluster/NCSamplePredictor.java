package model.util.neuralcluster;

import io.CSVFileWriter;
import java.awt.Point;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.helper.XMLParser;
import model.classifier.NeuralNetworkClassifier;
import model.util.Predictor;
import weka.core.Instances;

public class NCSamplePredictor extends Predictor{
    private final int offset;
    private final int positiveResultThreshold;
    private final double rateThreshold;
    private final DecimalFormat df;
        
    private final NCSampleProcessor sampleProcessor;
    private final NeuralNetworkClassifier neuralNetworkClassifier;
    private final NCSampleTransformer sampleTransformer;
    
    // This variable indicates if will get all files of "directory" for predict or not
    private boolean isFullFolder;

    public NCSamplePredictor() {
        this.df = new DecimalFormat("#.####");
        this.df.setRoundingMode(RoundingMode.HALF_UP);
        
        this.offset = XMLParser.getSlides();
        this.positiveResultThreshold = XMLParser.getPositiveResultThreshold();
        this.rateThreshold = Double.parseDouble(this.df.format(XMLParser.getRateThreshold()));
        this.sampleProcessor = new NCSampleProcessor();
        this.neuralNetworkClassifier = new NeuralNetworkClassifier();
        this.sampleTransformer = new NCSampleTransformer();
    }
       
    public NCSamplePredictor setIsFullFolder(boolean value){
        this.isFullFolder = value;
        return this;
    }
    
    @Override
    protected boolean isSick(ArrayList<String> photos){
        ArrayList<Boolean> predictedResult = new ArrayList<>();
        int startIndex = (this.isFullFolder) ? 0 : (photos.size() / 2 - 1);
        
        for(int i = startIndex; i <= photos.size() - this.offset; i++){
            ArrayList<String> subPhotos = new ArrayList<>(photos.subList(i, i + this.offset));
            predictedResult.add(isGOEtoRateThreshold(subPhotos, i));
        }
        
        return isGOEtoSlideThreshold(predictedResult);
    }
    
    private boolean isGOEtoRateThreshold(ArrayList<String> subPhotos, int indexSubPhotos){
        this.sampleProcessor.setPhotos(subPhotos);
            
        Instances instances = this.sampleProcessor.generateSample();
        Instances predictedInstances = this.neuralNetworkClassifier.classify(instances);
        ArrayList<Point> coordinates = this.sampleProcessor.getCoordinateByPolygon();
        
        this.sampleTransformer.setCoordinates(coordinates);
        this.sampleTransformer.setDataSet(predictedInstances);
        
        this.sampleTransformer.categorizeData();
        
        ArrayList<Point> realNormalCoordinates = this.sampleTransformer.getRealNormalCoordinates();
        ArrayList<Point> realAbnormalCoordinates = this.sampleTransformer.getRealAbnormalCoordinates();
        
        CSVFileWriter.writeToFile(realNormalCoordinates, "Normal", indexSubPhotos);
        CSVFileWriter.writeToFile(realAbnormalCoordinates, "Abnormal", indexSubPhotos);
        
        System.out.println("rate"+ String.valueOf(getRate()));
        
        return getRate() >= this.rateThreshold;
    }
    
    private double getRate(){
        double numOfAbnormalPoints = (double)this.sampleTransformer.getRealAbnormalCoordinates().size();
        double numOfNormalPoints = (double)this.sampleTransformer.getRealNormalCoordinates().size();
        
        System.out.println("normal: " + numOfNormalPoints);
        System.out.println("abnormal: " + numOfAbnormalPoints);
        
        double rate = 0.0;
                
        if(numOfNormalPoints != 0){
            rate = Double.parseDouble(this.df.format(numOfAbnormalPoints / numOfNormalPoints));
        }
        
        return rate;
    }
    
    private boolean isGOEtoSlideThreshold(ArrayList<Boolean> predictedResult){
        int positiveResult = 0;
        
        for(boolean result : predictedResult){
            if(result){
                positiveResult++;
            }
        }
        return positiveResult >= this.positiveResultThreshold;
    }
}
