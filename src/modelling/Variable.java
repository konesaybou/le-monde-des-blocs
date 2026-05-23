package modelling;

import java.util.*;

public class Variable {

    protected String nom;
    protected Set<Object> domain;

    public Variable(String nom, Set<Object> domain) {
        this.nom = nom;
        this.domain = domain;
    }

    public String getName() {
        return nom;
    }

    public Set<Object> getDomain() {
        return domain;
    }

    @Override
    public boolean equals(Object obj) {
        Variable variable = (Variable) obj;
        if (this == obj) {
            return true;
        }

        if (obj == null || (!(obj instanceof Variable))) {
            return false;
        }

        return this.nom.equals(variable.nom);
    }

    @Override
    public int hashCode() {

        return this.nom.hashCode();
    }

    @Override
    public String toString() {
        return "{Nom : "+this.nom+" ; Domain : "+this.domain+"}";
    }

}