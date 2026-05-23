package modelling;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        // Définition des domaines
        Set<Object> domainM1 = new HashSet<>();
        domainM1.add("A");
        domainM1.add("B");
        domainM1.add("C");

        Set<Object> domainM2 = new HashSet<>();
        domainM2.add("A");
        domainM2.add("B");
        domainM2.add("C");

        Set<Object> valBool = new HashSet<>();
        valBool.add(true);


        // Création des variables
        Variable m1 = new Variable("M1", domainM1);
        Variable m2 = new Variable("M2", domainM2);
        BooleanVariable bv = new BooleanVariable("X");

        // Contrainte de Différence
        DifferenceConstraint dif = new DifferenceConstraint(m1, m2);

        Map<Variable, Object> affectation1 = new HashMap<>();
        affectation1.put(m1, "A");
        affectation1.put(m2, "B");
        System.out.println("Affectation 1 = DifferenceConstraint satisfaite : " + dif.isSatisfiedBy(affectation1));

        Map<Variable, Object> affectation2 = new HashMap<>();
        affectation2.put(m1, "A");
        affectation2.put(m2, "A");
        System.out.println("Affectation 2 = DifferenceConstraint non satisfaite : " + dif.isSatisfiedBy(affectation2));

        // Contrainte d’Implication
        Set<Object> subset1 = new HashSet<>();
        subset1.add("A");
        subset1.add("B");

        Set<Object> subset2 = new HashSet<>();
        subset2.add("C");

        Implication impli = new Implication(m1, subset1, m2, subset2);

        Map<Variable, Object> affectation3 = new HashMap<>();
        affectation3.put(m1, "A");
        affectation3.put(m2, "C");
        System.out.println("Affectation 3 = Implication satisfaite : " + impli.isSatisfiedBy(affectation3));

        Map<Variable, Object> affectation4 = new HashMap<>();
        affectation4.put(m1, "A");
        affectation4.put(m2, "B");
        System.out.println("Affectation 4 = Implication non satisfaite: " + impli.isSatisfiedBy(affectation4));

        // Contrainte Unaire
        Set<Object> subSetUniare = new HashSet<>();
        subSetUniare.add("A");
        subSetUniare.add("C");

        UnaryConstraint unaire = new UnaryConstraint(m1, subSetUniare);

        Map<Variable, Object> affectation5 = new HashMap<>();
        affectation5.put(m1, "A");
        System.out.println("Affectation 5 = UnaryConstraint satisfaite : " + unaire.isSatisfiedBy(affectation5));

        Map<Variable, Object> affectation6 = new HashMap<>();
        affectation6.put(m1, "B");
        System.out.println("Affectation 6 = UnaryConstraint non satisfaite : " + unaire.isSatisfiedBy(affectation6));


        // Contrainte Unaire sur VariableBoolean
        UnaryConstraint boolConstraint = new UnaryConstraint(bv,valBool);

        Map<Variable, Object> affectationBV1 = new HashMap<>();
        affectationBV1.put(bv, true);
        System.out.println("Affectation booléenne 1 = UnaryConstraint satisfaite : " + boolConstraint.isSatisfiedBy(affectationBV1));

        Map<Variable, Object> affectationBV2 = new HashMap<>();
        affectationBV2.put(bv,false);
        System.out.println("Affectation booléenne 2 = UnaryConstraint non satisfaite : " + boolConstraint.isSatisfiedBy(affectationBV2));



    }
}