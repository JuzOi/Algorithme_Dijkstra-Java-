package graphe;

import java.util.*;

public class GrapheLAdj extends Graphe {
    private List<Map<String, Integer>> listeAdj;
    private Map<String, Integer> indices;

    public GrapheLAdj(){
        this.listeAdj = new ArrayList<>();
        this.indices = new HashMap<>();
    }

    @Override
    public void ajouterSommet(String noeud){
        if (!indices.containsKey(noeud)){
            indices.put(noeud, listeAdj.size());
            Map<String, Integer> hashAdj = new HashMap<>();
            listeAdj.add(hashAdj);
        }
    }

    public void ajouterArc(String source, String destination, Integer valeur) {
        if (!indices.containsKey(source)) {
            ajouterSommet(source);
        }
        if (!indices.containsKey(destination)) {
            ajouterSommet(destination);
        }
        int indexSource = indices.get(source);

        if (listeAdj.get(indexSource).containsKey(destination)) {
            throw new IllegalArgumentException("Un arc existe déjà entre les sommets : " + source + " et " + destination);
        }
        if (valeur < 0)
            throw new IllegalArgumentException("Les valuations ne doivent pas etre negatives " + valeur);

        listeAdj.get(indexSource).put(destination, valeur);
    }

    @Override
    public void oterSommet(String noeud) {
        if (indices.containsKey(noeud)) {
            int index = indices.get(noeud);
            indices.remove(noeud);

            listeAdj.remove(index);
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (indices.containsKey(source) && indices.containsKey(destination)) {
            int indexSource = indices.get(source);

            if (!listeAdj.get(indexSource).containsKey(destination)) {
                throw new IllegalArgumentException("Aucun arc n'existe entre les sommets : " + source + " et " + destination);
            }

            listeAdj.get(indexSource).remove(destination);
        } else {
            throw new IllegalArgumentException("Sommet source et/ou sommet de destination introuvable : " + source + ", " + destination);
        }
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successors = new ArrayList<>();
        if (indices.containsKey(sommet)) {
            int index = indices.get(sommet);
            successors.addAll(listeAdj.get(index).keySet());
        }
        return successors;
    }

    @Override
    public List<String> getSommets() {
        List<String> vertices = new ArrayList<>(indices.keySet());
        Collections.sort(vertices);
        return vertices;
    }

    @Override
    public int getValuation(String src, String dest) {
        int indexSource = indices.get(src);
        return listeAdj.get(indexSource).get(dest);
    }

    @Override
    public boolean contientSommet(String sommet) {
        return indices.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        int indexSource = indices.get(src);
        return listeAdj.get(indexSource).containsKey(dest);
    }
}
