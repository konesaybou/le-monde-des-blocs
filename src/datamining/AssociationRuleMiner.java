package datamining;

import java.util.*;

import datamining.AssociationRule;

public interface AssociationRuleMiner {

    public BooleanDatabase getDatabase();
    public Set<AssociationRule> extract(float minFrequency, float minConfidence);
    
}
