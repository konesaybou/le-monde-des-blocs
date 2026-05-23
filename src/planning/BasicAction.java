package planning;

import java.util.*;
import modelling.*;


public class BasicAction implements Action{

    private Map<Variable,Object> preCodition;
    private Map<Variable,Object> effet;
    private int cost;

    public BasicAction(Map<Variable,Object> preCodition, Map<Variable,Object> effet, int cost){
        
        this.preCodition = preCodition;
        this.effet = effet;
        this.cost = cost;
    }


    @Override
    public boolean isApplicable(Map<Variable, Object> etat) {
            for(Variable var : this.preCodition.keySet()){
                if(!etat.containsKey(var)|| !etat.get(var).equals(this.preCodition.get(var))){
                    return false;
                }
            }

        return true;

    }

    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> etat) {
        Map<Variable,Object> nextState= new HashMap<>(etat);
        nextState.putAll(this.effet);
        return nextState;

    }


    @Override
    public int getCost(){
        return this.cost;
    }

    @Override
    public String toString(){
        String res ="";
        res += "\n prec : "+this.preCodition+" ; ";
        res += "; eff : "+this.effet+ " ";
        res += "; cout : "+this.cost+ "";
        return res;
    }

}