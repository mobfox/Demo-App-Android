package sdk.mobfox.com.mobfox_app;

/**
 * Created by shahafsh on 6/12/18.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.mobfox.sdk.banner.Banner;
import com.mobfox.sdk.networking.MobfoxRequestParams;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;

import sdk.mobfox.com.mobfox_app.barcode.BarcodeCaptureActivity;


public class Tab1_320x50 extends Activity implements AdapterView.OnItemSelectedListener {

    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static String invh = "fe96717d9875b9da4339ea5367eff1ec";

    public TextView logText;
    public EditText invhText, floorText;
    public Button qrcode, loadBtn;
    public Context c;
    public MobfoxRequestParams requestParams;

    public ProgressBar progressBar;

    float floor = -1;
    public String server = "";

    public Banner banner;
    public LinearLayout view;


    private SdkInitializationListener initSdkListener() {
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
                // MoPub SDK initialized
                Log.d("mopub", "init");
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1_320x50);
        c = getApplicationContext();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPreferences !=null){
            String str = sharedPreferences.getString("domain","");
            //Toast.makeText(c,str,Toast.LENGTH_SHORT).show();
        }



        if (!MoPub.isSdkInitialized()) {
            SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder("4ad212b1d0104c5998b288e7a8e35967")
                    .build();
            MoPub.initializeSdk(this, sdkConfiguration, initSdkListener());
        }

        requestParams = new MobfoxRequestParams();

        floorText     = findViewById(R.id.floor_etext);
        logText       = findViewById(R.id.logText);
        invhText      = findViewById(R.id.invhText);
        loadBtn       = findViewById(R.id.load_native_btn);
        qrcode        = findViewById(R.id.qrcode);
        view          = findViewById(R.id.banner_container);
        progressBar   = findViewById(R.id.mfBannerProgressBar);

        progressBar.setVisibility(View.GONE);


        Spinner mediationSpinner = findViewById(R.id.mediation_spinner);

        ArrayAdapter<CharSequence> mediationSpinnerAdapter = ArrayAdapter.createFromResource(c,
                R.array.mediation_array, android.R.layout.simple_spinner_item);

        mediationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mediationSpinner.setAdapter(mediationSpinnerAdapter);
        mediationSpinner.setOnItemSelectedListener(this);


        invhText.setText(invh);


        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });


        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBanner();
            }
        });
    }


    public void loadBanner() {

        invh = invhText.getText().toString();
        view.removeAllViews();
        logText.setText("");
        progressBar.setVisibility(View.VISIBLE);

        banner = new Banner(c, 320, 50, invh, new Banner.Listener() {
            @Override
            public void onBannerError(Banner banner, Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(c, e.toString(), Toast.LENGTH_SHORT).show();
                logText.setText(e.toString());
            }

            @Override
            public void onBannerLoaded(Banner banner) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(c, "MobFox Banner loaded", Toast.LENGTH_SHORT).show();
                view.addView(banner);
            }

            @Override
            public void onBannerClosed(Banner banner) {
                Toast.makeText(c, "MobFox Banner closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerFinished() {
                Toast.makeText(c, "MobFox Banner finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerClicked(Banner banner) {
                Toast.makeText(c, "MobFox Banner clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoFill(Banner banner) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(c, "MobFox Banner - no fill", Toast.LENGTH_SHORT).show();

            }
        });

        if (floorText.getText().length() > 0) {
            floor = Float.parseFloat(floorText.getText().toString());
            if (floor > 0) {
                requestParams.setParam(MobfoxRequestParams.R_FLOOR, floor);
                banner.addParams(requestParams);
            }
        }

        if (invh.contains("http")){
            requestParams.setParam(MobfoxRequestParams.DEBUG_REQUEST_URL,invh);
            banner.addParams(requestParams);
        }


        banner.load();


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
        } else super.onActivityResult(requestCode, resultCode, data);
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
                invhText.setText(R.string.mfbannerHash);
                break;
            case "AdMob":
                invhText.setText(R.string.mfamHash);
                break;
            case "MoPub":
                invhText.setText(R.string.mfmpHash);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
