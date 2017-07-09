package ing_sw.frith.dmimap;


import java.util.ArrayList;
import ing_sw.frith.dmimap.map.MapNodeName;




public class NameList {


    private static String[] REGEXP;

    {
        REGEXP = new String[3];

        REGEXP[0] = "[a-zA-Z]{2,20}";
        REGEXP[1] = "[A-Z][0-9]";
        REGEXP[2] = "[a-zA-Z\\s]{2,20}";

    }

    private ArrayList<ArrayList<CoupleNameNodeID>> list;




    public NameList() {

        list  = new ArrayList<>();

        for(int i = 0; i < 3; i++) {

            list.add(new ArrayList<CoupleNameNodeID>());

        }

    }






    public void add(MapNodeName name, int node_id) {

        list.get(name.getType()).add(new CoupleNameNodeID(name, node_id));

    }



    public boolean matches(int type, String input) {

        return input.trim().matches(REGEXP[type]);

    }




    public int getNodeID(int type, String input) {

        input = input.trim();

        for (CoupleNameNodeID couple :list.get(type)) {


            if(couple.getName().matches(input)) {

                return couple.getNode_id();

            }

        }

        return -1;

    }




    private class CoupleNameNodeID {

        private final MapNodeName name;
        private final int node_id;

        public CoupleNameNodeID(MapNodeName name, int node_id) {

            this.name = name;
            this.node_id = node_id;

        }


        public MapNodeName getName() {

            return name;

        }


        public int getNode_id() {

            return node_id;

        }

    }

}
