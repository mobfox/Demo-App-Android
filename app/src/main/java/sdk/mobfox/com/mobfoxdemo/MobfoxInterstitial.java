package sdk.mobfox.com.mobfoxdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.mobfox.sdk.interstitial.Interstitial;
import com.mobfox.sdk.interstitial.InterstitialListener;
import com.mobfox.sdk.networking.MobfoxRequestParams;

import sdk.mobfox.com.mobfoxdemo.barcode.BarcodeCaptureActivity;

public class MobfoxInterstitial extends Activity implements AdapterView.OnItemSelectedListener {

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

    float floor = -1;

    Context c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_ad);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(144,202,249));


        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.dummy_container);

        c = getApplicationContext();

        Spinner serverSpinner = (Spinner) findViewById(R.id.server_spinner);

        logText     = (TextView) findViewById(R.id.logText);
        floorText   = (EditText) findViewById(R.id.floor_etext);
        invhText    = (EditText) findViewById(R.id.invhText);
        invhText.setText(invh);


        final MobfoxInterstitial self = this;

        load = (Button) findViewById(R.id.loadBtn);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invh = invhText.getText().toString();
                interAd = new Interstitial(self,invh);

                if (floorText.getText().length() > 0){
                    floor = Float.parseFloat(floorText.getText().toString());
                    if(floor > 0){
                        MobfoxRequestParams mfrp = new MobfoxRequestParams();
                        mfrp.setParam(MobfoxRequestParams.R_FLOOR,floor);
                        interAd.setRequestParams(mfrp);
                    }
                }

                interAd.setListener(new InterstitialListener() {
                    @Override
                    public void onInterstitialLoaded(Interstitial interstitial) {
                        Toast.makeText(self, "inter loaded", Toast.LENGTH_SHORT).show();
                        interAd.show();
                    }

                    @Override
                    public void onInterstitialFailed(String e) {
                        Toast.makeText(self, e, Toast.LENGTH_SHORT).show();
                        logText.setText(e);

                    }

                    @Override
                    public void onInterstitialClosed() {
                        Toast.makeText(self, "closed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onInterstitialClicked() {
                        Toast.makeText(self, "clicked", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onInterstitialShown() {
                        Toast.makeText(self, "shown", Toast.LENGTH_SHORT).show();
                        logText.setText("");
                    }

                    @Override
                    public void onInterstitialFinished() {
                        Toast.makeText(self, "finished", Toast.LENGTH_SHORT).show();
                    }
                });
                interAd.load();
            }
        });

        ArrayAdapter<CharSequence> serverSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.servers_array, android.R.layout.simple_spinner_item);
        serverSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serverSpinner.setAdapter(serverSpinnerAdapter);
        serverSpinner.setOnItemSelectedListener(this);

        qrcode = (Button) findViewById(R.id.qrcode);
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });
///
///        floorBtn = (Button) findViewById(R.id.floor);
///        floorBtn.setOnClickListener(new View.OnClickListener() {
///            @Override
///            public void onClick(View view) {
///                floor = 4.0f;
///           }
///       });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    // mResultTextView.setText(barcode.displayValue);
                    //invh = barcode.displayValue;
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
            case "NorthVir":
                //set server to north virginia
                Toast.makeText(c, "North Virginia", Toast.LENGTH_SHORT).show();
                break;
            case "Tokyo":
                //set server to tokyo
                Toast.makeText(c, "Tokyo", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
