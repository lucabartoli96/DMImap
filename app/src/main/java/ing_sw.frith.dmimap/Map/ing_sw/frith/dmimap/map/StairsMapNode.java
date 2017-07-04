package ing_sw.frith.dmimap.map;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;






public class StairsMapNode extends MapNode {

    private static final int DEFAULT_D        = 80;



    private boolean up;
    private final Bitmap image;






    public StairsMapNode(String id, int x, int y, boolean up, Bitmap image) {

        super(id, x, y, DEFAULT_D);

        this.up = up;
        this.image = image;

    }






    @Override
    public void draw(Canvas canvas, Paint nodePaint) {

        canvas.drawBitmap(image, null, this, nodePaint);

    }





    @Override
    public void onClick(OnClickMapNodeHandler handler) {

        handler.onClickedStairs(up);

    }


}