package ing_sw.frith.dmimap;


import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.content.ContentValues.TAG;





public class Position implements View.OnClickListener, ZXingScannerView.ResultHandler {




    private static String QRCODE_REGEXP = "^[0-2]:[a-zA-Z]{2,20}$";
    private static String[] ERR_MSG;
    private static int DFLT_TEXT_COLOR = Color.BLACK;
    private static int ERR_TEXT_COLOR  = Color.RED;


    {

        ERR_MSG = new String[4];

        ERR_MSG[0] = "Non trovato";
        ERR_MSG[1] = "Nome non permesso";
        ERR_MSG[2] = "QRCode non permesso";
        ERR_MSG[3] = "Non immesso";
    }



    private ZXingScannerView scannerView;
    private View screen;
    private TextView pos;
    private Button scan;

    private NameList name_list;


    private int position;






    public Position(ZXingScannerView scannerView, View screen, TextView pos, Button scan, NameList name_list) {



        this.scannerView = scannerView;
        this.screen      = screen;
        this.pos         = pos;
        this.scan        = scan;

        this.name_list = name_list;

        scannerView.setResultHandler(this);

        pos.setTextColor(DFLT_TEXT_COLOR);

        position = -1;

        scan.setOnClickListener(this);

    }









    private void switchView() {

        if(screen.getVisibility() == View.VISIBLE) {

            Log.d(TAG, "switchView: screen visible");
            screen.setVisibility(View.INVISIBLE);
            scannerView.setVisibility(View.VISIBLE);

        } else {

            Log.d(TAG, "switchView: screen invisible");
            scannerView.setVisibility(View.INVISIBLE);
            screen.setVisibility(View.VISIBLE);

        }

    }






    @Override
    public void onClick(View view) {

        unerror();
        switchView();
        scannerView.startCamera();

    }







    @Override
    public void handleResult(Result result) {


        scannerView.resumeCameraPreview(this);
        scannerView.stopCamera();
        String scanned = result.getText();



        if(scanned.matches(QRCODE_REGEXP)) {

            int    type  = Character.getNumericValue(scanned.charAt(0));
            String input = scanned.substring(2);

            Log.d(TAG, "handleResult: type: " + type + " input: " + input);

            if(name_list.matches(type, input)) {

                int node_id = name_list.getNodeID(type, input);

                if(node_id != -1) {

                    position = node_id;

                } else {

                    error(0);
                }


            } else {

                error(1);

            }


        } else {

            error(2);

        }


        switchView();


        Log.d(TAG, "result: " + scanned + "position: " + position);

    }



    private void error(int err_code) {

        position = -1;
        pos.setTextColor(ERR_TEXT_COLOR);
        pos.setText(ERR_MSG[err_code]);

    }


    private void unerror() {

        pos.setTextColor(DFLT_TEXT_COLOR);
        pos.setText("Specifica posizione");
    }



    public int getNode() {

        if(position == -1)

            error(3);

        return position;

    }



}
