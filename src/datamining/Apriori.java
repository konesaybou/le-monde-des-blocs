package datamining;

import modelling.*;
import java.util.*;

public class Apriori extends AbstractItemsetMiner {
    protected BooleanDatabase base;

    public Apriori(BooleanDatabase base) {
        super(base);
    }

    // Méthode retournant l'ensemble des itemsets ayant une fréquence >= à celle
    // donnée.
    public Set<Itemset> frequentSingletons(float minimalFrequency) {
        Set<Itemset> itemsets = new HashSet<>();
        for (BooleanVariable item : super.getDatabase().getItems()) {
            Set<BooleanVariable> items = new HashSet<>();
            items.add(item);
            float frq = super.frequency(items);
            if (frq >= minimalFrequency) {
                itemsets.add(new Itemset(items, frq));
            }
        }
        return itemsets;

    }

    // Méthode permettent de combiner deux ensembles d'items triés en respectant des
    // conditions
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> items1,
            SortedSet<BooleanVariable> items2) {
        SortedSet<BooleanVariable> combinedItems = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        if (items1.isEmpty() || items2.isEmpty()) {
            return null;
        }
        if (items1.size() == items2.size() && items1.headSet(items1.last()).equals(items2.headSet(items2.last()))
                && (!items1.last().equals(items2.last()))) {
            combinedItems.addAll(items1);
            combinedItems.addAll(items2);
            return combinedItems;
        }
        return null;
    }

    // Méthode vérifiant si on supprimer un item de l'ensemble, les autres sont
    // toujours contenu dans la collection
    public static boolean allSubsetsFrequent(Set<BooleanVariable> items,
            Collection<SortedSet<BooleanVariable>> collectionItems) {
        for (BooleanVariable item : items) {
            Set<BooleanVariable> subsets = new HashSet<>(items);
            subsets.remove(item);
            if (!collectionItems.contains(subsets)) {
                return false;
            }
        }
        return true;
    }

    // Méthode permettant de renvoyer des items de k à k+1 tout en prenant en compte
    // les singletons
    @Override
    public Set<Itemset> extract(float minimalFrequency) {
        List<Itemset> items = new ArrayList<>();
        Set<Itemset> results = new HashSet<>();
        items.addAll(frequentSingletons(minimalFrequency));
        results.addAll(items);

        List<SortedSet<BooleanVariable>> frequentsItemsets = new ArrayList<>();
        for (Itemset item : items) {
            SortedSet<BooleanVariable> frequentItems = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            frequentItems.addAll(item.getItems());
            frequentsItemsets.add(frequentItems);
        }
        while (!frequentsItemsets.isEmpty()) {
            List<SortedSet<BooleanVariable>> frequents = new ArrayList<>();
            for (int i = 0; i < frequentsItemsets.size() - 1; i++) {
                for (int j = i + 1; j < frequentsItemsets.size(); j++) {
                    SortedSet<BooleanVariable> items1 = frequentsItemsets.get(i);
                    SortedSet<BooleanVariable> items2 = frequentsItemsets.get(j);
                    SortedSet<BooleanVariable> combinedItems = combine(items1, items2);

                    if (combinedItems != null) {
                        if (allSubsetsFrequent(combinedItems, frequentsItemsets)) {
                            float frequency = super.frequency(combinedItems);
                            if (frequency >= minimalFrequency) {
                                results.add(new Itemset(combinedItems, frequency));
                                frequents.add(combinedItems);
                            }
                        }
                    }

                }
            }
            frequentsItemsets = frequents;
        }
        return results;
    }

}