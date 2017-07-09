package ing_sw.frith.dmimap;


import android.util.Log;
import android.view.View;
import android.widget.Button;

import ing_sw.frith.dmimap.map.Map;
import ing_sw.frith.dmimap.map.MapNode;

import static android.content.ContentValues.TAG;

public class StartNavigation implements View.OnClickListener {


    private Button start;
    private Position position;
    private Destination destination;
    private Graph graph;
    private Map map;




    public StartNavigation(Button start, Position position, Destination destination, Graph graph, Map map) {

        this.start = start;
        this.position = position;
        this.destination = destination;
        this.graph = graph;
        this.map = map;

        start.setOnClickListener(this);
    }




    @Override
    public void onClick(View view) {

        int s = position.getNode();
        int u = destination.getNode();
        int[] path_ids;


        if(s != -1 && u != -1) {

            path_ids = graph.pairShortestPath(s, u);

            MapNode[] path = new MapNode[path_ids.length];

            for(int i = 0; i < path.length ; i++) {

                path[i] = MapR.iToMapNode(path_ids[i]);

            }

            map.setPath(path);

        }

    }
}
