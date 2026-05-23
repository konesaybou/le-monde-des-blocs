package datamining;

import java.util.*;

import modelling.BooleanVariable;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    public BruteForceAssociationRuleMiner(BooleanDatabase database) {
        super(database);
    }

    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        Set<Set<BooleanVariable>> allCandidat = new HashSet<>();
        List<Set<BooleanVariable>> candidat = new ArrayList<>();
        candidat.add(new HashSet<>());
        for (BooleanVariable bv : items) {
            int size = candidat.size();
            for (int i = 0; i < size; i++) {
                Set<BooleanVariable> subSet = new HashSet<>(candidat.get(i));
                subSet.add(bv);
                candidat.add(subSet);
            }
        }
        // parcours la liste des candidats puis supprime l'ensemble vide et l'ensemble
        // lui-même.
        for (Set<BooleanVariable> bvSet : candidat) {
            if (!bvSet.isEmpty() && bvSet.size() < items.size())
                allCandidat.add(bvSet);
        }

        return allCandidat;
    }

    @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfidence) {

        Set<Itemset> Fminfr = new Apriori(super.getDatabase()).extract(minFrequency);
        Set<AssociationRule> setRuleFrAndConfid = new HashSet<>();

        for (Itemset itemset : Fminfr) {
            Set<BooleanVariable> items = itemset.getItems();

            Set<Set<BooleanVariable>> allCandidates = allCandidatePremises(items);

            for (Set<BooleanVariable> premise : allCandidates) {

                Set<BooleanVariable> conclusion = new HashSet<>(items);
                conclusion.removeAll(premise);

                if (!conclusion.isEmpty()) {
                    float frequency = frequency(items, Fminfr);
                    float confidence = confidence(premise, conclusion, Fminfr);
                    if (confidence >= minConfidence) {
                        AssociationRule rule = new AssociationRule(premise, conclusion, frequency, confidence);
                        setRuleFrAndConfid.add(rule);
                    }
                }
            }
        }

        return setRuleFrAndConfid;
    }

}