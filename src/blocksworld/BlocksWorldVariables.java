package blocksworld;

import modelling.*;

import java.util.*;

public class BlocksWorldVariables {
    protected int nbBlocks;
    protected int nbPiles;
    protected Map<Integer, Variable> onVars;
    protected Map<Integer, Variable> fixedVars;
    protected Map<Integer, Variable> freeVars;

    public BlocksWorldVariables(int nbBlocks, int nbPiles) {

        this.nbBlocks = nbBlocks;
        this.nbPiles = nbPiles;

        this.onVars = new HashMap<>();
        this.fixedVars = new HashMap<>();
        this.freeVars = new HashMap<>();
        this.variablesGenerator();
    }
    
    //Génère toutes les variables pour le monde des blocs
    public void variablesGenerator() {

        for (int i = 0; i < nbBlocks; i++) {
            Variable onVar = new Variable("on_" + i, createDomain(i));
            onVars.put(i, onVar);
        }

        for (int i = 0; i < nbBlocks; i++) {
            BooleanVariable fixedVar = new BooleanVariable("fixed_" + i);
            fixedVars.put(i, fixedVar);
        }

        for (int i = 0; i < nbPiles; i++) {
            BooleanVariable freeVar = new BooleanVariable("free_" + i);
            freeVars.put(i, freeVar);
        }
    }

    // Cette méthode renvoie le domaine d'un bloc privé de lui meme.
    private Set<Object> createDomain(int block) {
        Set<Object> domain = new HashSet<>();
        for (int nb = -nbPiles; nb < 0; nb++) {
            domain.add(nb);
        }

        for (int i = 0; i < nbBlocks; i++) {
            if (i != block) {
                domain.add(i);
            }
        }

        return domain;

    }

    // renvoie toutes les variables
    public Set<Variable> getAllVariables() {
        Set<Variable> allVariables = new HashSet<>();
        allVariables.addAll(onVars.values());
        allVariables.addAll(fixedVars.values());
        allVariables.addAll(freeVars.values());
        return allVariables;
    }

    /**
     * 
     * @param listPile liste des piles
     * @return representation des piles du monde des blocs
     */
    public Map<Variable, Object> getState(List<List<Integer>> listPile) {
        Map<Variable, Object> state = new HashMap<>();

        for (int pile = 0; pile < listPile.size(); pile++) {
            List<Integer> p = listPile.get(pile);

            if (p.isEmpty()) {
                state.put(freeVars.get(pile), true);
            } else {
                state.put(freeVars.get(pile), false);

                for (int b = 0; b < p.size(); b++) {
                    int block = p.get(b);
                    if (b == 0) {
                        state.put(onVars.get(block), -(pile + 1));
                    } else {

                        int blockPrime = p.get(b - 1);
                        state.put(onVars.get(block), blockPrime);

                    }

                    if (b < p.size() - 1) {
                        state.put(fixedVars.get(block), true);
                    } else {
                        state.put(fixedVars.get(block), false);
                    }
                }
            }
        }

        return state;
    }

    public Map<Integer, Variable> getOnVars() {
        return onVars;
    }

    public Map<Integer, Variable> getFixedVars() {
        return fixedVars;
    }

    public Map<Integer, Variable> getFreeVars() {
        return freeVars;
    }

    @Override
    public String toString() {

        String res = " \n BlocksWorld Variables : ";
        res += "\n onVars : " + onVars.values() + "\n";
        res += "\n fixedVars : " + fixedVars.values() + "\n";
        res += "\n freeVars : " + freeVars.values() + "\n";

        return res;
    }
}
