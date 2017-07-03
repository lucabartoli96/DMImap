package ing_sw.frith.dmimap;


import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;




public class MapNodeList {




    private ArrayList<ArrayList<MapNode>> list;
    private Paint nodePaint;




    public MapNodeList(ArrayList<ArrayList<MapNode>> list) {

        this.list = list;
        nodePaint = new Paint();

    }





    public void drawNodes(Canvas canvas, int current_floor) {


        for(MapNode node : list.get(current_floor)){

            nodePaint.setColor(node.getColor());
            canvas.drawOval(node, nodePaint);

        }

    }




    public void onTouchedMapNode(int current_floor, int x, int y) {

        MapNode clicked;

        for(MapNode node : list.get(current_floor)){

            if(node.contains(x, y)) {

                clicked = node;
                break;
            }

        }

        

    }






    public void updatePositions(int current_floor, int x, int y, int l) {

        for(MapNode node : list.get(current_floor)){

            node.updatePosition(x, y, l);

        }

    }


}
