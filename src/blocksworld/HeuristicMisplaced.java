package blocksworld;

import java.util.Map;

import modelling.*;
import planning.*;

/*
 * Heuristique admissible 1 : nombre de blocs mal placés
 */
public class HeuristicMisplaced implements Heuristic {

    private Map<Variable, Object> goal;

    public HeuristicMisplaced(Map<Variable, Object> goal) {
        this.goal = goal;
    }

    // Estimation en fonction du nombre de blocs mal placés de l'état passé en
    // parametre
    @Override
    public float estimate(Map<Variable, Object> state) {
        float cpt = 0;

        for (Variable var : state.keySet()) {

            if (!goal.get(var).equals(state.get(var))) {
                cpt++;
            }
        }

        return cpt;
    }

}