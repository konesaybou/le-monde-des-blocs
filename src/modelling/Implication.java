package modelling;

import java.util.*;

public class Implication implements Constraint{

    protected Variable v1;
    protected Variable v2;
    protected Set<Object> subSet1;
    protected Set<Object> subSet2;

    public Implication(Variable v1, Set<Object> subSet1, Variable v2, Set<Object> subSet2){
        this.v1 = v1;
        this.v2 = v2;
        this.subSet1 = subSet1;
        this.subSet2 = subSet2;
    }

    @Override
    public Set<Variable> getScope(){
        Set<Variable> varaibleSet = new HashSet<>();
        varaibleSet.add(this.v1);
        varaibleSet.add(this.v2);
        return varaibleSet;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> affectation){

        for(Variable var : this.getScope() ){
            if(!affectation.containsKey(var) ){
                throw new IllegalArgumentException("La "+var.getName()+" n'a pas d'affectation \n");
            }
        }

        if(this.subSet1.contains(affectation.get(this.v1))){
            return this.subSet2.contains(affectation.get(this.v2));            
        }

        return true;

    }

    @Override
    public String toString() {
        return "Implication [v1: " + v1 + ", v2: " + v2 + ", subSet1: " + subSet1 + ", subSet2: " + subSet2 + "]";
    }





}