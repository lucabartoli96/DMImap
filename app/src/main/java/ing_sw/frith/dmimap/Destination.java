package ing_sw.frith.dmimap;


import android.graphics.Color;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.content.ContentValues.TAG;





public  class Destination implements RadioGroup.OnCheckedChangeListener{


    private static String[] HINTS;
    private static String[] ERR_MSG;
    private static int      DEFAULT_HINT_COLOR;


    {
        HINTS = new String[4];
        HINTS[0] = "cognome: \"Rossi\"";
        HINTS[1] = "nome aula: \"A0\"";
        HINTS[2] = "luogo: \"portineria\"";
        HINTS[3] = "imposta tipo ->";

        ERR_MSG = new String[4];

        ERR_MSG[0] = "Non trovato";
        ERR_MSG[1] = "Non permesso";
        ERR_MSG[2] = "Tipo non impostato";
        ERR_MSG[3] = "Non immesso";


    }


    private TextView des;
    private RadioGroup type;

    private NameList name_list;

    private String des_string;
    private int type_id;


    private int destination;

    private int err_code;



    public Destination(TextView des, RadioGroup type, NameList name_list) {

        this.des  = des;
        this.type = type;
        this.name_list = name_list;

        this.type_id = -1;
        this.des_string = null;
        this.destination = -1;


        DEFAULT_HINT_COLOR = des.getHintTextColors().getDefaultColor();

        des.setHint(HINTS[3]);

        type.setOnCheckedChangeListener(this);

    }






    public int getNode() {

        getFields();
        update();

        if(destination == -1)

            error();

        return destination;

    }



    private void getFields() {



        int i = type.getCheckedRadioButtonId();

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



        des_string = des.getText().toString();


    }




    private void update() {


        boolean type_setted    = type_id != -1;
        boolean string_setted  = des_string != null  &&  !des_string.equals("");
        boolean matches        = true;


        if(string_setted && type_setted) {

            matches = name_list.matches(type_id, des_string);

        }


        if(type_setted && string_setted && matches) {

            int node_id = name_list.getNodeID(type_id, des_string);

            if(node_id != -1) {

                destination = node_id;

            } else {

                err_code = 0;
                destination = -1;
            }


        } else {

            if(!matches) {

                err_code = 1;

            } else if(!type_setted) {

                err_code =  2;

            } else {

                err_code = 3;
            }

            destination = -1;

        }

        Log.d(TAG, "update:\n" + "destination: " + des_string + "\ntype: "  + type_id + "\ndestination: " + destination);

    }








    private void error() {

        des.setText("");
        des.setHintTextColor(Color.RED);
        des.setHint(ERR_MSG[err_code]);

    }



    private void un_error() {

        des.setHintTextColor(DEFAULT_HINT_COLOR);
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



        des.setText("");
        un_error();
        des.setHint(HINTS[type_id]);

    }



}

