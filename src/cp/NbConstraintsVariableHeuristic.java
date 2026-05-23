package cp;
import modelling.*;
import java.util.*;

public class NbConstraintsVariableHeuristic implements VariableHeuristic{
    private Set<Constraint> constraints;
    private boolean preference;

     public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean preference){
        this.constraints = constraints;
        this.preference = preference;
     }

    //Méthode retournant la meilleure variable selon l'heuristique(selon le nombre de contraintes
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains){
        Map<Variable, Integer> bestVariable = new HashMap<>();
        //Stocker la variable et le nombre de contraintes dans un map
        for(Variable variable : variables){
            int nbConstraints = 0;
            for(Constraint constraint : constraints){
                if(constraint.getScope().contains(variable)){
                    nbConstraints++;
                }
            }
            bestVariable.put(variable, nbConstraints);
        }
        if(preference == true){
            Map.Entry<Variable, Integer> max = Collections.max(bestVariable.entrySet(), Map.Entry.comparingByValue());
            return max.getKey();
        } else {
            Map.Entry<Variable, Integer> min = Collections.min(bestVariable.entrySet(), Map.Entry.comparingByValue());
            return min.getKey();
        }
    }

    @Override
    public String toString() {
        return "NbConstraintsVariableHeuristic [constraints=" + constraints + ", preference=" + preference + "]";
    }


}