package ing_sw.frith.dmimap;


import android.graphics.Color;
import android.graphics.RectF;


public class MapNode extends RectF implements UpdatableMapItem {



    private static final int UNSELECTED_COLOR = Color.BLACK;
    private static final int SELECTED_COLOR   = Color.RED;

    private static final int DEFAULT_D        = 50;


    private String id;

    private final int x;
    private final int y;
    private int d;


    private int color;
    private boolean selected;



    public MapNode(String id, int x, int y) {

        super(x, y, x + DEFAULT_D, y + DEFAULT_D);

        this.id = id;
        this.x = x;
        this.y = y;
        this.d = DEFAULT_D;
        this.color = UNSELECTED_COLOR;
        this.selected = false;

    }



    @Override
    public void updatePosition(int mapX, int mapY, int mapD) {

        int next_x, next_y;

        int distance = (x * mapD)/100;

        next_x = mapX + distance;
        next_y = mapY + distance;

        set(next_x,next_y, next_x + d, next_y + d);

    }





    public int getColor() {

        return color;

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

        return  "id = " + id + "\n" +
                "x  = " + x  + "\n" +
                "y  = " + y  + "\n";

    }
}
