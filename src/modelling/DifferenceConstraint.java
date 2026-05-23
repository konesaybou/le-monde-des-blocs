package modelling;

import java.util.*;

public class DifferenceConstraint implements Constraint {

    protected Variable v1;
    protected Variable v2;

    public DifferenceConstraint(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
        // this.v1 = new Variable("v1",new HashSet<Object>());
        // this.v2 = new Variable("v2",new HashSet<Object>());
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> variableSet = new HashSet<>();
        variableSet.add(this.v1);
        variableSet.add(this.v2);
        return variableSet;

    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> affectation) {

        for (Variable var : this.getScope()) {
            if (!affectation.containsKey(var)) {
                throw new IllegalArgumentException("La " + var.getName() + " n'a pas d'affectation \n");
            }
        }

        return (!affectation.get(this.v1).equals(affectation.get(this.v2)));
    }

    @Override
    public String toString() {
        return "DifferenceConstraint [v1: " + v1 + ", v2: " + v2 + "]";
    }


    
}