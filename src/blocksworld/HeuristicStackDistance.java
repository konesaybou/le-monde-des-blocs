package blocksworld;

import java.util.*;

import modelling.*;
import planning.*;

/*
 * Heuristique admissible : nombre de blocs pas à la bonne profondeurs (distance de Hamming)
 */
public class HeuristicStackDistance implements Heuristic {
    private Map<Variable, Object> goal;

    public HeuristicStackDistance(Map<Variable, Object> goal) {
        this.goal = goal;
    }

    // Estimation en fonction nombre de blocs pas à la bonne profondeurs de l'état
    // passé en
    // parametre
    @Override
    public float estimate(Map<Variable, Object> state) {

        float h = 0;
        for (Variable var : this.goal.keySet()) {
            Object goalValue = this.goal.get(var);
            Object stateValue = state.get(var);

            if (!goalValue.equals(stateValue)) {
                h++;
            }
        }

        return h;
    }

}
