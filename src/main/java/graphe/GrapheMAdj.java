package graphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheMAdj extends Graphe {
	private List<List<Integer>> matrice;
    private Map<String, Integer> indices;

    public GrapheMAdj() {
        matrice = new ArrayList<>();
        indices = new HashMap<>();
    }

    public GrapheMAdj(String str) {
    	this();
		this.peupler(str);
	}

    @Override
    public void ajouterSommet(String noeud) {
        if (!indices.containsKey(noeud)) {
            indices.put(noeud, indices.size());

            for (List<Integer> ligne : matrice)
                ligne.add(0);

            List<Integer> newLigne = new ArrayList<>(Collections.nCopies(matrice.size() + 1, 0));
            matrice.add(newLigne);
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
        int indexDestination = indices.get(destination);

        if (matrice.get(indexSource).get(indexDestination) > 0) {
            throw new IllegalArgumentException("Un arc existe déjà entre les sommets : " + source + " et " + destination);
        }
        if (valeur < 0)
            throw new IllegalArgumentException("Les valuations ne doivent pas être négatives : " + valeur);

        matrice.get(indexSource).set(indexDestination, valeur);
    }

    @Override
    public void oterSommet(String noeud) {
        if (indices.containsKey(noeud)) {
            int index = indices.get(noeud);
            indices.remove(noeud);

            matrice.remove(index);
            for (List<Integer> row : matrice)
                row.remove(index);

            Map<String, Integer> newIndices = new HashMap<>();
            for (Map.Entry<String, Integer> entry : indices.entrySet()) {
                int oldIndex = entry.getValue();
                if (oldIndex > index) {
                    newIndices.put(entry.getKey(), oldIndex - 1);
                } else {
                    newIndices.put(entry.getKey(), oldIndex);
                }
            }
            indices = newIndices;
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (indices.containsKey(source) && indices.containsKey(destination)) {
            int indexSource = indices.get(source);
            int indexDestination = indices.get(destination);

            if (matrice.get(indexSource).get(indexDestination) == 0) {
                throw new IllegalArgumentException("Aucun arc n'existe entre les sommets : " + source + " et " + destination);
            }

            matrice.get(indexSource).set(indexDestination, 0);
        } else {
            throw new IllegalArgumentException("Sommet source et/ou sommet de destination introuvable : " + source + ", " + destination);
        }
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successors = new ArrayList<>();
        if (indices.containsKey(sommet)) {
            int index = indices.get(sommet);
            for (Map.Entry<String, Integer> entry : indices.entrySet()) {
                if (matrice.get(index).get(entry.getValue()) > 0) {
                    successors.add(entry.getKey());
                }
            }
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
        int indexDestination = indices.get(dest);
        return matrice.get(indexSource).get(indexDestination);
    }

    @Override
    public boolean contientSommet(String sommet) {
        return indices.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        int indexSource = indices.get(src);
        int indexDestination = indices.get(dest);
        return matrice.get(indexSource).get(indexDestination) > 0;
    }

}
