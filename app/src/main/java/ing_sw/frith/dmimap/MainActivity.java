package ing_sw.frith.dmimap;

import android.content.Context;
import android.content.res.Resources;
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



        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            res = getResources();


            initUIObjects();
            initMapResources();
            initMap();
            initNameList();
            initGraph();

        }






    private void initMapResources() {

        String description = MapR.readDescriptor(this);
        MapR.createParser(description);
    }






    private void initUIObjects() {


        pos        = (TextView)       findViewById(R.id.pos);
        scan       = (Button)         findViewById(R.id.scan);


        position    = new Position(pos, scan);

        des        = (TextView)       findViewById(R.id.des);
        type       = (RadioGroup)     findViewById(R.id.type);

        destination = new Destination(des, type);



        map_window = (RelativeLayout) findViewById(R.id.map_window);
        start      = (Button)         findViewById(R.id.start);


        Log.d(TAG, "initUIObjects!");

    }





    private class Position implements View.OnClickListener {


        private TextView pos;
        private Button scan;



        public Position(TextView pos, Button scan) {

            this.pos = pos;
            this.scan = scan;

            scan.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

            //QRCodeScanning
            //FindName
            //FindNodeId
            //Filllabelpos

        }

    }








    private class Destination implements RadioGroup.OnCheckedChangeListener, TextView.OnEditorActionListener, View.OnClickListener {


        private TextView des;
        private RadioGroup type;
        private InputMethodManager imm;


        private String des_string;
        private int type_id;

        private boolean focus = false;

        public Destination(TextView des, RadioGroup type) {

            this.des = des;
            this.type = type;

            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            desFocus(focus);

            des.setOnEditorActionListener(this);
            des.setOnClickListener(this);
            type.setOnCheckedChangeListener(this);

        }





        private void update() {


            Log.d(TAG, "update:\n" + "destination: " + des_string + "\ntype: "  + type_id);

        }




        @Override
        public boolean onEditorAction(TextView textView, int key, KeyEvent keyEvent) {


            if(key == EditorInfo.IME_ACTION_DONE) {

                des_string = des.getText().toString();
                desFocus(false);
                update();


                return true;
            }


            return false;


        }






        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {


            switch (i) {

                case R.id.office:

                    type_id = 0;
                    break;

                case R.id.classroom:

                    type_id = 1;
                    break;

                case R.id.other:

                    type_id = 2;
                    break;

            }

            update();

        }


        @Override
        public void onClick(View view) {

            if(imm.isActive()) {

                des_string = des.getText().toString();
                update();

            }

            focus =  !focus;
            desFocus(focus);

        }



        private void desFocus(boolean b) {


            if(b) {

                des.setFocusableInTouchMode(true);
                des.requestFocus();
                focus = true;

            } else {

                des.clearFocus();
                des.setFocusable(false);
                focus = false;

            }

            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


        }

    }






    public void initGraph() {

        graph = MapR.getGraph();

    }






    private void initMap() {

        map = new Map(this, MapR.getFloorsNumber(), MapR.getFloorsImages(), MapR.getNodes(), MapR.getEdges());
        map_window.addView(map);

        //// TODO: SISTEMARE AGGIUNTA DELLA VIEW

        
        Log.d(TAG, "initMap!");

    }



    private void initNameList() {

        name_list = MapR.getNameList();

    }


}


