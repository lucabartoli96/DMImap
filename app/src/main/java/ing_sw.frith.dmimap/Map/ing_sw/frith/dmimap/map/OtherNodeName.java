package ing_sw.frith.dmimap.map;


import java.util.ArrayList;

public class OtherNodeName extends MapNodeName {


    private static final int TYPE = 2;

    private String name;

    public OtherNodeName(String name) {

        super();
        this.name = name;

    }


    @Override
    public boolean matches(String input) {

        return name.equalsIgnoreCase(input);

    }



    @Override
    public int getType() {

        return TYPE;

    }


    @Override
    public String toString() {

        return "altro: " + name;

    }

}

