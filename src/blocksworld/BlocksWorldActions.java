package blocksworld;

import java.util.*;
import planning.*;
import modelling.*;

public class BlocksWorldActions extends BlocksWorldVariables {

    public BlocksWorldActions(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);

    }

    /**
     * 
     * @return renvoie toutes les actions possibles
     */
    public Set<Action> getAllActions() {

        Set<Action> allActions = new HashSet<>();

        for (int b = 0; b < nbBlocks; b++) {

            for (int bPrime = 0; bPrime < nbBlocks; bPrime++) {

                if (b != bPrime) {

                    // Déplacer le bloc b qui est sur le bloc b' vers bloc b''
                    for (int bPrimeSecond = 0; bPrimeSecond < nbBlocks; bPrimeSecond++) {

                        if (bPrimeSecond != b && bPrimeSecond != bPrime) {
                            allActions.add(this.action1(b, bPrime, bPrimeSecond));
                        }
                    }

                    // Déplacer le bloc b qui est sur le bloc b' vers une pile vide p
                    for (int p = 0; p < nbPiles; p++) {
                        allActions.add(this.action2(b, bPrime, p));
                    }
                }
            }

            for (int p = 0; p < nbPiles; p++) {
                // Déplacer le bloc b qui est dans la pile p vers bloc b'
                for (int bPrime = 0; bPrime < nbBlocks; bPrime++) {
                    if (b != bPrime) {
                        allActions.add(this.action3(b, p, bPrime));
                    }
                }

                // Déplacer le bloc b, de pile p vers une autre pile vide p'
                for (int pPrime = 0; pPrime < nbPiles; pPrime++) {
                    if (p != pPrime) {
                        allActions.add(action4(b, p, pPrime));
                    }
                }
            }
        }

        return allActions;
    }

    /**
     * 
     * @param b            le bloc à deplacer
     * @param bPrime       le bloc en dessous du bloc b
     * @param bPrimeSecond le bloc sur lequel on veut déposer le bloc b
     * @return  une action pour déplacer un bloc b qui sur un bloc bprime vers un
     *  autre bloc bprimeSecond
     */

    private Action action1(int b, int bPrime, int bPrimeSecond) {

        Map<Variable, Object> prec = new HashMap<>();
        prec.put(onVars.get(b), bPrime);
        prec.put(fixedVars.get(b), false);
        prec.put(fixedVars.get(bPrimeSecond), false);

        Map<Variable, Object> effets = new HashMap<>();
        effets.put(onVars.get(b), bPrimeSecond);
        effets.put(fixedVars.get(bPrime), false);
        effets.put(fixedVars.get(bPrimeSecond), true);

        return new BasicAction(prec, effets, 1);
    }

    /**
     * 
     * @param b      le bloc à deplacer
     * @param bPrime le bloc en dessous du bloc b
     * @param p      la pile vide
     * @return une action pour déplacer un bloc b qui est sur un bloc bprime vers le
     *         fond d'une pile vide p
     */

    private Action action2(int b, int bPrime, int p) {

        Map<Variable, Object> prec = new HashMap<>();
        prec.put(onVars.get(b), bPrime);
        prec.put(fixedVars.get(b), false);
        prec.put(freeVars.get(p), true);

        Map<Variable, Object> effets = new HashMap<>();
        effets.put(onVars.get(b), -(p + 1));
        effets.put(fixedVars.get(bPrime), false);
        effets.put(freeVars.get(p), false);

        return new BasicAction(prec, effets, 1);
    }

    /**
     * 
     * @param b      le bloc à deplacer
     * @param p      la pile sur laquelle le bloc b est posée
     * @param bPrime le bloc sur lequel on veut déposer le bloc b
     * @return une action pour déplacer un bloc b qui est sur une pile p vers un
     *         autre bloc bprime
     */

    private Action action3(int b, int p, int bPrime) {

        Map<Variable, Object> prec = new HashMap<>();
        prec.put(onVars.get(b), -(p + 1)); // Bloc b est sur la pile p
        prec.put(fixedVars.get(b), false); // Il n'y rien au dessus du bloc
        prec.put(freeVars.get(p), false); // La pile n'est pas vide
        prec.put(fixedVars.get(bPrime), false); // Il n'y rien au dessus du bloc bPrime

        Map<Variable, Object> effets = new HashMap<>();
        effets.put(onVars.get(b), bPrime);
        effets.put(freeVars.get(p), true);
        effets.put(fixedVars.get(bPrime), true);

        return new BasicAction(prec, effets, 1);
    }

    /**
     * 
     * @param b      le bloc à deplacer
     * @param p      la pile sur laquelle le bloc b est posée
     * @param pPrime la pile vide
     * @return une action pour déplacer un bloc b qui est sur une pile p vers vers
     *         le fond d'une pile vide p_prime
     */

    private Action action4(int b, int p, int pPrime) {

        Map<Variable, Object> prec = new HashMap<>();
        prec.put(onVars.get(b), -(p + 1)); // Bloc b est sur la pile p
        prec.put(fixedVars.get(b), false);
        prec.put(freeVars.get(p), false);
        prec.put(freeVars.get(pPrime), true);

        Map<Variable, Object> effets = new HashMap<>();
        effets.put(onVars.get(b), -(pPrime + 1));
        effets.put(freeVars.get(p), true);
        effets.put(freeVars.get(pPrime), false);

        return new BasicAction(prec, effets, 1);
    }

    @Override
    public String toString() {

        String res = super.toString();
        res += "\n BlocksWorld Actions : \n";

        for (Action action : this.getAllActions()) {
            res += action + "\n";
        }
        return res;
    }

}
