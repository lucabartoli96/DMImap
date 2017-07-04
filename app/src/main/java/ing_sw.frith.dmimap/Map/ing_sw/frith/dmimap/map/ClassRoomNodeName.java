package ing_sw.frith.dmimap.map;



public class ClassRoomNodeName extends MapNodeName {

    private static final int TYPE = 1;
    private String name;



    public ClassRoomNodeName(String name) {

        this.name = name;

    }



    @Override
    public int getType() {

        return TYPE;

    }




    @Override
    public int hashCode() {

        return name.hashCode();
    }

}