package ing_sw.frith.dmimap;


import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ing_sw.frith.dmimap.map.Map;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


import static android.content.ContentValues.TAG;




public class MainActivity extends AppCompatActivity{




        private Graph graph;
        private ZXingScannerView scannerView;
        private boolean stopped;
        private Map map;
        private NameList name_list;




        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);



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








    private void initMap() {

        map = new Map(this, MapR.getFloorsNumber(), MapR.getFloorsImages(), MapR.getNodes(), MapR.getEdges());
        RelativeLayout map_window = (RelativeLayout) findViewById(R.id.map_window);
        map_window.addView(map);

    }







    private void initNameList() {

        name_list = MapR.getNameList();

    }





    public void initGraph() {

        graph = MapR.getGraph();

    }








    private void initUIObjects() {


        scannerView =new ZXingScannerView(this);
        scannerView.setVisibility(View.INVISIBLE);
        addContentView(scannerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        View     screen      =                  findViewById(R.id.screen);
        TextView pos         = (TextView)       findViewById(R.id.pos);
        Button   scan        = (Button)         findViewById(R.id.scan);



        Position position    = new Position(scannerView, screen, pos, scan, name_list);

        TextView   des        = (TextView)       findViewById(R.id.des);
        RadioGroup type       = (RadioGroup)     findViewById(R.id.type);

        Destination destination = new Destination(des, type, name_list);


        Button start      = (Button)         findViewById(R.id.start);


        new StartNavigation(start, position, destination, graph, map);

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


