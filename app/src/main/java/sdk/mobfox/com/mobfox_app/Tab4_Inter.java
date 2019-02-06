package sdk.mobfox.com.mobfox_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.mobfox.sdk.interstitial.Interstitial;
import com.mobfox.sdk.interstitial.InterstitialListener;
import com.mobfox.sdk.networking.MobfoxRequestParams;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;

import sdk.mobfox.com.mobfox_app.barcode.BarcodeCaptureActivity;

/**
 * Created by shahafsh on 6/12/18.
 */

public class Tab4_Inter extends Activity implements AdapterView.OnItemSelectedListener{

    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static String invh = "267d72ac3f77a3f447b32cf7ebf20673";

    public Interstitial interAd;
    public EditText invhText;
    public EditText floorText;
    public Button load;
    public Button qrcode;
    public TextView logText;
    public String server="";
    public Switch videoSwitch;
    public ProgressBar progressBar;

    MobfoxRequestParams mfrp;


    float floor = -1;

    Context c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab4_inter);

        if (!MoPub.isSdkInitialized()){
            SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder("4ad212b1d0104c5998b288e7a8e35967")
                    .build();
            MoPub.initializeSdk(this,sdkConfiguration, initSdkListener());
        }

        c = this;

        mfrp = new MobfoxRequestParams();

        logText     = findViewById(R.id.logText);
        floorText   = findViewById(R.id.floor_etext);
        invhText    = findViewById(R.id.invhText);
        load        = findViewById(R.id.load_native_btn);
        videoSwitch = findViewById(R.id.video_switch);
        progressBar = findViewById(R.id.mfBannerInterPB);

        progressBar.setVisibility(View.GONE);

        Spinner mediationSpinner = findViewById(R.id.mediation_spinner);


        ArrayAdapter<CharSequence> mediationSpinnerAdapter = ArrayAdapter.createFromResource(c,
                R.array.mediation_array, android.R.layout.simple_spinner_item);

        mediationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mediationSpinner.setAdapter(mediationSpinnerAdapter);
        mediationSpinner.setOnItemSelectedListener(this);

        invhText.setText(invh);


        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                invh = invhText.getText().toString();

                //to display vast from url - Interstitial must be initialized using video hash
                if (invh.contains("http") && videoSwitch.isChecked()) {
                    interAd = new Interstitial(c, "80187188f458cfde788d961b6882fd53");

                } else {
                    interAd = new Interstitial(c, invh);

                }

                interAd.setListener(new InterstitialListener() {
                    @Override
                    public void onInterstitialLoaded(Interstitial interstitial) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(c, "inter loaded", Toast.LENGTH_SHORT).show();
                        interAd.show();
                    }

                    @Override
                    public void onInterstitialFailed(String e) {
                        progressBar.setVisibility(View.GONE);
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

                if (floorText.getText().length() > 0){
                    floor = Float.parseFloat(floorText.getText().toString());
                    if(floor > 0){
                        mfrp.setParam(MobfoxRequestParams.R_FLOOR,floor);
                        interAd.setRequestParams(mfrp);
                    }
                }

                if (invh.contains("http")) {
                    if (videoSwitch.isChecked()) {
                        mfrp = new MobfoxRequestParams();
                        mfrp.setParam(MobfoxRequestParams.DEBUG_VIDEO_REQUEST_URL,invh);
                        mfrp.setParam(MobfoxRequestParams.DEBUG_WATERFALL, "[\"video\"]");
                        interAd.setRequestParams(mfrp);
                    } else {
                        mfrp = new MobfoxRequestParams();
                        mfrp.setParam(MobfoxRequestParams.DEBUG_REQUEST_URL, invh);
                        mfrp.setParam(MobfoxRequestParams.DEBUG_WATERFALL,  "[\"banner\"]");
                        interAd.setRequestParams(mfrp);
                    }

                }




                interAd.load();
            }
        });

        videoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (videoSwitch.isChecked()){
                    if (!invhText.getText().toString().contains("http")){
                        invhText.setText("80187188f458cfde788d961b6882fd53");
                    }
                }else {
                    if (!invhText.getText().toString().contains("http")){
                        invhText.setText("267d72ac3f77a3f447b32cf7ebf20673");
                    }
                }
            }
        });


        qrcode = (Button) findViewById(R.id.qrcode);
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });
    }



    private SdkInitializationListener initSdkListener() {
        return  new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
                // MoPub SDK initialized
                Log.d("mopub", "init");
            }
        };
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

        if (((TextView) parent.getChildAt(0))!=null){
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
        }

        String spinnerId = parent.getItemAtPosition(position).toString();
//        int parentId = parent.getId();

        switch (spinnerId) {
            case "Default":
                server = "";
                break;
            case "North Virginia":
                //set server to north virginia
                server = "http://nvirginia-my.mobfox.com";
                break;
            case "Tokyo":
                //set server to tokyo
                server = "http://tokyo-my.mobfox.com";
                break;
            case "None":
                invhText.setText(R.string.mfInterHash);
                break;
            case "AdMob":
                invhText.setText(R.string.mfamInterHash);
                break;
            case "MoPub":
                invhText.setText(R.string.mfmpInterHash);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
