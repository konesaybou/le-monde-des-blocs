package cp;

import modelling.*;

import java.util.*;

public class ArcConsistency {
    private Set<Constraint> constraints;
    private Set<Constraint> unaryConstraint;
    private Set<Constraint> binaryConstraint;
    private Set<Variable> variable;

    public ArcConsistency(Set<Constraint> constraints) {
        this.unaryConstraint = new HashSet<>();
        this.binaryConstraint = new HashSet<>();
        this.variable = new HashSet<>();

        for (Constraint constraint : constraints) {
            Set<Variable> scope = constraint.getScope();
            if (scope.size() == 1) {
                Variable var = scope.iterator().next();
                this.variable.add(var);
                unaryConstraint.add(constraint);
            } else if (scope.size() == 2) {
                Iterator<Variable> it = scope.iterator();
                Variable var1 = it.next();
                Variable var2 = it.next();
                this.variable.add(var1);
                this.variable.add(var2);
                binaryConstraint.add(constraint);
            } else {
                throw new IllegalArgumentException("Ni unaire ni binaire");
            }

        }

    }

    // Méthode qui supprime les valeurs ne respectant pas les contraintes unaires
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domains) {
        Map<Variable, Set<Object>> satisfieddomains = new HashMap<>();
        satisfieddomains.putAll(domains);
        for (Variable var : variable) {
            for (Object value : domains.get(var)) {
                for (Constraint unConst : unaryConstraint) {
                    Map<Variable, Object> cons = new HashMap<>();
                    cons.put(var, value);
                    if (unConst.getScope().contains(var)) {
                        if (!unConst.isSatisfiedBy(cons)) {
                            Set<Object> subdomains = new HashSet<>(satisfieddomains.get(var));
                            subdomains.remove(value);
                            satisfieddomains.put(var, subdomains);
                        }
                    }
                }
            }
        }
        for (Variable variable : domains.keySet()) {
            domains.put(variable, satisfieddomains.get(variable));
        }

        // on vérifie si toutes les valeurs de domainses ont été supprimées après avoir
        // appliquer la contrainte
        for (Map.Entry<Variable, Set<Object>> domainValue : domains.entrySet()) {
            Set<Object> domain = domainValue.getValue();
            if (domain.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // Méthode qui vérifie si une valeur de d1 a un support dans d2(démontrer si
    // c'est viable)
    public boolean revise(Variable v1, Set<Object> d1, Variable v2, Set<Object> d2) {
        boolean delete = false;
        Set<Object> removeDomain = new HashSet<>();
        Map<Variable, Object> partialInstantiation = new HashMap<>();
        for (Object object1 : d1) {
            boolean viable = false;
            for (Object object2 : d2) {
                boolean allSatified = true;
                for (Constraint constraint : binaryConstraint) {
                    if (constraint.getScope().contains(v1) && constraint.getScope().contains(v2)) {
                        partialInstantiation.put(v1, object1);
                        partialInstantiation.put(v2, object2);
                        if (!constraint.isSatisfiedBy(partialInstantiation)) {
                            allSatified = false;
                            break;
                        }
                    }
                }
                if (allSatified) {
                    viable = true;
                    break;
                }
            }
            if (!viable) {
                removeDomain.add(object1);
                delete = true;
            }
        }
        d1.removeAll(removeDomain);
        return delete;
    }

    public boolean ac1(Map<Variable, Set<Object>> domains) {
        boolean change = false;
        if (!enforceNodeConsistency(domains)) { // valeurs ne respectant les contraintes unaires
            return false;
        }
        do {
            change = false;
            for (Variable variable1 : domains.keySet()) {
                Set<Object> domain1 = new HashSet<>(domains.get(variable1));
                for (Variable variable2 : domains.keySet()) {
                    Set<Object> domain2 = new HashSet<>(domains.get(variable2));
                    if (!variable2.equals(variable1) && revise(variable1, domain1, variable2, domain2)) {
                        change = true;
                    }
                }
                domains.put(variable1, domain1);
            }
        } while (change);
        for (Variable variable : domains.keySet()) {
            if (domains.get(variable).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "ArcConsistency [constraints=" + constraints + ", unaryConstraint=" + unaryConstraint
                + ", binaryConstraint=" + binaryConstraint + ", variable=" + variable + "]";
    }
}