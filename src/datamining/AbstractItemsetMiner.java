package datamining;

import modelling.*;
import java.util.*;

public abstract class AbstractItemsetMiner implements ItemsetMiner {
    BooleanDatabase base;// ensemble de transactions

    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName()
            .compareTo(var2.getName());

    public AbstractItemsetMiner(BooleanDatabase base) {
        this.base = base;
    }

    @Override
    public BooleanDatabase getDatabase() {
        return this.base;
    }

    // Méthode retournant le fréquence des items dans la base.
    public float frequency(Set<BooleanVariable> items) {
        float init = 0;
        for (Set<BooleanVariable> transaction : getDatabase().getTransactions()) {
            if (transaction.containsAll(items)) {
                init++;
            }
        }
        return init / getDatabase().getTransactions().size();
    }

    @Override
    public String toString() {
        return "AbstractItemsetMiner(base) = " + base;
    }
}