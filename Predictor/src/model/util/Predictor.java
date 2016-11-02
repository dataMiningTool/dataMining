package model.util;

import weka.core.Instances;

public interface Predictor {
    public boolean isSick(Instances instances);
}
