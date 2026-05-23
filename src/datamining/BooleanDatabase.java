package datamining;
import modelling.*;
import java.util.*;

public class BooleanDatabase{
    private Set<BooleanVariable> items;//instance de la classe BooleanVariable
    private List<Set<BooleanVariable>> transactions;//collection d'items

    public BooleanDatabase(Set<BooleanVariable> items){
        this.items = items;
        this.transactions = new ArrayList<>();
    }

    public void add(Set<BooleanVariable> transaction){
        this.transactions.add(new HashSet<>(transaction));
    }

    public Set<BooleanVariable> getItems(){
        return items;
    }

    public List<Set<BooleanVariable>> getTransactions(){
        return transactions;
    } 
    @Override
    public String toString(){
        return "BooleanDatabase [items = [" + items + "]" + " ; transactions [" + transactions + "]" + "]";
    }
}