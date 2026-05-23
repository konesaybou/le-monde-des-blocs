package planning;

import java.util.*;
import modelling.*;

public class BasicGoal implements Goal{

    private Map<Variable, Object> instanciationPartielle;

    public BasicGoal(Map<Variable, Object> instanciationPartielle){
        this.instanciationPartielle = instanciationPartielle;
    }

    public boolean isSatisfiedBy(Map<Variable, Object> etat){

        if(etat == null){
            return false;
        }

        for (Map.Entry<Variable,Object>  var : this.instanciationPartielle.entrySet()){
            Variable varKey = var.getKey();
            Object varValue = var.getValue();

            if (!etat.containsKey(varKey) || !etat.get(varKey).equals(varValue)){
                return false;
            } 
        }

        return true;
    }

    @Override
    public String toString(){
        String res = "instantiation partielle : "+this.instanciationPartielle;
        return res;
    }


}