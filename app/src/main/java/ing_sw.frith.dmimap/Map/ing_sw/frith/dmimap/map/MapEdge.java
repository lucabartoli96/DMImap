package ing_sw.frith.dmimap.map;


import android.graphics.Color;

public class MapEdge {



    private MapNode node_1;
    private MapNode node_2;

    private final boolean visible;



    public MapEdge(MapNode node_1, MapNode node_2, boolean visible) {

        this.node_1 = node_1;
        this.node_2 = node_2;

        this.visible = visible;
    }



    public float startX() {

        return node_1.centerX();
    }





    public float startY() {

        return node_1.centerY();
    }






    public float stopX() {

        return node_2.centerX();
    }







    public float stopY() {

        return node_2.centerY();
    }




    public int getColor() {

        if(node_1.isSelected() && node_2.isSelected()) {

            return Color.RED;

        } else {

            return Color.BLACK;
        }

    }


    public boolean isVisible() {

        return visible;

    }

    @Override
    public String toString() {

        return "{" +
                "node_1=" + node_1 +
                ", node_2=" + node_2 +
                '}';
    }


}
