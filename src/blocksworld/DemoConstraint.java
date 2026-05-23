package blocksworld;

import java.util.*;
import modelling.*;

public class DemoConstraint {
    public static void main(String[] args) {

        System.out.println("\n--------le monde des blocs avec les contraintes croissantes--------\n");

        IncreasingBwConstraints blocs = new IncreasingBwConstraints(4, 2);

        List<List<Integer>> listePile1 = List.of(
                List.of(0, 1), // pile 0
                List.of(2, 3) // pile 1
        );
        Set<Constraint> contrainteCroissante = blocs.increasingConstraints();
        Map<Variable, Object> mondeCroissant = blocs.getState(listePile1);

        boolean croissant = false;
        for (Constraint c : contrainteCroissante) {
            if (c.isSatisfiedBy(mondeCroissant)) {
                croissant = true;
            }
        }

        System.out.println("Le monde ci-dessous \n" + mondeCroissant + " \n est-il croissant ? : " + croissant + "\n");

        System.out.println("----------------- le monde des blocs avec les contraintes non croissantes ----------------\n");

        List<List<Integer>> listePile2 = List.of(
                List.of(3, 1),
                List.of(0, 2));

        Map<Variable, Object> mondeNonCroissant = blocs.getState(listePile2);

        boolean nonCroissant = false;
        for (Constraint c : contrainteCroissante) {
            if (c.isSatisfiedBy(mondeNonCroissant)) {
                nonCroissant = true;
            }
        }

        //resultat attendu est : true
        System.out.println(" Le monde ci-dessous \n" + mondeNonCroissant + "\n est-il non croissant ? : " + nonCroissant + "\n");


        System.out.println("------------ le monde des blocs avec les contraintes regulière --------------\n");

        RegularBwConstraints regular = new RegularBwConstraints(4, 2);

        List<List<Integer>> listePile3 = List.of(
                List.of(0, 2),
                List.of(1, 3));

        Set<Constraint> contraintesRegulieres = regular.regularConstraints();
        Map<Variable, Object> mondeRegulier = blocs.getState(listePile3);

        boolean regulier = false;
        for (Constraint c : contraintesRegulieres) {
            if (c.isSatisfiedBy(mondeRegulier)) {
                regulier = true;
            }
        }

        System.out.println("Le monde ci-dessous \n" + mondeRegulier + "\n est-il regulier ? : " + regulier + "\n");


        System.out.println("--------le monde des blocs avec les contraintes non regulière----------\n");
        List<List<Integer>> listePile4 = List.of(
                List.of(2, 0),
                List.of(3, 1));

        Map<Variable, Object> nonRegulier = blocs.getState(listePile4);

        boolean nRegulier = false;
        for (Constraint c : contraintesRegulieres) {
            if (c.isSatisfiedBy(nonRegulier)) {
                nRegulier = true;
            }
        }

        System.out.println("Le monde ci-dessous \n" + nonRegulier + " \n est-il non regulier ? : " + nRegulier + "\n");

        System.out.println("--------- le monde des blocs avec les contraintes croissantes et regulières ----------\n");

        List<List<Integer>> listePile5 = List.of(
                List.of(0, 1),
                List.of(2, 3));

        Map<Variable, Object> mndRegulier = blocs.getState(listePile5);

        boolean regulierCroissant = false;
        for (Constraint c : contrainteCroissante) {
            if (c.isSatisfiedBy(mndRegulier)) {
                regulierCroissant = true;
            }
        }

        System.out.println("Le monde ci-dessous \n" + mndRegulier + " \n est-il croissant ? : " + regulierCroissant + "\n");

        boolean croissantRegulier = false;
        for (Constraint c : contraintesRegulieres) {
            if (c.isSatisfiedBy(mndRegulier)) {
                croissantRegulier = true;
            }
        }

        System.out.println("Ce même monde regulier est-il régulier ? : " + croissantRegulier + "\n");

    }
}
