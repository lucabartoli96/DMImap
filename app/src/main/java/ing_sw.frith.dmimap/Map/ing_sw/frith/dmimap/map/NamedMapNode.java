package ing_sw.frith.dmimap.map;


import android.graphics.Canvas;
import android.graphics.Paint;



public class NamedMapNode extends MapNode {


    private static final int DEFAULT_D        = 3;

    private MapNodeName name;

    private boolean position;


    public NamedMapNode(int id, int x, int y, MapNodeName name, int floor) {

        super(id, x, y, DEFAULT_D, floor);

        this.name = name;
        position  = false;
    }


    
    public MapNodeName getName() {

        return name;

    }


    public void makePosition() {

        position = true;

    }



    @Override
    public void unselect() {
        super.unselect();

        position = false;

    }



    @Override
    public void draw(Canvas canvas, Paint nodePaint) {


        if(position) {

            canvas.drawRect(this, nodePaint);

        } else {

            canvas.drawOval(this, nodePaint);

        }


    }





    @Override
    public void onClick(OnClickMapNodeHandler handler) {

        handler.onClickedNamed(name);

    }

}
