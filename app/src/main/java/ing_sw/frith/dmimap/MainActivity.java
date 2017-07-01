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


        //UI components
        private TextView pos, des;
        private RelativeLayout map_window;
        private Map      map;

        //Files
        Parser parser;





        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            res = getResources();

            parser = createParser();

            initUIObjects();

            initMap();



        }







        private Parser createParser() {

            InputStream stream;
            String string = "";

            try {

                stream = res.getAssets().open("map_description.json");
                string = new Scanner(stream).useDelimiter("\\Z").next();

            }catch(IOException e) {

                Log.d(TAG, "onCreate: Error while opening map_description.json\n" + e.toString());
                System.exit(0);
            }

            Log.d(TAG, "createParser: Parser created!");
            
            return new Parser(string);

        }






        private void initUIObjects() {

            pos        = (TextView)       findViewById(R.id.pos);
            des        = (TextView)       findViewById(R.id.des);
            map_window = (RelativeLayout) findViewById(R.id.map_window);

            Log.d(TAG, "initUIObjects!");

        }








        private void initMap() {

            map = new Map(this, parser.getFloorsNumber(), parser.getNodes());
            map_window.addView(map);

            Log.d(TAG, "initMap!");

        }



}


