package blocksworld;

import java.util.*;

import modelling.*;

public class RegularBwConstraints extends BlocksWorldConstraints {

    public RegularBwConstraints(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);
    }

    /**
     * 
     * @return les constraintes regulières
     */
    public Set<Constraint> regularConstraints() {
        Set<Constraint> regularConstraints = new HashSet<>();

        for (int i = 0; i < onVars.size(); i++) {
            Variable on1 = onVars.get(i);

            for (int j = 0; j < onVars.size(); j++) {

                if (i != j) {
                    Variable on2 = onVars.get(j);
                    Set<Object> domain1 = new HashSet<>(Set.of(j));
                    regularConstraints.add(new Implication(on1, domain1, on2, createDomain(i, j)));
                }

            }
        }

        return regularConstraints;
    }

    private Set<Object> createDomain(int on1, int on2) {
        Set<Object> domain = new HashSet<>();

        int ecart = Math.abs(on1 - on2);

        if (on2 > on1) {

            if (on2 + ecart < nbBlocks) {
                domain.add(on2 + ecart);

            }

        } else {
            if (on2 - ecart >= 0) {
                domain.add(on2 - ecart);
            }
        }

        for (int j = 1; j <= nbPiles; j++) {
            domain.add(-j);
        }

        return domain;
    }

    @Override
    public String toString() {

        String res = "\n Regular Constraints: \n";

        for (Constraint constraint : this.regularConstraints()) {
            res += constraint + "\n";
        }
        return res;
    }

}
