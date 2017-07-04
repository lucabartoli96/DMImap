package ing_sw.frith.dmimap.map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import static android.content.ContentValues.TAG;


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
    private MapNodeList nodes;

    //variable for edges
    private MapEdgeList edges;



    public Map(Context context, int floorsNumber, Bitmap[] floors, MapNodeList nodes, MapEdgeList edges) {

        super(context);

        //resources and package infos
        Resources res = getResources();
        String package_name = context.getPackageName();


        //makes map fit its parent
        int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
        this.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT));



        //initialize floors array
        FLOORS_NUMBER = floorsNumber;





        this.floors   = floors;
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


        //variables for nodes
        this.nodes = nodes;
        nodes.setOnClickMapNodeHandler(new Handler());


        //variable for edges
        this.edges = edges;

    }






    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if(l == 0) {

            l = Math.min(canvas.getHeight(), canvas.getWidth());
            nodes.updatePositions(current_floor, x, y, l);

        }


        dst.set(x, y, x + l, y + l);
        canvas.drawBitmap(current_floor_image, null, dst, null);


        edges.drawEdges(canvas, current_floor);
        nodes.drawNodes(canvas, current_floor);

    }










    /**
     *
     *  Methods for dragging!
     *
     */

    private void start_drag(int x, int y) {

        this.D_x = x - this.x;
        this.D_y = y - this.y;


        //da rimuovere
        int percent_x, percent_y;

        percent_x = (100 * D_x) / this.l;
        percent_y = (100 * D_y) / this.l;


        Log.d(TAG, "start_drag: \nx:" + percent_x + "\ny: " + percent_y);
        /////
    }



    private void drag(int x, int y) {

        this.x = x - D_x;
        this.y = y - D_y;

    }



    private void end_drag() {

        this.D_x = 0;
        this.D_y = 0;

    }







    @Override
    public boolean onTouchEvent(MotionEvent event) {
        
        int x = (int) event.getX();
        int y = (int) event.getY();
        
        
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                start_drag(x, y);
                nodes.clickedMapNode(current_floor, x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                drag(x, y);
                nodes.updatePositions(current_floor, this.x, this.y, this.l);
                break;

            case MotionEvent.ACTION_UP:

                end_drag();
                break;

        }

        invalidate();

        return true;
    }







    private class Handler implements OnClickMapNodeHandler {





        public void onClickedNamed() {

        }





        public void onClickedStairs(boolean up){


            if(up) {

                if(current_floor + 1 < floors.length) {

                    current_floor++;
                }

            } else {

                if(current_floor >= 1)

                    current_floor--;

            }

            current_floor_image = floors[current_floor];
            nodes.updatePositions(current_floor, Map.this.x, Map.this.y, Map.this.l);

        }
    }






}

