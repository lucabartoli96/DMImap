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


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder().append("ufficio: ");

        for(int i = 0; i < owners.size(); i++ ) {

            sb.append(owners.get(i));

            if(i != owners.size() - 1)

                sb.append(',');


        }

        return sb.toString();

    }

}
