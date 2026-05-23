package blocksworld;

import java.util.*;

import cp.*;
import modelling.*;

public class DemoSolverIncrease {
    

    public static void main(String[] args) {

        int nbBlocks = 7;
        int nbPiles = 4;

        // MONDE CROISSANT SEUL
        System.out.println("\n--------MONDE DES BLOCS CROISSANT---------\n");

        IncreasingBwConstraints increasing = new IncreasingBwConstraints(nbBlocks, nbPiles);
        Set<Constraint> contraintesCroissantes = increasing.increasingConstraints();

        // MAC Solver
        System.out.println("\n MACSolver : configuration croissante\n");

        MACSolver macSolver = new MACSolver(increasing.getAllVariables(), contraintesCroissantes);

        long t1 = System.currentTimeMillis();
        Map<Variable, Object> sol1 = macSolver.solve();
        long t2 = System.currentTimeMillis();

        System.out.println("Solution MACSolver :\n" + sol1 + "\n");
        System.out.println("Temps d'exécution : " + (t2 - t1) + " ms\n");

        // Backtrack Solver
        System.out.println("\n BacktrackSolver : configuration croissante \n");

        BacktrackSolver bt1 = new BacktrackSolver(increasing.getAllVariables(), contraintesCroissantes);

        long t3 = System.currentTimeMillis();
        Map<Variable, Object> sol2 = bt1.solve();
        long t4 = System.currentTimeMillis();

        System.out.println("Solution BacktrackSolver :\n" + sol2 + "\n");
        System.out.println("Temps d'exécution : " + (t4 - t3) + " ms\n");

        // Heuristic MAC Solver
        System.out.println("\n-------  HeuristicMACSolver (NbConstraints)  croissant ---------\n");

        NbConstraintsVariableHeuristic varHeur1 = new NbConstraintsVariableHeuristic(contraintesCroissantes, false);
        RandomValueHeuristic valHeur = new RandomValueHeuristic(new Random());

        HeuristicMACSolver hmac1 = new HeuristicMACSolver(increasing.getAllVariables(), contraintesCroissantes,varHeur1, valHeur);

        long t5 = System.currentTimeMillis();
        Map<Variable, Object> sol3 = hmac1.solve();
        long t6 = System.currentTimeMillis();

        System.out.println("Solution HeuristicMACSolver (NbConstraints) :\n" + sol3 + "\n");
        System.out.println("Temps d'exécution : " + (t6 - t5) + " ms\n");

        // MONDE CROISSANT ET RÉGULIER
        System.out.println("\n--------------MONDE DES BLOCS CROISSANT ET REGULIER------------\n");

        RegularBwConstraints regular = new RegularBwConstraints(nbBlocks, nbPiles);

        Set<Constraint> contraintesMix = new HashSet<>();
        contraintesMix.addAll(increasing.increasingConstraints()); // contraintes croissantes
        contraintesMix.addAll(regular.regularConstraints()); // contraintes régulières
        contraintesMix.addAll(regular.getAllConstraints()); // contraintes de bases

        // MAC Solver
        System.out.println("\nMACSolver configuration mixte \n");

        MACSolver macSolver2 = new MACSolver(increasing.getAllVariables(), contraintesMix);

        long t7 = System.currentTimeMillis();
        Map<Variable, Object> sol4 = macSolver2.solve();
        long t8 = System.currentTimeMillis();

        System.out.println("Solution MACSolver (mixte) :\n" + sol4 + "\n");
        System.out.println("Temps d'exécution : " + (t8 - t7) + " ms\n");

        // BacktrackSolver
        System.out.println("\nBacktrackSolver : configuration mixte \n");

        BacktrackSolver bt2 = new BacktrackSolver(increasing.getAllVariables(), contraintesMix);

        long t9 = System.currentTimeMillis();
        Map<Variable, Object> sol5 = bt2.solve();
        long t10 = System.currentTimeMillis();

        System.out.println("Solution BacktrackSolver (mixte) :\n" + sol5 + "\n");
        System.out.println("Temps d'exécution : " + (t10 - t9) + " ms\n");

        // Heuristic MAC Solver (NbConstraints)
        System.out.println("\nHeuristicMACSolver (NbConstraints) mixte\n");

        NbConstraintsVariableHeuristic varHeur2 = new NbConstraintsVariableHeuristic(contraintesMix, false);

        HeuristicMACSolver hmac2 = new HeuristicMACSolver(increasing.getAllVariables(), contraintesMix, varHeur2,valHeur);

        long t11 = System.currentTimeMillis();
        Map<Variable, Object> sol6 = hmac2.solve();
        long t12 = System.currentTimeMillis();

        System.out.println("Solution HeuristicMACSolver (mixte) :\n" + sol6 + "\n");
        System.out.println("Temps d'exécution : " + (t12 - t11) + " ms\n");

    }
}
