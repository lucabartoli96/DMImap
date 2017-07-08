package ing_sw.frith.dmimap.map;


import java.util.ArrayList;

public class OfficeNodeName extends MapNodeName {


    private static final int TYPE = 0;

    private ArrayList<String> owners;



    public OfficeNodeName(ArrayList<String> owners) {

        super();
        this.owners = owners;

    }




    public boolean matches(String input) {

        for (String owner : owners) {

            if(owner.equalsIgnoreCase(input))

                return true;

        }

        return false;

    }



    @Override
    public int getType() {

        return TYPE;

    }

}
