package sdk.mobfox.com.mobfox_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.mobfox.sdk.interstitial.Interstitial;
import com.mobfox.sdk.interstitial.InterstitialListener;
import com.mobfox.sdk.networking.MobfoxRequestParams;

import sdk.mobfox.com.mobfox_app.barcode.BarcodeCaptureActivity;

/**
 * Created by shahafsh on 6/12/18.
 */

public class Tab4_Inter extends Activity implements AdapterView.OnItemSelectedListener{

    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static String invh = "267d72ac3f77a3f447b32cf7ebf20673";

    static long millisecs;
    // final DummyAdapter da = new DummyAdapter();
    public Interstitial interAd;
    public EditText invhText;
    public EditText floorText;
    public Button load;
    public Button qrcode;
    public TextView logText;
    public String server="";

    MobfoxRequestParams mfrp;


    float floor = -1;

    Context c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab4_inter);

//        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dummy_container);

        c = this;

        mfrp = new MobfoxRequestParams();


        Spinner serverSpinner = (Spinner) findViewById(R.id.server_spinner);

        logText     = (TextView) findViewById(R.id.logText);
        floorText   = (EditText) findViewById(R.id.floor_etext);
        invhText    = (EditText) findViewById(R.id.invhText);
        invhText.setText(invh);


        //final MobfoxInterstitial self = this;

        load = (Button) findViewById(R.id.load_btn);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invh = invhText.getText().toString();
                interAd = new Interstitial(c,invh);

                if (floorText.getText().length() > 0){
                    floor = Float.parseFloat(floorText.getText().toString());
                    if(floor > 0){
                        mfrp.setParam(MobfoxRequestParams.R_FLOOR,floor);
                        interAd.setRequestParams(mfrp);
                    }
                }
                if (!server.equals("")){
                    if (server.equals("http://nvirginia-my.mobfox.com")){
                        mfrp.setParam("debugResponseURL", server);
                        interAd.setRequestParams(mfrp);
                    }
                    if (server.equals("http://tokyo-my.mobfox.com")){
                        mfrp.setParam("debugResponseURL", server);
                        interAd.setRequestParams(mfrp);

                    }

                }

                interAd.setListener(new InterstitialListener() {
                    @Override
                    public void onInterstitialLoaded(Interstitial interstitial) {
                        Toast.makeText(c, "inter loaded", Toast.LENGTH_SHORT).show();
                        interAd.show();
                    }

                    @Override
                    public void onInterstitialFailed(String e) {
                        Toast.makeText(c, e, Toast.LENGTH_SHORT).show();
                        logText.setText(e);

                    }

                    @Override
                    public void onInterstitialClosed() {
                        Toast.makeText(c, "closed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onInterstitialClicked() {
                        Toast.makeText(c, "clicked", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onInterstitialShown() {
                        Toast.makeText(c, "shown", Toast.LENGTH_SHORT).show();
                        logText.setText("");
                    }

                    @Override
                    public void onInterstitialFinished() {
                        Toast.makeText(c, "finished", Toast.LENGTH_SHORT).show();
                    }
                });
                interAd.load();
            }
        });

        ArrayAdapter<CharSequence> serverSpinnerAdapter = ArrayAdapter.createFromResource(c,
                R.array.servers_array, android.R.layout.simple_spinner_item);
        serverSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serverSpinner.setAdapter(serverSpinnerAdapter);
        serverSpinner.setOnItemSelectedListener(this);

        qrcode = (Button) findViewById(R.id.qrcode);
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    invhText.setText(barcode.displayValue);
                }
            }
        }
        else super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerId = parent.getItemAtPosition(position).toString();
//        int parentId = parent.getId();

        switch (spinnerId) {
            case "North Virginia":
                //set server to north virginia
                server = "http://nvirginia-my.mobfox.com";
//                Toast.makeText(c, "North Virginia", Toast.LENGTH_SHORT).show();
                break;
            case "Tokyo":
                //set server to tokyo
                server = "http://tokyo-my.mobfox.com";

//                Toast.makeText(c, "Tokyo", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
