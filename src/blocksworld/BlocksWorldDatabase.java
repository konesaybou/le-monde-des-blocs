package blocksworld;

import java.util.*;

import modelling.*;

public class BlocksWorldDatabase {

    private int nbBlocks,nbPiles;
    private Set<BooleanVariable> database;


    public BlocksWorldDatabase(int nbBlocks,int nbPiles){
        this.nbBlocks=nbBlocks;
        this.nbPiles = nbPiles;
        this.database = new HashSet<>();
     

        this.variablesGenerator();
    }


    //genère toutes les variables pour  BooleanDatabase
    public void variablesGenerator(){

        for (int b = 0; b < nbBlocks; b++) {

            //on_b_b' variables
            for (int bPrime = 0; bPrime < nbBlocks; bPrime++) {
                
                if(b!=bPrime){
                    database.add(new BooleanVariable("on"+b+"_"+bPrime));
                }
            }

            //on-table_b_p et free_p variables
            for (int p = 0; p < nbPiles; p++) {
                database.add(new BooleanVariable("on-table"+b+"_"+p));
                database.add(new BooleanVariable("free"+p));
            }

            //fixed_b variables
            database.add(new BooleanVariable("fixed"+b));
            
        }
    }

/**
 * 
 * @return renvoie les Booleanvariables de la database 
 */
public Set<BooleanVariable> getVariables(){ return database;}


/**
 * Permet de retourner une instance du monde des blocs
 * @param stacks liste des piles
 * @return  une instance du monde des blocs 
 */
public Set<BooleanVariable> getInstance(List<List<Integer>> stacks){

    Set<BooleanVariable> instance = new HashSet<>();

    for (int p=0;p<stacks.size();p++) {

        List<Integer> stack = stacks.get(p);

        if(stack.isEmpty()){
            instance.add(new BooleanVariable("free"+p));
        }
        else{
         

                for (int b = 0; b < stack.size(); b++) {

                    int blockB = stack.get(b);
                  
                    if(b==0){
                        instance.add(new BooleanVariable("on-table"+blockB+"_"+p));
                    }else{

                        int blockBPrime = stack.get(b-1);
                        instance.add(new BooleanVariable("on"+blockB+"_"+blockBPrime));
                    }
                    
                    if(b < stack.size()-1){
                       instance.add(new BooleanVariable("fixed"+blockB));
                    }
                }     
              
        }
    }


  

    return instance;
}


@Override
public String toString() {
     
     String res = "\n Variables pour BooleanDatabase : \n";
                   
    for (BooleanVariable variable : this.getVariables()) {
        res += variable + "\n";
    }
    return res;
}
}