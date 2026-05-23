package cp;
import modelling.*;
import java.util.*;

public class RandomValueHeuristic implements ValueHeuristic{
    Random randomGenerator;

    public RandomValueHeuristic(Random randomGenerator){
        this.randomGenerator = randomGenerator;
    }

    @Override
    public List<Object> ordering(Variable variable, Set<Object> domains){
        List<Object> list = new ArrayList<>(domains);
        Collections.shuffle(list, randomGenerator);
        return list;
    }

    @Override
    public String toString() {
        return "RandomValueHeuristic [randomGenerator = " + randomGenerator + "]";
    }
}