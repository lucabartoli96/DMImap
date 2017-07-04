package ing_sw.frith.dmimap.map;


import java.util.ArrayList;

public class OfficeNodeName extends MapNodeName {


    private static final int TYPE = 0;

    private ArrayList<String> owners;

    public OfficeNodeName(ArrayList<String> owners) {

        this.owners = owners;

    }

    @Override
    public int getType() {

        return TYPE;

    }

    @Override
    public int hashCode() {

        return owners.hashCode();

    }
}
