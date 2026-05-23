package datamining;

import java.util.*;

import datamining.AssociationRuleMiner;
import modelling.BooleanVariable;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {

    protected BooleanDatabase database;

    public AbstractAssociationRuleMiner(BooleanDatabase database) {
        this.database = database;
    }

    public BooleanDatabase getDatabase() {
        return database;
    }

    public static float frequency(Set<BooleanVariable> items, Set<Itemset> frequent) {

        float frequence = 0;
        boolean estDansItems = false;

        for (Itemset it : frequent) {
            Set<BooleanVariable> item = it.getItems();
            if (item.size() == items.size() && item.containsAll(items)) {
                frequence = it.getFrequency();
                estDansItems = true;
            }
        }

        if (!estDansItems) {

            throw new IllegalArgumentException("Items n'est pas présent dans l'ensemble d'ItemSet");
        }

        return frequence;
    }

    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion,
            Set<Itemset> frequent) {

        float freqPremise = frequency(premise, frequent); // pas de this car methode static de la classe
        Set<BooleanVariable> premiseCopy = new HashSet<>(premise);
        // Ajouter la conclusion a la premise : Z = X U Y
        premiseCopy.addAll(conclusion);

        // recuperation de la frequence F(XY)
        float freqConclusionPremisse = frequency(premiseCopy, frequent);
        return freqConclusionPremisse / freqPremise;
    }

    @Override
    public String toString() {
        return "AbstractAssociationRuleMiner [database=" + database + "]";
    }

}
