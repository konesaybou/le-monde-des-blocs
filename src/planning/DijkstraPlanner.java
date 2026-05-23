package planning;

import java.util.*;
import modelling.*;

public class DijkstraPlanner implements Planner{

    private Map<Variable, Object> initialState; 
    private Set<Action> actions; 
    private Goal goal; 
    private MonComparateur comparateur;
    private int sonde;
    private boolean active;

    public DijkstraPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.sonde = 0;
        this.active = false;
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

    
    public List<Action> get_dijkstra_plan(Map<Map<Variable,Object>,Map<Variable,Object>> father,Map<Map<Variable,Object>,Action> plan, Map<Variable,Object> goal ){

        List<Action> DIJ_plan = new LinkedList<>();

        while (goal !=null){
            Action action = plan.get(goal);
            if(action != null){
                DIJ_plan.add(action);
            }
            goal = father.get(goal);
        }
        Collections.reverse(DIJ_plan);
        return DIJ_plan;
    }

    public List<Action> dijkstra(){
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable,Object>,Float> distance = new HashMap<>();  
        Map<Variable,Object> instantiation = new HashMap<>();
        Map<Variable,Object> next = new HashMap<>();

        comparateur = new MonComparateur(distance);
        /**
         * ici la PriorityQueue va appeler la methode compare de MonComparateur en interne
         * pour savoir comment ordonner les éléments.
         */
        PriorityQueue<Map<Variable,Object>> open = new PriorityQueue<>(comparateur);

        open.add(this.initialState);
        distance.put(this.initialState,0.0f);
        father.put(this.initialState,null);

        while(!open.isEmpty()){
            instantiation = open.poll(); // on va retirer et recuperer l'etat avec la plus petit distance.
            if(this.active){ this.sonde += 1;}
            if(goal.isSatisfiedBy(instantiation)){
                return this.get_dijkstra_plan(father,plan,instantiation);
            }

            for (Action action : this.getActions()){
                if(action.isApplicable(instantiation)){
                    next = action.successor(instantiation);
                    if(!distance.containsKey(next)){
                        distance.put(next,Float.POSITIVE_INFINITY);
                    }
                    Float costValue = (distance.get(instantiation) + action.getCost());
                    if(distance.get(next) > costValue){
                        distance.put(next,costValue);
                        father.put(next,instantiation);
                        plan.put(next,action);
                        open.add(next);
                    }
                }
            }

        }

        return null;
    }


    @Override
    public List<Action> plan(){

        List<Action> res, planFinal;

        planFinal = new ArrayList<Action>(); 
        res = dijkstra();

        if(res == null){return null;}

        for (Action action: res) {
           if(action != null){
            planFinal.add(action);
           }   
        }
        //System.out.println(res.toString());
        return planFinal;
    }
   

    @Override
    public String toString(){
        String res = "etat initial : "+this.initialState+"\n";
        res += "\n actions : "+this.actions+"\n";
        res += " but : "+this.goal+"\n";
        return res;
    }


}