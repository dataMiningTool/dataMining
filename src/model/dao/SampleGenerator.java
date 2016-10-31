package model.dao;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import model.datastore.DataRow;
import model.datastore.Sample;
import model.helper.StringHelper;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class SampleGenerator {
    private final ImageUtils imgObj;
    private PixelValuesGetter plgObj;
    
    private ArrayList<String> photos;
    private String directoryPath;

    public SampleGenerator() {
        this.imgObj = new ImageUtils();
        this.photos = new ArrayList<>();
    }

    public void setPlgObj(PixelValuesGetter plgObj) {
        this.plgObj = plgObj;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }
    
    public ArrayList<DataRow> generateByAuto(){
        ArrayList<ArrayList<Integer>> listOfSlideValues = new ArrayList<>();
        this.plgObj = new PixelValuesGetter();
        ArrayList<Integer> numberOfPixelsPerSlide = new ArrayList<>();
        int minMaskArea = Integer.MAX_VALUE;
        Polygon actualMask = new Polygon();
        
        for(String photo : this.photos){
            Polygon mask= createPolygonByMask(photo);
            Rectangle boundingBoxOfMask = mask.getBounds();
            //Tinh dien tich
            int maskArea = (int) boundingBoxOfMask.getHeight() * (int) boundingBoxOfMask.getWidth();
            
            if(maskArea < minMaskArea){
                minMaskArea = maskArea;
                actualMask = mask;
            }
        }
        
        for(String photo : this.photos){
            this.imgObj.setFilePath(StringHelper.getAbsolutePath(this.directoryPath, photo));
            this.plgObj.setPolygon(actualMask);
            ArrayList<Integer> pixelValues = this.plgObj.getPixelValuesByPolygon(this.imgObj.loadMat());
            listOfSlideValues.add(pixelValues);
        }
        
        for(ArrayList<Integer> slideValues : listOfSlideValues){
            numberOfPixelsPerSlide.add(slideValues.size());
        }
        
        int minNumberOfPixelsPerSlide = Collections.min(numberOfPixelsPerSlide);
        
        for(ArrayList<Integer> slideValues : listOfSlideValues){
            for(int i = minNumberOfPixelsPerSlide; i <= slideValues.size() - 1; i++){
                slideValues.remove(i);
            }
        }
        
        Sample sample = new Sample();
        sample.setSampleData(listOfSlideValues);
        sample.setSampleType(2);
        
        return sample.dataReformat();
    }
    
    public ArrayList<DataRow> generateByPolygon(int sampleType){
        ArrayList<ArrayList<Integer>> listOfSlideValues = new ArrayList<>();
        
        for(String photo : this.photos){
            this.imgObj.setFilePath(StringHelper.getAbsolutePath(this.directoryPath, photo));
            ArrayList<Integer> pixelValues = this.plgObj.getPixelValuesByPolygon(this.imgObj.loadMat());
            listOfSlideValues.add(pixelValues);
        }
        
        Sample sample = new Sample();
        sample.setSampleData(listOfSlideValues);
        sample.setSampleType(sampleType);
        
        return sample.dataReformat();
    }   
    
    private Polygon createPolygonByMask(String photo){
        this.imgObj.setFilePath(StringHelper.getAbsolutePath(this.directoryPath, photo));
        Mat mask = this.imgObj.createMask();
        Mat contoursFrame = mask.clone();
        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(contoursFrame, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
        
        Polygon polygon = new Polygon();
        
        for(MatOfPoint matOfPoint : contours){
            for(Point point : matOfPoint.toList()){
                polygon.addPoint((int)point.x, (int)point.y);
            }
        }
        
        return polygon;
    }
}
