package graphe;

import java.util.*;

public class GrapheLAdj extends Graphe{
    private Map<String, List<Arc>> listeAdj;

    public GrapheLAdj(){
        this.listeAdj = new HashMap<>();
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!listeAdj.containsKey(noeud))
            listeAdj.put(noeud, new ArrayList<>(0));
    }

    public void ajouterArc(String source, String destination, Integer valeur) {
        if (!listeAdj.containsKey(source)) {
            ajouterSommet(source);
        }
        if (!listeAdj.containsKey(destination)) {
            ajouterSommet(destination);
        }
        for (Arc arc : listeAdj.get(source)) {
            if (arc.getDestination().equals(destination)) {
                throw new IllegalArgumentException("Un arc existe déjà entre les sommets : " + source + " et " + destination);
            }
        }
        if (valeur < 0)
            throw new IllegalArgumentException("Les valuations ne doivent pas etre negatives " + valeur);

        listeAdj.get(source).add(new Arc(source, destination, valeur));
    }

    @Override
    public void oterSommet(String noeud) {
        if (listeAdj.containsKey(noeud)) {
            listeAdj.remove(noeud);
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (listeAdj.containsKey(source) && listeAdj.containsKey(destination)) {
            boolean arcExiste = false;

            for (Arc arc : listeAdj.get(source))
                if (arc.getDestination().equals(destination)) {
                    listeAdj.get(source).remove(arc);
                    arcExiste = true;
                }

            if (!arcExiste) {
                throw new IllegalArgumentException("Aucun arc n'existe entre les sommets : " + source + " et " + destination);
            }

        } else {
            throw new IllegalArgumentException("Sommet source et/ou sommet de destination introuvable : " + source + ", " + destination);
        }
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successors = new ArrayList<>();
        if (listeAdj.containsKey(sommet))
            for (Arc arc : listeAdj.get(sommet))
                successors.add(arc.getDestination());
        return successors;
    }

    @Override
    public List<String> getSommets() {
        List<String> vertices = new ArrayList<>(listeAdj.keySet());
        Collections.sort(vertices);
        return vertices;
    }

    @Override
    public int getValuation(String src, String dest) {
        for (Arc arc : listeAdj.get(src))
            if (arc.getDestination().equals(dest))
                return arc.getValuation();
        return 0;
    }
    @Override
    public boolean contientSommet(String sommet) {
        return listeAdj.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        for (Arc arc : listeAdj.get(src))
            if (arc.getDestination().equals(dest))
                return true;
        return false;
    }
}