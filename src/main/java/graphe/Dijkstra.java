package graphe;

import java.util.*;

import org.jheaps.AddressableHeap;
import org.jheaps.tree.FibonacciHeap;

public class Dijkstra {
    private static FibonacciHeap<Integer, String> heap = new FibonacciHeap<>();
    private static Map<String, AddressableHeap.Handle<Integer, String>> noeudsNonTraites = new HashMap<>();

    public static void dijkstra(IGraphe g, String source, Map<String, Integer> dist, Map<String, String> prev){
        for (String sommet : g.getSommets())
            noeudsNonTraites.put(sommet, heap.insert(Integer.MAX_VALUE, sommet ));

        dist.put(source, 0);
        noeudsNonTraites.get(source).decreaseKey(0);

        algoDijkstra(g, dist, prev);
    }

    public static void algoDijkstra(IGraphe g, Map<String, Integer> dist, Map<String, String> prev){
        while (!heap.isEmpty()){
            AddressableHeap.Handle<Integer, String> noeudMin = heap.deleteMin();
            String noeudMinVal = noeudMin.getValue();
            int noeudMinDist = noeudMin.getKey();
            noeudsNonTraites.remove(noeudMinVal);

            if (noeudMinDist != Integer.MAX_VALUE) {
                dist.put(noeudMinVal, noeudMinDist);
                mettreAJourDistances(g, noeudMinVal, noeudMinDist, prev);
            }
        }
    }

    public static void mettreAJourDistances(IGraphe g, String noeudMinVal, int noeudMinDist, Map<String, String> prev){
        for (String succ : g.getSucc(noeudMinVal)) {
            if (noeudsNonTraites.containsKey(succ)) {
                int newDist = noeudMinDist + g.getValuation(noeudMinVal, succ);
                if (newDist < noeudsNonTraites.get(succ).getKey()) {
                    noeudsNonTraites.get(succ).decreaseKey(newDist);
                    prev.put(succ, noeudMinVal);
                }
            }
        }
    }
    public static String chemin(String src, String dest, IGraphe g){
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();

        dijkstra(g, src, dist, prev);

        StringBuilder s = new StringBuilder();
        if (dist.get(dest)< 0)
            return "pas de chemin entre " + src + " et " + dest;

        s.append("Dijkstra").append("\n");
        s.append(dist.get(dest)).append("\n");
        String sommetCourant = dest;
        StringBuilder chemin = new StringBuilder();
        while (sommetCourant != null){
            chemin.insert(0, sommetCourant + " ");
            sommetCourant = prev.get(sommetCourant);
        }
        s.append(chemin);
        return s.toString();
    }
}