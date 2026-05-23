package blocksworld;

import java.util.*;

import modelling.*;
import planning.*;

public class DemoPlannificateur {
        public static void main(String[] args) {

                // Définition du monde
                BlocksWorldActions bw = new BlocksWorldActions(4, 3);

                // Etat BUT 
                List<List<Integer>> but = List.of(
                                List.of(0, 1,2), // pile 1
                                List.of(3), // pile 2
                                List.of() // pile3
                                );

                // Etat INITIAL 
                List<List<Integer>> init = List.of(List.of(0,2),List.of(1,3),List.of());

                Set<Action> actions = bw.getAllActions();

                Map<Variable, Object> etatInit = bw.getState(init);
                Map<Variable, Object> etatBut = bw.getState(but);

                Goal goal = new BasicGoal(etatBut);

                Heuristic h1 = new HeuristicMisplaced(etatBut);
                Heuristic h2 = new HeuristicStackDistance(etatBut);


                // DFS
                System.out.println(" PLANIFICATEUR DFS\n");

                DFSPlanner dfsPlanner = new DFSPlanner(etatInit, actions, goal);
                dfsPlanner.activateNodeCount(true);

                long startDFS = System.currentTimeMillis();
                List<Action> dfsPlan = dfsPlanner.plan();
                long endDFS = System.currentTimeMillis();

                System.out.println("Plan trouvé (DFS) : " + dfsPlan + "\n");
                System.out.println("Noeuds explorés (DFS) : " + dfsPlanner.getSonde());
                System.out.println("Temps de calcul : " + (endDFS - startDFS) + " ms\n");

                // BFS
                System.out.println(" PLANIFICATEUR BFS \n");

                BFSPlanner bfsPlanner = new BFSPlanner(etatInit, actions, goal);
                bfsPlanner.activateNodeCount(true);

                long startBFS = System.currentTimeMillis();
                List<Action> bfsPlan = bfsPlanner.plan();
                long endBFS = System.currentTimeMillis();

                System.out.println("Plan trouvé (BFS) : " + bfsPlan + "\n");
                System.out.println("Noeuds explorés (BFS) : " + bfsPlanner.getSonde());
                System.out.println("Temps de calcul : " + (endBFS - startBFS) + " ms\n");

                // DIJKSTRA
                System.out.println("PLANIFICATEUR DIJKSTRA\n");

                DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(etatInit, actions, goal);
                dijkstraPlanner.activateNodeCount(true);

                long startDij = System.currentTimeMillis();
                List<Action> dijkstraPlan = dijkstraPlanner.plan();
                long endDij = System.currentTimeMillis();

                System.out.println("Plan trouvé (Dijkstra) : " + dijkstraPlan + "\n");
                System.out.println("Noeuds explorés (Dijkstra) : " + dijkstraPlanner.getSonde());
                System.out.println("Temps de calcul : " + (endDij - startDij) + " ms\n");

                // A*
                System.out.println("PLANIFICATEUR A* \n");

                AStarPlanner aStarPlanner = new AStarPlanner(etatInit, actions, goal, h1);
                aStarPlanner.activateNodeCount(true);

                long startA = System.currentTimeMillis();
                List<Action> aStarPlan = aStarPlanner.plan();
                long endA = System.currentTimeMillis();

                System.out.println("Plan trouvé (A*) : " + aStarPlan + "\n");
                System.out.println("Noeuds explorés (A*) : " + aStarPlanner.getSonde());
                System.out.println("Temps de calcul : " + (endA - startA) + " ms\n");
                System.out.println("\nESTIMATION DES HEURISTIQUES*\n");

                System.out.println("HeuristicMisplaced (nombre de blocs mal placés) : " + h1.estimate(etatInit)+ " déplacement(s)");
                System.out.println("HeuristicStackDistance (distance de Hamming) : " + h2.estimate(etatInit)+ " déplacement(s)\n");


                BlocksWorldDisplayer bd = new BlocksWorldDisplayer(4, bw,"BFS_PLANNER");
                bd.showPlan(etatInit, bfsPlan);

                BlocksWorldDisplayer bd2 = new BlocksWorldDisplayer(4, bw,"ASTAR_PLANNER");
                bd2.showPlan(etatInit, aStarPlan);

                BlocksWorldDisplayer bd3 = new BlocksWorldDisplayer(4, bw,"DIJKSTRA_PLANNER");
                bd3.showPlan(etatInit, dijkstraPlan);

                BlocksWorldDisplayer bd4 = new BlocksWorldDisplayer(4, bw,"DFS_PLANNER");
                bd4.showPlan(etatInit, dfsPlan);              

        }

}
