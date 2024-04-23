package graphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheLArcs extends Graphe {
    private List<String> noeuds;
    private List<Arc> arcs;

    public GrapheLArcs(){
        this.arcs = new ArrayList<>(0);
        this.noeuds = new ArrayList<>(0);
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!noeuds.contains(noeud)) {
            noeuds.add(noeud);
        }
    }

    public void ajouterArc(String source, String destination, Integer valeur) {
        if (!noeuds.contains(source)) {
            ajouterSommet(source);
        }
        if (!noeuds.contains(destination)) {
            ajouterSommet(destination);
        }

        for (Arc arc : arcs) {
            if (arc.getSource().equals(source) && arc.getDestination().equals(destination)) {
                throw new IllegalArgumentException("Un arc existe déjà entre les sommets : " + source + " et " + destination);
            }
        }

        if (valeur < 0) {
            throw new IllegalArgumentException("Les valuations ne doivent pas être négatives : " + valeur);
        }
        arcs.add(new Arc(source, destination, valeur));
    }

    @Override
    public void oterSommet(String noeud) {
        if (noeuds.contains(noeud)) {
            noeuds.remove(noeud);

            for (Arc arc : arcs){
                if (arc.getSource().equals(noeud) || arc.getDestination().equals(noeud)){
                    arcs.remove(arc);
                }
            }

        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (noeuds.contains(source) && noeuds.contains(destination)) {
            boolean arcExiste = false;
            Arc arcASupprimer = null;
            for (Arc arc : arcs) {
                if (arc.getSource().equals(source) && arc.getDestination().equals(destination)) {
                    arcExiste = true;
                    arcs.remove(arc);
                }
            }
            if (!arcExiste)
                throw new IllegalArgumentException("Aucun arc n'existe entre les sommets : " + source + " et " + destination);

        } else {
            throw new IllegalArgumentException("Sommet source et/ou sommet de destination introuvable : " + source + ", " + destination);
        }
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successors = new ArrayList<>();
        if (noeuds.contains(sommet)) {
            for (Arc arc : arcs) {
                if (arc.getSource().equals(sommet))
                    successors.add(arc.getDestination());
            }
        }
        return successors;
    }

    @Override
    public List<String> getSommets() {
        return new ArrayList<>(noeuds);
    }

    @Override
    public int getValuation(String src, String dest) {
        for (Arc arc : arcs)
            if (arc.getSource().equals(src) && arc.getDestination().equals(dest))
                return arc.getValuation();
        return 0;
    }

    @Override
    public boolean contientSommet(String sommet) {
        return noeuds.contains(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        for (Arc arc : arcs)
            if (arc.getSource().equals(src) && arc.getDestination().equals(dest))
                return true;
        return false;
    }
}
