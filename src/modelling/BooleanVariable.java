package modelling;

import java.util.*;

public class BooleanVariable extends Variable {

    protected String nom;

    public BooleanVariable(String nom) {
        super(nom, new HashSet<>(Set.of(true, false)));
        this.nom = nom;

    }

    @Override
    public int hashCode() {
        
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
       
        return super.equals(obj);
    }




}