package ing_sw.frith.dmimap.map;


import android.graphics.Canvas;
import android.graphics.Paint;

public class CrossMapNode extends MapNode {






    private static final int DEFAULT_D  = 10;




    public CrossMapNode(String id, int x, int y) {

        super(id, x, y, DEFAULT_D);

    }




    @Override
    public void draw(Canvas canvas, Paint nodePaint) {

        canvas.drawOval(this, nodePaint);

    }





    @Override
    public void onClick(OnClickMapNodeHandler handler) {

    }




}
