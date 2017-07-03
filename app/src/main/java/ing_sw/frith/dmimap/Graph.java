package ing_sw.frith.dmimap;

import java.util.ArrayList;

public class Graph {

    private int n;
    private ArrayList<ArrayList<Vertex>> adj;

    public Graph(int n, ArrayList<ArrayList<Vertex>> adj) {

        this.n = n;
        this.adj = adj;
    }

    /*

        accedere una lista:
        adj.get(i);


        sapere quanto è lungo un arraylist
        adj.size();

        primo elemento della lista v-esima:
        adj.get(v).get(0);  come scrivere   adj[v][0]

        peso dell'elemtno
        adj.get(v).get(0).W();


        ciclo for-each, che scandisce tutti i nodi
        for(Vertex u : adj.get(v)) {

            relax(fsdfasd);

        ]

     */


    public int[] pairShortestPath(int u, int v) {

        //TODO: implementare Dijkstra per due nodi

        return null;
    }



}
