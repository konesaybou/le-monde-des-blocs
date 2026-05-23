package cp;

import java.util.*;

import modelling.*;

public class Main {

    public static void main(String[] args) {

        /**
         * Exercice
         * 
         * On veut colorier 4 régions — A, B, C et D — avec 3 couleurs : rouge, vert et bleu,en respectant la contrainte :
         * deux régions voisines doivent avoir des couleurs différentes.
         */

         
        // Définition du domaine (les couleurs)
        Set<Object> couleurs = new HashSet<>(Set.of("Rouge", "Vert", "Bleu"));

        // Création des variables
        Variable A = new Variable("A", couleurs);
        Variable B = new Variable("B", couleurs);
        Variable C = new Variable("C", couleurs);
        Variable D = new Variable("D", couleurs);

        // Ensemble des variables
        Set<Variable> variables = new HashSet<>(Set.of(A, B, C, D));

        // Définition des contraintes de différence
        Set<Constraint> constraints = new HashSet<>();
        constraints.add(new DifferenceConstraint(A, B));
        constraints.add(new DifferenceConstraint(A, C));
        constraints.add(new DifferenceConstraint(B, D));
        constraints.add(new DifferenceConstraint(C, D));

        // Résolution avec les différents solveurs
        System.out.println("---- COLORIAGE DE CARTE ---\n");

        // Backtracking
        BacktrackSolver backtrack = new BacktrackSolver(variables, constraints);
        Map<Variable, Object> solutionBT = backtrack.solve();
        System.out.println("Solution Backtracking : " + solutionBT);

        // MACSolver
        MACSolver mac = new MACSolver(variables, constraints);
        Map<Variable, Object> solutionMAC = mac.solve();
        System.out.println("Solution MACSolver : " + solutionMAC);

        // Heuristic MACSolver
        Random rand = new Random();
        VariableHeuristic varHeuristic = new NbConstraintsVariableHeuristic(constraints, true);
        ValueHeuristic valHeuristic = new RandomValueHeuristic(rand);

        HeuristicMACSolver heuristicSolver = new HeuristicMACSolver(variables, constraints, varHeuristic, valHeuristic);

        Map<Variable, Object> solutionHeuristic = heuristicSolver.solve();
        System.out.println("Solution HeuristicMACSolver : " + solutionHeuristic);

    }

}
