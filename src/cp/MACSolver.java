package cp;
import modelling.*;
import java.util.*;

public class MACSolver extends AbstractSolver{
    private ArcConsistency arcConsistency;

    public MACSolver(Set<Variable> variables, Set<Constraint> constraints){
        super(variables, constraints);
        this.arcConsistency = new ArcConsistency(constraints);
    }

    public ArcConsistency getArcConsistency() {
        return this.arcConsistency;
    }

    @Override
    public Map<Variable, Object> solve(){
        Map<Variable, Set<Object>> domains = new HashMap<>();
        for (Variable variable : variables) {
            domains.put(variable, variable.getDomain());
        }
        return macSolver(new HashMap<>(), new LinkedList<Variable>(variables), domains);
    }

    public Map<Variable, Object> macSolver(Map<Variable, Object> partialInstantiation, LinkedList<Variable> uninstantiatedVariable, Map<Variable, Set<Object>> domains){
        if(uninstantiatedVariable.isEmpty()){
            return partialInstantiation;
        } else {
            
            if(!this.arcConsistency.ac1(domains)){
                return null;
            }
            Variable xi = uninstantiatedVariable.poll();
            for(Object vi : xi.getDomain()){
                Map<Variable, Object> n = new HashMap<>(partialInstantiation);
                n.put(xi, vi);
                Map<Variable, Set<Object>> domain = new HashMap<>();
                domain.putAll(domains);
                domain.put(xi, new HashSet<>(Set.of(vi)));
                if(isConsistent(n)){
                    Map<Variable, Object> r = new HashMap<>();
                    r = macSolver(n, uninstantiatedVariable, domain);
                    if(r != null){
                        return r;
                    }
                }
            }
            uninstantiatedVariable.add(xi);
            return null;
        }
    }

    @Override
    public String toString() {
        return "MACSolver [arcConsistency=" + this.arcConsistency + "]";
    }
}