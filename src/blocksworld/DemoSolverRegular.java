package blocksworld;

import java.util.*;

import cp.*;
import modelling.*;

public class DemoSolverRegular {

    public static void main(String[] args) {

        int nbBlocks = 4;
        int nbPiles = 2;

        System.out.println("\n-----------INSTANCE RÉGULIÈRE DU MONDE---------------\n");

        RegularBwConstraints regular = new RegularBwConstraints(nbBlocks, nbPiles);
        Set<Constraint> contraintes = regular.regularConstraints();
        Set<Variable> variables = regular.getAllVariables();

        // MAC_SOLVER
        System.out.println("\n--------MACSolver----------\n");

        MACSolver macSolver = new MACSolver(variables, contraintes);

        long startMac = System.currentTimeMillis();
        Map<Variable, Object> solMac = macSolver.solve();
        long endMac = System.currentTimeMillis();

        System.out.println("Solution MACSolver :\n" + solMac + "\n");
        System.out.println("Temps d'exécution : " + (endMac - startMac) + " ms");

        // BACKTRACK_SOLVER
        System.out.println("\n--------BacktrackSolver-------\n");

        BacktrackSolver btSolver = new BacktrackSolver(variables, contraintes);

        long startBT = System.currentTimeMillis();
        Map<Variable, Object> solBT = btSolver.solve();
        long endBT = System.currentTimeMillis();

        System.out.println("Solution BacktrackSolver :\n" + solBT + "\n");
        System.out.println("Temps d'exécution : " + (endBT - startBT) + " ms");

        // Heuristic MAC Solver : NbConstraintsVariableHeuristic
        System.out.println("\n-----------HeuristicMACSolver (Heuristique: Nombre de contraintes)------------\n");

        NbConstraintsVariableHeuristic varHeur1 = new NbConstraintsVariableHeuristic(contraintes, false);
        RandomValueHeuristic valHeur = new RandomValueHeuristic(new Random());

        HeuristicMACSolver hmac1 = new HeuristicMACSolver(variables, contraintes, varHeur1, valHeur);

        long startH1 = System.currentTimeMillis();
        Map<Variable, Object> solH1 = hmac1.solve();
        long endH1 = System.currentTimeMillis();

        System.out.println("Solution HeuristicMACSolver (NbConstraints) :\n" + solH1 + "\n");
        System.out.println("Temps d'exécution : " + (endH1 - startH1) + " ms");

        // Heuristic MAC Solver : DomainSizeVariableHeuristic
        System.out.println("\n-----------HeuristicMACSolver (Heuristique: Taille des domaines)------------\n");

        DomainSizeVariableHeuristic varHeur2 = new DomainSizeVariableHeuristic(true);

        HeuristicMACSolver hmac2 = new HeuristicMACSolver(variables, contraintes, varHeur2, valHeur);

        long startH2 = System.currentTimeMillis();
        Map<Variable, Object> solH2 = hmac2.solve();
        long endH2 = System.currentTimeMillis();

        System.out.println("Solution HeuristicMACSolver (DomainSize) :\n" + solH2 + "\n");
        System.out.println("Temps d'exécution : " + (endH2 - startH2) + " ms");

    }
}
