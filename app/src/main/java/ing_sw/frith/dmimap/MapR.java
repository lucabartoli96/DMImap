package ing_sw.frith.dmimap;


import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static android.content.ContentValues.TAG;



/**
 * TODO: Gestire le eccezioni!
 */





public final class MapR {




    private static JSONObject json;




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


            for(int i = 0; i < nodes_json.length(); i++) {


                JSONArray floor_json     = nodes_json.getJSONArray(i);
                ArrayList<MapNode> floor = new ArrayList<>();


                for(int j = 0; j < floor_json.length(); j++){


                    JSONObject map_node_json = floor_json.getJSONObject(j);


                    String id = map_node_json.getString("id");
                    int     x = map_node_json.getInt("x"), y = map_node_json.getInt("y");


                    MapNode    map_node      = new MapNode(id, x, y);


                    floor.add(map_node);

                }

                nodes.add(floor);
            }

        } catch (JSONException e) {

            Log.d(TAG, "getNodes: Nodes array not written in correct way|\n" + e.toString());

        }

        Log.d(TAG, "getNodes: " + nodes.toString());

        return new MapNodeList(nodes);

    }

}
