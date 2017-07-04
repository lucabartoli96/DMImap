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
    //decidere se mettere anche d come percentuale
    private int d;


    private int color;
    private boolean selected;



    public MapNode(int id, int x, int y, int d) {

        super(x, y, x + d, y + d);

        this.id = id;
        this.x = x;
        this.y = y;
        this.d = d;
        this.color = UNSELECTED_COLOR;
        this.selected = false;

    }



    //abstract methods
    public abstract void draw(Canvas canvas, Paint nodePaint);
    public abstract void onClick(OnClickMapNodeHandler handler);




    @Override
    public void updatePosition(int mapX, int mapY, int mapD) {

        int next_x, next_y;

        int distance = (x * mapD)/100;

        next_x = mapX + distance;
        next_y = mapY + distance;

        set(next_x,next_y, next_x + d, next_y + d);

    }




    //Some getters

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


    //Toggle functions for color and state





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

        return  "id = " + id + "\n" +
                "x  = " + x  + "\n" +
                "y  = " + y  + "\n";

    }
}
