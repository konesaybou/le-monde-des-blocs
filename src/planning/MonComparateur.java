package planning;

import java.util.*;
import modelling.*;

public class MonComparateur implements Comparator<Map<Variable,Object>>{

    private Map<Map<Variable,Object>,Float> distance;

    public MonComparateur(Map<Map<Variable,Object>,Float> distance){
      this.distance = distance;
    }

    @Override
    public int compare(Map<Variable,Object> obj1, Map<Variable,Object> obj2) {
        Float etat1 = distance.getOrDefault(obj1, Float.POSITIVE_INFINITY);
        Float etat2 = distance.getOrDefault(obj2, Float.POSITIVE_INFINITY);
       return Float.compare(etat1,etat2);
    }
    
}
