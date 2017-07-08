package ing_sw.frith.dmimap;


import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.content.ContentValues.TAG;





public  class Destination implements RadioGroup.OnCheckedChangeListener, TextView.OnEditorActionListener/*, View.OnClickListener*/{


    private static String[] HINTS;


    {
        HINTS = new String[4];
        HINTS[0] = "\"cognome\"";
        HINTS[1] = "\"nome aula\"";
        HINTS[2] = "\"nome luogo\"";
        HINTS[3] = "imposta tipo ->";

    }


    private TextView des;
    private RadioGroup type;
    private InputMethodManager imm;

    private NameList name_list;

    private String des_string;
    private int type_id;


    private int destination;

    //private boolean focus = false;




    public Destination(TextView des, RadioGroup type, InputMethodManager imm, NameList name_list) {

        this.des  = des;
        this.type = type;
        this.imm  = imm;
        this.name_list = name_list;

        this.type_id = -1;
        this.des_string = null;
        this.destination = -1;

        //desFocus(focus);

        des.setHint(HINTS[3]);

        des.setOnEditorActionListener(this);
        //des.setOnClickListener(this);
        type.setOnCheckedChangeListener(this);

    }







    private void update() {


        if(des_string != null && type_id != -1 && name_list.matches(type_id, des_string)) {

            int node_id = name_list.getNodeID(type_id, des_string);

            if(node_id != -1) {

                destination = node_id;

            } else {

                //TODO: Display error message
                destination = -1;
            }


        } else {

            //TODO: Display error message
            destination = -1;

        }

        Log.d(TAG, "update:\n" + "destination: " + des_string + "\ntype: "  + type_id + "\ndestination: " + destination);

    }




    public int getNode() {

        return destination;

    }





    @Override
    public boolean onEditorAction(TextView textView, int key, KeyEvent keyEvent) {


        if(key == EditorInfo.IME_ACTION_DONE) {

            des_string = des.getText().toString();
            //desFocus(false);
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

        des.setText("");
        des.setHint(HINTS[type_id]);

        update();

    }




/*

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

*/

}

