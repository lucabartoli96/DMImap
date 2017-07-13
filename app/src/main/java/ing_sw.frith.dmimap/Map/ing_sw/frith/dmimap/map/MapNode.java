package ing_sw.frith.dmimap.map;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import ing_sw.frith.dmimap.UpdatableMapItem;


public abstract class MapNode extends RectF implements UpdatableMapItem {



    private static final int UNSELECTED_COLOR = Color.BLACK;
    private static final int SELECTED_COLOR   = Color.RED;




    private int id;

    private final int x;
    private final int y;
    private final int d;
    private final int floor;


    private int color;
    private boolean selected;



    public MapNode(int id, int x, int y, int d, int floor) {

        super();

        this.id = id;
        this.x = x;
        this.y = y;
        this.d = d;
        this.floor = floor;
        this.color = UNSELECTED_COLOR;
        this.selected = false;

    }



    public abstract void draw(Canvas canvas, Paint nodePaint);
    public abstract void onClick(OnClickMapNodeHandler handler);




    @Override
    public void updatePosition(int mapX, int mapY, int mapD) {


        int distance_x = (x * mapD)/100;
        int distance_y = (y * mapD)/100;
        int next_d = (d * mapD)/100;

        int next_x = mapX + distance_x - next_d/2;
        int next_y = mapY + distance_y - next_d/2;


        set(next_x,next_y, next_x + next_d, next_y + next_d);

    }





    public int getId() {

        return id;

    }



    public int getColor() {

        return color;

    }



    public boolean isSelected() {

        return selected;

    }



    public int getX() {

        return x;

    }



    public int getY() {

        return y;

    }

    public int getFloor() {

        return floor;

    }






    public void select() {

        if(!selected) {

            selected = true;
            color    = SELECTED_COLOR;

        }
    }





    public void unselect() {

        if(selected) {

            selected = false;
            color    = UNSELECTED_COLOR;
        }
    }








    @Override
    public String toString() {

        return  "node_id = " + id + "\n" +
                "x  = " + x  + "\n" +
                "y  = " + y  + "\n";

    }
}
