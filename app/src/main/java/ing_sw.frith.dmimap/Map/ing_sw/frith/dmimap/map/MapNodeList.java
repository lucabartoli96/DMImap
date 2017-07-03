package ing_sw.frith.dmimap.map;


import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;




public class MapNodeList {




    private ArrayList<ArrayList<MapNode>> list;
    private Paint nodePaint;

    private OnClickMapNodeHandler handler;






    public MapNodeList(ArrayList<ArrayList<MapNode>> list) {

        this.list = list;
        nodePaint = new Paint();

    }





    public void setOnClickMapNodeHandler(OnClickMapNodeHandler handler) {

        this.handler = handler;
    }







    public void drawNodes(Canvas canvas, int current_floor) {


        for(MapNode node : list.get(current_floor)){

            nodePaint.setColor(node.getColor());
            node.draw(canvas, nodePaint);

        }

    }







    public void clickedMapNode(int current_floor, int x, int y) {

        MapNode clicked = null;

        for(MapNode node : list.get(current_floor)){

            if(node.contains(x, y)) {

                clicked = node;
                break;
            }

        }


        if(clicked != null)

            clicked.onClick(handler);


    }








    public void updatePositions(int current_floor, int x, int y, int l) {

        for(MapNode node : list.get(current_floor)){

            node.updatePosition(x, y, l);

        }

    }





}
