package ing_sw.frith.dmimap;


import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static android.content.ContentValues.TAG;



/**
 * TODO: Gestire le eccezioni!
 */




public final class MapR {




    private static JSONObject json;



    private static ArrayList<MapNode> array;
    private static HashMap<String, Integer> hash;









    public static String readDescriptor(Resources res) {


        InputStream stream;
        String string = "";

        try {

            stream = res.getAssets().open("map_description.json");
            string = new Scanner(stream).useDelimiter("\\Z").next();

        }catch(IOException e) {

            Log.d(TAG, "onCreate: Error while opening map_description.json\n" + e.toString());
            System.exit(0);
        }

        Log.d(TAG, "createParser: MapR created!");

        return string;

    }











    public static void createParser(String json_string) {

        try {

            json = new JSONObject(json_string);

        }catch(JSONException e) {

            Log.d(TAG, "MapR: Impossible to create JSON parser!\n" + e.toString());

        }

        Log.d(TAG, "MapR: json object created.");

    }






    public static int getFloorsNumber() {

        int retval = 0;

        try{

            retval = json.getInt("floors_number");

        }catch(JSONException e) {

            Log.d(TAG, "MapR: Can't find floors_number!\n" + e.toString());
        }

        Log.d(TAG, "getFloorsNumber: " + retval);

        return retval;
    }










    public static MapNodeList getNodes() {


        ArrayList<ArrayList<MapNode>> nodes = new ArrayList<>();



        try{


            JSONArray nodes_json = json.getJSONArray("nodes");

            array = new ArrayList<>();
            hash  = new HashMap<>();

            int m = 0;


            for(int i = 0; i < nodes_json.length(); i++) {


                JSONArray floor_json     = nodes_json.getJSONArray(i);
                ArrayList<MapNode> floor = new ArrayList<>();



                for(int j = 0; j < floor_json.length(); j++){


                    JSONObject map_node_json = floor_json.getJSONObject(j);


                    String id = map_node_json.getString("id");
                    int     x = map_node_json.getInt("x"), y = map_node_json.getInt("y");


                    MapNode    map_node      = new MapNode(id, x, y);

                    array.add(map_node);
                    hash.put(id, m);


                    floor.add(map_node);
                    m++;

                }

                nodes.add(floor);
            }

        } catch (JSONException e) {

            Log.d(TAG, "getNodes: Nodes array not written in correct way|\n" + e.toString());

        }


        Log.d(TAG, "getNodes: " + nodes.toString());

        return new MapNodeList(nodes);

    }








    public static MapEdgeList getEdges() {

        ArrayList<ArrayList<MapEdge>> edges = new ArrayList<>();

        try{


            JSONArray edges_json = json.getJSONArray("edges");


            for(int i = 0; i < edges_json.length(); i++) {


                JSONArray floor_json     = edges_json.getJSONArray(i);
                ArrayList<MapEdge> floor = new ArrayList<>();



                for(int j = 0; j < floor_json.length(); j++){


                    JSONObject map_edge_json = floor_json.getJSONObject(j);


                    int index_1 = map_edge_json.getInt("node_1");
                    int index_2 = map_edge_json.getInt("node_2");

                    MapNode node_1 = array.get(index_1);
                    MapNode node_2 = array.get(index_2);


                    MapEdge  map_edge      = new MapEdge(node_1, node_2);


                    floor.add(map_edge);

                }

                edges.add(floor);
            }

        } catch (JSONException e) {

            Log.d(TAG, "getNodes: Edges array not written in correct way|\n" + e.toString());

        }

        Log.d(TAG, "getEdges: " + edges.toString());


        return new MapEdgeList(edges);

    }










    public static Graph getGraph() {

        ArrayList<ArrayList<Vertex>> graph = null;
        int size = 0;

        try{

            JSONArray graph_json = json.getJSONArray("graph");
            graph = new ArrayList<>();

            for(int i= 0; i < graph_json.length(); i++) {

                JSONArray list_json = graph_json.getJSONArray(i);
                ArrayList<Vertex> list = new ArrayList<>();

                for(int j = 0; j < list_json.length(); j++) {

                    JSONObject vertex_json = list_json.getJSONObject(j);

                    int v = vertex_json.getInt("v");
                    int w = vertex_json.getInt("w");

                    Vertex vertex = new Vertex(v, w);

                    list.add(vertex);
                }

                graph.add(list);
            }


            size = graph.size();

        } catch (JSONException e) {

            Log.d(TAG, "getNodes: Graph not written in correct way|\n" + e.toString());

        }

        Log.d(TAG, "getGraph: " + graph.toString());

        return new Graph(size , graph);

    }








    public static int convert(String id) {

        return hash.get(id);

    }







    public static String convert(int i) {

        return array.get(i).getId();

    }


}
