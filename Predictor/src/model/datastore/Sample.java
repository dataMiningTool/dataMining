package model.datastore;

import java.util.ArrayList;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class Sample {
    private static final String NOT_PREDICTED_CHARACTER = "?";
    private static final String ATTRIBUTE_NAME = "slide";
    private static final String CLASS_NAME = "class";
    private static final String RELATION_NAME = "DataMiningDataset";
    private static final String NORMAL_CLASS = "Normal";
    private static final String ABNORMAL_CLASS = "Abnormal";
    
    private ArrayList<ArrayList<Integer>> sampleData;

    public Sample() {
        this.sampleData = new ArrayList<>();
    }

    public void setSampleData(ArrayList<ArrayList<Integer>> sampleData) {
        this.sampleData = sampleData;
    }
    
    private FastVector getHeader(){
        int numOfColumn = this.sampleData.size();
        FastVector wekaHeader = new FastVector(numOfColumn + 1);
        
        for(int i = 1; i <= numOfColumn; i++){
            Attribute attribute = new Attribute(ATTRIBUTE_NAME + String.valueOf(i));
            wekaHeader.add(attribute);
        }
        
        FastVector fvClassVal = new FastVector(2);
        fvClassVal.addElement(NORMAL_CLASS);
        fvClassVal.addElement(ABNORMAL_CLASS);
        Attribute classAttribute = new Attribute(CLASS_NAME, fvClassVal);
        
        wekaHeader.add(classAttribute);
        
        return wekaHeader;
    }
    
    public Instances getInstances(){
        FastVector header = getHeader();
        int capacity = this.sampleData.get(0).size();
        
        int numOfAttribute = header.size() - 1;
        int lastIndex = header.size() - 1;
        
        Instances instances = new Instances(RELATION_NAME, header,capacity);
        instances.setClassIndex(lastIndex);
        
        for(int instanceIndex = 0; instanceIndex < capacity; instanceIndex++){
            Instance instance = new DenseInstance(header.size());
            
            for(int attributeIndex = 0; attributeIndex < numOfAttribute; attributeIndex++){
                instance.setValue((Attribute)header.get(attributeIndex), this.sampleData.get(attributeIndex).get(instanceIndex));
            } 
            
            instance.setValue((Attribute)header.get(lastIndex), NOT_PREDICTED_CHARACTER);
            instances.add(instance);
        }
        
        return instances;
    }
}
