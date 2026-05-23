package planning;

import java.util.*;
import modelling.*;

public class DFSPlanner implements Planner {
    private Map<Variable, Object> initialState; 
    private Set<Action> actions; 
    private Goal goal; 
    private int sonde;
    private boolean active;

    public DFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.sonde = 0;
        this.active = false;
        
    }

    @Override
    public List<Action> plan() {
        Stack<Action> actionsStack = new Stack<>();
        Set<Map<Variable, Object>> closed = new HashSet<>();
        closed.add(this.initialState);
        List<Action> planFinal = dfs(this.initialState, actionsStack, closed);
        
        return planFinal;
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    @Override
    public Set<Action> getActions() {
        return this.actions;
    }

    @Override
    public Goal getGoal() {
        return this.goal;
    }

    @Override
    public void activateNodeCount(boolean activate) {
        active = activate;
    }


    public int getSonde(){
        return sonde;
    }


    public List<Action> dfs(Map<Variable, Object> instantiation, Stack<Action> plan, Set<Map<Variable, Object>> closed) {

        if(this.active){ this.sonde += 1;}

        if (goal.isSatisfiedBy(instantiation)) {
            return new ArrayList<>(plan);
        }

        for (Action action : this.getActions()) {
            if (action.isApplicable(instantiation)) {
                Map<Variable, Object> next = action.successor(instantiation);
                if (!closed.contains(next)) {
                    plan.push(action);
                    closed.add(next);
                    List<Action> subplan = dfs(next, plan, closed);
                    if (subplan != null && !subplan.isEmpty()) {
                        return subplan;
                    } else {
                        plan.pop(); 
                        
                    }
                }
            }
        }
        return null; 
    }


    @Override
    public String toString(){
        String res = "etat initial : "+this.initialState+"\n";
        res += "\n actions : "+this.actions+"\n";
        res += " but : "+this.goal+"\n";
        return res;
    }


}
