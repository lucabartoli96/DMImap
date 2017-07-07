package ing_sw.frith.dmimap.map;


import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;



public class MapEdgeList {


    private static final int DEFAULT_LINE_WIDTH = 15;

    private ArrayList<ArrayList<MapEdge>> list;
    private Paint edgePaint;






    public MapEdgeList(ArrayList<ArrayList<MapEdge>> list) {

        this.list = list;
        edgePaint = new Paint();
        edgePaint.setStrokeWidth(DEFAULT_LINE_WIDTH);
    }






    public void drawEdges(Canvas canvas, int current_floor) {


        for(MapEdge edge : list.get(current_floor)){


            if(edge.isVisible()) {

                edgePaint.setColor(edge.getColor());
                canvas.drawLine(edge.startX(), edge.startY(), edge.stopX(), edge.stopY(), edgePaint);

            }

        }

    }



}
