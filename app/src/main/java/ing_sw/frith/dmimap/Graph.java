package ing_sw.frith.dmimap;

import java.util.ArrayList;


public class Graph {



    private final int N;


    private ArrayList<ArrayList<Vertex>> adj;







    public Graph(int n, ArrayList<ArrayList<Vertex>> adj) {

        this.N = n;
        this.adj = adj;
    }







    public int[] pairShortestPath(int s, int des) {

        int[] d = new int[N];
        int[] c = new int[N];  //distanze in termini di archi come nell dfs
        int[] p = new int[N];

        for(int i = 0; i < N; i++) {

            d[i] = Integer.MAX_VALUE;
            p[i] = -1;

        }

        d[s] = 0;
        c[s] = 0;

        PriorityQueue Q = new PriorityQueue(d);

        boolean flag = false;
        int u = -1;

        while(!Q.empty() && !flag) {

            u = Q.extract_min();

            if(u == des) {

                flag = true;

            } else {


                for (Vertex vertex : adj.get(u)) {

                    relax(u, vertex, d, c, p);

                }

            }

        }


        int[] path = new int[c[u] + 1];

        int i = u, j = c[u];

        while(i != -1) {

            path[j] = i;
            i = p[i];
            j--;

        }


        return path;
    }








    private void relax(int u, Vertex vertex, int[] d, int[] c, int[] p) {


        int v = vertex.getV(), w = vertex.W();


        if(d[u] > d[v] + w) {

            d[u] = d[v] + w;
            c[u] = c[v] + 1;
            p[u] = v;

        }


    }












    private class PriorityQueue {

        private int[] d;
        private ArrayList<Integer> indexes;

        public PriorityQueue(int[] d) {

            this.d = d;

            indexes = new ArrayList<>();

            for(int i = 0; i < N; i++) {

                indexes.set(i, i);

            }

        }

        public boolean empty() {

            return indexes.isEmpty();
        }



        public int extract_min() {

            int m = -1, min = Integer.MIN_VALUE;

            for(Integer i : indexes) {

                if(d[i] < min) {

                    m = i;
                    min = d[i];
                }

            }

            indexes.remove(m);

            return m;

        }
    }


}
