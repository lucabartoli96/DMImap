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
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import ing_sw.frith.dmimap.map.Map;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.content.ContentValues.TAG;




public class MainActivity extends AppCompatActivity{



        //useful references
        Resources res;


        Graph graph;

        //UI components
        private ZXingScannerView scannerView;
        private boolean stopped;
        private View screen;
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


        scannerView =new ZXingScannerView(this);
        scannerView.setVisibility(View.INVISIBLE);
        addContentView(scannerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        screen      =                  findViewById(R.id.screen);
        pos         = (TextView)       findViewById(R.id.pos);
        scan        = (Button)         findViewById(R.id.scan);



        position    = new Position(scannerView, screen, pos, scan, name_list);

        des        = (TextView)       findViewById(R.id.des);
        type       = (RadioGroup)     findViewById(R.id.type);

        destination = new Destination(des, type, name_list);


        start      = (Button)         findViewById(R.id.start);


        startNavigation = new StartNavigation(start, position, destination, graph, map);


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

    @Override
    public void onResume() {
        super.onResume();

        if(stopped) {

            scannerView.startCamera();
            stopped = false;

        }



    }



    @Override
    protected void onPause() {
        super.onPause();

        if(scannerView.getVisibility() == View.VISIBLE) {

            scannerView.stopCamera();
            stopped = true;

        }


    }

}


