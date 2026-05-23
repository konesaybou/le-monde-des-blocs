package planning;

import java.util.*;
import modelling.*;

public class Main {

    public static void main(String[] args) {

        // Création de variables et de domaines
        Set<Object> domain= new HashSet<>(Arrays.asList("a","b","c","d","e","f","g","h"));
        Variable variable = new Variable("var", domain);
        
        // Création d'un état initial
        Map<Variable, Object> initialState = new HashMap<>();
        initialState.put(variable, "a");

        // Affichage de l'état initial
        System.out.println("Etat initial : " + initialState);

        // Création des préconditions et effets d'actions
        // Action 1
        Map<Variable, Object> precondition1 = new HashMap<>();
        precondition1.put(variable, "a");
        Map<Variable, Object> effects1 = new HashMap<>();
        effects1.put(variable, "b");
        BasicAction action1 = new BasicAction(precondition1, effects1, 1);

        Map<Variable, Object> precondition2 = new HashMap<>();
        precondition2.put(variable, "a");
        Map<Variable, Object> effects2 = new HashMap<>();
        effects2.put(variable, "c");
        BasicAction action2 = new BasicAction(precondition2, effects2, 1);

        Map<Variable, Object> precondition3 = new HashMap<>();
        precondition3.put(variable, "a");
        Map<Variable, Object> effects3 = new HashMap<>();
        effects3.put(variable, "d");
        BasicAction action3 = new BasicAction(precondition3, effects3, 1);

        Map<Variable, Object> precondition4 = new HashMap<>();
        precondition4.put(variable, "b");
        Map<Variable, Object> effects4 = new HashMap<>();
        effects4.put(variable, "c");
        BasicAction action4 = new BasicAction(precondition4, effects4, 1);

        Map<Variable, Object> precondition5 = new HashMap<>();
        precondition5.put(variable, "b");
        Map<Variable, Object> effects5 = new HashMap<>();
        effects5.put(variable, "e");
        BasicAction action5 = new BasicAction(precondition5, effects5, 1);

        Map<Variable, Object> precondition6 = new HashMap<>();
        precondition6.put(variable, "c");
        Map<Variable, Object> effects6 = new HashMap<>();
        effects6.put(variable, "d");
        BasicAction action6 = new BasicAction(precondition6, effects6, 1);

        Map<Variable, Object> precondition7 = new HashMap<>();
        precondition7.put(variable, "d");
        Map<Variable, Object> effects7 = new HashMap<>();
        effects7.put(variable, "f");
        BasicAction action7 = new BasicAction(precondition7, effects7, 1);
    
        Map<Variable, Object> precondition8 = new HashMap<>();
        precondition8.put(variable, "d");
        Map<Variable, Object> effects8 = new HashMap<>();
        effects8.put(variable, "e");
        BasicAction action8 = new BasicAction(precondition8, effects8, 1);

        Map<Variable, Object> precondition9 = new HashMap<>();
        precondition9.put(variable, "f");
        Map<Variable, Object> effects9 = new HashMap<>();
        effects9.put(variable, "h");
        BasicAction action9 = new BasicAction(precondition9, effects9, 1);

        Map<Variable, Object> precondition10 = new HashMap<>();
        precondition10.put(variable, "f");
        Map<Variable, Object> effects10 = new HashMap<>();
        effects10.put(variable, "g");
        BasicAction action10 = new BasicAction(precondition10, effects10, 1);

        Map<Variable, Object> precondition11 = new HashMap<>();
        precondition11.put(variable, "e");
        Map<Variable, Object> effects11 = new HashMap<>();
        effects11.put(variable, "h");
        BasicAction action11 = new BasicAction(precondition11, effects11, 1);


        Map<Variable, Object> precondition12 = new HashMap<>();
        precondition12.put(variable, "e");
        Map<Variable, Object> effects12 = new HashMap<>();
        effects12.put(variable, "g");
        BasicAction action12 = new BasicAction(precondition12, effects12, 1);

        Map<Variable, Object> precondition13 = new HashMap<>();
        precondition13.put(variable, "g");
        Map<Variable, Object> effects13 = new HashMap<>();
        effects13.put(variable, "h");
        BasicAction action13 = new BasicAction(precondition13, effects13, 1);


        // Création d'un ensemble d'actions
        Set<Action> actions = new HashSet<>();
        actions.add(action1);
        actions.add(action2);
        actions.add(action3);
        actions.add(action4);
        actions.add(action5);
        actions.add(action6);
        actions.add(action7);
        actions.add(action8);
        actions.add(action9);
        actions.add(action10);
        actions.add(action11);
        actions.add(action12);
        actions.add(action13);

        //Création de but
        Map<Variable, Object> goalState = new HashMap<>();
        goalState.put(variable, "h");
        BasicGoal goal = new BasicGoal(goalState);

        //Affichage du but
        System.out.println("Etat du but : " + goalState +"\n");

        //Démonstration des algorithmes
        DFSPlanner dfsPlanner = new DFSPlanner(initialState, actions, goal);
        dfsPlanner.activateNodeCount(true); // activer le compteur des noeuds explorés

        BFSPlanner bfsPlanner = new BFSPlanner(initialState, actions, goal);
        bfsPlanner.activateNodeCount(true);

        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(initialState, actions, goal);
        dijkstraPlanner.activateNodeCount(true);

        Heuristic heuristic = new MonHeuristic();
        AStarPlanner aStarPlanner = new AStarPlanner(initialState, actions, goal, heuristic);
        aStarPlanner.activateNodeCount(true);


        // La liste des plans trouvé
        List<Action> dfsPlan = dfsPlanner.plan();
        List<Action> bfsPlan = bfsPlanner.plan();
        List<Action> dijkstraPlan = dijkstraPlanner.plan();
        List<Action> aStarPlan = aStarPlanner.plan();

        // Affichage du plan et le nombre de nœuds explorés
        System.out.println("***** Plan trouvé par DFS : *****\n" + dfsPlan +"\n");
        System.out.println("Nombre des noeuds explorés par DFS : " + dfsPlanner.getSonde()+"\n");

        System.out.println("***** Plan trouvé par BFS : *****\n" + bfsPlan +"\n");
        System.out.println("Nombre des noeuds explorés par BFS : " + bfsPlanner.getSonde()+"\n");

        System.out.println("***** Plan trouvé par Dijkstra : *****\n" + dijkstraPlan +"\n");
        System.out.println("Nombre des noeuds explorés par Dijkstra : " + dijkstraPlanner.getSonde()+"\n");

        System.out.println("***** Plan trouvé par AStar : *****\n" + aStarPlan +"\n ***************\n");
        System.out.println("Nombre des noeuds explorés par AStar : " + aStarPlanner.getSonde() + "\n");

        
    


    }
}