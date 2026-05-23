package cp;
import java.util.*;
import modelling.*;

public class BacktrackSolver extends AbstractSolver{

    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints){
        super(variables, constraints);
    }

    @Override
    public Map<Variable, Object> solve(){
        return backtrack(new HashMap<>(), new LinkedList<Variable>(variables));
    }

    public Map<Variable, Object> backtrack(Map<Variable, Object> partialInstantiation, LinkedList<Variable> uninstantiatedVariable){
        if(uninstantiatedVariable.isEmpty()){
            return partialInstantiation;
        }
        Variable xi = uninstantiatedVariable.poll();
        for(Object vi: xi.getDomain()){
            Map<Variable, Object> n = new HashMap<>(partialInstantiation);
            n.put(xi, vi);
            if(isConsistent(n)){
                Map<Variable, Object> r = backtrack(n, uninstantiatedVariable);
                if(r != null){
                    return r;
                }
            }
            
        }
        uninstantiatedVariable.add(xi);
        return null;
    }

    
}