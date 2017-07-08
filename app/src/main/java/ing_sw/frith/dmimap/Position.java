package ing_sw.frith.dmimap;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import static android.content.ContentValues.TAG;





public class Position implements View.OnClickListener {


    private static String QRCODE_REGEXP = "^[0-2]:[a-zA-Z]{2,20}$";


    private TextView pos;
    private Button scan;

    private NameList name_list;


    private int position;




    public Position(TextView pos, Button scan, NameList name_list) {

        this.pos = pos;
        this.scan = scan;

        this.name_list = name_list;

        position = -1;

        scan.setOnClickListener(this);

    }





    @Override
    public void onClick(View view) {

        String scanned = "0:Marcugini"; //=QRCODESCAN

        if(scanned.matches(QRCODE_REGEXP)) {

            int    type  = Character.getNumericValue(scanned.charAt(0));
            String input = scanned.substring(2);

            Log.d(TAG, "onClick: type: " + type + " input: " + input);

            if(name_list.matches(type, input)) {

                int node_id = name_list.getNodeID(type, input);

                if(node_id != -1) {

                    position = node_id;

                } else {

                    //TODO: Display error message
                    position = -1;
                }


            } else {

                //TODO: Display error message
                position = -1;

            }


        }

        Log.d(TAG, "onClick: position: " + position);


    }

}
