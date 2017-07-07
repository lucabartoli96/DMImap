package ing_sw.frith.dmimap;


import java.util.ArrayList;

import ing_sw.frith.dmimap.map.MapNodeName;




public class NameList {


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






    public int getNodeID(int type, String input) {

        return 0;

    }




    private class CoupleNameNodeID {

        public final MapNodeName name;
        public final int node_id;

        public CoupleNameNodeID(MapNodeName name, int node_id) {

            this.name = name;
            this.node_id = node_id;

        }
    }

}
