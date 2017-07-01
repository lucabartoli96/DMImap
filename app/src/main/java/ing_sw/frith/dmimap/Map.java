package ing_sw.frith.dmimap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;





public class Map extends View {

    private final int FLOORS_NUMBER;

    //floors variables
    private int      current_floor;
    private Bitmap[] floors;
    private Bitmap   current_floor_image;



    //variables for Rect
    private Rect dst;
    private int x;
    private int y;
    private int l;



    //variables for dragging
    private int D_x;
    private int D_y;



    //variables for nodes
    private ArrayList<ArrayList<MapNode>> nodes;
    private Paint nodePaint;











    public Map(Context context, int floorsNumber, ArrayList<ArrayList<MapNode>> nodes) {

        super(context);

        //resources and package infos
        Resources res = getResources();
        String package_name = context.getPackageName();


        //makes map fit its parent
        int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
        this.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT));



        //initialize floors array
        FLOORS_NUMBER = floorsNumber;
        floors = new Bitmap[FLOORS_NUMBER];

        for(int i = 0; i < FLOORS_NUMBER; i++) {

            String fname = "floor_" + i;
            int id = res.getIdentifier(fname, "drawable", package_name);
            floors[i] = BitmapFactory.decodeResource(res, id);

        }

        current_floor = 0;

        current_floor_image = floors[current_floor];

        //intitialize x and y coordinates of dst to top-left corner
        //and l to 0 (it is set elsewhere), allocate dst
        x = 0;
        y = 0;
        l= 0;

        dst = new Rect(x, y, l, l);

        //initialize just for good style
        D_x = 0;
        D_y = 0;


        //variables fro nodes
        nodePaint = new Paint();
        this.nodes = nodes;

    }






    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if(l == 0) {

            l = Math.min(canvas.getHeight(), canvas.getWidth());
            update_map_nodes_position();

        }


        dst.set(x, y, x + l, y + l);
        canvas.drawBitmap(current_floor_image, null, dst, null);



        for(MapNode node : nodes.get(current_floor)){

            nodePaint.setColor(node.getColor());
            canvas.drawOval(node, nodePaint);

        }


    }










    /**
     *
     *  Methods for dragging!
     *
     */

    private void start_drag(int x, int y) {

        this.D_x = x - this.x;
        this.D_y = y - this.y;

    }

    private void drag(int x, int y) {

        this.x = x - D_x;
        this.y = y - D_y;

        update_map_nodes_position();

    }

    private void end_drag() {

        this.D_x = 0;
        this.D_y = 0;

    }








    private void map_node_touched(int x, int y) {


        for(MapNode node : nodes.get(current_floor)){

            if(node.contains(x, y)) {

                node.select();

            }

        }

    }



    private void update_map_nodes_position() {

        for(MapNode node : nodes.get(current_floor)){

            node.updatePosition(this.x, this.y, this.l);

        }
    }






    @Override
    public boolean onTouchEvent(MotionEvent event) {
        
        int x = (int) event.getX();
        int y = (int) event.getY();
        
        
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                start_drag(x, y);
                map_node_touched(x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                drag(x, y);
                break;

            case MotionEvent.ACTION_UP:

                end_drag();
                break;

        }

        invalidate();

        return true;
    }


}

