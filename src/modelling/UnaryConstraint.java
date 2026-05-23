package modelling;

import java.util.*;

public class UnaryConstraint implements Constraint{

    protected Variable v;

    protected Set<Object> subSet;


    public UnaryConstraint(Variable v, Set<Object> subSet){
        this.v = v;
        this.subSet = subSet;

    }

    @Override
    public Set<Variable> getScope(){
        Set<Variable> varaibleSet = new HashSet<>();
        varaibleSet.add(this.v);
        return varaibleSet;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> affectation){

        for(Variable var : this.getScope() ){
            if(!affectation.containsKey(var) ){
                throw new IllegalArgumentException("La "+var.getName()+" n'a pas d'affectation \n");
            }
        }

        return this.subSet.contains(affectation.get(this.v));

    }

    @Override
    public String toString() {
        return "UnaryConstraint [v: " + v + ", subSet: " + subSet + "]";
    }





}