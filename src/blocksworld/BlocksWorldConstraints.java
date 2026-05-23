package blocksworld;

import modelling.*;

import java.util.*;

public class BlocksWorldConstraints extends BlocksWorldVariables {

    protected Set<Constraint> constraints;

    public BlocksWorldConstraints(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);
        this.constraints = new HashSet<>();

        this.constraintsGenerator();

    }

    //Permet de generer toutes les contraintes de bases
    public void constraintsGenerator() {
        // les contraintes de difference val_onb # val_onb'
        for (int i = 0; i < onVars.size(); i++) {
            for (int j = i + 1; j < onVars.size(); j++) {

                this.constraints.add(new DifferenceConstraint(onVars.get(i), onVars.get(j)));
            }
        }

        // les contraintes d'implication pour les fixed_b (si on_b = b' ---> fixed_b' = true)
        for (int b = 0; b < onVars.size(); b++) {
            Variable onb = onVars.get(b);
            for (int bPrime = 0; bPrime < onVars.size(); bPrime++) {
                if (b != bPrime) {
                    Set<Object> onDomain = new HashSet<>(Set.of(bPrime));

                    Set<Object> bPrimeDomain = new HashSet<>(Set.of(true));

                    this.constraints.add(new Implication(onb, onDomain, fixedVars.get(bPrime), bPrimeDomain));
                }
            }
        }

        // les contraintes d'implication free_b (si on_b = −(p+1) ---> free_p = false)
        for (int b = 0; b < onVars.size(); b++) {
            Variable onb = onVars.get(b);
            for (int p = 0; p < freeVars.size(); p++) {

                Set<Object> domain = new HashSet<>(Set.of(-(p + 1)));

                Set<Object> freeDomain = new HashSet<>(Set.of(false));

                this.constraints.add(new Implication(onb, domain, freeVars.get(p), freeDomain));
            }
        }

    }

    /**
     * 
     * @return toutes les contraintes du monde des blocs
     */

    public Set<Constraint> getAllConstraints() {
        return this.constraints;
    }

    @Override
    public String toString() {

        String res = super.toString();
        res += "\n BlocksWorld Constraints: \n";

        for (Constraint constraint : this.constraints) {
            res += constraint + "\n";
        }
        return res;
    }

}
