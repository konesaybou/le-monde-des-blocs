package blocksworld;

import bwgeneratordemo.*;
import java.util.*;

import datamining.*;
import modelling.*;

//import bwmodel.*;

public class DemoDatabase {

    public static void main(String[] args) {


        int nbBlocks = 8;
        int nbPiles = 3;


        System.out.println("\n-------GENERATION DE LA BASE BOOLÉENNE--------\n");

        BlocksWorldDatabase bwDatabase = new BlocksWorldDatabase(nbBlocks, nbPiles);

        int nbStates = 10000; // a changer au choix
        BooleanDatabase db = new BooleanDatabase(bwDatabase.getVariables());

        // Génération aléatoire d'états
        System.out.println("Base construite avec " + nbStates + " états aléatoires.\n");
        for (int i = 0; i < nbStates; i++) {
            // Drawing a state at random
            Random rd = new Random();
            List<List<Integer>> state = Demo.getState(rd);
            // Converting state to instance
            Set<BooleanVariable> instance = bwDatabase.getInstance(state);
            // Adding state to database
            db.add(instance);
        }


        // Paramètres Apriori
        float minFreq = 2.0f / 3.0f;
        float confidence = 95.0f / 100.0f;
        Apriori apriori = new Apriori(db);

        Set<Itemset> itemsets = apriori.extract(minFreq);

        System.out.println("\nITEMSETS FREQUENTS\n");
        for (Itemset item : itemsets) {
            System.out.println(item);
        }

        // Règles d'association
        AssociationRuleMiner ruleMiner = new BruteForceAssociationRuleMiner(db);

        Set<AssociationRule> rules = ruleMiner.extract(minFreq, confidence);

        System.out.println("\nASSOCIATION RULES\n");
        for (AssociationRule r : rules) {
            System.out.println(r);
        }

    }

}
