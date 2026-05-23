package cp;
import modelling.*;
import java.util.*;

public class DomainSizeVariableHeuristic implements VariableHeuristic{
    private boolean preference;

    public DomainSizeVariableHeuristic(boolean preference){
        this.preference = preference;
    }
@Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains){

        //Fonction Lambda(anonyme)
        Comparator<Variable> comparator = (v1, v2) -> {
            int size1 = domains.get(v1).size();
            int size2 = domains.get(v2).size();
            return Integer.compare(size1, size2);
        };
        // Sélection de la variable avec le domaine le plus petit ou soit le plus grand
        if(preference == true){
            return Collections.max(variables, comparator);
        }
        return Collections.min(variables, comparator);
        
    }

    @Override
    public String toString() {
        return "DomainSizeVariableHeuristic [preference=" + preference + "]";
    }
}