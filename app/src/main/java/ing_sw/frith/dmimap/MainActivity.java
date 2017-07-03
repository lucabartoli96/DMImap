package ing_sw.frith.dmimap;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static android.content.ContentValues.TAG;




public class MainActivity extends AppCompatActivity {


        //useful references
        Resources res;


        Graph graph;

        //UI components
        private TextView pos, des;
        private RelativeLayout map_window;
        private Map      map;





        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            res = getResources();


            String description = MapR.readDescriptor(res);
            MapR.createParser(description);


            initUIObjects();

            initGraph();

            initMap();



        }






        private void initUIObjects() {

            pos        = (TextView)       findViewById(R.id.pos);
            des        = (TextView)       findViewById(R.id.des);
            map_window = (RelativeLayout) findViewById(R.id.map_window);

            Log.d(TAG, "initUIObjects!");

        }



        public void initGraph() {

            graph = MapR.getGraph();

        }






        private void initMap() {

            map = new Map(this, MapR.getFloorsNumber(), MapR.getNodes(), MapR.getEdges());
            map_window.addView(map);

            Log.d(TAG, "initMap!");

        }



}


