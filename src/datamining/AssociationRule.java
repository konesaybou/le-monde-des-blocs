package datamining;

import modelling.*;
import java.util.*;

public class AssociationRule{

    private Set<BooleanVariable> premise;
    private Set<BooleanVariable> conclusion;
    private float frequency;
    private float confidence;

    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float frequency, float confidence){
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = frequency;
        this.confidence = confidence;
    }

    public Set<BooleanVariable> getConclusion() {
        return this.conclusion;
    }

    public float getFrequency() {
        return this.frequency;
    }

    public float getConfidence() {
        return this.confidence;
    }

    public Set<BooleanVariable> getPremise(){
        return this.premise;
    }

    @Override
    public String toString() {
        return "AssociationRule [premise=" + premise + ", conclusion=" + conclusion + ", frequency=" + frequency
                + ", confidence=" + confidence + "]";
    }




}
