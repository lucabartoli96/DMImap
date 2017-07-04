package ing_sw.frith.dmimap;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import ing_sw.frith.dmimap.map.CrossMapNode;
import ing_sw.frith.dmimap.map.MapEdge;
import ing_sw.frith.dmimap.map.MapEdgeList;
import ing_sw.frith.dmimap.map.MapNode;
import ing_sw.frith.dmimap.map.MapNodeList;
import ing_sw.frith.dmimap.map.NamedMapNode;
import ing_sw.frith.dmimap.map.StairsMapNode;

import static android.content.ContentValues.TAG;





public final class MapR {




    private static JSONObject json;



    private static ArrayList<MapNode> nodes_array;
    private static HashMap<String, Integer> nodes_hash;

    private static HashMap<String, Integer> edges_hash;



    private static Context   context;
    private static String  package_name;
    private static Resources res;



    private static int FLOORS_NUMBER;






    private static void parseError(String error_message, Exception e) {

        if(e != null) {

            Log.d(TAG, "parseError:\n" + error_message +  "\n" + e.toString());

        } else {

            Log.d(TAG, "parseError:\n" + error_message);

        }

        System.exit(0);

    }








    public static String readDescriptor(Context context) {

        MapR.context      = context;
        MapR.package_name = context.getPackageName();
        MapR.res          = context.getResources();



        InputStream stream;
        String string = "";

        try {

            stream = res.getAssets().open("map_description.json");
            string = new Scanner(stream).useDelimiter("\\Z").next();

        }catch(IOException e) {

            parseError("Error while opening map_description.json\n", e);

        }

        Log.d(TAG, "createParser: MapR created!");

        return string;

    }













    public static void createParser(String json_string) {

        try {

            json = new JSONObject(json_string);

        }catch(JSONException e) {

            parseError("MapR: Impossible to create JSON parser!\n", e);

        }

        Log.d(TAG, "MapR: json object created.");

    }









    public static int getFloorsNumber() {

        int retval = 0;

        try{

            retval = json.getInt("floors_number");

        }catch(JSONException e) {

            parseError("Can't find floors_number!\n", e);
        }


        Log.d(TAG, "getFloorsNumber: " + retval);

        FLOORS_NUMBER = retval;

        return retval;
    }







    private static Bitmap[] getStairsImages() {

        Bitmap[] images = new Bitmap[2];

        int id;


        id = res.getIdentifier("up_stairs", "drawable", package_name);
        images[0] = BitmapFactory.decodeResource(res, id);

        id =    res.getIdentifier("down_stairs", "drawable", package_name);
        images[1] = BitmapFactory.decodeResource(res, id);


        return images;
    }







    public static Bitmap[] getFloorsImages() {


        Bitmap[] floors = new Bitmap[FLOORS_NUMBER];

        for(int i = 0; i < FLOORS_NUMBER; i++) {

            int id = res.getIdentifier("floor_" + i, "drawable", package_name);
            floors[i] = BitmapFactory.decodeResource(res, id);

        }

        return floors;

    }







    public static MapNodeList getNodes() {


        ArrayList<ArrayList<MapNode>> nodes = new ArrayList<>();

        Bitmap[] stairs = getStairsImages();


        try{


            JSONArray nodes_json = json.getJSONArray("nodes");

            nodes_array = new ArrayList<>();
            nodes_hash = new HashMap<>();

            int m = 0;


            for(int i = 0; i < nodes_json.length(); i++) {


                JSONArray floor_json     = nodes_json.getJSONArray(i);
                ArrayList<MapNode> floor = new ArrayList<>();



                for(int j = 0; j < floor_json.length(); j++){


                    JSONObject map_node_json = floor_json.getJSONObject(j);


                    String id = map_node_json.getString("id");
                    int     x = map_node_json.getInt("x"), y = map_node_json.getInt("y");


                    int type = map_node_json.getInt("type");

                    MapNode    map_node;

                    int image_index;
                    boolean up;

                    switch(type) {

                        case 0:

                            map_node = new CrossMapNode(id, x, y);

                            break;

                        case 1:

                            map_node = new NamedMapNode(id, x, y);
                            break;

                        case 2:

                            up = map_node_json.getBoolean("up");
                            image_index = up ? 0 : 1;
                            map_node = new StairsMapNode(id, x, y, up, stairs[image_index]);
                            break;

                        default:

                            Log.d(TAG, "getNodes: PROBLEMS WITH NODE TYPE!");
                            map_node = null;
                            break;

                    }


                    nodes_array.add(map_node);
                    nodes_hash.put(id, m);


                    floor.add(map_node);
                    m++;

                }

                nodes.add(floor);
            }

        } catch (JSONException e) {

            parseError("Nodes nodes_array not written in correct way|\n", e);

        }


        Log.d(TAG, "getNodes: " + nodes.toString());

        return new MapNodeList(nodes);

    }




    private static int compute_distance(MapNode node_1, MapNode node_2) {


        int x_1 = node_1.getX();
        int y_1 = node_1.getY();
        int x_2 = node_2.getX();
        int y_2 = node_2.getY();

        int X = x_1 - x_2, Y = y_1 - y_2;

        X *= X;
        Y *= Y;

        return (int) Math.sqrt(X + Y);


    }









    public static MapEdgeList getEdges() {

        ArrayList<ArrayList<MapEdge>> edges = new ArrayList<>();

        edges_hash = new HashMap<>();

        try{


            JSONArray edges_json = json.getJSONArray("edges");

            int[]     index   = new int[2];
            MapNode[] node    = new MapNode[2];
            String[]  couple  = new String[2];



            for(int i = 0; i < edges_json.length(); i++) {


                JSONArray floor_json     = edges_json.getJSONArray(i);
                ArrayList<MapEdge> floor = new ArrayList<>();



                for(int j = 0; j < floor_json.length(); j++){


                    JSONArray map_edge_json = floor_json.getJSONArray(j);



                    index[0] = map_edge_json.getInt(0);
                    index[1] = map_edge_json.getInt(1);

                    node[0] = nodes_array.get(index[0]);
                    node[1] = nodes_array.get(index[1]);

                    couple[0] = index[0] + "-" + index[1];
                    couple[1] = index[1] + "-" + index[0];

                    int weight;


                    if(map_edge_json.length() == 3)  {


                        weight = map_edge_json.getInt(2);

                    } else {

                        weight = compute_distance(node[0], node[1]);

                    }

                    edges_hash.put(couple[0], weight);
                    edges_hash.put(couple[1], weight);



                    MapEdge  map_edge = new MapEdge(node[0], node[1]);


                    floor.add(map_edge);

                }

                edges.add(floor);
            }

        } catch (JSONException e) {

            parseError("Edges nodes_array not written in correct way|\n", e);

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


                    int v = list_json.getInt(j);
                    int w = edges_hash.get(i + "-" + v);

                    Vertex vertex = new Vertex(v, w);

                    list.add(vertex);
                }

                graph.add(list);
            }


            size = graph.size();

        } catch (JSONException e) {

            parseError("Graph not written in correct way|\n", e);

        }


        Log.d(TAG, "getGraph: " + graph.toString());

        return new Graph(size , graph);

    }






    public static int convert(String id) {

        return nodes_hash.get(id);

    }







    public static String convert(int i) {

        return nodes_array.get(i).getId();

    }


}
