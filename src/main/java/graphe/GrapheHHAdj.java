package graphe;

import java.util.*;

public class GrapheHHAdj extends Graphe{
    private Map<String, Map<String, Integer>> hashAdj;

    public GrapheHHAdj(){
        this.hashAdj = new HashMap<>();
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!hashAdj.containsKey(noeud))
            hashAdj.put(noeud, new HashMap<>());
    }

    public void ajouterArc(String source, String destination, Integer valeur) {
        if (!hashAdj.containsKey(source)) {
            ajouterSommet(source);
        }
        if (!hashAdj.containsKey(destination)) {
            ajouterSommet(destination);
        }
        for (String succ : hashAdj.get(source).keySet()) {
            if (succ.equals(destination)) {
                throw new IllegalArgumentException("Un arc existe déjà entre les sommets : " + source + " et " + destination);
            }
        }
        if (valeur < 0)
            throw new IllegalArgumentException("Les valuations ne doivent pas etre negatives " + valeur);

        hashAdj.get(source).put(destination, valeur);
    }

    @Override
    public void oterSommet(String noeud) {
        hashAdj.remove(noeud);
    }

    @Override
    public void oterArc(String source, String destination) {
        if (hashAdj.containsKey(source) && hashAdj.containsKey(destination)) {
            for (String succ : hashAdj.get(source).keySet())
                if (succ.equals(destination))
                    hashAdj.get(source).remove(succ);


            if (!hashAdj.get(source).containsKey(destination)) {
                throw new IllegalArgumentException("Aucun arc n'existe entre les sommets : " + source + " et " + destination);
            }

        } else {
            throw new IllegalArgumentException("Sommet source et/ou sommet de destination introuvable : " + source + ", " + destination);
        }
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successors = new ArrayList<>();
        if (hashAdj.containsKey(sommet))
            successors.addAll(hashAdj.get(sommet).keySet());
        return successors;
    }

    @Override
    public List<String> getSommets() {
        List<String> vertices = new ArrayList<>(hashAdj.keySet());
        Collections.sort(vertices);
        return vertices;
    }

    @Override
    public int getValuation(String src, String dest) {
        return hashAdj.get(src).get(dest);
    }
    @Override
    public boolean contientSommet(String sommet) {
        return hashAdj.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        return hashAdj.get(src).containsKey(dest);
    }
}