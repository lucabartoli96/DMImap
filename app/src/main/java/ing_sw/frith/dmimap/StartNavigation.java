package ing_sw.frith.dmimap;


import android.widget.Button;

import ing_sw.frith.dmimap.map.Map;

public class StartNavigation {


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
    }

}
