package planning;

import java.util.*;
import modelling.*;

public class AStarPlanner implements Planner{

    private Map<Variable, Object> initialState; 
    private Set<Action> actions; 
    private Goal goal; 
    private MonComparateur comparateur;
    private Heuristic heuristic;
    private BFSPlanner bfsPlan;
    private int sonde;
    private boolean active;

    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
        this.sonde = 0;
        this.active = false;
    }


    @Override
    public List<Action> plan(){

        return this.aStar();
    }

    public List<Action> aStar(){

        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable,Object>,Float> distance = new HashMap<>();
        Map<Map<Variable,Object>,Float> value = new HashMap<>();  
        Map<Variable,Object> instantiation = new HashMap<>();
        Map<Variable,Object> next = new HashMap<>();

        comparateur = new MonComparateur(distance);

        PriorityQueue<Map<Variable,Object>> open = new PriorityQueue<>(comparateur);

        open.add(this.initialState);
        distance.put(this.initialState,0.0f);
        father.put(this.initialState,null);
        value.put(this.initialState,this.heuristic.estimate(this.initialState));

        while(!open.isEmpty()){

            instantiation = open.poll();
            if(this.active){ this.sonde += 1;}
            if(goal.isSatisfiedBy(instantiation)){
                return getBfsPlan(father,plan,instantiation);
            }

            for(Action action : this.getActions()){
                if(action.isApplicable(instantiation)){
                    next = action.successor(instantiation);

                    if(!distance.containsKey(next)){
                        distance.put(next,Float.POSITIVE_INFINITY);
                    }

                    Float costValue = (distance.get(instantiation) + action.getCost());
                    Float costHeuristic = (distance.get(next) + this.heuristic.estimate(next));
                    if(distance.get(next) > costValue){
                        distance.put(next,costValue);
                        value.put(next,costHeuristic);
                        father.put(next,instantiation);
                        plan.put(next,action);
                        open.add(next);
                    }                
                }
            }

        }


        return null;

    }


    public List<Action> getBfsPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, 
                                    Map<Map<Variable, Object>, Action> plan, 
                                    Map<Variable, Object> goal)
                                    {
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

    @Override
    public String toString(){
        String res = "etat initial : "+this.initialState+"\n";
        res += "\n actions : "+this.actions+"\n";
        res += " but : "+this.goal+"\n";
        return res;
    }



}