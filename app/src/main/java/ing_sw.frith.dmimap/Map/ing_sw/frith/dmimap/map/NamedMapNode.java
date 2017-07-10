package ing_sw.frith.dmimap.map;


import android.graphics.Canvas;
import android.graphics.Paint;

public class NamedMapNode extends MapNode {


    private static final int DEFAULT_D        = 20;

    private MapNodeName name;




    public NamedMapNode(int id, int x, int y, MapNodeName name) {

        super(id, x, y, DEFAULT_D);

        this.name = name;
    }


    
    public MapNodeName getName() {

        return name;

    }


    @Override
    public void draw(Canvas canvas, Paint nodePaint) {

        canvas.drawOval(this, nodePaint);

    }





    @Override
    public void onClick(OnClickMapNodeHandler handler) {

    }


}
