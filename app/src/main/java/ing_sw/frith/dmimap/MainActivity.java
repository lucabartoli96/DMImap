package ing_sw.frith.dmimap;

import android.content.Context;
import android.content.res.Resources;
import android.inputmethodservice.InputMethodService;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ing_sw.frith.dmimap.map.Map;
import static android.content.ContentValues.TAG;




public class MainActivity extends AppCompatActivity {


        //useful references
        Resources res;


        Graph graph;

        //UI components
        private TextView pos, des;
        private RelativeLayout map_window;
        private RadioGroup type;
        private Button scan, start;
        private Map map;

        NameList name_list;


        private Position position;
        private Destination destination;
        private StartNavigation startNavigation;



        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            res = getResources();


            initMapResources();
            initMap();
            initNameList();
            initGraph();
            initUIObjects();

        }






    private void initMapResources() {

        String description = MapR.readDescriptor(this);
        MapR.createParser(description);
    }






    private void initUIObjects() {


        pos        = (TextView)       findViewById(R.id.pos);
        scan       = (Button)         findViewById(R.id.scan);


        position    = new Position(pos, scan, name_list);

        des        = (TextView)       findViewById(R.id.des);
        type       = (RadioGroup)     findViewById(R.id.type);

        destination = new Destination(des, type, name_list);


        start      = (Button)         findViewById(R.id.start);


        StartNavigation startNavigation = new StartNavigation(start, position, destination, graph, map);


        Log.d(TAG, "initUIObjects!");

    }






    public void initGraph() {

        graph = MapR.getGraph();

    }






    private void initMap() {

        map = new Map(this, MapR.getFloorsNumber(), MapR.getFloorsImages(), MapR.getNodes(), MapR.getEdges());
        map_window = (RelativeLayout) findViewById(R.id.map_window);
        map_window.addView(map);

        
        Log.d(TAG, "initMap!");

    }



    private void initNameList() {

        name_list = MapR.getNameList();

    }


/*

    @Override
    protected void onPause() {

        super.onPause();

        Log.d(TAG, "onDestroy: 1");

        if(imm.isActive()) {

            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            Log.d(TAG, "onDestroy: 2");
        }


    }

*/

}


