package ing_sw.frith.dmimap.map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;



public class Map extends View {

    private final int FLOORS_NUMBER;


    private Context context;
    private int      current_floor;
    private Bitmap[] floors;
    private Bitmap   current_floor_image;
    private Rect dst;
    private int x;
    private int y;
    private int l;
    private int D_x;
    private int D_y;
    private MapNodeList nodes;
    private MapEdgeList edges;
    private MapNode[] path;
    ScaleGestureDetector scaleDetector;



    public Map(Context context, int floorsNumber, Bitmap[] floors, MapNodeList nodes, MapEdgeList edges) {

        super(context);

        this.context = context;

        Resources res = getResources();
        String package_name = context.getPackageName();

        int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
        this.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT));

        FLOORS_NUMBER = floorsNumber;
        this.floors   = floors;
        current_floor = 0;
        current_floor_image = floors[current_floor];


        x = 0;
        y = 0;
        l= 0;
        dst = new Rect(x, y, l, l);


        D_x = 0;
        D_y = 0;



        this.nodes = nodes;
        nodes.setOnClickMapNodeHandler(new Handler());
        this.edges = edges;


        scaleDetector  =new ScaleGestureDetector(context, new ScaleListener());
    }






    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if(l == 0) {

            l = Math.min(canvas.getHeight(), canvas.getWidth());
            nodes.updatePositions(current_floor, x, y, l);
            edges.updateWidth(l);

        }


        dst.set(x, y, x + l, y + l);
        canvas.drawBitmap(current_floor_image, null, dst, null);


        edges.drawEdges(canvas, current_floor);
        nodes.drawNodes(canvas, current_floor);

    }






    public void setPath(MapNode[] path) {


        if(this.path != null)

            for(MapNode node : this.path) {

                node.unselect();

            }


        this.path = path;

        NamedMapNode position = (NamedMapNode) path[0];

        int floor = position.getFloor();
        setFloor(floor);


        for(MapNode node : this.path) {

            node.select();

        }

        position.makePosition();

        invalidate();

    }



    private void setFloor(int floor) {

        if(floor >= 0 && floor < FLOORS_NUMBER && floor != current_floor) {

            current_floor = floor;
            current_floor_image = floors[floor];

        }

    }










    private void start_drag(int x, int y) {

        this.D_x = x - this.x;
        this.D_y = y - this.y;

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


        if(event.getPointerCount() == 2) {


            scaleDetector.onTouchEvent(event);

        } else {


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

        }

        return true;
    }




    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {


        private boolean scaling = false;
        private float initial_l;
        private float initial_span;
        private float D_x;
        private float D_y;



        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {

            float focus_x = detector.getFocusX();
            float focus_y = detector.getFocusY();


            if(dst.contains((int) focus_x, (int) focus_y)) {

                scaling = true;
                initial_l = l;
                initial_span = detector.getCurrentSpan();
                D_x = focus_x - (float) x;
                D_y = focus_y - (float) y;

            }


            return true;
        }



        @Override
        public boolean onScale(ScaleGestureDetector detector) {


            if(scaling) {


                float span = detector.getCurrentSpan();

                float ratio = (span / initial_span);

                float next_l   =  ratio * initial_l;
                float next_D_x =  ratio * D_x;
                float next_D_y =  ratio * D_y;

                float next_x   = detector.getFocusX() - next_D_x;
                float next_y   = detector.getFocusY() - next_D_y;

                l = (int) next_l;
                x = (int) next_x;
                y = (int) next_y;


                nodes.updatePositions(current_floor, x, y, l);
                edges.updateWidth(l);
                invalidate();
            }

            return true;
        }


    }







    private class Handler implements OnClickMapNodeHandler {





        public void onClickedNamed(MapNodeName name) {

            Toast.makeText(context, name.toString() ,Toast.LENGTH_SHORT).show();

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

