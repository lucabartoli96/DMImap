package ing_sw.frith.dmimap.map;


import android.graphics.Canvas;
import android.graphics.Paint;


public class CrossMapNode extends MapNode {






    private static final int DEFAULT_D  = 1;




    public CrossMapNode(int id, int x, int y, int floor) {

        super(id, x, y, DEFAULT_D, floor);

    }




    @Override
    public void draw(Canvas canvas, Paint nodePaint) {

        canvas.drawOval(this, nodePaint);

    }





    @Override
    public void onClick(OnClickMapNodeHandler handler) {

    }



}
