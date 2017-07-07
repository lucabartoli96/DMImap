package ing_sw.frith.dmimap.map;


public abstract class MapNodeName {

    private static int PROG_ID = 0;

    private int id;




    public MapNodeName() {

        this.id = PROG_ID;

        PROG_ID++;

    }


    public abstract int getType();


    public int getId() {

        return id;

    }

}
