package cp;
import java.util.*;
import modelling.*;

public abstract class AbstractSolver implements Solver{
    protected Set<Variable> variables;
    protected Set<Constraint> constraints;

    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints){
        this.variables = variables;
        this.constraints = constraints;
    }

    public Set<Variable> getVariables(){
        return this.variables;
    }

    public Set<Constraint> getConstraints(){
        return this.constraints;
    }

    //Méthode qui retourne true si l'affectation partielle des variables satisfait toutes les contraintes et false sinon
    public boolean isConsistent(Map<Variable, Object> partielInstantiation){
        for(Constraint constraint: this.constraints ){
            if(partielInstantiation.keySet().containsAll(constraint.getScope())){
                if(!constraint.isSatisfiedBy(partielInstantiation)){
                    return false;
                }
            }
        }
        return true;
    }


}