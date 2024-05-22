package appli;

import java.util.HashMap;
import java.util.Map;

import graphe.*;

import static graphe.GraphImporter.*;

public class Appli {
    public static void main(String[] args) {
        GrapheLArcs lArcsTest = new GrapheLArcs();
        GrapheMAdj mAdjTest = new GrapheMAdj();
        GrapheHHAdj hAdjTest = new GrapheHHAdj();
        GrapheLAdj lAdjTest = new GrapheLAdj();

        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();

        long debut = System.nanoTime();
        Arc arc = importer("graphes/barabasi/g-100002-1.txt", lArcsTest);
        long fin = System.nanoTime();
        System.out.println("L'importation a dure " + (fin - debut)/1000000 + " millisecondes");

        debut = System.nanoTime();
        System.out.println(Dijkstra.chemin(arc.getSource(), arc.getDestination(), lArcsTest));
        fin = System.nanoTime();
        System.out.println("dijkstra a dure " + (fin - debut)/1000000 + " millisecondes");
    }
}