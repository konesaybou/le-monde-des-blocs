package cp;
import modelling.*;
import java.util.*;

public class HeuristicMACSolver extends AbstractSolver{
    private VariableHeuristic heuristicVariable;
    private ValueHeuristic heuristicValue;

    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic heuristicVariable, ValueHeuristic heuristicValue){
        super(variables, constraints);
        this.heuristicVariable = heuristicVariable;
        this.heuristicValue = heuristicValue;
    }

    @Override
    public Map<Variable, Object> solve(){
        Map<Variable, Set<Object>> domains = new HashMap<>();
        for(Variable variable : this.variables){
            domains.put(variable, variable.getDomain());
        }
        return macSolver(new HashMap<>(), new LinkedList<Variable>(this.variables), domains);
    }

    public Map<Variable, Object> macSolver(Map<Variable, Object> partialInstantiation, LinkedList<Variable> uninstantiatedVariable, Map<Variable, Set<Object>> domains){
        if(uninstantiatedVariable.isEmpty()){
            return partialInstantiation;
        } else {
            ArcConsistency arcConsistency = new ArcConsistency(this.constraints);
            if(!arcConsistency.ac1(domains)){
                return null;
            }
            Variable variable = heuristicVariable.best(new HashSet<>(uninstantiatedVariable),domains);
            uninstantiatedVariable.remove(variable);
            for(Object object : heuristicValue.ordering(variable, domains.get(variable))){
                Map<Variable, Object> n = new HashMap<>(partialInstantiation);
                n.put(variable, object);
                Map<Variable, Set<Object>> domain = new HashMap<>();
                domain.putAll(domains);
                domain.put(variable, new HashSet<>(Set.of(object)));
                if(isConsistent(n)){
                    Map<Variable, Object> r = new HashMap<>();
                    r = macSolver(n, uninstantiatedVariable, domain);
                    if(r != null){
                        return r;
                    }
                }
            }
            uninstantiatedVariable.add(variable);
            return null;
        }
    }


    @Override
    public String toString() {
        return "HeuristicMACSolver [heuristicVariable=" + heuristicVariable + ", heuristicValue=" + heuristicValue
                + "]";
    }


}