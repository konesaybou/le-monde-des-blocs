package blocksworld;

import java.util.*;

import modelling.*;

public class IncreasingBwConstraints extends BlocksWorldConstraints {

    public IncreasingBwConstraints(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);

    }

    /**
     * 
     * @return les constraintes croissantes
     */
    public Set<Constraint> increasingConstraints() {
        Set<Constraint> increasingConstraints = new HashSet<>();

        for (int i = 0; i < onVars.size(); i++) {
            Variable on1 = onVars.get(i);

            for (int j = 0; j < onVars.size(); j++) {

                if (i != j) {
                    Variable on2 = onVars.get(j);
                    Set<Object> domaine1 = new HashSet<>(Set.of(j));

                    increasingConstraints.add(new Implication(on1, domaine1, on2, createDomain(i, j)));
                }

            }
        }

        return increasingConstraints;
    }

    private Set<Object> createDomain(int on1, int on2) {
        Set<Object> domain = new HashSet<>();

        if (on2 > on1) {
            return domain;
        }

        for (int j = 1; j <= nbPiles; j++) {
            domain.add(-j);
        }

        for (int i = on2 - 1; i > -1; i--) {
            domain.add(i);
        }

        return domain;
    }

    @Override
    public String toString() {

        String res = "\n IncreasingBlocks Constraints: \n";

        for (Constraint constraint : this.increasingConstraints()) {
            res += constraint + "\n";
        }
        return res;
    }

}
