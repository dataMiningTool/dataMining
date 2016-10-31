package model.dao;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import model.io.CSVFileWriter;
import org.opencv.core.Mat;

public class PixelValuesGetter {
    private Polygon polygon;
    
    public PixelValuesGetter() {
        this.polygon = new Polygon();
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public PixelValuesGetter(Polygon polygon) {
        this.polygon = polygon;
    }
    
    public ArrayList<Integer> getPixelValuesByPolygon(Mat grayMat){
        ArrayList<Integer> pixelValues = new ArrayList<>();
        ArrayList<Point> coordinateValues = new ArrayList<>();
        Rectangle boundaryBox = this.polygon.getBounds();
        
        double minX = boundaryBox.getMinX();
        double maxX = boundaryBox.getMaxX();
        double minY = boundaryBox.getMinY();
        double maxY = boundaryBox.getMaxY();
        
        for(int x = (int) minX; x <= maxX; x++){
            for(int y = (int) minY; y <= maxY; y++){
                if(this.polygon.contains(x, y)){
                    pixelValues.add((int)grayMat.get(y, x)[0]);
                    coordinateValues.add(new Point(x, y));
                }
            }
        }
        
        CSVFileWriter.writeToFile(coordinateValues);
        return pixelValues;
    }
}
