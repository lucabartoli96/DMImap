package ing_sw.frith.dmimap;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import ing_sw.frith.dmimap.map.ClassRoomNodeName;
import ing_sw.frith.dmimap.map.CrossMapNode;
import ing_sw.frith.dmimap.map.MapEdge;
import ing_sw.frith.dmimap.map.MapEdgeList;
import ing_sw.frith.dmimap.map.MapNode;
import ing_sw.frith.dmimap.map.MapNodeList;
import ing_sw.frith.dmimap.map.MapNodeName;
import ing_sw.frith.dmimap.map.NamedMapNode;
import ing_sw.frith.dmimap.map.OfficeNodeName;
import ing_sw.frith.dmimap.map.OtherNodeName;
import ing_sw.frith.dmimap.map.StairsMapNode;

import static android.content.ContentValues.TAG;





public final class MapR {




    private static JSONObject json;



    private static ArrayList<MapNode> nodes_array;
    
    
    
    private static HashMap<MapNodeName, Integer> named_nodes_hash;



    private static Context   context;
    private static String  package_name;
    private static Resources res;



    private static int FLOORS_NUMBER;
    private static int NODE_NUMBER;



    private static ArrayList<ArrayList<Vertex>> graph;




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







    private static MapNodeName getName(JSONObject name_json) throws JSONException {


        MapNodeName name;

        if(name_json.has("office")) {


            JSONArray owners_json = name_json.getJSONArray("office");
            ArrayList<String> owners = new ArrayList<>();

            for(int i = 0; i < owners_json.length(); i++) {

                owners.add(owners_json.getString(i));

            }

            name = new OfficeNodeName(owners);


        } else if(name_json.has("classroom")) {

            String classroom = name_json.getString("classroom");

            name = new ClassRoomNodeName(classroom);

        } else {

            String other = name_json.getString("other");

            name = new OtherNodeName(other);
        }


        return name;

    }







    public static MapNodeList getNodes() {


        ArrayList<ArrayList<MapNode>> nodes = new ArrayList<>();

        Bitmap[] stairs = getStairsImages();


        try{


            JSONArray nodes_json = json.getJSONArray("nodes");

            nodes_array = new ArrayList<>();
            named_nodes_hash = new HashMap<>();

            int id = 0;


            for(int i = 0; i < nodes_json.length(); i++) {


                JSONArray floor_json     = nodes_json.getJSONArray(i);
                ArrayList<MapNode> floor = new ArrayList<>();



                for(int j = 0; j < floor_json.length(); j++){


                    JSONObject map_node_json = floor_json.getJSONObject(j);


                    int     x = map_node_json.getInt("x"), y = map_node_json.getInt("y");


                    MapNode    map_node;

                    if(map_node_json.has("name")) {


                        JSONObject name_json = map_node_json.getJSONObject("name");

                        MapNodeName name = getName(name_json);

                        map_node = new NamedMapNode(id, x, y, name);

                        named_nodes_hash.put(name , id);


                    } else if(map_node_json.has("stairs")) {

                        boolean up = map_node_json.getBoolean("stairs");
                        map_node = new StairsMapNode(id, x, y, up, stairs[up ? 0 : 1]);

                    } else {

                        map_node = new CrossMapNode(id, x, y);

                    }


                    nodes_array.add(map_node);

                    floor.add(map_node);
                    id++;

                }

                nodes.add(floor);
            }

            NODE_NUMBER = id;

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


        graph = new ArrayList<>();

        for(int i = 0; i < NODE_NUMBER; i++) {

            graph.add(new ArrayList<Vertex>());

        }



        try{


            JSONArray edges_json = json.getJSONArray("edges");

            int[]     index   = new int[2];
            MapNode[] node    = new MapNode[2];
            Vertex[]  vertex  = new Vertex[2];


            for(int i = 0; i < edges_json.length(); i++) {


                JSONArray floor_json     = edges_json.getJSONArray(i);
                ArrayList<MapEdge> floor = new ArrayList<>();



                for(int j = 0; j < floor_json.length(); j++){


                    JSONArray map_edge_json = floor_json.getJSONArray(j);



                    index[0] = map_edge_json.getInt(0);
                    index[1] = map_edge_json.getInt(1);

                    node[0] = nodes_array.get(index[0]);
                    node[1] = nodes_array.get(index[1]);


                    int weight;
                    boolean visible;


                    if(map_edge_json.length() == 3)  {


                        weight = map_edge_json.getInt(2);
                        visible = false;

                    } else {

                        weight = compute_distance(node[0], node[1]);
                        visible = true;

                    }

                    vertex[0] = new Vertex(index[0], weight);
                    vertex[1] = new Vertex(index[1], weight);


                    graph.get(index[0]).add(vertex[1]);
                    graph.get(index[1]).add(vertex[0]);


                    MapEdge  map_edge = new MapEdge(node[0], node[1], visible);


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

        Log.d(TAG, "getGraph: " + graph.toString());

        return new Graph(graph.size() , graph);

    }




}
