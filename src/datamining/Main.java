package datamining;

import java.util.*;

import modelling.*;

public class Main {
    public static void main(String[] args) {

        /**
         * Cette demonstration est un exemple d'exercie disponible dans le cours.
         */

        // Etape 1 : création des items booléens
        BooleanVariable A = new BooleanVariable("A");
        BooleanVariable B = new BooleanVariable("B");
        BooleanVariable C = new BooleanVariable("C");
        BooleanVariable D = new BooleanVariable("D");
        BooleanVariable E = new BooleanVariable("E");

        Set<BooleanVariable> items = new HashSet<>(Set.of(A, B, C, D, E));

        // Etape 2 : création de la base de données transactionnelle
        BooleanDatabase db = new BooleanDatabase(items);

        db.add(Set.of(A, B, C, D, E)); // T1
        db.add(Set.of(A, C)); // T2
        db.add(Set.of(A, B, C, D)); // T3
        db.add(Set.of(B, C)); // T4
        db.add(Set.of(A, B, C)); // T5
        db.add(Set.of(E)); // T6

        System.out.println("Base de données :");
        System.out.println(db);

        // Etape 3 : extraction des itemsets fréquents (Apriori)
        Apriori apriori = new Apriori(db);
        float minFreq = 0.3f;
        Set<Itemset> frequents = apriori.extract(minFreq);

        System.out.println("\n----Itemsets fréquents (minfr = " + minFreq + ")------");
        for (Itemset it : frequents) {
            System.out.println(it);
        }

        // Etape 4 : génération des règles d’association
        BruteForceAssociationRuleMiner ruleMiner = new BruteForceAssociationRuleMiner(db);
        float minConfidence = 0.6f;

        Set<AssociationRule> rules = ruleMiner.extract(minFreq, minConfidence);

        System.out.println("\nRègles d’association (minfr = " + minFreq + ", minconf = " + minConfidence + ")---");
        for (AssociationRule r : rules) {
            System.out.println(r);
        }

    }

}
