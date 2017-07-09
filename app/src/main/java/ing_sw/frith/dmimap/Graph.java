package ing_sw.frith.dmimap;

import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


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

            Log.d(TAG, "pairShortestPath: min: " + u + "d[u]: " + d[u]);

            if(u == des) {

                flag = true;

            } else {


                for (Vertex vertex : adj.get(u)) {

                    relax(u, vertex, d, c, p);

                }

                Log.d(TAG, "pairShortestPath: " + print(d));

            }

        }


        int[] path = new int[c[u] + 1];



        int i = u, j = c[u];

        while(i != -1) {

            path[j] = i;
            i = p[i];
            j--;

        }

        Log.d(TAG, "pairShortestPath: length: " + path.length + "\npath: " + print(path));

        return path;
    }





    private String print(int[] d) {


        StringBuilder string = new StringBuilder();

        for(int i= 0; i < d.length; i++) {

            string.append(",").append(d[i]);

        }


        return string.toString();
    }





    private void relax(int u, Vertex vertex, int[] d, int[] c, int[] p) {


        int v = vertex.getV(), w = vertex.W();

        String msg = "Relax: " +

                "\n" + "u:" + u +
                "\n" + "d[u]:" + d[u] +
                "\n" + "v:" + v +
                "\n" + "d[v]:" + d[v]+
                "\n" + "w:" + w +
                "\n" + "d[u] + w" + (d[u] + w) +
                "\n" + "cond:" + (d[v] > d[u] + w);


        Log.d(TAG, "relax: " + msg);

        int sum;


        if(d[u] == Integer.MAX_VALUE) {

            sum = d[u];

        } else {

            sum = d[u] + w;

        }


        if(d[v] > sum) {

            d[v] = sum;
            c[v] = c[u] + 1;
            p[v] = u;

        }


        msg = "Relax: " +

                "\n" + "v:" + v +
                "\n" + "d[v]:" + d[v];

        Log.d(TAG, "after-relax: " + msg);


    }












    private class PriorityQueue {

        private int[] d;
        private ArrayList<Integer> indexes;

        public PriorityQueue(int[] d) {

            this.d = d;

            indexes = new ArrayList<>();

            for(int i = 0; i < N; i++) {

                indexes.add(i);

            }

        }

        public boolean empty() {

            return indexes.isEmpty();
        }



        public int extract_min() {

            int min_v = -1, min_i = -1, min = Integer.MAX_VALUE;

            Log.d(TAG, "extract_min: " + print(d));

            for(int i = 0; i < indexes.size(); i++ ) {

                int v = indexes.get(i);

                if(d[v] < min) {

                    min_i = i;
                    min_v = v;
                    min = d[v];
                }

            }


            indexes.remove(min_i);

            return min_v;

        }
    }


}
