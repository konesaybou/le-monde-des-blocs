package planning;

import java.util.*;
import modelling.*;

public class BFSPlanner implements Planner {
    private Map<Variable, Object> initialState;
    private Set<Action> actions;
    private Goal goal; 
    private int sonde;
    private boolean active;

    public BFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.sonde = 0;
        this.active = false;  
    }

    @Override
    public List<Action> plan() {
        return bfs();
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



    public List<Action> bfs() {
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Set<Map<Variable, Object>> closed = new HashSet<>();
        Queue<Map<Variable, Object>> open = new LinkedList<>();
        open.add(initialState);
        father.put(initialState, null);

        if (goal.isSatisfiedBy(initialState)) {
            return new ArrayList<>();
        }

        while (!open.isEmpty()) {
            Map<Variable, Object> instantiation = open.remove();
            closed.add(instantiation);

            if(this.active){ this.sonde += 1;}

            for (Action action : actions) {
                if (action.isApplicable(instantiation)) {
                    Map<Variable, Object> next = action.successor(instantiation);
                    if (!closed.contains(next) && !open.contains(next)) {
                        father.put(next, instantiation);
                        plan.put(next, action);
                        if (goal.isSatisfiedBy(next)) {
                            return getBfsPlan(father, plan, next);
                        } else {
                            open.add(next);
                        }
                    }
                }
            }
        }
        return null;
    }

    public List<Action> getBfsPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, 
                                     Map<Map<Variable, Object>, Action> plan, 
                                     Map<Variable, Object> goal) {
        List<Action> bfsPlan = new LinkedList<>();

        while (goal != null) {
            Action action = plan.get(goal);
            if (action != null) {
                bfsPlan.add(0, action);
            }
            goal = father.get(goal);
        }

        //Collections.reverse(bfsPlan); 
        return bfsPlan;
    }


    @Override
    public String toString(){
        String res = "etat initial : "+this.initialState+"\n";
        res += "\n actions : "+this.actions+"\n";
        res += " but : "+this.goal+"\n";
        return res;
    }




}
